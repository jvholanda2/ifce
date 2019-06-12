#include "SanUSB48.h" //Exemplo simples de semáforo e operação de dois displays de 7 segmentos multiplexados
#include <osa.h> //Vídeo-aula: https://www.youtube.com/watch?v=R8DqMxU_KK4 e em https://www.youtube.com/watch?v=s6BG8ZN0aDk
//Evitar uso de outros laços dentro das tasks (como for, do - while, etc.!)

unsigned char set_seg[10] = {0x40,0x79,0x24,0x30,0x19,0x12,0x02,0x78,0x00,0x10};
unsigned char set_seg_2[10] = {0x10, 0x00, 0x78, 0x02, 0x12, 0x19, 0x30, 0x24, 0x79, 0x40};
unsigned int i = 0, j = 0, flag = 0;

#pragma interrupt interrupcao 
void interrupcao(){

    if (PIR1bits.TMR1IF)
    {
        PIR1bits.TMR1IF = 0;
        TMR1H           = 0xD8;     
        TMR1L           = 0xF0;
        OS_Timer(); //estoura a cada 10.000us (para clock de 4MHz)
    }
}

void PIC_Init(void)
{
    LATB            = 0x00;
    TRISB           = 0x00;

    T1CON           = 0x80;     // modo 16 bits
    TMR1H           = 0xD8;     // 1ms
    TMR1L           = 0xF0;

    INTCON = 0;
    INTCONbits.PEIE = 1;
    PIR1bits.TMR1IF = 0;        // Flag interrupcao Timer1
    PIE1bits.TMR1IE = 1;        // Habilita interrupcao Timer1
    T1CONbits.TMR1ON= 1;        // Liga Timer1
}

void Task_1(void)
{
    while(1)
    {
        if (flag == 0){
            nivel_alto(pin_c0);
            OS_Delay(5000);
            nivel_baixo(pin_c0);
            nivel_alto(pin_c1);
            OS_Delay(5000);
            nivel_baixo(pin_c1);
            
            flag = 1;
        }else{
            nivel_alto(pin_c2);
            OS_Delay(1000);
//            nivel_baixo(pin_c2);
          //  OS_Delay(1000);
            
        }
    }
}

void Task_2(void)
{
    while(1){
        ++i;
        if(i>99){
            i = 0;
            flag = 1;
        }
        
        OS_Delay(100);
    }
}

void Task_3(void)
{
    while(1)
    {
        nivel_baixo(pin_a1);
        nivel_alto(pin_a0);
        PORTB = set_seg_2[i/10];
        OS_Delay(5);
        nivel_alto(pin_a1);
        nivel_baixo(pin_a0);
        PORTB = set_seg_2[i%10];
        OS_Delay(5);
    }
}
/* // OSAcf.h configurado com 4 tasks
void Task_4(void)
{
    while(1)
    {
        OS_Delay(400);
        LATBbits.LATB4^=1;
    }
} */

void main(void)
{
    clock_int_48MHz();

    PIC_Init();                     // Configurações gerais do PIC
   
    OS_Init();
    TRISB=0b00000000;
    TRISA=0b00000000;

    OS_Task_Create(1,Task_1);       // Criando uma tarefa,  prioridade 1
    OS_Task_Create(2,Task_2);       // Criando uma tarefa,  prioridade 2
    OS_Task_Create(3,Task_3);       // Criando uma tarefa,  prioridade 3
    //OS_Task_Create(4,Task_4);       // Criando uma tarefa,  prioridade 4

    OS_EI();                        // Habilita interrupcoes
    OS_Run();                       // Executa o RTOS
}



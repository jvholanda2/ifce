#include "SanUSB48.h" //Exemplo com 4 tarefas (task) concorrentes (paralelas)
#include <osa.h> //Vídeo-aula: http://youtu.be/PjZ1JIqNcd0 e https://www.youtube.com/watch?v=s6BG8ZN0aDk

unsigned char set_seg[10] = {0x40,0x79,0x24,0x30,0x19,0x12,0x02,0x78,0x00,};
unsigned char set_seg_2[10] = {0x10, 0x00, 0x78, 0x02, 0x12, 0x19, 0x30, 0x24, 0x79, 0x40};
unsigned int i = 0, j = 0, flag = 0;

#pragma interrupt interrupcao 
void interrupcao(){

    if (PIR1bits.TMR1IF)
    {
        PIR1bits.TMR1IF = 0;
        TMR1H           = 0xD8;     
        TMR1L           = 0xF0;
        OS_Timer();
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
        if ((PORTEbits.RE3==1)&&(PORTCbits.RC0==0)){
            nivel_alto(pin_c2);
        }
        else if(PORTEbits.RE3==0) {
            nivel_baixo(pin_c2);
            nivel_alto(pin_c1);
            OS_Delay(2000);
            nivel_baixo(pin_c1);
            nivel_alto(pin_c0);
        }
        OS_Delay(100);
    }
}

void Task_2(void)
{
    while(1){
        if (PORTCbits.RC0==1){
            ++i;
            if(i>99){
                i = 0;
                nivel_baixo(pin_c0);
                flag = 1;
            }
        }
        OS_Delay(100);
    }
}

void Task_3(void)
{
    while(1)
    {
        if (PORTCbits.RC0==1){
            nivel_baixo(pin_a1);
            nivel_alto(pin_a0);
            PORTB = set_seg_2[i/10];
            OS_Delay(5);
            nivel_alto(pin_a1);
            nivel_baixo(pin_a0);
            PORTB = set_seg_2[i%10];
            OS_Delay(5);
        }
        else{
            OS_Delay(100);
        }
    }
}
/* // OSAcf.h configurado com 3 tasks
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

    OS_EI();                        // Habilita interrupcpes
    OS_Run();                       // Executa o RTOS
}



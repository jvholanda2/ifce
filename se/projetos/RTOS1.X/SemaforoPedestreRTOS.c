#include "SanUSB48.h" //Exemplo simples de semáforo com sinal para pedestre
#include <osa.h> //Vídeo-aula: https://www.youtube.com/watch?v=MllQSaisF64 e em https://www.youtube.com/watch?v=s6BG8ZN0aDk
//Evitar uso de outros laços dentro das tasks (como for, do - while, etc.!)

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

int flag = 0;
void Task_Traffic_Light(void){
    while(1){
        if ((PORTBbits.RB4==0)&&(PORTBbits.RB6==0)){
            nivel_alto(pin_b5);
           }
        OS_Delay(100);
    }
}

void Task_Motion_Sensor(void){
    while(1){
        if((PORTAbits.RA0==1)&&(PORTBbits.RB4==0)){
            nivel_baixo(pin_b5);
            nivel_alto(pin_b6);
            OS_Delay(2000);
            nivel_alto(pin_b4);
            nivel_baixo(pin_b6);
            OS_Delay(3000);
            flag = 1;
            OS_Delay(2000);
            flag = 0;
            nivel_baixo(pin_b4);
            nivel_alto(pin_b5);
            OS_Delay(3000);
        }
        OS_Delay(100);
    }
}

void Task_Red_Led_Pedestrian(void){
    while(1){
        if((PORTBbits.RB4 == 1)&&(flag == 0)){
            nivel_alto(pin_b7);
            OS_Delay(100);
        }
        else if ((flag == 1)||(PORTBbits.RB6 == 1)){
            LATBbits.LATB7^=1;
            OS_Delay(200);
        }
        else{
            nivel_baixo(pin_b7);
            OS_Delay(100);
        }

        
    }
}

void main(void){
    clock_int_48MHz();
    PIC_Init();                     // Configurações gerais do PIC
    OS_Init();                      

    OS_Task_Create(2,Task_Traffic_Light);       // Criando uma tarefa,  prioridade 2
    OS_Task_Create(1,Task_Motion_Sensor);       // Criando uma tarefa,  prioridade 1
    OS_Task_Create(1,Task_Red_Led_Pedestrian);       // Criando uma tarefa,  prioridade 1

    OS_EI();                        // Habilita interrupcoes
    OS_Run();                       // Executa o RTOS
}



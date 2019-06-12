#include "SanUSB48.h" //Exemplo simples de semáforo com sinal sonoro
#include <osa.h> //Vídeo-aula: https://www.youtube.com/watch?v=UJkGRgsEmFA e em https://www.youtube.com/watch?v=s6BG8ZN0aDk
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
void Task_Verde(void){
    while(1){
       /* LATAbits.LATA0^=1;
        LATAbits.LATA1^=1;
        LATAbits.LATA2^=1;*/
        if ((PORTAbits.RA0==0)&&(PORTAbits.RA1==0)){
            nivel_alto(pin_a2);
            nivel_baixo(pin_a3);
           }
        OS_Delay(100);
    }
}

void Task_Botao(void){
    while(1){
        if((PORTEbits.RE3==0)&&(PORTAbits.RA0==0)){
            nivel_baixo(pin_a2);
            nivel_alto(pin_a1);
            OS_Delay(2000);
            nivel_alto(pin_a0);
            nivel_baixo(pin_a1);
            OS_Delay(7000);
            flag = 1;
            OS_Delay(5000);
            flag = 2;
            OS_Delay(3000);
            nivel_baixo(pin_a0);
            nivel_alto(pin_a2);
            flag = 0;
            OS_Delay(3000);
        }
        OS_Delay(100);
    }
}

void Task_Sinalizador_Sonoro(void){
    while(1){
        if((PORTAbits.RA0 == 1)&&(flag == 0)){
            LATAbits.LATA3^=1;
            OS_Delay(500);
        }
        else if ((flag == 1)){
            LATAbits.LATA3^=1;
            OS_Delay(300);
        }
        else if ((flag == 2)){
            LATAbits.LATA3^=1;
            OS_Delay(100);
        }
        else {

            nivel_baixo(pin_a3);
            OS_Delay(100);
        }
    }
}

void main(void){
    clock_int_48MHz();
    PIC_Init();                     // Configurações gerais do PIC
    OS_Init();                      

    OS_Task_Create(1,Task_Verde);       // Criando uma tarefa,  prioridade 2
    OS_Task_Create(1,Task_Botao);       // Criando uma tarefa,  prioridade 1
    OS_Task_Create(1,Task_Sinalizador_Sonoro);       // Criando uma tarefa,  prioridade 1

    OS_EI();                        // Habilita interrupcoes
    OS_Run();                       // Executa o RTOS
}



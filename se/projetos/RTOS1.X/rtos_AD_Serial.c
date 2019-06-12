#include "SanUSB48.h" //Vídeo: https://www.youtube.com/watch?v=c2WLafjximM
#include <osa.h>

#pragma interrupt interrupcao

long int valor;
unsigned char comando;
int speed = 500;

void interrupcao() {

    if (PIR1bits.TMR1IF) {
        PIR1bits.TMR1IF = 0;
        TMR1H = 0xD8;
        TMR1L = 0xF0;
        OS_Timer();
    }
}

void PIC_Init(void) {
    LATB = 0x00;
    TRISB = 0x00;

    T1CON = 0x80; // modo 16 bits
    TMR1H = 0xD8; // 1ms
    TMR1L = 0xF0;

    INTCON = 0;
    INTCONbits.PEIE = 1;
    PIR1bits.TMR1IF = 0; // Flag interrupcao Timer1
    PIE1bits.TMR1IE = 1; // Habilita interrupcao Timer1
    T1CONbits.TMR1ON = 1; // Liga Timer1
}

void Task_1(void) {
    while (1) { //ANALÓGICO DIGITAL(10 bits)

        valor = le_AD10bits(0);
        //sendnum(valor);
        printf("%ld\n\r",valor);
        if (valor>=0 && valor<=300){
            OS_Delay(speed);
            inverte_saida(pin_b5);
            nivel_baixo(pin_b6);
        }else if(valor>300 && valor<=800){
            OS_Delay(speed);
            inverte_saida(pin_b6);
            nivel_baixo(pin_b5);
        }else if(valor>800){
            OS_Delay(speed);
            inverte_saida(pin_b6);
            inverte_saida(pin_b5);
        }
    }

}


void Task_2(void) {
   while (1) {
        if (serial_interrompeu)  {
     serial_interrompeu=0;
     comando = le_serial();

         switch (comando){
             case 'a':
                speed = 600;
             break;
             case 'b':
                speed = 300;
             break;
             case 'c':
                speed = 100;
             break;

         }
   }
        OS_Delay(100);
    }
}


void Task_3(void) {
    while (1) {
        inverte_saida(pin_b7);
        OS_Delay(500);
    }
}

void main(void) {
    clock_int_48MHz();

    taxa_serial(19200);
    habilita_canal_AD(AN0);


    PIC_Init(); // Configurações gerais do PIC

    OS_Init();
    //TRISB = 0b00000000;
    //TRISA = 0b00000000;

    OS_Task_Create(1, Task_1); // Criando uma tarefa,  prioridade 1
    OS_Task_Create(2, Task_2); // Criando uma tarefa,  prioridade 2
    OS_Task_Create(3, Task_3); // Criando uma tarefa,  prioridade 3

    OS_EI(); // Habilita interrupcpes
    OS_Run(); // Executa o RTOS
}

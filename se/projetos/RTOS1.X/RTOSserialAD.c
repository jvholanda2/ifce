#include "SanUSB48.h" // Vídeo: https://www.youtube.com/watch?v=6XXgR39VYCs
#include <osa.h>

#pragma interrupt interrupcao

long int resultado;

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

        resultado = le_AD10bits(0);
        sendnum(resultado);
        if (resultado>=0 && resultado<=100){
            OS_Delay(100);
            inverte_saida(pin_b5);
        }else if(resultado>100){
            OS_Delay(500);
            inverte_saida(pin_b5);
        }       

    }

}


void Task_2(void) {
    while (1) {
        if (PIR1bits.RCIF) //Flag que indica byte na USART - Serial_avaliable()
        {
            PIR1bits.RCIF = 0; //reset flag
            switch (RCREG)// byte recebido
            {
                case 'l': nivel_alto(pin_b7);
                    break; //Chega l acende o led

                case 'd': nivel_baixo(pin_b7);
                    break; //Chega d apaga o led

            }
            break;

        }
        OS_Delay(100);
    }
}

void Task_3(void) {
    while (1) {
        inverte_saida(pin_b3);
        OS_Delay(100);
    }
}

void main(void) {
    clock_int_48MHz();
    taxa_serial(19200);
    habilita_canal_AD(AN0);

    PIC_Init(); // Configurações gerais do PIC

    OS_Init();
    TRISB = 0b00000000;
    TRISA = 0b00000000;

    OS_Task_Create(1, Task_1); // Criando uma tarefa,  prioridade 1
    OS_Task_Create(2, Task_2); // Criando uma tarefa,  prioridade 2
    OS_Task_Create(3, Task_3); // Criando uma tarefa,  prioridade 3   

    OS_EI(); // Habilita interrupcpes
    OS_Run(); // Executa o RTOS
}

#include "SanUSB48.h" //Exemplo com 4 tarefas (task) concorrentes (paralelas)
#include <osa.h> //Vídeo-aula: https://www.youtube.com/watch?v=s6BG8ZN0aDk
//Evitar uso de outros laços dentro das tasks (como for, do - while, etc.!)

//Carro
#define TEMPO_CARRO         2000

#define ESTADO_CARRO_VERMELHO   0x1
#define ESTADO_CARRO_AMARELO    0x2
#define ESTADO_CARRO_VERDE      0x4

//Pedestre
#define TEMPO_PEDESTRE      20000
#define TEMPO_PEDESTRE_STEP 1000

#define ESTADO_PEDESTRE_VERMELHO    0x8
#define ESTADO_PEDESTRE_VERDE       0x10

//Buzzer
#define TEMPO_BUZZER_1  10000
#define TEMPO_BUZZER_2  4000
#define BUZZER_STEP_1   1000
#define BUZZER_STEP_2   300
#define ESTADO_BUZZER_ON    0x20
#define ESTADO_BUZZER_OFF   0x00


unsigned char num[10] = {0x40,0x79,0x24,0x30,0x19,0x12,0x02,0x78,0x00,0x10};

unsigned char estado_carro = ESTADO_CARRO_VERDE;

unsigned char estado_pedestre = ESTADO_PEDESTRE_VERMELHO;
int tempo_pedestre = 0;

int tempo_buzzer = 0, estado_buzzer = 0, buzzer_step = 0;

int buffer = 0;

void liga_buzzer()
{
    tempo_buzzer = TEMPO_BUZZER_1;
    buzzer_step = BUZZER_STEP_1;
}

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
        PORTA = estado_pedestre | estado_carro | estado_buzzer;

        if (tempo_pedestre) {
            buffer = tempo_pedestre / 1000;
            PORTB = ~num[buffer % 10];
            PORTC = 0x1;
            OS_Delay(10);
            PORTB = ~num[buffer / 10];
            PORTC = 0x2;
//            OS_Delay(20);
        } else {
            PORTB = 0x0;
            PORTC = 0x0;
        }

        OS_Delay(10);
    }
}

void Task_2(void)
{
    while(1)
    {
        if(!entrada_pin_e3 && !tempo_pedestre)
        {
            //Passa para amarelo
            OS_Delay(TEMPO_CARRO);
            estado_carro = ESTADO_CARRO_AMARELO;
            //Passa para vermelho
            OS_Delay(TEMPO_CARRO);
            estado_carro = ESTADO_CARRO_VERMELHO;
            
            //Muda o semáforo do pedestre para verde e inicializa o display
            estado_pedestre = ESTADO_PEDESTRE_VERDE;
            tempo_pedestre = TEMPO_PEDESTRE;
        }

        if (tempo_pedestre)
        {
            OS_Delay(TEMPO_PEDESTRE_STEP);
            tempo_pedestre -= TEMPO_PEDESTRE_STEP;
            if (!tempo_buzzer && tempo_pedestre <= TEMPO_BUZZER_1) {
                liga_buzzer();
            }
            if (tempo_pedestre < 0) {
                tempo_pedestre = 0;
                estado_pedestre = ESTADO_PEDESTRE_VERMELHO;
                estado_carro = ESTADO_CARRO_VERDE;
            }
        }
        OS_Delay(10);
    }
}

void Task_3(void)
{
    while(1)
    {
        if (tempo_buzzer) {
            estado_buzzer = ESTADO_BUZZER_ON;
            OS_Delay(buzzer_step);
            estado_buzzer = ESTADO_BUZZER_OFF;
            OS_Delay(buzzer_step);
            tempo_buzzer -= 2 * buzzer_step;

            if (tempo_buzzer != BUZZER_STEP_2 && tempo_buzzer <= TEMPO_BUZZER_2) {
                buzzer_step = BUZZER_STEP_2;
            }
            if(tempo_buzzer < 0 ) {
                tempo_buzzer = 0;
            }

        }
        OS_Delay(50);
    }
}

void Task_4(void)
{
    while(1)
    {
        OS_Delay(10);
    }
}

void main(void)
{
    clock_int_48MHz();

    PIC_Init();                     // Configurações gerais do PIC
   
    OS_Init();                      
    TRISA = 0x0;
    TRISB = 0x0;
    TRISC = 0x0;
    OS_Task_Create(1,Task_1);       // Criando uma tarefa,  prioridade 1
    OS_Task_Create(2,Task_2);       // Criando uma tarefa,  prioridade 1
    OS_Task_Create(3,Task_3);       // Criando uma tarefa,  prioridade 1
//    OS_Task_Create(1,Task_4);       // Criando uma tarefa,  prioridade 1

    OS_EI();                        // Habilita interrupcoes
    OS_Run();                       // Executa o RTOS
}



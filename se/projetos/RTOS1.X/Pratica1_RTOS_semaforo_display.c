#include "SanUSB48.h" //Exemplo com 4 tarefas (task) concorrentes (paralelas)
#include <osa.h> //V?deo-aula: https://youtu.be/uAz0Zqqf6lQ
//Evitar uso de outros la?os dentro das tasks (como for, do - while, etc.!)


#pragma interrupt interrupcao

//variaveis semaforo
int setseg[] ={0x40, 0x79, 0x24, 0x30, 0x19, 0x12, 0x02, 0x78, 0x00, 0x10};//numeros de 0 a 9 em hexadecimal para display ANODO COMUM
int i,z,dezena,unidade,flag = 0;
//variaveis semaforo

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

void Semaforo_Carro(void)
{
    /*
     OBS: o "nivel_alto(pin_a3)" precisar ficar fora da verificacao "if(flag==0)
     */
    while(1)
    {
        nivel_alto(pin_a3);     //liga vemelho p carro
        if(flag==0){
            nivel_alto(pin_a1);     //liga vemelho p pedestre
            //nivel_alto(pin_a3);     //liga vemelho p carro
            nivel_baixo(pin_a0);    //desliga verde p pedestre
            OS_Delay(1000);
            nivel_baixo(pin_a3);    //desliga vemelho p carro
            nivel_alto(pin_a5);     //liga verde p carro
            OS_Delay(1000);
            nivel_baixo(pin_a5)     ;//desliga verde p carro
            nivel_alto(pin_a4);     //liga amarelo p carro
            OS_Delay(1000);
            nivel_baixo(pin_a4);    //desliga amarelo p carros
        }
        OS_Delay(100);
    }
}

void Verifica_Botao(void)
{
    /*

     */
    while(1)
    {
        //tempo_ms(100);
        inverte_saida(pin_b7);
        //if((PORTEbits.RE3 == 1) && (PORTAbits.RA3 == 1){

        //}else
        if ((PORTEbits.RE3 == 0)){
            flag = 1;
        }
        OS_Delay(100);
    }
}

void Display7Segm(void)
{
    /*

     */
    while(1)
    {
        if(flag==1 && (PORTAbits.RA3 == 1)){
            nivel_baixo (pin_a5);//desliga verde p carro
            nivel_baixo (pin_a4);//desliga amarelo p carro
            nivel_alto (pin_a3);//liga vermelho p carro

            nivel_baixo (pin_a1);//desliga vermelho p pedestre
            nivel_alto (pin_a0);//liga verde p pedestre
            for(i=99;i>=0;i--){//contagem decrescente d 15 a 0
                for(z=0;z<15;z++){
                    dezena=i/10;//setando a dezena, primeiro display
                    unidade=i%10;//setando a unidade, segundo display
                    nivel_alto(pin_c0);//ativa display 1
                    nivel_baixo(pin_c1);//desativa display 2
                    PORTB=setseg[dezena];
                    tempo_ms(5);
                    //trocando os displays
                    nivel_alto(pin_c1);//ativa display 2
                    nivel_baixo(pin_c0);// desativa display 1
                    PORTB=setseg[unidade];
                    tempo_ms(5);
                }
            }
            nivel_baixo (pin_a0);//desliga verde p pedestre
            nivel_alto (pin_a1);//liga vermelho p pedestre
            flag = 0;
        }
        OS_Delay(100);
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

    PIC_Init();                     // Configura??es gerais do PIC

    OS_Init();

    OS_Task_Create(1,Verifica_Botao);       // Criando uma tarefa,  prioridade 1
    OS_Task_Create(2,Display7Segm);       // Criando uma tarefa,  prioridade 2
    OS_Task_Create(3,Semaforo_Carro);       // Criando uma tarefa,  prioridade 3
    
    //OS_Task_Create(4,Task_4);       // Criando uma tarefa,  prioridade 4

    OS_EI();                        // Habilita interrupcoes
    OS_Run();                       // Executa o RTOS
}
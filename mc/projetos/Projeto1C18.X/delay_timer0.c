#include "SanUSB1.h" // Pr�tica de interrup��o do temporizador 0

 unsigned int i;

#pragma interrupt interrupcao
void interrupcao()
    {
    if (INTCONbits.TMR0IF)     {  //espera o estouro do timer0 -> TMR0L=0
    INTCONbits.TMR0IF = 0;        //limpa a flag de interrup��o
    ++i; if(i>=500){i=0;
                    inverte_saida(pin_b7);}
    TMR0L = TMR0L + 6; // load time before plus 250us x 4 (prescaler 001) = 1000us = 1ms into TMR0 so that it rolls over (for 4MHz oscilator clock)
                                }
    }


void main() {
clock_int_4MHz();
 //T0CON BITS = TMR0ON , T08BIT(0=16bits OR 1=8bits), T0CS , T0SE , PSA , T0PS2 T0PS1 T0PS0.
 //Defaull 1 in all bits.
T0CON =0B11000001; //TMR0ON, 8 bits, Prescaler 1:4 (001 - see datasheet)
TMR0L = 6;

RCONbits.IPEN = 1;    //apenas interrup��es de alta prioridade (default no SO)
INTCONbits.GIEH = 1;  //Habilita interrup��es de alta prioridade 
INTCONbits.TMR0IE = 1;//Habilita interup��o timer 0

while (1){
    if(!entrada_pin_e3){Reset();}//pressionar o bot�o para grava��o
		
    //inverte_saida(pin_b7);
    //timer0_ms(500);
         }


}

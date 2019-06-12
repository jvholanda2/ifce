#include "SanUSB1.h" // Emula��o de decodificador para display de 7 segmentos - palavra StoP

#pragma interrupt interrupcao //Tem que estar aqui ou dentro do firmware.c
void interrupcao(){
        }

short int X, Y;  //Entradas
short int a, b, c, d, e, f, g;  //sa�das

void decodificador(short int X, short int Y) //Fun��o auxiliar do decodificador fixo para StoP
{
a=X  ^  Y;	saida_pino(pin_b0,a);        //Anodo comum
b=!X | !Y;	saida_pino(pin_b1,b);
c=Y;		saida_pino(pin_b2,c);
d=X & Y;	saida_pino(pin_b3,d);
e=!X & !Y;	saida_pino(pin_b4,e);
f=X & !Y;	saida_pino(pin_b5,f);
g=0 ;		saida_pino(pin_b6,g);
}

void main(){
clock_int_4MHz();
while (1)
{
decodificador(0,0); // Insere as entradas X=0 e Y=0 no decodiicador 
tempo_ms(1000);

decodificador(0,1); // Sa�da letra t
tempo_ms(1000);

decodificador(1,0); // Sa�da letra o
tempo_ms(1000);

decodificador(1,1); // Sa�da letra P
tempo_ms(1000);
}}


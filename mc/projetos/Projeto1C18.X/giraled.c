
#include "SanUSB1.h" //rotacionar leds

unsigned char d=0b10000000;   // 8 bits

#pragma interrupt interrupcao //Tem que estar  dentro do firmware.c
void interrupcao(){
                 	 }

void main(){
clock_int_4MHz();
TRISB=0; TRISD=0; // porta B como sa�da

	while(1)
	{
	if(!entrada_pin_e3){Reset();}//pressionar o bot�o para grava��o via USB

	d=d>>1; if (d == 0b00000001) { d=0b10000000;}  //0b -> valor bin�rio
	PORTB=d;PORTD=d;
	tempo_ms(100);
	}
}


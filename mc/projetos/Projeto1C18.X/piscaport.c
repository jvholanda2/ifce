#include "SanUSB1.h"

#pragma interrupt interrupcao
void interrupcao(){//se houver interrupção inserir função aqui
                   }

void main (void)
{
    clock_int_4MHz();

    while (1)
    {
       if(!entrada_pin_e3){Reset();}//pressionar o botão para gravação
       nivel_alto(pin_b7); 
       tempo_ms(3000);
       nivel_baixo(pin_b7);
       tempo_ms(250);
       
       nivel_alto(pin_b6); 
       tempo_ms(500);
       nivel_baixo(pin_b6);
       tempo_ms(250);
       
       nivel_alto(pin_b5); 
       tempo_ms(1000);
       nivel_baixo(pin_b5);
       tempo_ms(250);
       
    }
}

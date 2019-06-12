#include "SanUSB1.h"

#pragma interrupt interrupcao
void interrupcao(){//se houver interrupção inserir função aqui
}

void display7Segmento(unsigned int k){
    unsigned int i;
    unsigned char set_seg[10] = {0x40,0x79,0x24,0x30,0x19,0x12,0x02,0x78,0x00,0x10};    
    TRISB = 0b00000000;    
    for(i = 0; i<=k; i++){        
        PORTB = set_seg[k-i];
        tempo_ms(1000);
    }
    PORTB=0xFF;
}

void semaforoPedestre(unsigned int flag,unsigned int temp_vermelho){    
    if (flag == 1){
        nivel_alto(pin_a4);//b3
        nivel_baixo(pin_a3);//b4       
        display7Segmento(temp_vermelho);
        flag = 0;
    }else{
        tempo_ms_SemaforoJJR(temp_vermelho*1000);
    }
}

void main (void)
{
    unsigned int flag;
    unsigned int temp_verde=4;
    unsigned int temp_amarelo=2;
    unsigned int temp_vermelho=6;
    clock_int_4MHz();

    while (1)
    {        
        //if(!entrada_pin_e3){Reset();}
        nivel_alto(pin_a3);//b4
        nivel_baixo(pin_a4);//b3

        nivel_alto(pin_a0);//b7 
        flag = tempo_ms_SemaforoJJR(temp_verde*1000);
        nivel_baixo(pin_a0);
        flag = tempo_ms_SemaforoJJR(250);

        nivel_alto(pin_a1);//b6 
        flag = tempo_ms_SemaforoJJR(temp_amarelo*1000);
        nivel_baixo(pin_a1);
        flag =  tempo_ms_SemaforoJJR(250);

        nivel_alto(pin_a2);//b5
        semaforoPedestre(flag,temp_vermelho);
        nivel_baixo(pin_a2);
        tempo_ms_SemaforoJJR(250);
    }
}

#include "SanUSB1.h"
#pragma interrupt interrupcao
#byte port_b = 0xf81;//REGISTRO PORTA B
unsigned char set_seg[10] = {0x40,0x79,0x24,0x30,0x19,0x12,0x02,0x78,0x00,0x10};    
int i,z,dezena,unidade;
unsigned int flag;
unsigned int temp_verde=4;
unsigned int temp_amarelo=2;
unsigned int temp_vermelho=6;

void interrupcao(){//se houver interrupção inserir função aqui
}

void display7Segmento(unsigned int k){
    PORTB= 0b00000000;// Define os pinos da porta B como saÌda
    TRISB=0x00; // porta B como saÌda.
     
    for(i=99;i>0;i--)//CONTA DE 00 A 99
    {
        for(z=0;z<15;z++)
        { //REPETE CADA NUMERO POR 15 VEZES POIS O DELAY ... CURTO E N?O DARIA TEMPO VER!
            dezena=i/10;//QUEBRA A VARIAVEL i EM 2 PARTES, PRIMEIRO EM DEZENA
            //SE O N?MERO FOR 27, DEZENA=2 E UNIDADE=7
            unidade=i%10;//DEPOIS EM UNIDADE
            nivel_alto(pin_c0);//SELECIONA 1. DISPLAY
            nivel_baixo(pin_c1);
            PORTB=set_seg[dezena];//MOSTRA O VALOR DE DEZENA
            tempo_ms(10);
            nivel_alto(pin_c1);//SELECIONA 2. DISPLAY
            nivel_baixo(pin_c0);
            PORTB=set_seg[unidade];//MOSTRA O VALOR DE UNIDADE
            tempo_ms(10);
        }
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

void main (void){    
    clock_int_4MHz();
    while (1){        
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

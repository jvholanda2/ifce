/******************************************************************************
 * Descri��o:   	Firmware capaz de prover a comunica��o entre um teclado
 *                      PS/2 e o microcontrolador
 * V�deo: http://www.youtube.com/watch?v=yV6MWybpB3w
 *
 * Cr�ditos a Bruno A. Dayrell
 * 
 * 			H A R D W A R E:
 *
 *  			Conector Femea:		Pinos | Porta | Fun��o
 *                                        1      RD6    Teclado Data
 *      	6 [] 5			  3       - 	GND
 *             4      3                   4       -	VCC
 *                                        5      RD7	Teclado Clock
 *            	 2  1                     
 *		Comunica��o Teclado -> PIC
 *
 *		1 start bit.  Sempre 0 (Pode ser usado para ativar interrup��o).
 *		8 data bits, Menos significante primeiro.
 *		1 bit de paridade (paridade �mpar)
 *		1 stop bit.  Sempre 1.
 *****************************************************************************/

#include "SanUSB48.h"	
#include "PS2.h"     
#include "lcd1.h"    

    char letra;
    int i;

#pragma interrupt interrupcao
void interrupcao() {}

void main(void)
{   clock_int_48MHz();
    taxa_serial(19200);

    ConfiguraLCD();     

    while(1)
    {    
        // aguarda o pino de start bit (start bit = 0)
        while(PS2_PINDATA){if(!entrada_pin_e3){Reset();}}//Estado de grava��o via USB, se o bot�o for pressionado

        // Verifica��o da condi��o de permitir leitura do teclado
        if(!PS2_PINDATA)
                    {
                            letra = LeTeclado();
                    }
        Delay10KTCYx(30);
                    // Tratamento do caractere que verifica se n�o � nenhum
                    // caractere de ERRO e tamb�m se n�o � alguma tecla especial
                    // como exemplo, backspace ou enter.
		if(letra!= MISSMATCH_ERROR && letra!= TIMEOUT_ERROR && letra!= PARIDADE_ERROR ) //0, 1 e 2
     		{
			if(letra == 0x0D)//Enter
			{
                                // In�cio da 2� Linha
				PosicaoCursorLCD(2,1);
			}
			else
			{
				if(letra == 0x08) // Backspace - limpa todo o display
				{
                                        LimpaDisplay();
				}
				else
				{
                                        EscreveCaractereLCD(letra);
				}
			}
		} 

    } 


}


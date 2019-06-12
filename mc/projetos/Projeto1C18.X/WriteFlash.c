#include "SanUSB48.h" // http://www.microchip.com/forums/m55318-p4.aspx

char mem[8],x; //buffer de 8 bytes
unsigned char cont_mem_dados;
unsigned short cont_mem_prog, count;

#pragma interrupt interrupcao
void interrupcao(){}

/*Note que a rotina que grava o dado na mem�ria de programa, na realidade, grava o valor de 4 words (8 bytes). Por�m, 
 * o software da experi�ncia altera o valor de apenas uma dessas words. O problema � que a grava��o de informa��es
 * na mem�ria de programa, no caso do PIC18F4550, sempre deve ser realizada no m�nimo de 4 em 4 words. Desta forma,
 * n�o � poss�vel gravar apenas um valor ou uma word (2 Bytes). Sempre a grava��o ser� de 4 words no m�nimo. Portanto,
 * sempre que se desejar gravar menos do que 4 words mantendo as outras intactas, deve-se inicialmente realizar uma
 * leitura de todas as 4 words, alterar as que se desejarem e regravas todas de uma �nica vez.
 * Um valor relativo � mem�ria EEPROM interna de dados pode variar de 0 a FFh (8-bits).
 * Um valor relativo � mem�ria de programa flash, tamb�m com incremento e decremento rotativo pode variar  de 0 a 3FFFh,
 * pois dos 16 bits de cada word (2 bytes) s� s�o v�lidos 14 bits para escrita.
*/

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* Fun��o de escrita da FLASH interna *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
// OBS: ESTA FUN��O, O ENDERE�O DEVE TER FINAL 0 OU 8 (8 bytes ? 4 words)
// EX.: 0x500, 0x508, 0x60, 0x68 ...

void write_FLASH(unsigned addr, char *buffer){
 unsigned char i;
 TBLPTR = addr; //carrega endere�o
 EECON1bits.EEPGD = 1; //aponta para mem�ria FLASH
 EECON1bits.CFGS = 0; //habilita acesso a FLASH
 EECON1bits.WREN = 1; //habilita escrita
 EECON1bits.FREE = 1; //apaga FLASH a partir do endere�o apontado no TBLPTR
 INTCONbits.GIE = 0; //desabilita todas as interrup��es
 EECON2 = 0x55; //escreve 0x55 em EECON2 (obrigat�rio)
 EECON2 = 0xAA; //escreve 0xAA em EECON2 (obrigat�rio)
 EECON1bits.WR = 1; //inicializa escrita da FLASH para apagar
 INTCONbits.GIE = 1; //habilita todas as interrup��es
 while(EECON1bits.WR);
 EECON1bits.WREN = 0; //desabilita escrita 
//*********************************************************
 for(i = 0;i < 8;i++)
 {
 TABLAT = buffer[i]; //carrega buffer[8] em TABLAT para gravar o conte�do do buffer 
 _asm
 TBLWTPOSTINC //escreve dado carregado em TABLAT e incrementa TBLPTR
 _endasm
 if(((i + 1) % 8) == 0) //identifica os grupos de 8 bytes e habilita a escrita
 {
 TBLPTR -= 8; //retorna 8 posi��es em TBLPTR para gravar
 EECON1bits.EEPGD = 1; //aponta para mem�ria FLASH
 EECON1bits.CFGS = 0; //habilita acesso a FLASH
 EECON1bits.WREN = 1; //habilita escrita
 EECON1bits.FREE = 0; //somente escrita da FLASH
 INTCONbits.GIE = 0; //desabilita todas as interrup��es
 EECON2 = 0x55; //escreve 0x55 em EECON2 (obrigat�rio)
 EECON2 = 0xAA; //escreve 0xAA em EECON2 (obrigat�rio)
 EECON1bits.WR = 1; //inicializa escrita da FLASH
 INTCONbits.GIE = 1; //habilita todas as interrup��es
 while(EECON1bits.WR);
 EECON1bits.WREN = 0; //desabilita escrita

 TBLPTR += 8; //retorna � posi��o original em TBLPTR
 }
 }
}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* Fun��o de leitura da FLASH interna *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
unsigned short read_FLASH(unsigned endereco)
{
 union
    {
    struct
    {
        unsigned short low:8;
    unsigned short high:8;
    } ;
 struct
    {
    unsigned short all;
    } ;
    }dado;
 TBLPTR = endereco; //carrega endere�o
 _asm //l� posi��o apontada pelo TBLPTR, envia
    TBLRDPOSTINC //dado para TABLAT e incrementa TBLPTR
    _endasm
    dado.low = TABLAT; //salva dado em dado.low

    _asm //l� posi��o apontada pelo TBLPTR, envia
    TBLRDPOSTINC //dado para TABLAT e incrementa TBLPTR
    _endasm
    dado.high = TABLAT; //salva dado em dado.high

    return(dado.all); //retorna dado dispon�vel em dado.all
}

void main () {
    clock_int_48MHz();
    habilita_interrupcao(recep_serial);
    taxa_serial(19200);
  // for(x=0;x<18;x++) {inverte_saida(pin_b7);tempo_ms(100);}

    cont_mem_dados = le_eeprom(0x10); //L� MEM�RIA EEPROM
    cont_mem_prog = read_FLASH(0x3000); //L� MEM�RIA FLASH
   
    

    while(1){
    ++count; ++ cont_mem_dados; ++ cont_mem_prog;

    if(!entrada_pin_e3)//Se apertar o bot�o...
      {
            for(x=0;x<8;x++) {mem[x]=0;} //ZERA BUFFER mem PARA GRAVA��O DA MEM�RIA FLASH
            escreve_eeprom(0x10, cont_mem_dados); // count_mem_dados GRAVA UM VALOR NA EEPROM INTERNA AT� 0XFF
            mem[0] = cont_mem_prog;
            mem[1] = cont_mem_prog >> 8;             // cont_mem_prog -> mem[1] byte mais significativo
            //sendsw((char *)"A\r\n");
             swputc('A');swputc(' '); //
            //write_FLASH(0x3000, &mem); //colocar &mem depois; grava 4 words na FLASH (bytes 0x5000 a 0x5007),
             tempo_ms(500);
       tempo_ms(1000);
            sendrw((rom char *)"M\r\n");
            sendnum(le_eeprom(0x10)); sendrw((rom char *)" ");
            sendnum(read_FLASH(0x3000)); sendrw((rom char *)" "); //mostra a primeira word (0x5000 e 0x5001); valor at� 0X3FFF
         }

inverte_saida(pin_b7);
tempo_ms(500);

 }}

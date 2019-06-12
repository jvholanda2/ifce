/*
 * Blink
 * Turns on an LED on for one second,
 * then off for one second, repeatedly.
 */

#include "Arduino.h"

static const uint8_t TabelaBit [] =
{        //Segments
// a  b  c  d  e  f  g
// 5  6  12 13 19 16 26    ->No. pino editável BCM ou wiringPi(wPi)
   1, 1, 1, 1, 1, 1, 0,    // Plota 0 - 0
   0, 1, 1, 0, 0, 0, 0,    // Plota 1 - 1
   1, 1, 0, 1, 1, 0, 1,    // Plota 2 - 2
   1, 1, 1, 1, 0, 0, 1,    // Plota 3 - 3
   0, 1, 1, 0, 0, 1, 1,    // Plota 4 - 4
   1, 0, 1, 1, 0, 1, 1,    // Plota 5 - 5
   1, 0, 1, 1, 1, 1, 1,    // Plota 6 - 6
   1, 1, 1, 0, 0, 0, 0,    // Plota 7 - 7
   1, 1, 1, 1, 1, 1, 1,    // Plota 8 - 8
   1, 1, 1, 1, 0, 1, 1,    // Plota 9 - 9
   1, 1, 1, 0, 1, 1, 1,    // Plota A - 10
   0, 0, 1, 1, 1, 1, 1,    // Plota b - 11
   1, 0, 0, 1, 1, 1, 0,    // Plota C - 12
   0, 1, 1, 1, 1, 0, 1,    // Plota d - 13
   1, 0, 0, 1, 1, 1, 1,    // Plota E - 14
   1, 0, 0, 0, 1, 1, 1,    // Plota F - 15
   0, 0, 0, 0, 0, 0, 0,    // Plota blank - 16
   // palavras
   0, 1, 1, 0, 1, 1, 1,    // Plota H - 17
   1, 0, 0, 1, 1, 1, 1,    // Plota E - 18
   0, 0, 0, 1, 1, 1, 0,    // Plota L - 19
   1, 1, 1, 1, 1, 1, 0,    // Plota O - 20
} ;

void num_display(int num) {
  uint8_t seg, Valor ;
  static const uint8_t pinoBCM [7] ={16,5,4,0,2,14,12}; //Simples de atualizar os pinos ligados aos display de 7 segmentos
  for (seg = 0 ; seg < 7 ; ++seg) {
    Valor = TabelaBit [num * 7 + seg] ; //num * 7 indica a linha e seg indica a coluna
    digitalWrite (pinoBCM[seg], Valor) ; //escreve no segmento o bit 0 (LOW) ou 1 (HIGH) de acordo com a tabela TabelaBit
  }
}

static int flag = 0;
void funcaoInterrupt() {
  //flag=1;
  flag = 1;
  //digitalWrite(16, state);
  Serial.println("OI");
}

void setup()
{
  // initialize LED digital pin as an output.
  pinMode(16, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(0, OUTPUT);
  pinMode(2, OUTPUT);
  pinMode(14, OUTPUT);
  pinMode(12, OUTPUT);

  //PEDESTRE
  pinMode(10, OUTPUT);
  pinMode(9, OUTPUT);

  //SEMAFORO
  pinMode(15, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(1, OUTPUT);

  //Serial.begin(115200);
  pinMode(13, INPUT_PULLUP); //D7
  attachInterrupt(13, funcaoInterrupt, FALLING);//CHANGE, RISING, FALLING
  funcaoInterrupt();
}

void loop(){

  // PEDESTRE
  digitalWrite (10, HIGH) ;
  digitalWrite (9, LOW) ;

  // SEMAFORO
  digitalWrite (15, HIGH) ;
  delay(1000);
  digitalWrite (15, LOW) ;

  digitalWrite (3, HIGH) ;
  delay(1000);
  digitalWrite (3, LOW) ;

  digitalWrite (1, HIGH) ;
  delay(1000);
  digitalWrite (1, LOW) ;

  // PEDESTRE

  if (flag == 1) {
    digitalWrite (15, HIGH) ;

    digitalWrite (10, LOW) ;
    digitalWrite (9, HIGH) ;

    int num = 10;
    for (int i = 0 ; i < num ; i++)
    {
      num_display(i);
      delay(1000);
    }

    digitalWrite (16, LOW);
    digitalWrite (5, LOW);
    digitalWrite (4, LOW);
    digitalWrite (0, LOW);
    digitalWrite (2, LOW);
    digitalWrite (14, LOW);
    digitalWrite (12, LOW);
    flag = 0;
  }

}

#include <ESP8266WiFi.h>
//LCD
#include "LiquidCrystal_I2C.h"
#include <Wire.h>
//telegrambot
#include <WiFiClientSecure.h>
#include "UniversalTelegramBot.h"
//timer
#include "NTPClient.h"//Biblioteca do NTP.
#include <WiFiUdp.h>//Biblioteca do UDP.

const char *ssid = "XXXXXXXX"; 
const char *pass = "YYYYYYYY";

String ipEnd;
String hora;
String data;
float sensorVolt;
float voltVazamento=2.55;//Default

//LCD
LiquidCrystal_I2C lcd(0x27, 16, 2);

//thingspeak
WiFiClient client;
String urlGrafico = "https://thingspeak.com/channels/XXXXXX";
String apiKey = "XXXXXXXXXXXXXXXXX"; // API key para ThingSpeak
const char* server = "api.thingspeak.com";
int porta = 80;

//Telegram - //instalar a versao 5.13.2 da Arduinojson via Gerenciar bibliotecas
#define BOTtoken "XXXXXXXXXXXXXXXXXXXXXXXXXXxx"//Define o Token do *seu* BOT
WiFiClientSecure clientTel;
UniversalTelegramBot bot(BOTtoken, clientTel);
String id, text;//Váriaveis para armazenamento do ID e TEXTO gerado pelo Usuario
String msgCabecalho     = "SisIoTGas MQ-5\nLink do Gráfico:\n"+urlGrafico;  
String msgAlerta= "";  
//Telegram ChatID
#define CHAT_ID_SisEmb "XXXXXXXXX"    

//timer
WiFiUDP udp;//Cria um objeto "UDP".
NTPClient ntp(udp, "a.st1.ntp.br", -3 * 3600, 60000);//Cria um objeto "NTP" com as configurações.

void delay2(int miliseg){  
  delay(miliseg);
  yield();//Efetua o feed do SW WDT.
  ESP.wdtFeed();//Alimenta o Watchdog.  
}

String getIp(){  
  IPAddress ip = WiFi.localIP();  
  return ip.toString();
}

void setVolt(float v){
  if(v>0){
    voltVazamento=v;
  }//se negativo nao mudo
}

void piscarLCD(){
  lcd.noBacklight();
  delay2(150);     
  lcd.backlight();
  delay2(150);     
  lcd.noBacklight();
  delay2(150);      
  lcd.backlight();
}

void printDisplay(String texto1,String texto2, int miliseg){  
  lcd.clear();  
  lcd.print(texto1);
  lcd.setCursor(0,1);  
  lcd.print(texto2);  
  delay2(miliseg);   
}

void acionarPino(int pinoDx, boolean estadoPinoDx){
  if(estadoPinoDx==true){//Ligado
     digitalWrite(pinoDx,HIGH);         
  }else{//Desligado
     digitalWrite(pinoDx,LOW);
  }  
}

void conectarWifi(){  
  Serial.println("conectarWifi(Entrou)");
  WiFi.begin(ssid, pass);  
  while(WiFi.status() != WL_CONNECTED) {    
    Serial.print(".");
    delay2(500);    
  }    
  ipEnd=getIp();
  Serial.println("\nWiFi conectado");
  Serial.println(ipEnd);
  printDisplay("WiFi conectado",ipEnd,250);    
  Serial.println("conectarWifi(Saiu)");
}

bool testarConexao(){  
  if(WiFi.status() != WL_CONNECTED){
    Serial.println("WiFi desconectado!!!");
    conectarWifi();//so sai depois q conectar
    return false;
  }else{
    return true;
  }
}

void setup(){
  Serial.begin(115200);  
  Serial.println("setup(ENTROU)");  
  lcd.begin(16,2);
  lcd.init(); 
  piscarLCD();
  Serial.print("Conectando...");
  printDisplay("Conectando... ",String(ssid),1000);
  Serial.println(ssid);  
  conectarWifi();
  //pinMode(D0, INPUT_PULLUP);// sinal digital
  pinMode(D7, OUTPUT);
  pinMode(D4, OUTPUT);
  //Time
  acionarPino(D4,true);//apagar
  ntp.begin();//Inicia o NTP. 
  //postarAlertaTelegram(msgCabecalho+"\nStartado!");
  delay2(50);  
  Serial.println("setup(SAIU)"); 
}

void atualizarmsgAlerta(){  
  String msgCorpo;
  msgCorpo.concat("\nZona 1 - Cozinha");
  msgCorpo.concat("\nSSID: ");
  msgCorpo.concat(ssid);
  msgCorpo.concat(", IP: ");
  msgCorpo.concat(ipEnd);
  msgCorpo.concat("\nALERTA!!!\n");
  msgCorpo.concat(data+", vazamento as "+hora+" , sensor: "+String(sensorVolt)+"V\n");
  msgAlerta=msgCabecalho+msgCorpo;
}

void atualizarDataHora(){ 
  int tentativas=0;
  while(!ntp.update()&&tentativas<7) {
    ntp.forceUpdate();
    Serial.print(".");
    tentativas=tentativas+1;
    delay2(50);
  }  
  hora = ntp.getFormattedTime();//HORA atual.
  String dataTemp = ntp.getFormattedDate();//data
  int splitT = dataTemp.indexOf("T");
  data = dataTemp.substring(0, splitT);//data cortada
}

void loop(){ 
  float adc = analogRead(A0);  
  sensorVolt = lerSensorMQ5(adc);//calculando volt/vazamento
  atualizarmsgAlerta();
  atualizarDataHora();
    
  Serial.println(data+" "+hora+" "+String(sensorVolt)+"V");   
  printDisplay(data,hora+"   "+String(sensorVolt) + "V",50);//encaixado no lcd16x2

  if(sensorVolt>voltVazamento){//acima = vazamento
    acionarPino(D7,true);
    if(testarConexao())
      postarThingSpeak();
    if(testarConexao())
      postarAlertaTelegram(msgAlerta);         
    acionarPino(D7,false);
  }
  if(testarConexao())
    lerComandoTelegram();   
  delay(500);
  ESP.wdtFeed();//Alimenta o Watchdog.    
}

float lerSensorMQ5(float adc){  
  if (isnan(adc)){
    Serial.println("Falha ao ler o sensor MQ-5!");
    return -1;
  }  
  return (adc/1024*5.0);
}

void postarThingSpeak(){
  int conectar = client.connect(server, porta);  
  // "184.106.153.149" or api.thingspeak.com
  Serial.println("Conecatando: "+String(conectar));
  if (conectar){
    Serial.println("Post Thingspeak...");  
    printDisplay("Postando...","Thingspeak",50);  
    String urlPost = apiKey;
    urlPost.concat("&field1=");
    urlPost.concat(String(sensorVolt)); 
    urlPost.concat("\r\n'\'");
     
    client.print("POST /update HTTP/1.1\n");
    client.print("Host: api.thingspeak.com\n");
    client.print("Connection: close\n");
    client.print("X-THINGSPEAKAPIKEY: " + apiKey + "\n");
    client.print("Content-Type: application/x-www-form-urlencoded\n");
    client.print("Content-Length: ");
    client.print(urlPost.length());
    client.print("\n\n");
    client.print(urlPost);
    Serial.print("\nMedidor de Gás ");
    Serial.print(sensorVolt);
    Serial.println("V");
    Serial.println("---> Enviado Thingspeak.");    
    printDisplay("Enviado...","Thingspeak.",50);  
  }
  client.stop();
  Serial.println("Esperando...");  
  // thingspeak precisa de um atraso mínimo de 15 segundos entre as atualizações, eu configurei para 30 segundos
  delay2(500);
}

//Telegram *****************************************************************************
void postarAlertaTelegram(String msg) {
  Serial.println("Post Telegram...");  
  printDisplay("Postando...","Telegram...",50);
  
  if (bot.sendMessage(CHAT_ID_SisEmb, msg, "Markdown")){
    Serial.println("Enviado para o Telegram sucesso");    
    printDisplay("Enviado para:","Telegram",50); 
  }else{
    Serial.println("Erro Telegram");    
    printDisplay("Erro ao enviar","Telegram",50); 
  }
  Serial.println("Esperando...");  
  delay2(500);
}

void lerComandoTelegram(){    
    if (millis() > Bot_lasttime + Bot_mtbs)  {
      Serial.println("lerComandoTelegram(Entrou)");
      int msg = bot.getUpdates(bot.last_message_received + 1);
      for (int i = 0; i < msg; i++){          
        id = bot.messages[i].chat_id;//Armazenara o ID do Usuario.
        text = bot.messages[i].text;//Armazenara o TEXTO do Usuario.
        text.toUpperCase();//Converte a STRING_TEXT em Maiuscúla.
        text.trim();
        Serial.println("id-> "+id);
        Serial.println("text-> "+text);
        if(text.equals("SETUP")){
          setup();
          bot.sendMessage(id, "SETUP", "");//Responde a msg
        }else if(text.equals("CHATID")){        
          bot.sendMessage(id, "CHAT ID-> "+id, "");//Responde a msg
        }else if(text.equals("IP")){        
          bot.sendMessage(id, "IP-> "+ipEnd, "");//Responde a msg
        }else if(text.equals("SENSOR")){        
          bot.sendMessage(id, "MQ5-> "+String(sensorVolt), "");//Responde a msg
        }else if(text.equals("GRAFICO")){        
          bot.sendMessage(id, "Link-> "+urlGrafico, "");//Responde a msg
        }else if(text.equals("LIMIAR")){       
          bot.sendMessage(id, "Limiar de Calibragem-> "+String(voltVazamento)+"V", "");//Responde a msg
        }else if(text.equals("-0.1V")){
          setVolt(voltVazamento-0.1);        
          bot.sendMessage(id, "Novo Volt Vazamento-> "+String(voltVazamento), "");//Responde a msg
        }else if(text.equals("-1V")){
          setVolt(voltVazamento-1);        
          bot.sendMessage(id, "Novo Volt Vazamento-> "+String(voltVazamento), "");//Responde a msg
        }else if(text.equals("+0.1V")){
          setVolt(voltVazamento+0.1);        
          bot.sendMessage(id, "Novo Volt Vazamento-> "+String(voltVazamento), "");//Responde a msg
        }else if(text.equals("+1V")){
          setVolt(voltVazamento+1);        
          bot.sendMessage(id, "Novo Volt Vazamento-> "+String(voltVazamento), "");//Responde a msg
        }else if (text.equals("COMANDOS")){
          String cmd = "Comando: SETUP  -> SETUP()\n";
          cmd.concat("Comando: CHATID -> CHAT_ID\n");
          cmd.concat("Comando: IP     -> IP LOCAL\n");
          cmd.concat("Comando: SENSOR -> sensor Volt online\n");
          cmd.concat("Comando: GRAFICO-> Link do Grafico\n");
          cmd.concat("Comando: LIMIAR -> Limiar de Calibragem\n"); 
          cmd.concat("Comando: -0.1V  -> - 0.1V no limite!\n");
          cmd.concat("Comando: -1V    -> - 1V   no limite!\n");
          cmd.concat("Comando: +0.1V  -> + 0.1V no limite!\n");
          cmd.concat("Comando: +1V    -> + 1V   no limite!\n");
          cmd.concat("Comando: COMANDOS-> TODOS COMANDOS\n");
           bot.sendMessage(id, cmd, "");
        }else{//Envia uma mensagem de erro.
           bot.sendSimpleMessage(id, "Comando Invalido", "");
        }
        delay2(50);
      } 
    Bot_lasttime = millis();
    Serial.println("lerComandoTelegram(Saiu)");
  }   
}


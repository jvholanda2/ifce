#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>

SoftwareSerial mySerial(13, 15); // RX, TX (7 tx->12) (8 rx -> 13)

String ssid = "android";
String password = "0123456789";
String fone="+5511900001111";//m
String msg="WiFi desconectado!";

void ligLedWifi(){
  digitalWrite(4, 0);//vermelho
  digitalWrite(5, 1);//verde
}
void desLedWifi(){
  digitalWrite(4, 1);//vermelho
  digitalWrite(5, 0);//verde
}
void setWifi(){
  Serial.println("setWifi()");
  delay(1500); 
  Serial.println("ssid:");
  String id="";
  while(id.equals("")){
    if(Serial.available()){
      id = Serial.readStringUntil('\n');
    }
  }  
  id.trim();//remover char final \n ou \r ou ...
  ssid=id;
  Serial.println("Novo ssid    : "+ssid);
  delay(1500);
  Serial.println("password: ");
  String pass="";
  while(pass.equals("")){
    if(Serial.available()){
      pass = Serial.readStringUntil('\n');
    }
  }  
  pass.trim();//remover char final \n ou \r ou ...
  password=pass;  
  Serial.println("Novo Password: "+password);  
  delay(1500);
}
void setFone(){
  Serial.println("setFone()");
  String cel="";
  while(cel.equals("")){
    if(Serial.available()){
      cel = Serial.readStringUntil('\n');
    }
  }  
  cel.trim();//remover char final \n ou \r ou ...
  fone=cel;
  Serial.println("Novo Fone: "+fone);
}
void setMsg(){
  Serial.println("setMsg()");
  String msgaux="";
  while(msgaux.equals("")){
    if(Serial.available()){
      msgaux = Serial.readStringUntil('\n');
    }
  }  
  msgaux.trim();//remover char final \n ou \r ou ...
  msg=msgaux;
  Serial.println("Nova Mensagem: "+msg);
}
void setup() {
  delay(5000);
  Serial.println("setup(ENTROU)");
  Serial.println("WiFi-SMS v1.0");
  Serial.begin(115200);  
  mySerial.begin(115200);
  pinMode(5, OUTPUT);//D1
  pinMode(4, OUTPUT);//D2
  pinMode(0, OUTPUT);//D3    
  //setWifi();
  conectarWifi();//So saira depois de conectar
  delay(500);
  configModem();      
  Serial.println("setup(SAIU)");
}
void conectarWifi(){
  Serial.println("conectarWifi(ENTROU)");
  desLedWifi();
  Serial.print("Conectando para: ");
  Serial.println(ssid);  
  WiFi.begin(&ssid[0], &password[0]);
  while (WiFi.status() != WL_CONNECTED) {//So saira depois de conectar
    delay(500);
    Serial.print(".");   
  }  
  Serial.println("\nWiFi conectado");
  Serial.println(WiFi.localIP());
  ligLedWifi();
  Serial.println("conectarWifi(SAIU)");
}
void testarConexao(){
  if(WiFi.status() != WL_CONNECTED) {
    Serial.println("WiFi desconectado!!!");
    desLedWifi();
    enviarSms(fone,msg);
    delay(5000);
    conectarWifi();   
    delay(1000);
    enviarSms(fone,"WiFi reconectado!"); 
    Serial.println("WiFi reconectado!");
    delay(5000);
  }
  while (WiFi.status() != WL_CONNECTED) {//.....
    delay(10000);
    Serial.print(".");
  }
}
void limparBufferSerial(){  
  while(Serial.available()){//limpando buffer
    Serial.read();
    delay(500);
  }
}

void loop() {
  //Menu  
  if(Serial.available()){//Se digitar algo no Monitor Serial do Arduino IDE
    String key=Serial.readStringUntil('\n');
    key.trim();//remover char final \n ou \r ou ...
    Serial.println(key);
    //Serial.println(key.length());
    
    if(key.equals("setup")){
      setup();
    }
    if(key.equals("configmodem")){
      configModem();
    }
    if(key.equals("conectarwifi")){
      conectarWifi();
    }    
    if(key.equals("getwifi")){
        Serial.println("ssid: "+ssid);
        Serial.println("password: "+password);
    }
    if(key.equals("monitorserial")){
      monitorSerial();
    }
    if(key.equals("setwifi")){    
      setWifi();
      conectarWifi();
    }
    if(key.equals("setfone")){
      setFone();    
    }
    if(key.equals("getfone")){
        Serial.println("Fone: "+fone);
    }
    if(key.equals("setmsg")){
      setMsg();    
    }
    if(key.equals("getmsg")){
      Serial.println("Mensagem: "+msg);
    }
    if(key.equals("enviarsms")){
      enviarSms(fone,msg);
    }
    if(key.equals("ctrlz")){    
      mySerial.print((char)26);
      delay(500);
    }
    limparBufferSerial();
  }
  testarConexao();
  verificarModem();  
}
//funcoes do SMS
String verificarModem(){
  String str;
  if (mySerial.available()) { //Se o módulo GSM nos enviou algo, então
    digitalWrite(0, 1);//ledSMS
    str = mySerial.readStringUntil('\n');
    str.trim();//remover char final \n ou \r ou ...
    Serial.println("Modem: "+str);
    delay(500);
    digitalWrite(0, 0);//ledSMS
  }
  return str;
}
void configModem(){
  String resposta;
  Serial.println("configModem(ENTROU)");    
  do{
    mySerial.println("AT");  
    delay(500);
    verificarModem();
    resposta=verificarModem();
    delay(500);
   }while(resposta.equals("OK")==0);          
   
   do{
    mySerial.println("AT+CMGF=1");
    delay(500);
    verificarModem();
    resposta=verificarModem();
    delay(500);
   }while(resposta.equals("OK")==0);          
   
   do{
    mySerial.println("AT+CSCS=\"GSM\"");
    delay(500);
    verificarModem();    
    resposta=verificarModem();
    delay(500);
   }while(resposta.equals("OK")==0);    
  Serial.println("configModem(SAIU)");
}
void enviarSms(String fone, String msg) {
  String resposta;  
  Serial.println("enviarSms(ENTROU)");
  do{  
    do{
      digitalWrite(0, 1);//ledSMS
      mySerial.println("AT+CMGS=\"" + fone + "\"");
      delay(500); 
      verificarModem();
      resposta=verificarModem();//OK
      delay(500);
    }while(resposta.equals(">")==0);
  
    mySerial.print(msg);
    delay(500); 
    verificarModem();//msg
    delay(500);
    mySerial.print((char)26);//ctrlz
    delay(500); 
    verificarModem();//ctrlz
    resposta=verificarModem();//ERROR
  }while(resposta.equals("ERROR"));

  
  do{//lendo msgs
    resposta=verificarModem();//OK
  }while(resposta.equals("OK")==0);// !=OK
  
  digitalWrite(0, 0);//ledSMS  
  Serial.println("\nenviarSms(SAIU)");
}
void monitorSerial(){
  String str;
  Serial.print("MonitorSerial(ENTROU) ");
  do{
    if(Serial.available()){//Se digitar algo no Monitor Serial do Arduino IDE
      str=Serial.readStringUntil('\n');
      str.trim();//remover char final \n ou \r ou ...
      if(str.equals("ctrlz")){
        mySerial.println((char)26);
        delay(500);
      }else if(!str.equals("sair")){
        Serial.print("\nMonitorSerial: ");
        mySerial.println(str);//comando
        delay(500);
        verificarModem();//comando
      }
      verificarModem();//resposta do comando
    } 
  }while(str.equals("sair")==0);
  Serial.println("MonitorSerial(SAIU)");
}

package Serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

public class SerialComLeitura implements Runnable, SerialPortEventListener {

    public String Dadoslidos;
    public int nodeBytes;
    private int baudrate;
    private int timeout;
    private CommPortIdentifier cp;
    private SerialPort porta;
    private OutputStream saida;
    private InputStream entrada;
    private Thread threadLeitura;
    private boolean IDPortaOK;
    private boolean PortaOK;
    private boolean Leitura;
    private boolean Escrita;
    private String Porta;
    protected String peso;

    
    public SerialComLeitura( String p , int b , int t ){
        this.Porta = p;
        this.baudrate = b;
        this.timeout = t;
    }

    public void habilitarEscrita() {
        this.Escrita = true;
        this.Leitura = false;
    }
    
    public void habilitarLeitura() {
        this.Leitura = true;
        this.Escrita = false;
    }
    
    public void ObterIdDaPorta(){
        try {
            cp = CommPortIdentifier.getPortIdentifier(Porta);
            if ( cp == null ) {
                System.out.println(Messages.getString("SerialComLeitura.0")); //$NON-NLS-1$
                IDPortaOK = false;
                System.exit(1);
            }
            IDPortaOK = true;
        } catch (Exception e) {
            System.out.println(Messages.getString("SerialComLeitura.1") + e); //$NON-NLS-1$
            IDPortaOK = false;
            System.exit(1);
        }
    }
  
    
    public void AbrirPorta(){

        try {
            porta = (SerialPort)cp.open(Messages.getString("SerialComLeitura.2"), timeout); //$NON-NLS-1$
            PortaOK = true;

            //configurar parâmetros
            porta.setSerialPortParams(baudrate,
            porta.DATABITS_8,
            porta.STOPBITS_1,
            porta.PARITY_NONE);
            porta.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

        }catch(Exception e){
            PortaOK = false;
            System.out.println(Messages.getString("SerialComLeitura.3") + e); //$NON-NLS-1$
            System.exit(1);
        }
}

    public void LerDados(){

        if (Escrita == false){
            try {
                entrada = porta.getInputStream();
            } catch (Exception e) {
                System.out.println(Messages.getString("SerialComLeitura.4") + e); //$NON-NLS-1$
                System.exit(1);
            }

            try {
                porta.addEventListener(this);
            } catch (Exception e) {
                System.out.println(Messages.getString("SerialComLeitura.5") + e); //$NON-NLS-1$
                System.exit(1);

            }
            porta.notifyOnDataAvailable(true);

            try {
                threadLeitura = new Thread(this);
                threadLeitura.start();
                run();
            } catch (Exception e) {
                System.out.println(Messages.getString("SerialComLeitura.6") + e); //$NON-NLS-1$
            }
        }
    }
    
    
    public void EnviarUmaString(String msg){

        if (Escrita==true) {

            try {

                saida = porta.getOutputStream();
                System.out.println(Messages.getString("SerialComLeitura.7")); //$NON-NLS-1$

            } catch (Exception e) {

                System.out.println(Messages.getString("SerialComLeitura.8") + e ); //$NON-NLS-1$

            }

            try {

                System.out.println(Messages.getString("SerialComLeitura.9") + Porta ); //$NON-NLS-1$
                System.out.println(Messages.getString("SerialComLeitura.10") + msg ); //$NON-NLS-1$
                saida.write(msg.getBytes());
                Thread.sleep(100);
                saida.flush();

            } catch (Exception e) {
                System.out.println(Messages.getString("SerialComLeitura.11")); //$NON-NLS-1$
                System.out.println(Messages.getString("SerialComLeitura.12") + e ); //$NON-NLS-1$
                System.exit(1);
            }

        } 
        else {
            System.exit(1);
        }
    }

    public void run(){
        try {
            Thread.sleep(5);
        } catch (Exception e) {
            System.out.println(Messages.getString("SerialComLeitura.13") + e); //$NON-NLS-1$
        }
}

  
    public void setPeso(String peso) {
        this.peso = peso;
    }
    
    public String getPeso() {
        return this.peso;
    }


    public void serialEvent(SerialPortEvent ev) {
        StringBuffer bufferLeitura = new StringBuffer();
        
        int novoDado = 0;
        
        switch (ev.getEventType()) {

            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
            case SerialPortEvent.DATA_AVAILABLE:

                //Novo algoritmo de leitura.

                while(novoDado != -1){

                    try{
                        novoDado = entrada.read();
                        if(novoDado == -1){

                            break;

                        }

                        if('\r' == (char)novoDado){

                            bufferLeitura.append('\n');

                        }else{

                            bufferLeitura.append((char)novoDado);

                        }

                    }catch(IOException ioe){

                        System.out.println(Messages.getString("SerialComLeitura.14") + ioe); //$NON-NLS-1$

                    }

                }
                setPeso(new String(bufferLeitura));
                System.out.println(getPeso());
            break;
        }
    }
    
    public void FecharCom(){
            try {

                porta.close();

            } catch (Exception e) {

                System.out.println(Messages.getString("SerialComLeitura.15") + e); //$NON-NLS-1$
                System.exit(0);
            }
    }

    public String obterPorta(){
        return Porta;
    }
    
    public int obterBaudrate(){
        return baudrate;
    }
   
    
}

package Auxiliar;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Servidor extends Thread{
	private  int indiceJanela;
	private String portaOrigem;
	private String mensagem;
	private BufferMSG bufferMSG;//Passgem por referencia: buffer da Janela e Servidor
	
	private DatagramSocket ds;
	private byte[] msg;
	private DatagramPacket pkg;
	
	public Servidor(int indiceJanela,String portaOrigem,BufferMSG bufferMSG){
		this.setIndiceJanela(indiceJanela);
		this.portaOrigem=portaOrigem;
		this.bufferMSG=bufferMSG;//referencia
	}
	
	public void run(){
    	ouvir();
    }

	public void ouvir() {
		while(true){
			try {
				//Converte o argumento recebido para inteiro (numero da porta)      
				int port = Integer.parseInt(portaOrigem);				
				
				//Cria o DatagramSocket para aguardar mensagens, neste momento o m�todo fica bloqueando
				//at� o recebimente de uma mensagem
				ds = new DatagramSocket(port);

				//Preparando o buffer de recebimento da mensagem
				msg = new byte[256];	        
				//Prepara o pacote de dados
				pkg = new DatagramPacket(msg, msg.length);  

				System.out.println("Servidor.ouvir() bloqueado: " + port);
				//Recebimento da mensagem
				ds.receive(pkg);//Thread eh bloqueada aqui(esperando o pacote)????
				
				System.out.println("Servidor.ouvir() debloqueado: " + port);				
				mensagem = "  "+new String(pkg.getData()).trim()+"\n";
				
				//Atualiza o obj buffer por referencia
				bufferMSG.concatenarBufferMSG(mensagem);//Isto eh feito dentro da class Servidor pq ele eh bloqueada
				
				System.out.println("\tMensagem recebida: " + mensagem);
								
				bufferMSG.setMsgCli(false);//cores da msg no taChat
				bufferMSG.setMsgServ(true);//cores da msg no taChat
				
				//System.out.println("\tbufferMSG: " + bufferMSG.getBufferMSG());
				ds.close();				
			}catch(IOException ioe) {
				
			}
		}

	}
	    
	public void setPorta(String porta) {
		this.portaOrigem=porta;
	}
	public String getPorta() {
		return portaOrigem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem=mensagem ;
	}
	public String getMensagem() {
		return mensagem;
	}

	public int getIndiceJanela() {
		return indiceJanela;
	}

	public void setIndiceJanela(int indiceJanela) {
		this.indiceJanela = indiceJanela;
	}
	
}

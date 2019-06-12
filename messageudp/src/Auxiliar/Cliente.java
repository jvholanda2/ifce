package Auxiliar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
	
	public Cliente(){
		
	}
	
	public boolean enviarMensagem (String hostDestino,String portaDestino,String mensagem){
		System.out.println("Cliente.enviarMensagem()");
		try {
			//
			InetAddress hDestino = InetAddress.getByName(hostDestino);
			int pDestino = Integer.parseInt(portaDestino);
			byte[] msg = mensagem.getBytes();

			//Montar o pacote a ser enviado	      
			DatagramPacket pkg = new DatagramPacket(msg,msg.length, hDestino, pDestino);

			// Criar o DatagramSocket que ser� respons�vel por enviar a mensagem
			DatagramSocket ds = new DatagramSocket();

			//Enviar a mensagem
			ds.send(pkg);		
			//a janelaM concatema a msg no buffer... 
			
			System.out.println("\tMensagem enviada para: "+hDestino.getHostAddress()+":"+pDestino+", "+"Mensagem: "+mensagem);
			ds.close();
			return true; 
		}
		catch(IOException ioe) {
			System.err.println("Erro:"+ioe);
			return false;
		}
	}
}

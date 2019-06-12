package Auxiliar;

import java.awt.TextArea;

import javax.swing.UIManager;

public class AtualizaChat extends Thread{
	private TextArea taChat;
	private BufferMSG bufferMSG;
	
	public AtualizaChat(TextArea taChat,BufferMSG bufferMSG) {
		this.bufferMSG=bufferMSG;
		this.taChat=taChat;
	}
	public void run(){
		atualizar();
	}
	
	public void atualizar(){
		while(true){
			//Cor
			if(bufferMSG.getMsgServ()){
				//taChat.setForeground(Color.RED);
				taChat.setForeground(UIManager.getColor("OptionPane.errorDialog.border.background"));
			}else{
				//taChat.setForeground(new Color(0, 128, 0));
				taChat.setForeground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
			}
			taChat.setText(bufferMSG.getBufferMSG());
			
			//System.out.println("AtualizarChat.atualizar()");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
	}

	
}

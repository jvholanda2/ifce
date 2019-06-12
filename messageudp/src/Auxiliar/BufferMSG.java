package Auxiliar;

public class BufferMSG {
	//Var

	private String bufferMSG;//Passgem por referencia: buffer da Janela e Servidor
	private String mensagem;
	private boolean msgServ;
	private boolean msgCli;

	public BufferMSG() {
		setBufferMSG("");//Inicializando o Buffer
	}
	

	public void concatenarBufferMSG(String mensagem) {
		this.bufferMSG += mensagem;
	}
	
	public String getBufferMSG() {
		return bufferMSG;
	}

	public void setBufferMSG(String bufferMSG) {
		this.bufferMSG = bufferMSG;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public boolean getMsgServ() {
		return msgServ;
	}
	public void setMsgServ(boolean msgServ) {
		this.msgServ = msgServ;
	}
	public boolean getMsgCli() {
		return msgCli;
	}
	public void setMsgCli(boolean msgCli) {
		this.msgCli = msgCli;
	}
}

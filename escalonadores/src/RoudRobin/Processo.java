package RoudRobin;

public class Processo {

	private int Elemento;
	int tempoRestante;
	private int tempoRetorno;
	private int tempoResposta;
	private int prioridade;
	
	public Processo(){}
	
	public Processo(int elemento, int tempoRestante, int prioridade) {
		Elemento = elemento;
		this.tempoRestante = tempoRestante;
		this.prioridade = prioridade;
	}
	
	
	public int getElemento() {
		return Elemento;
	}
	public void setElemento(int elemento) {
		Elemento = elemento;
	}
	public int getTempoRestante() {
		return tempoRestante;
	}
	public void setTempoRestante(int tempoRestante) {
		this.tempoRestante = tempoRestante;
	}
	public int getTempoRetorno() {
		return tempoRetorno;
	}
	public void setTempoRetorno(int tempoRetorno) {
		this.tempoRetorno = tempoRetorno;
	}
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	public int getTempoResposta() {
		return tempoResposta;
	}
	public void setTempoResposta(int tempoResposta) {
		this.tempoResposta = tempoResposta;
	}
	
	public String toString(){
		
		return Elemento +" " ;
	}

}

package Arvore;

import java.util.ArrayList;



public class No {

	private No pai;
	private ArrayList<Integer> chaves = new ArrayList<Integer>();
	private ArrayList<No> filhos = new ArrayList<No>();
	boolean folha;
	
	
	public No(int ordem){
		folha = true;
		
	}
	
	
	
	public ArrayList<Integer> getChaves() {
		return chaves;
	}

	public void setChaves(ArrayList<Integer> chaves) {
		this.chaves = chaves;
	}

	public ArrayList<No> getFilhos() {
		return filhos;
	}

	public void setFilhos(ArrayList<No> filhos) {
		this.filhos = filhos;
	}

	

	public void setFolha(boolean folha) {
		this.folha = folha;
	}
	public void imprimirChaves(){
		for (int i = 0; i < chaves.size(); i++) {
			System.out.print(" "+chaves.get(i) );
		}
		System.out.println();
	}
	public boolean isFolha(){
		if(filhos.size()==0){
			return true;
		}
		return false;
	}

}

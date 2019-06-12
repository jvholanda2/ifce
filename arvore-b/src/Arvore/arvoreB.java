package Arvore;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class arvoreB {
	private No raiz;
	private int qtdChaves;
	private int ordem;
	
	public arvoreB(int ordem){
		raiz = new No(ordem);
		qtdChaves = 0;
		this.ordem = ordem;
	}
	
	public <T> No inserir(int elemento){
		
		if(qtdChaves < ordem){
			
			if(raiz.getChaves().contains(elemento)){
				System.out.println("Elemento já existe!");
				return null;
			
			}else{
				
				raiz.getChaves().add(elemento);
				Collections.sort(raiz.getChaves());
				qtdChaves++;
				
				if(noCheio(raiz)){
					System.out.println("Cheio! *");
					raiz = split(raiz);	
				}		
				return raiz;
			}
		}

		No no = buscaNo(elemento);
		
		if (no ==null) {
			System.out.println("Elemento já existente");
			return null;
		}else{
			
		
			no.getChaves().add(elemento);	
			Collections.sort(no.getChaves());
			qtdChaves++;
			
			if(noCheio(no)){
				System.out.println("Cheio! *");
				balancear(raiz,null,elemento);	
				
			}			
		}
			
		
		return no;
		
	}
	
	
	private No split(No no) {
		
		No noPai = new No(ordem);
		No noFilho1 = new No(ordem);
		No noFilho2 = new No(ordem);
		
		
		
		int indice = no.getChaves().size()/2;
		
		noPai.getChaves().add(no.getChaves().get(indice));
		
		for (int i = 0; i < indice; i++) {
			noFilho1.getChaves().add(no.getChaves().get(i));
		}
		for (int i = indice+1; i < no.getChaves().size(); i++) {
			noFilho2.getChaves().add(no.getChaves().get(i));
		}
		
		
		noPai.getFilhos().add(noFilho1);
		noPai.getFilhos().add(noFilho2);
		
		return noPai;
		
	
	}
	
	
	private No split1(No no) {
		
		No noRaiz = new No(ordem);
		No noFilho1 = new No(ordem);
		No noFilho2 = new No(ordem);
		
		
		
		int indice = no.getChaves().size()/2;
		
		noRaiz.getChaves().add(no.getChaves().get(indice));
		
		for (int i = 0; i < indice; i++) {
			noFilho1.getChaves().add(no.getChaves().get(i));
		}
		for (int i = indice+1; i < no.getChaves().size(); i++) {
			noFilho2.getChaves().add(no.getChaves().get(i));
		}
		
		
		noRaiz.getFilhos().add(noFilho1);
		noRaiz.getFilhos().add(noFilho2);
		
		for (int i = 0; i < no.getFilhos().size()/2; i++) {
			noFilho1.getFilhos().add(no.getFilhos().get(i));
		}
		for (int i = no.getFilhos().size()/2; i < no.getFilhos().size(); i++) {
			noFilho2.getFilhos().add(no.getFilhos().get(i));
		}
		raiz = noRaiz;
		return noRaiz;
		
	
	}
	
	private void ordFilhos(No no){
		No aux;
		
		for (int i = 0; i < no.getFilhos().size(); i++) {
			for (int j = 0; j < no.getFilhos().size(); j++) {
				if(no.getFilhos().get(i).getChaves().get(0) < no.getFilhos().get(j).getChaves().get(0)){
					aux = no.getFilhos().get(j);
					no.getFilhos().set(j,no.getFilhos().get(i));
					no.getFilhos().set(i,aux);	
				}	
			}	
		}
		
	}
	
	private void dividir(No no,No noPai) {
		if(noPai != null){
			No noFilho1 = new No(ordem);
			No noFilho2 = new No(ordem);
			
			int indice = no.getChaves().size()/2;
			
			for (int i = 0; i < noPai.getFilhos().size(); i++) {
				
			//remover ponteiro para dividi-lo - primeiro procura em que pagina esta o elemento
				if(noPai.getFilhos().get(i).getChaves().contains(   no.getChaves().get(indice)   ) ){
					//apos descorberto o indice da apgina , excluimosp para dvidi-la
					noPai.getFilhos().remove(i);
				}
			}
			// pai recebe a chave que subiu apos a pagina filha estar cheia
			noPai.getChaves().add(no.getChaves().get(indice));
			Collections.sort(noPai.getChaves());
			
			
			// preenchimento dos novos dois filhos a direita e esquerda
			for (int i = 0; i < indice; i++) {
				noFilho1.getChaves().add(no.getChaves().get(i));
				
			}
			for (int i = indice+1; i < no.getChaves().size(); i++) {
				noFilho2.getChaves().add(no.getChaves().get(i));
				
			}
			
			if(!no.isFolha()){
				// se nó interno noFilho terá filhos e precisamos setalos
				for (int i = 0; i < indice+1; i++) {
					noFilho1.getFilhos().add(no.getFilhos().get(i));
					
				}
				for (int i = indice+1; i < no.getChaves().size()+1; i++) {
					noFilho2.getFilhos().add(no.getFilhos().get(i));
				
				}
			}
			
			no.getFilhos().removeAll(no.getFilhos());
			noPai.getFilhos().add(noFilho1);
			noPai.getFilhos().add(noFilho2);
			ordFilhos(noPai);
			
			
		}
		else{
			
			split1(no);
		}

	}
	
	
	
	
	public void balancear(No no,No noPai, int elemento){
		for (int i = 0; i < no.getChaves().size(); i++) {
			
			//enquanto meu elemento foi maior que os valores da chave da pagina
			//continuo para a proxima chave
			
			if(elemento == no.getChaves().get(i)){
				
				if(noCheio(no)){		
					dividir(no,noPai);
					break;
				}
			}
			 	
			// se o elemento é menor do que a chave passa o ponteiro esquerda da chave
			else if(elemento < no.getChaves().get(i)){
				
				if(no.getFilhos().size() != 0){
					balancear( no.getFilhos().get(i),no,elemento);
						
					if(noCheio(no)){		
						dividir(no,noPai);
						break;
					}	
				}
			}
			// se é a ultima chave da pagina, passa o ponteiro o ultimo ponteiro
			else if( i == (no.getChaves().size()-1)){
				
				if(no.getFilhos().size() != 0){
					balancear( no.getFilhos().get(i+1),no,elemento);
										
					if(noCheio(no)){		
						dividir(no,noPai);
						break;
					}
	
				}
			}
		}
	
}	

	public No buscaNo(int elemento){
		return busca(raiz, elemento);
		
	}
	public No busca(No no, int elemento){
		
		for (int i = 0; i < no.getChaves().size(); i++) {	
			if(elemento == no.getChaves().get(i)){return null;}
			 
				// concluir se o posso valor é maior que o elemento
			// se sim passo o ponteiro do filho
			if(elemento < no.getChaves().get(i)){
				
				if(no.getFilhos().size() != 0){
					return busca( no.getFilhos().get(i),elemento);
				}else{
					return no;
				}
			}
			
			else if( i == (no.getChaves().size()-1)){
				
				if(no.getFilhos().size() != 0){
					return busca( no.getFilhos().get(i+1),elemento);
				}else{
					return no;
				}
			}
		}
		return null;
	}
	
public No buscaNoRemovido(No no, int elemento){
		
		for (int i = 0; i < no.getChaves().size(); i++) {	
			if(elemento == no.getChaves().get(i)){return no;}
			 
				// concluir se o posso valor é maior que o elemento
			// se sim passo o ponteiro do filho
			if(elemento < no.getChaves().get(i)){
				if(no.getFilhos().size() != 0){
					return buscaNoRemovido( no.getFilhos().get(i),elemento);
				}else{
					return no;
				}
			}
			else if( i == (no.getChaves().size()-1)){
				
				if(no.getFilhos().size() != 0){
					return buscaNoRemovido( no.getFilhos().get(i+1),elemento);
				}else{
					return no;
				}
			}
		}
		return null;
	}
	
	public No buscaPai(No no,No noPai, int elemento){
		int index=0;
		
		for (int i = 0; i < no.getChaves().size(); i++) {
			
			
			if(elemento == no.getChaves().get(i)){return noPai;}
			 
				// concluir se o posso valor é maior que o elemento
			// se sim passo o ponteiro do filho
			if(elemento < no.getChaves().get(i)){
				
				if(no.getFilhos().size() != 0){
					return buscaPai( no.getFilhos().get(i),no,elemento);
				}else{
					return noPai;
				}
			}
			
			else if( i == (no.getChaves().size()-1)){
				
				if(no.getFilhos().size() != 0){
					return buscaPai( no.getFilhos().get(i+1),no,elemento);
				}else{
					return noPai;
				}
			}
			
		}
		return null;
	}
	
	
	public boolean noCheio(No no){
		if(no.getChaves().size() == ordem){
			return true;
		}
		return false;
	}
	
	public void imprimirArvore(){
		imprimirArvore(raiz);
	}
	public void imprimirArvore(No no){
		
		no.imprimirChaves();
		for (int i = 0; i < no.getFilhos().size(); i++) {
			imprimirArvore(no.getFilhos().get(i));
		}
	}
	
	public void remover(int elemento){
		No no = buscaNoRemovido(raiz,elemento);
		No noPai = buscaPai(raiz,null,elemento);
		remover(no,noPai,elemento);
	}
	public void remover(No no,No noPai, int elemento){
		No irmaoDir =null;
		No irmaoEsq =null;
		int indice=0;
		int indiceElemento=0;
		Integer indiceIrmaoDir = null;
		Integer indiceIrmaoEsq = null;
		indiceElemento = no.getChaves().indexOf(elemento);
		
		// pega o indice da pagina e do pai
		if(noPai!=null){ //caso nao seja raiz
			for (int i = 0; i < noPai.getChaves().size(); i++) {
				if(elemento < noPai.getChaves().get(i)){
					indice = i;
				}else{
					indice = noPai.getChaves().size();
				}
				break;
			}
			
			indiceIrmaoDir = indiceIrmaoDir(no, noPai, elemento);
			indiceIrmaoEsq = indiceIrmaoEsq(no, noPai, elemento);
			// pega os irmaos se existe
			if(indiceIrmaoDir != null){
				
				irmaoDir = noPai.getFilhos().get(indiceIrmaoDir);
			}
			if(indiceIrmaoEsq != null){
				irmaoEsq = noPai.getFilhos().get(indiceIrmaoEsq);
			}
		}
		
		
		if(no.isFolha()){
			if(no.getChaves().size() > (ordem-1)/2){
				
				//apenas exclui
				no.getChaves().remove(indiceElemento);
				
			}else if(irmaoDir != null || irmaoEsq != null){
				if(irmaoDir != null && irmaoDir.getChaves().size() > (ordem-1)/2){
					
					redistribuicao(no,noPai,indiceIrmaoDir,elemento);
				}
				else if(irmaoEsq != null && irmaoEsq.getChaves().size() > (ordem-1)/2){
					
					redistribuicao(no,noPai,indiceIrmaoEsq,elemento);
					
				}
				else if(irmaoEsq != null && irmaoEsq.getChaves().size()+no.getChaves().size() < ordem){
					
			
					concatenacaoRecursiva(raiz, null,elemento);
					
				}else if(irmaoDir != null && irmaoDir.getChaves().size()+no.getChaves().size() < ordem){
		
					concatenacaoRecursiva(raiz, null,elemento);
					
				}
				
				
			}
		}
		else{
			//nó interno
			int indice1 =0;
			No eSucsPai=null;
			No elementoSucessor = sucessorNo(no.getFilhos().get(indiceElemento+1),null); //pagina do elemento sucessor
			No irmaoSucessor;
		//	Integer indiceIrmaoSucessor = null;
			
				indice1 = no.getChaves().indexOf(elemento)+1;
				eSucsPai = noPai(no.getFilhos().get(indice1),no); // pai da pagina do elemento sucessor
			
				
			//irmaoSucessor = eSucsPai.getFilhos().get(indice);
		//	indiceIrmaoSucessor = indiceIrmaoDir(elementoSucessor, eSucsPai,elementoSucessor.getChaves().get(0));
				

			if(eSucsPai.getChaves().get(0)==raiz.getChaves().get(0)){
				indice = 0;
			}else { indice = 1; }	// pega o indice ponteiro do irmao		
			irmaoSucessor = eSucsPai.getFilhos().get(indice);
			
			// apenas subtituição e exclusão do elelemtno substituto
			if(elementoSucessor.getChaves().size()> (ordem-1)/2){
			
				no.getChaves().set(indiceElemento, elementoSucessor.getChaves().get(0));
				elementoSucessor.getChaves().remove(0);
			}
				
			
			else if(elementoSucessor.getChaves().size() <= (ordem-1)/2 && irmaoSucessor.getChaves().size() > (ordem-1)/2){
				no.getChaves().set(indiceElemento, elementoSucessor.getChaves().get(0));	
				redistribuicao(elementoSucessor,eSucsPai,indice,elementoSucessor.getChaves().get(0)); 	
							
			}else{
					int elementoSucessorValor = elementoSucessor.getChaves().get(0);
					concatenacaoRecursiva(raiz, null, elementoSucessorValor);
					System.out.println(raiz);
					no = buscaNoRemovido(raiz, elemento);
					indice = no.getChaves().indexOf(elemento);
					no.getChaves().set(indice, elementoSucessorValor);
					
			}	
		
			
		}
	}
	
	public void concatenacao(No no,No noPai,int elemento){
		Integer indiceIrmaoDir = null;
		Integer indiceIrmaoEsq = null;
		No irmaoEsq =null;
		No irmaoDir =null;
		int indice=0;
		int irmaoEscolhido = 0; // 0 = irmaoEsq 1= irmaoDir
		int indiceElementoPai = 0 ;
		for (int i = 0; i < noPai.getChaves().size(); i++) {
			
			if(no.getFilhos().size() < (ordem-1)/2){
				indice = i;
			}else{
				indice = noPai.getChaves().size()-1;
			}break;
		}
		
		if(noPai != null){
			indiceIrmaoDir= indiceIrmaoDir(no, noPai);
			indiceIrmaoEsq= indiceIrmaoEsq(no, noPai);
			if(indiceIrmaoDir!=null){
				irmaoDir = noPai.getFilhos().get(indiceIrmaoDir);
			}
			if(indiceIrmaoEsq!=null){
				irmaoEsq = noPai.getFilhos().get(indiceIrmaoEsq);
			}
		}
		
		if(irmaoEsq != null && irmaoEsq.getChaves().size()+no.getChaves().size() < ordem){
			indiceElementoPai = indiceElementoPai(noPai, irmaoEsq, elemento);
			//		concatenacao();
			irmaoEsq.getChaves().addAll(no.getChaves());
			irmaoEsq.getChaves().add(noPai.getChaves().get(indiceElementoPai));
			irmaoEsq.getFilhos().addAll(no.getFilhos());
			irmaoEscolhido=0;	
			ordFilhos(irmaoEsq);
			Collections.sort(irmaoEsq.getChaves());
		}else if(irmaoDir != null && irmaoDir.getChaves().size()+no.getChaves().size() < ordem){
			indiceElementoPai = indiceElementoPai(noPai, irmaoDir, elemento);
			//			concatenacao();
			irmaoDir.getChaves().addAll(no.getChaves());
			irmaoDir.getChaves().add(noPai.getChaves().get(indiceElementoPai));
			irmaoDir.getFilhos().addAll(no.getFilhos());
			irmaoEscolhido=1;
			ordFilhos(irmaoDir);
			Collections.sort(irmaoDir.getChaves());
		}
		
		noPai.getChaves().remove(indiceElementoPai);
		int indiceNo = 0;
		for (int j = 0; j < noPai.getFilhos().size(); j++) {
			if(noPai.getFilhos().get(j).getChaves().equals(no.getChaves())){
				indiceNo = j;
			}
		}
		noPai.getFilhos().remove(indiceNo);
		ordFilhos(noPai);
		Collections.sort(noPai.getChaves());
		
		if( no.getChaves().size()==0 && noPai ==null){ //se a raiz ficar vazia o irmao será a raiz
			if (irmaoEscolhido == 0){ raiz=irmaoEsq; } else { raiz=irmaoDir; }
		}else if(raiz.getChaves().size() == 0){
			raiz = raiz.getFilhos().get(0);
		}
		
		
		
}
	
	public void concatenacaoRecursiva(No no,No noPai, int elemento){
		
		try{
			for (int i = 0; i < no.getChaves().size(); i++) {
				
				//enquanto meu elemento foi maior que os valores da chave da pagina
				//continuo para a proxima chave
				
				if(elemento == no.getChaves().get(i)){
					int index = no.getChaves().indexOf(elemento);
					no.getChaves().remove(index);
					if(no.getChaves().size() < (ordem-1)/2){
						
						concatenacao(no,noPai,elemento);
						break;
					}
				}
				 	
				// se o elemento é menor do que a chave passa o ponteiro esquerda da chave
				if(elemento < no.getChaves().get(i)){
					
					if(no.getFilhos().size() != 0){
						concatenacaoRecursiva( no.getFilhos().get(i),no,elemento);
							
						if(no.getChaves().size() < (ordem-1)/2){		
							concatenacao(no,noPai,elemento);
						break;
						}	
					}
	
				}
				// se é a ultima chave da pagina, passa o ponteiro o ultimo ponteiro
				else if( i == (no.getChaves().size()-1)){
					
					if(no.getFilhos().size() != 0){
						concatenacaoRecursiva( no.getFilhos().get(i+1),no,elemento);
											
						if(no.getChaves().size() < (ordem-1)/2){		
							concatenacao(no,noPai,elemento);
							break;
						}
		
					}
				}

			}
		}catch(IndexOutOfBoundsException e){
		
		}
		catch(NullPointerException a){
		
		}
		
	
}	
	
	
	
	public int indiceElementoPai(No noPai,No irmao,int elemento){
		int indice=0;
		for (int i = 0; i < noPai.getChaves().size(); i++) {
			
			if(elemento < noPai.getChaves().get(i) && noPai.getChaves().get(i) < irmao.getChaves().get(0) ){
				indice = i;
			}
			else if(elemento > noPai.getChaves().get(i) && noPai.getChaves().get(i) > irmao.getChaves().get(0)){
				indice = i;
			}
		}
		return indice;
	}
	
	public void redistribuicao(No no,No pai,int indiceIrmao,int elemento){
		int indiceElemento = no.getChaves().indexOf(elemento);
		int indice=0;
		No irmao = pai.getFilhos().get(indiceIrmao);
		int elementoPai =0;
		// pega o indice da pagina e do pai 
		indice = indiceElementoPai(pai,irmao, elemento);
		
		if(no.getChaves().size() > 0 ){
			no.getChaves().remove(indiceElemento);
		}
		
		elementoPai = pai.getChaves().get(indice);
			// irmao da esuqerda
		if(pai.getChaves().get(indice) > pai.getFilhos().get(indiceIrmao).getChaves().get(0)){ 
			
				
			
			pai.getChaves().add(irmao.getChaves().get(irmao.getChaves().size()-1));
			Collections.sort(pai.getChaves());
			
			irmao.getChaves().remove(irmao.getChaves().size()-1);
			
			int indiceElementoPai = pai.getChaves().indexOf(elementoPai);
			no.getChaves().add(pai.getChaves().get(indiceElementoPai));
			pai.getChaves().remove(indiceElementoPai);
				
		}else{ //irmao direita
			
			pai.getChaves().add(irmao.getChaves().get(0));
			Collections.sort(pai.getChaves());
			irmao.getChaves().remove(0);
			
			int indiceElementoPai = pai.getChaves().indexOf(elementoPai);
			no.getChaves().add(pai.getChaves().get(indice));
			pai.getChaves().remove(indice);
		}
			
		Collections.sort(no.getChaves());
		
}
	
	
	public No noPai(No no,No noPai){
		if(no.getFilhos().size() == 0){
			return noPai;
		}
		return noPai(no.getFilhos().get(0),no);
	}
	public No sucessorNo(No no,No noPai){
		if(no.getFilhos().size() == 0){
			return no;
		}
		return sucessorNo(no.getFilhos().get(0),no);
	}
	
	public Integer indiceIrmaoEsq(No no,No pai,int elemento){
		int indice = 0;
		for (int i = 0; i < pai.getFilhos().size(); i++) {
			if(pai.getFilhos().get(i).getChaves().contains(elemento)){
				indice = i;
				break;
			}
		}
		if(indice == 0){ return null;} //nao há irmao a esquerda
		return indice-1;
	}
	
	public Integer indiceIrmaoDir(No no,No pai,int elemento){
		int indice = 0;
		for (int i = 0; i < pai.getFilhos().size(); i++) {
			if(pai.getFilhos().get(i).getChaves().contains(elemento)){
				indice = i;
				break;
			}
		}
		if(indice == pai.getFilhos().size()-1){ return null;} //nao há irmao a esquerda
		return indice+1;
	}
	public Integer indiceIrmaoEsq(No no ,No pai){
		Integer indice=null;
		for (int i = 0; i < pai.getFilhos().size(); i++) {
			if(pai.getFilhos().get(i).getChaves().size() < (ordem-1)/2){
				indice = i;
				break;
			}
		}
		if(indice == 0){ return null;} //nao há irmao a esquerda
		return indice-1;
	}
	public Integer indiceIrmaoDir(No no ,No pai){
		Integer indice=null;
		for (int i = 0; i < pai.getFilhos().size(); i++) {
			if(pai.getFilhos().get(i).getChaves().size() < (ordem-1)/2){
				indice = i;
				break;
			}
		}
		if(indice == pai.getFilhos().size()-1){ return null;} //nao há irmao a esquerda
		return indice+1;
	}
}

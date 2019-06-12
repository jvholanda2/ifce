package Arvore;

import java.util.Collections;

public class teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		arvoreB b = new arvoreB(3);
		
		b.inserir(10);
		b.inserir(20);
		b.inserir(30);
		b.inserir(40);	
		b.inserir(50);
		b.inserir(60);
		b.inserir(70);
		b.inserir(15);
		b.inserir(25);
		b.inserir(28);	
		b.inserir(27);
		b.remover(15);
		b.inserir(80);
		b.inserir(90);
		
		
		b.imprimirArvore();
		System.out.println();
		
	}

}               
import java.io.*;

/**

 * João Júnior 
 * 		  
 * */
class Main{

	//Arquivo...
	private static FileReader arqFR;
	private static BufferedReader lerArqBR;
	public static PrintTour p1 ;

	
	public static void main(String args[]) throws IOException {
		
		System.out.println("\nGrafo0.txt");
		carregarArquivo("Grafo0.txt");
		p1.testar();		
		
		System.out.println("\nGrafo1.txt");
		carregarArquivo("Grafo1.txt");
		p1.testar();		
		
		System.out.println("\nGrafo2.txt");
		carregarArquivo("Grafo2.txt");
		p1.testar();		
		
		System.out.println("\nGrafo3.txt");
		carregarArquivo("Grafo3.txt");
		p1.testar();		
		
		System.out.println("\nGrafo4.txt");
		carregarArquivo("Grafo4.txt");
		p1.testar();		
		
		System.out.println("\nGrafo5.txt");
		carregarArquivo("Grafo5.txt");
		p1.testar();		
}
	
	protected static void carregarArquivo(String arq) throws IOException{
		System.out.println("Main.CarregarArq():");
		try{
			arqFR = new FileReader(arq);			
			lerArqBR = new BufferedReader(arqFR);
			

			int nV = Integer.parseInt(lerArqBR.readLine());
			//Inicializando o Grafo depois de ter lido o no. de vertice
			p1 = new PrintTour(nV);			
			
			String linha = lerArqBR.readLine();
			while(linha!=null){
				//lendo uma linha do arquivo
				String[] vert = linha.split(" ",2);
				System.out.println("\t"+vert[0]+"-"+vert[1]);				
				
				//armazenando os vertices na lista de strings
		    	int v = p1.setVertice(vert[0]);
		    	int w = p1.setVertice(vert[1]);

		    	//v e w sao os valores inteiros que repesentarao os vertices.
		    	// p.e. Vertice Vermelho podera ser representado por 0
		    	//		Vertice Azul podera ser representado por 1
		    	// Em outras palavras, ambas as classes trabalharao com as 
		    	// as representacoes de inteiros de cada vertice, nao com as strings!
				p1.addAresta(v,w);				
				linha= lerArqBR.readLine();				
			}	
			arqFR.close();
			lerArqBR.close();
		}catch(IOException e){
			System.out.println("Arquivo nao existe ou foi apagado!: "+e);			
		}
	}	
}

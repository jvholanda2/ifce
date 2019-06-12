import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class PrintTour{ 
	protected static int nV;// No. de vertices	
	protected static ArrayList <String>  vertice;
	protected static LinkedList<Integer> adjacente[];
	protected static boolean conectado;
	protected static boolean verticesPar;

	//Construtor		
	public PrintTour(int nV) {
		this.nV = nV;
		adjacente = new LinkedList[nV];
		vertice = new ArrayList<String>();
		for (int i=0; i<nV; ++i){
			adjacente[i] = new LinkedList();
		}
		conectado = false;
		verticesPar = true;
	}

	//adiciona
	static void addAresta(int v, int w){
		adjacente[v].add(w);
		adjacente[w].add(v);
	}

	// DFS que conta vértices alcançáveis ​​de v
	private static int DFSCont(int v, boolean[] visitado) {//Tour
		int cont =1;
		visitado[v]=true;

		int i=-1;
		for (int j = 0; j < adjacente[v].size(); j++) {
			i=adjacente[v].get(j);
			if(visitado[i]==false){
				cont = cont + DFSCont(i,visitado);
			}
		}

		return cont;
	}


	/***********       Metodos para verificar se o Grafo eh ou nao Euleriano     ******************/
	
	// Método para verificar se todos os vértices de grau não-zero estão conectados realmente 
	// DFS marca todos os vertice visitado = true. Se o grafo for conexo, todos serao visitados,
	// caso contrario havera um ou mais vértices com visitado = false
	static	boolean conectado(){
		//System.out.println("Grafo.conectado() ");  
		boolean visitado[] = new boolean[nV];//tudo false
		int i; 
		// Encontre um vértice com grau não-zero (vertice conectado)
		// Ao encontrar esse vertice, posso iniciar o DFS para averiguacao do grafo 
		for (i = 0; i < nV; i++){
			//System.out.println("\tadj["+i+"], qtd Adj: "+adj[i].size());
			if (adjacente[i].size() != 0)
				break;
		}
		// Se não houver aresta no grafo, retornar verdadeiro
		if (i == nV)
			return true;

		// Inicia a passagem DFS de um vértice com grau não-zero (vertice conectado)
		DFSCont(i, visitado);

		//Verifique se todos os vértices de grau não-zero (vertice conectado) foram visitados
		for (i = 0; i < nV; i++)
			if (visitado[i] == false && adjacente[i].size() > 0)
				return false;
		return true;
	}

	
	/***************     Metodos para Imprimir a Tour *************************/
	private static void removerAresta(int u, int v) {
		int valorU=-1;
		for (int i = 0; i < adjacente[u].size(); i++) {
			valorU=adjacente[u].get(i);			
			if(valorU==v){
				adjacente[u].remove(i);
			}
		}
		int valorV=-1;
		for (int i = 0; i < adjacente[v].size(); i++) {
			valorV=adjacente[v].get(i);			
			if(valorV==u){
				adjacente[v].remove(i);
			}
		}
	}
	
	//A função para verificar se aresta u-v pode ser considerada como a próxima aresta na Tour
	private static boolean validarProximaAresta(int u, int v) {
		//# A aresta u-v é válida em um dos seguintes dois casos:
		//	 1) Se v é o único vértice adjacente de você
		if(adjacente[u].size() == 1){//se eh uma ponte...
			return true;
		}else{
			//2) Se houver múltiplos adjacentes, então u-v não é uma ponte
			//Faça as seguintes etapas para verificar se u-v é uma ponte

			//2.a) contagem de vértices alcançáveis ​​de u 		
			boolean[] visitado = new boolean[nV];
			int cont1 = DFSCont(u, visitado);

			//2.b) Remova a aresta (u, v) e depois de remover a aresta, conte
			//vértices alcançáveis ​​de u 

			removerAresta(u, v);
			visitado = new boolean[nV];
			int cont2 = DFSCont (u,visitado);

			//2.c) Adicione a aresta de volta ao grafo
			addAresta(u,v);

			//2.d) Se count1 for maior, então aresta(u, v) é uma ponte
			return (cont1>cont2) ? false : true;
		}
	}

	//Imprimir o passeio de Euler a partir do vértice u
	
	//O Tour Euleriano comeca pela visita dos vertice adj de vertice 0
	//ao validar a aresta, removemos-a e seguimos para proxima validacao de aresta vizinha...
	private static void printTourEuleriano(int u) {
		//Volta atras para todos os vértices adjacentes a este vértice
		int v=-1;
		for (int i = 0; i < adjacente[u].size(); i++) {
			v=adjacente[u].get(i);
			//Se a aresta u-v não for removida, é uma aresta próxima válida
			if(validarProximaAresta(u,v)){
				System.out.print(vertice.get(u)+"-"+vertice.get(v)+" ");
				removerAresta(u,v);
				printTourEuleriano(v);
			}
		}   
	}

	//Ponto de partida
	public static void testar(){//Grafo Eleriano: conectado e todos vertices pares
		printGrafo();//
		
		conectado = conectado();
		//checando se todo os vertices sao pares
		for (int i = 0; i < nV; i++){
			if (adjacente[i].size()%2!=0){// eh impar?
				verticesPar=false;
				break;
			}
		}
		
		if (conectado && verticesPar){//true e true
			System.out.print("O Grafo Euleriano \n[Tour Euleriana]: ");
			printTourEuleriano(0);
			System.out.println("\n");
		}else{
			System.out.println("O Grafo nao eh Euleriano");
		}
	}	

	// add um vertice; retona indice para o addAresta() construir a lista adjacente[]
	// com os inteiros representantes das strings
	protected static int setVertice(String vert){
		//verificar se o vert ainda nao foi add
		if(!vertice.contains(vert)){
			vertice.add(vert);
		}
		return vertice.indexOf(vert);
	}

	
	public static void printGrafo(){
		System.out.println("Grafo:");    	
		for (int i = 0; i<adjacente.length; i++) {
			System.out.print("\tv: "+vertice.get(i)+"\n\t Vizinhos: ");
			for (int j = 0; j < adjacente[i].size(); j++) {
				//				      ( v e r t i c e).( V  e  r  t  A  d  j).valor
				System.out.print(" "+vertice.get(adjacente[i].get(j)));
			}
			System.out.println("\n");
		}    	
	}
}

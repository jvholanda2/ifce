package Principal;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.List;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

//Esta igual aa versao 2 em termos de janela com problemas, 
// mas foi acrescentada algumas classes.

@SuppressWarnings({ "unused", "serial" })
public class Principal extends JFrame {
 
	//Variaveis
	private String todosIPs;
	private int indiceJanela=-1;
	private int tempIndiceJanela;
	private String nomeUsuario;
	private String hostOrigem;
	private String portaOrigem;
	private String hostDestino;
	private String portaDestino;
	private String sugestaoIP;

	private String tempPortaOrigem;
	private String tempHostDestino;
	private String tempPortaDestino;

	private JPanel contentPane;	
	private JLabel lblNomeUsuario;
	private JLabel lblMeuIp;
	private JLabel lblHostOrigem; 
	private JLabel lblHistrico;
	private JTextField txtPortaOrigem;
	private JTextField txtHostDestino;
	private JTextField txtPortaDestino;
	private JButton btnAdd;
	private TextArea taHistorico ;
	private List liAmigos ;	

	//ArrayLists
	public ArrayList<JanelaM> aliJanAbertas;
	//listas usadas para controle no add de amigos, so no Principal
	public ArrayList<String> aliHostDestino;
	public ArrayList<String> aliPortasDestino;
	public ArrayList<String> aliPortasOrigem;

	//Obejtos
	public static Principal principal;
	public static Login login;


	//Arquivo...
	private File arquivoF ;
	private FileReader arqFR;
	private BufferedReader lerArqBR;
	private FileWriter arqW;
	private BufferedWriter escrArqBW;
	private JLabel lblLegendas;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					principal= new Principal();//Salta para o Construtor abaixo
					principal.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SocketException 
	 */

	public Principal() {

		setTitle("MESSENGER UDP");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 730);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		lblNomeUsuario = new JLabel("");
		lblNomeUsuario.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNomeUsuario.setBounds(125, 8, 280, 30);
		contentPane.add(lblNomeUsuario);

		//Instanciando
		aliJanAbertas = new ArrayList<>();
		aliHostDestino = new ArrayList<>();
		aliPortasDestino = new ArrayList<>();
		aliPortasOrigem = new ArrayList<>();		

		txtPortaDestino = new JTextField();		
		txtPortaDestino.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				txtPortaDestino.setNextFocusableComponent(txtPortaOrigem);
			}
		});
		txtPortaDestino.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				char c = evt.getKeyChar() ;
				if(c=='\n'){
					verificarAntesADD();
				}
			}
		});

		lblMeuIp = new JLabel("Meu IP ");
		lblMeuIp.setFont(new Font("Dialog", Font.BOLD, 16));
		lblMeuIp.setBounds(62, 45, 200, 23);
		contentPane.add(lblMeuIp);

		lblHostOrigem = new JLabel("");
		lblHostOrigem.setFont(new Font("Dialog", Font.BOLD, 16));
		lblHostOrigem.setBounds(62, 70, 200, 23);
		contentPane.add(lblHostOrigem);
		txtPortaDestino.setBounds(274, 130, 131, 22);
		contentPane.add(txtPortaDestino);
		txtPortaDestino.setColumns(10);

		txtPortaOrigem = new JTextField();
		txtPortaOrigem.setFont(new Font("Dialog", Font.BOLD, 14));		
		txtPortaOrigem.setText("1024");	
		txtPortaOrigem.setBounds(272, 70, 131, 23);
		contentPane.add(txtPortaOrigem);
		txtPortaOrigem.setColumns(10);

		JLabel lblEnviar = new JLabel("Porta de destino");
		lblEnviar.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblEnviar.setBounds(274, 110, 131, 15);
		contentPane.add(lblEnviar);

		JLabel lblOrigem = new JLabel("Porta de origem");
		lblOrigem.setFont(new Font("Dialog", Font.BOLD, 14));
		lblOrigem.setBounds(274, 45, 131, 23);
		contentPane.add(lblOrigem);

		JLabel lblAmigo = new JLabel("IP do amigo");
		lblAmigo.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblAmigo.setBounds(62, 110, 200, 15);
		contentPane.add(lblAmigo);

		txtHostDestino = new JTextField();		
		txtHostDestino.setBounds(62, 130, 200, 22);
		contentPane.add(txtHostDestino);
		txtHostDestino.setColumns(10);

		btnAdd = new JButton("+");
		btnAdd.setFont(new Font("Dialog", Font.BOLD, 16));
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c= e.getKeyChar();
				if(c=='\n'){
					verificarAntesADD(); 
				}
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verificarAntesADD(); 					
			}
		});
		btnAdd.setBounds(358, 160, 47, 22);
		contentPane.add(btnAdd);

		liAmigos = new List();
		liAmigos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(c=='\n'){
					//True = Carregou a linha nas Variaveis temps				
					if(getLinhaSelecionadaLiAmigos()){
						mostrarJanela();//Mostra Janela pelo tempIndiceJanela
					}
				}
			}
		});
		liAmigos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				//True = Carregou a linha nas Variaveis temps				
				if(getLinhaSelecionadaLiAmigos()){				
					mostrarJanela();//Mostra Janela pelo tempIndiceJanela	
				}			
			}
		});

		lblLegendas = new JLabel("Meu IP                      IP do Amigo            Janela");
		lblLegendas.setBounds(62, 205, 343, 15);
		contentPane.add(lblLegendas);
		liAmigos.setBounds(62, 225, 343, 275);		
		contentPane.add(liAmigos);

		taHistorico = new TextArea();
		taHistorico.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				//if(KeyEvent.VK_F1==112){
				if(KeyEvent.VK_TAB==k){	
					JOptionPane.showMessageDialog(null,"Ir para o próximo.","Ctrl + TAB",1);
				}
			}
		});
		taHistorico.setBounds(62, 543, 343, 81);
		contentPane.add(taHistorico);

		JButton btnIcon = new JButton("");
		btnIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, " Meus IPs:\n\n "+todosIPs,"MEUS IPs",1);
			}
		});
		btnIcon.setIcon(new ImageIcon(Principal.class.getResource("/Imagens/msn.png")));
		btnIcon.setBounds(64, 8, 43, 30);
		contentPane.add(btnIcon);

		lblHistrico = new JLabel("Histórico de Amigos");
		lblHistrico.setBounds(62, 523, 341, 15);
		contentPane.add(lblHistrico);

		JLabel lblAutores = new JLabel("João Júnior®™");
		lblAutores.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAutores.setFont(new Font("Dialog", Font.PLAIN, 9));
		lblAutores.setBounds(4, 643, 401, 25);
		contentPane.add(lblAutores);

		JLabel lblBack = new JLabel("");
		lblBack.setIcon(new ImageIcon(JanelaM.class.getResource("/Imagens/backJan.jpg")));
		lblBack.setBounds(0, 0, 523, 750);
		contentPane.add(lblBack);

		try {
			login = new Login();
			login.setVisible(true);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}
	//inicializar variaveis
	public void iniVars(){		
		System.out.println("Principal.iniVars(): "+nomeUsuario);

		//*****Criando uma sugestaoIP do IP Origem *******************
		int i=0;
		sugestaoIP="";
		StringTokenizer st = new StringTokenizer(hostOrigem, ".");		
		while (st.hasMoreTokens() &&i<3){
			sugestaoIP += st.nextToken()+".";
			i++;
		}
		//************************************************************
		System.out.println("\nsugestaoIP:"+sugestaoIP);

		//nomeUsuario; foi inicializado na Login e setado para aqui
		//hostOrigem; foi inicializado na Login e setado para aqui
		portaOrigem="1024";
		hostDestino=sugestaoIP;
		portaDestino="1024";

		//Mostrar nos Componentes
		lblNomeUsuario.setText(nomeUsuario);		
		lblHostOrigem.setText(hostOrigem);
		txtHostDestino.setText(sugestaoIP);
		txtPortaDestino.setText(portaDestino);

		//Depois que as variaveis estao carregadas... ler o arquivo...
		try {
			carregarArquivo();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	protected void verificarAntesADD(){
		System.out.println("Principal.verificarHostPorta()");

		if(!verificarPortaValida(txtPortaOrigem.getText())){
			JOptionPane.showMessageDialog(null, "Portas reservadas entre 0 e 1023!");
			txtPortaOrigem.setText("");			
		}else if(!verificarPortaValida(txtPortaDestino.getText())){
			JOptionPane.showMessageDialog(null, "Portas reservadas entre 0 e 1023!");
			txtPortaDestino.setText("");			
		}else{
			if(verificarArrayList(txtPortaOrigem.getText(),aliPortasOrigem)){//Se esta...
				JOptionPane.showMessageDialog(null, "A Porta de Origem ja esta reservada!");
			}else if(verificarArrayList(txtHostDestino.getText(),aliHostDestino)){//Se esta...
				JOptionPane.showMessageDialog(null, "Este IP já é seu amigo!");
				txtPortaDestino.setNextFocusableComponent(txtPortaDestino);
			}else{				
				//Liberado para adicionar....
				txtPortaDestino.setNextFocusableComponent(liAmigos);
				adicionarListAmigos();
				txtPortaOrigem.setText("");
				txtHostDestino.setText(sugestaoIP);
				txtPortaDestino.setText("");

				criarJanela();
				//so gravo no arquivo se ainda nao estiver no arquivo 
				if(!pesquisarArquivo(hostDestino, portaDestino))
					gravarArquivo();
			}
		}					
	}

	protected boolean verificarPortaValida(String porta){
		System.out.println("Principal.verificarPortaValida()");
		System.out.println("\tPorta: "+porta);

		if(porta.isEmpty()){			
			return false;
		}
		int portaInt = Integer.parseInt(porta);		
		if(portaInt<=1023){						
			return false;
		}
		return true;
	}

	//busca numa lista<String>
	public static boolean verificarArrayList(String valor,ArrayList<String> lista){
		System.out.println("Principal.verificarArrayList()");
		System.out.println("\tValor:"+valor);
		if (lista.contains(valor)){			
			return true;//Nao serve
		}
		return false;
	}

	//Quando apertar no txtPortaDestino ou no bntADD
	protected void adicionarListAmigos() {
		System.out.println("Principal.adicionarList()");

		portaOrigem=txtPortaOrigem.getText();
		hostDestino=txtHostDestino.getText();
		portaDestino=txtPortaDestino.getText();

		System.out.println("\tPorta de Origem redefinida:"+portaOrigem);
		System.out.println("\tEnviando para Destino: "+hostDestino+":"+portaDestino);		

		indiceJanela++;

		liAmigos.add(hostOrigem+":"+portaOrigem+"   "+hostDestino+":"+portaDestino+"   "+indiceJanela);//3Espaco
		aliPortasOrigem.add(portaOrigem);//Usadas no verificarAntesADD()
		aliHostDestino.add(hostDestino);//Usadas no verificarAntesADD()
		aliPortasDestino.add(portaDestino);//Usadas no verificarAntesADD()
	}

	private void mostrarJanela() {
		System.out.println("Principal.mostrarJanela(): indice: "+tempIndiceJanela);
		aliJanAbertas.get(tempIndiceJanela).setVisible(true);
	}

	private boolean criarJanela() {
		System.out.println("Principal.crirarJanela()");
		System.out.println("\t IndiceJanela: "+indiceJanela);
		//janela = new Janela();
		aliJanAbertas.add(new JanelaM());

		//j		a	n	e	l	a	   .iniVars(indiceJanela,nomeUsuario, hostOrigem ,portaOrigem, hostDestino, portaDestino,aliJanAbertas);		
		aliJanAbertas.get(indiceJanela).iniVars(indiceJanela,nomeUsuario,todosIPs,hostOrigem ,portaOrigem, hostDestino, portaDestino,aliJanAbertas);
		aliJanAbertas.get(indiceJanela).setVisible(false);
		
		System.out.println("\tlistajanela:TAM:"+aliJanAbertas.size()+aliJanAbertas.toArray() );
		printListaJanelasAbertas();
		return true;
	}
	protected boolean getLinhaSelecionadaLiAmigos(){
		System.out.println("Princiapl.getLinhaSelecionadaListAmigos");

		int qtdAmigos = liAmigos.getItemCount();
		System.out.println("\tqtdAmigos na ListAmigos: "+qtdAmigos);
		if(qtdAmigos==0){
			JOptionPane.showMessageDialog(null,"Voce nao tem Amigos :( ");
			return false;
		}
		//Se tiver pelo menos um registro na List...

		//Se clicar e nao tiver selecionado uma tupla...
		String linhaClicada = liAmigos.getSelectedItem();
		if(linhaClicada==null){
			JOptionPane.showMessageDialog(null,"Selecione um Amigo :P ");
			return false;
		}
		System.out.println("\tlinhaClicada: "+linhaClicada);
		String[] partesEnd = linhaClicada.split("   ",3);//3Espaco
		String[] parteOrigem = partesEnd[0].split(":",2);
		//hostOrigem=parteOrigem[0];Fixo!
		tempPortaOrigem=parteOrigem[1];

		String[] parteDestino = partesEnd[1].split(":",2);
		tempHostDestino=parteDestino[0];
		tempPortaDestino=parteDestino[1];		
		tempIndiceJanela=Integer.parseInt(partesEnd[2]);
		System.out.println("\t Temporaios: "+
				"\n\t tempIndiceJanela: "+tempIndiceJanela+
				"\n\t nomeUsuraio: "+nomeUsuario+
				"\n\t hotsOrigem: "+ hostOrigem+
				"\n\t tempPortaOrigem:"+ tempPortaOrigem+
				"\n\t tempHostDestino:"+ tempHostDestino+
				"\n\t tempPortaDestino:"+ tempPortaDestino);
		return true;
	}

	protected void carregarArquivo() throws IOException{
		try{
			arqFR = new FileReader("HostsDestinos.txt");			
			lerArqBR = new BufferedReader(arqFR);
			String ultimaLinha = lerArqBR.readLine();
			String textoArq="";
			while(ultimaLinha!=null){
				textoArq+=(ultimaLinha+"\r\n");
				ultimaLinha= lerArqBR.readLine();
				//System.out.println(textoArq);
			}	
			arqFR.close();
			lerArqBR.close();
			taHistorico.setText(textoArq);		
		}catch(IOException e){
			System.out.println("Arquivo nao existe ou foi apagado!");			
			criarDiretorio();
			gravarArquivo();
		}
	}

	protected boolean pesquisarArquivo(String ip, String porta){
		System.out.println("Principa.pesquisaArquivo()");
		try{
			arqFR = new FileReader("HostsDestinos.txt");			
			lerArqBR = new BufferedReader(arqFR);
			String linha = lerArqBR.readLine();
			while(linha!=null){
				if((ip+":"+porta).equals(linha)){
					System.out.println("\ttrue");
					return true;
				}
				linha= lerArqBR.readLine();
				//System.out.println(textoArq);
			}	
			arqFR.close();
			lerArqBR.close();			
		}catch(IOException e){
			System.out.println("Erro durante pesquisa no arquivo!");			
			criarDiretorio();
			gravarArquivo();
		}
		System.out.println("\tFalse");
		return false;
	}

	protected void criarDiretorio() {
		System.out.println("Diretorio criado: Arquivos/");
		arquivoF = new File("Arquivos");
		arquivoF.mkdir();

	}

	protected void gravarArquivo(){
		System.out.println("gravarArquivo()");

		try{	
			arquivoF = new File("HostsDestinos.txt");
			arqW = new FileWriter(arquivoF,true);
			escrArqBW = new BufferedWriter(arqW);
			if(hostDestino==null || hostDestino.equals("")){
				escrArqBW.write("");
				taHistorico.setText("");
			}else{
				escrArqBW.write(hostDestino+":"+portaDestino);
				escrArqBW.newLine();
				taHistorico.setText(taHistorico.getText()+
						hostDestino+":"+portaDestino+"\r\n");
			}			
			escrArqBW.close();
			arqW.close();
			arquivoF.exists();

		}catch(IOException e){
			e.printStackTrace();
		}	
	}

	//Funcoes so para auxliar quando havia erros ******************************************	
	public static void removerArrayList(String hostPorta,ArrayList<String> lista){
		System.out.println("Principal.removerArrayList()");
		lista.remove(hostPorta);		
	}

	public static void analizarArrayList(String qualLista,ArrayList<String> lista){
		System.out.println("analizarArrayList()");
		System.out.println("\t"+qualLista+" TAM: "+lista.size());
		System.out.println("\t"+qualLista+" Vazia?: "+lista.isEmpty());
	}


	public void printListaJanelasAbertas(){
		System.out.println("Primcipal.printListaJanelasAbertas()");
		int TAM = aliJanAbertas.size();
		for (int i = 0; i < TAM; i++) {		

			System.out.println("\t incice: "+ aliJanAbertas.get(i).getIndiceJanela()+
					"\n\t NomeUsuraio"+aliJanAbertas.get(i).getNomeUsuario()+
					"\n\t hostOrigem:"+aliJanAbertas.get(i).getHostOrigem()+
					"\n\t porta de Origem:"+aliJanAbertas.get(i).getPortaOrigem()+
					"\n\t hostDestino:"+aliJanAbertas.get(i).getHostDestino()+
					"\n\t portaDestino:"+aliJanAbertas.get(i).getPortaDestino());
		}
	}

	//Gets e Sets****************************************************
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getHostOrigem() {
		return hostOrigem;
	}

	public void setHostOrigem(String hostOrigem) {
		this.hostOrigem = hostOrigem;
	}

	public String getPortaOrigem() {
		return portaOrigem;
	}

	public void setPortaOrigem(String portaOrigem) {
		this.portaOrigem = portaOrigem;
	}

	public String getHostDestino() {
		return hostDestino;
	}

	public void setHostDestino(String hostDestino) {
		this.hostDestino = hostDestino;
	}

	public String getPortaDestino() {
		return portaDestino;
	}

	public void setPortaDestino(String portaDestino) {
		this.portaDestino = portaDestino;
	}

	public String getTodosIPs() {
		return todosIPs;
	}

	public void setTodosIPs(String todosIPs) {
		this.todosIPs = todosIPs;
	}
}
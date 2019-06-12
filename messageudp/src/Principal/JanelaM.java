package Principal;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import java.awt.Font;
import Auxiliar.AtualizaChat;
import Auxiliar.BufferMSG;
import Auxiliar.Cliente;
import Auxiliar.Servidor;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


@SuppressWarnings("serial")
public class JanelaM extends JFrame {

	//Variaveis
	private String todosIPs;
	private  int indiceJanela;
	private  String nomeUsuario;
	private  String hostOrigem;
	private  String portaOrigem;
	private  String hostDestino;
	private  String portaDestino;	
	private  BufferMSG bufferMSG;//Passgem por referencia: buffer da Janela e Servidor
	private  String mensagem;

	private JPanel contentPane;

	private JLabel lblNomeUsuario;
	private JLabel lblHostOrigem;
	private JLabel lblPortaOrigem;
	private JLabel lblPortaDestino; 
	private JLabel lblHostDestino;
	private JTextField txtMSG;
	public  TextArea taChat;

	//ArrayLists
	public ArrayList<JanelaM> aliJanAbertas;
	
	//Objetos
	public Servidor servidor;
	public Cliente cliente;
	public AtualizaChat atualizaChat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaM frame = new JanelaM();
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JanelaM() {
		setResizable(false);
		setTitle("MESSENGER UDP");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//setBounds(100, 100, 471, 530);
		setBounds(100, 100, 712, 659);
		setLocationRelativeTo(null);//Centralizar janela
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtMSG = new JTextField();
		txtMSG.setBounds(64, 494, 583, 29);
		contentPane.add(txtMSG);
		txtMSG.setColumns(10);
		
		taChat = new TextArea();
		taChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				//if(KeyEvent.VK_F1==112){
				if(KeyEvent.VK_TAB==k){	
					JOptionPane.showMessageDialog(null,"Ir para o próximo.","Ctrl + TAB",1);
				}
			}
		});
		taChat.setFont(new Font("Dialog", Font.PLAIN, 15));
		taChat.setBackground(SystemColor.text);
		taChat.setEditable(false);
		taChat.setBounds(64, 189, 583, 299);
		contentPane.add(taChat);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				btnEnviar.setNextFocusableComponent(txtMSG);
			}
		});
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviarMSG(); 
			}
		});

		//btnEnviar.setBounds(343, 439, 97, 25);
		btnEnviar.setBounds(550, 535, 97, 25);
		contentPane.add(btnEnviar);

		lblNomeUsuario = new JLabel("");		
		lblNomeUsuario.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNomeUsuario.setBounds(116, 8, 424, 30);
		contentPane.add(lblNomeUsuario);
		
		JLabel lblMsg = new JLabel("Mensagem");
		lblMsg.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblMsg.setBounds(64, 528, 173, 16);
		contentPane.add(lblMsg);

		lblPortaOrigem = new JLabel();
		lblPortaOrigem.setFont(new Font("Dialog", Font.PLAIN, 12));		
		lblPortaOrigem.setText("1235");
		lblPortaOrigem.setBounds(247, 79, 107, 19);
		contentPane.add(lblPortaOrigem);

		JLabel lbl2 = new JLabel("Porta de destino");
		lbl2.setFont(new Font("Dialog", Font.BOLD, 16));
		lbl2.setBounds(247, 110, 166, 23);
		contentPane.add(lbl2);

		JLabel lbl1 = new JLabel("IP do Amigo");
		lbl1.setFont(new Font("Dialog", Font.BOLD, 16));
		lbl1.setBounds(66, 110, 143, 23);
		contentPane.add(lbl1);

		JLabel lbl3 = new JLabel("Porta de origem");
		lbl3.setFont(new Font("Dialog", Font.PLAIN, 12));
		lbl3.setBounds(247, 58, 124, 15);
		contentPane.add(lbl3);

		JLabel lblMeuIp = new JLabel("Meu IP");
		lblMeuIp.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblMeuIp.setBounds(66, 58, 143, 15);
		contentPane.add(lblMeuIp);

		lblHostOrigem = new JLabel("");
		lblHostOrigem.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblHostOrigem.setBounds(66, 81, 163, 15);
		contentPane.add(lblHostOrigem);

		lblHostDestino = new JLabel(" ");
		lblHostDestino.setFont(new Font("Dialog", Font.BOLD, 16));
		lblHostDestino.setBounds(66, 130, 163, 25);
		contentPane.add(lblHostDestino);

		lblPortaDestino = new JLabel("");
		lblPortaDestino.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPortaDestino.setBounds(247, 130, 107, 25);
		contentPane.add(lblPortaDestino);

		JButton btnIcon = new JButton("");
		btnIcon.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				btnIcon.setNextFocusableComponent(txtMSG);
			}
		});
		btnIcon.setIcon(new ImageIcon(JanelaM.class.getResource("/Imagens/msn.png")));
		btnIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, " Incice Janela: "+indiceJanela+
						"\n Meus IPs:\n\n "+todosIPs,"MEUS IPs",1);
			}
		});
		btnIcon.setBounds(64, 8, 43, 30);
		contentPane.add(btnIcon);

		JLabel lblAutores = new JLabel("João Júnior®™");
		lblAutores.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAutores.setFont(new Font("Dialog", Font.PLAIN, 9));
		//lblAutores.setBounds(12, 464, 445, 25);
		lblAutores.setBounds(388, 572, 259, 25);
		contentPane.add(lblAutores);
		
		JButton btnFechar = new JButton("X");
		btnFechar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c= e.getKeyChar();
				if(c=='\n'){
					//A janela pesquisa seu objeto na listaJanela e seta invisivel="Fechando"				
					System.out.println("Janela indice:"+indiceJanela);
					aliJanAbertas.get(indiceJanela).setVisible(false);
					//PrincipalJMB.principal.aliJanAbertas.get(indiceJanela).setVisible(false);
				}
			}
		});
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//A janela pesquisa seu objeto na listaJanela e seta invisivel="Fechando"				
				System.out.println("Janela indice:"+indiceJanela);
				aliJanAbertas.get(indiceJanela).setVisible(false);
				//PrincipalJMB.principal.aliJanAbertas.get(indiceJanela).setVisible(false);
			}
		});
		btnFechar.setBounds(604, 8, 43, 30);
		contentPane.add(btnFechar);
		portaOrigem = lblPortaOrigem.getText();
		
		JLabel lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(JanelaM.class.getResource("/Imagens/fotoJan.png")));
		lblFoto.setBounds(545, 52, 102, 107);
		contentPane.add(lblFoto);

		JLabel lblBack = new JLabel("");		
		lblBack.setIcon(new ImageIcon(JanelaM.class.getResource("/Imagens/backJan.jpg")));
		//lblBack.setBounds(0, 0, 480, 494);
		lblBack.setBounds(0, 0, 837, 783);
		contentPane.add(lblBack);
		
		//Ao abrir a janela ja posso cirar o obj cliente para enviar msg
		cliente = new Cliente();
		bufferMSG =new BufferMSG();
		atualizaChat = new AtualizaChat(taChat, bufferMSG);
		atualizaChat.start();
		//Apertar Enter aciona o botao Entrar
		getRootPane().setDefaultButton(btnEnviar);	
	}
	
	public void iniVars(int indiceJanela,String nomeUsuario,String todosIPs,String hostOrigem,String portaOrigem,String hostDestino, String portaDestino,ArrayList<JanelaM> aliJanAbertas){
		this.indiceJanela=indiceJanela;

		this.nomeUsuario=nomeUsuario;		
		lblNomeUsuario.setText(nomeUsuario);
		
		this.todosIPs=todosIPs;
		
		this.hostOrigem = hostOrigem;
		lblHostOrigem.setText(hostOrigem);
		this.portaOrigem=portaOrigem;
		lblPortaOrigem.setText(portaOrigem);

		this.hostDestino=hostDestino;
		lblHostDestino.setText(hostDestino);

		this.portaDestino=portaDestino;
		lblPortaDestino.setText(portaDestino);

		this.aliJanAbertas=aliJanAbertas;
		
		//mensagem="";Qdo crio a jan eu carrego o buffer e nao a messagem,
					//ela so sera atribuida no no enviar/Servidor, e depois concatena o valor no buffer
		//Depois de receber as informacoes posso ouvir a porta
		servidor = new Servidor(indiceJanela,portaOrigem,bufferMSG);//buffer por referencia, pois eh uma objeto
		servidor.start();
		taChat.setText(bufferMSG.getBufferMSG());
		System.out.println("\nJanela,carregar("+this.nomeUsuario+","+this.portaOrigem+","+this.hostDestino+":"+this.portaDestino+")");
	}

	protected void enviarMSG (){
		if(!txtMSG.getText().equals("")){
			System.out.println("Janela.enviarMSG()");
			mensagem	= "  "+nomeUsuario+": "+txtMSG.getText()+"\n";
			if(cliente.enviarMensagem( hostDestino, portaDestino, mensagem)){
				txtMSG.setText("");
				bufferMSG.concatenarBufferMSG(mensagem);
				bufferMSG.setMsgCli(true);
				bufferMSG.setMsgServ(false);
			}
		}
	}
	
	public int getIndiceJanela() {
		return indiceJanela;
	}

	public void setIndiceJanela(int indiceJanela) {
		this.indiceJanela = indiceJanela;
	}

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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}

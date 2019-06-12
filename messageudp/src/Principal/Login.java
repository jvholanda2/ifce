package Principal;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class Login extends JFrame {
	
	//Var
	String faixaIP=null;
	private String todosIPs;
	
	private JPanel contentPane;
	private JLabel lblPortaOrigem;
	private JButton btnEntrar; 	
	private JTextField txtNomeUsuario;
	private JLabel lblAutores;
	private JComboBox<String> cbHostOrgiem;
	private JLabel lblAjuda;
	private JLabel lblFoto;
	
	//Obejtos	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frameLogin = new Login();
					frameLogin.setVisible(true);
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

	public Login() throws SocketException {
		setTitle("MESSENGER UDP");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setBounds(100, 100, 467, 730);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUser = new JLabel("Qual seu nome?");
		lblUser.setBounds(155, 283, 160, 15);
		contentPane.add(lblUser);
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtNomeUsuario = new JTextField();
		txtNomeUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				apertarF1(e.getKeyCode());
			}
		});
		txtNomeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		txtNomeUsuario.setText("Anonimo");
		txtNomeUsuario.setBounds(155, 303, 160, 19);
		contentPane.add(txtNomeUsuario);
		txtNomeUsuario.setColumns(10);
		
		lblPortaOrigem = new JLabel("Seu IP");
		lblPortaOrigem.setBounds(155, 354, 160, 19);
		contentPane.add(lblPortaOrigem);
		lblPortaOrigem.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				apertarF1(e.getKeyCode());
				char c= e.getKeyChar();
				if(c=='\n'){
					if(txtNomeUsuario.getText()==null	|| txtNomeUsuario.getText().equals("") ){
						txtNomeUsuario.setText("Anonimo");
					}
					Principal.login.setVisible(false);
					Principal.principal.setVisible(true);
					Principal.principal.setNomeUsuario(txtNomeUsuario.getText());
					Principal.principal.setHostOrigem((String) cbHostOrgiem.getSelectedItem());
					Principal.principal.setTodosIPs(todosIPs);
					System.out.println(Principal.principal.getNomeUsuario());
					System.out.println(Principal.principal.getHostOrigem());
					Principal.principal.iniVars();
				}
			}
		});
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtNomeUsuario.getText()==null	|| txtNomeUsuario.getText().equals("") ){
					txtNomeUsuario.setText("Anonimo");
				}
				Principal.login.setVisible(false);
				Principal.principal.setVisible(true);
				Principal.principal.setNomeUsuario(txtNomeUsuario.getText());
				Principal.principal.setHostOrigem((String) cbHostOrgiem.getSelectedItem());
				Principal.principal.setTodosIPs(todosIPs);
				System.out.println(Principal.principal.getNomeUsuario());
				System.out.println(Principal.principal.getHostOrigem());
				Principal.principal.iniVars();		
			}
		});
		
		cbHostOrgiem = new JComboBox<String>();
		cbHostOrgiem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				apertarF1(e.getKeyCode());
			}
		});
		cbHostOrgiem.setBounds(155, 382, 160, 19);
		contentPane.add(cbHostOrgiem);
		
		lblAjuda = new JLabel("IP Ajuda? F1");
		lblAjuda.setHorizontalAlignment(SwingConstants.CENTER);
		lblAjuda.setBounds(153, 409, 160, 15);
		contentPane.add(lblAjuda);
		btnEntrar.setBounds(177, 458, 117, 25);
		contentPane.add(btnEntrar);
		
		lblAutores = new JLabel("João Júnior®™");
		lblAutores.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAutores.setFont(new Font("Dialog", Font.PLAIN, 9));
		lblAutores.setBounds(12, 646, 417, 15);
		contentPane.add(lblAutores);
		

		
		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(Principal.class.getResource("/Imagens/msn.png")));
		lblIcon.setBounds(37, 8, 30, 21);
		contentPane.add(lblIcon);
		
		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(Login.class.getResource("/Imagens/fotoLog.png")));
		lblFoto.setBounds(182, 130, 102, 110);
		contentPane.add(lblFoto);
		
		JLabel lblBack = new JLabel("");
		lblBack.setIcon(new ImageIcon(Login.class.getResource("/Imagens/backJan.jpg")));
		lblBack.setBounds(0, 0, 618, 812);
		contentPane.add(lblBack);
		
		try {
			showIP(false);
			//cbHostOrgiem.setSelectedItem("192.168.1.11");
		} catch (SocketException e1) {					
			e1.printStackTrace();
		}
		//Apertar Enter aciona o botao Entrar
		//getRootPane().setDefaultButton(btnEntrar);		
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	protected void showIP(boolean filtro) throws SocketException{
		Enumeration nis = null;		
		todosIPs="";
        try {
            nis = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String ip = null;
		while (nis.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) nis.nextElement();
            Enumeration ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = (InetAddress) ias.nextElement();
                
                todosIPs+=ia.getHostAddress()+"\r\n";
                if(filtro){//so IPs na faixaIP
                	if (ia.getHostAddress().contains(faixaIP)) {
	                	ip=ia.getHostAddress();  
	                	cbHostOrgiem.addItem(ia.getHostAddress());
                	}                	
                }else{//Todo IPs
                	cbHostOrgiem.addItem(ia.getHostAddress());
                }
            }
        }
        //System.out.println("todosIP: " + todosIPs);		
	}
	
	private void apertarF1(int k){
		//System.out.println("k:"+k);
		
		//if(k==112){//Da certo tbm
		if(k==KeyEvent.VK_F1){	
			faixaIP = JOptionPane.showInputDialog(null,"Ex.: 192","Filtro IP", 1);
			//System.out.println("FaixaIP:"+faixaIP);
			if(faixaIP!=null){//Se nao cancelou...				
				cbHostOrgiem.removeAllItems();
				try {
					showIP(true);						
				} catch (SocketException e1) {				
					e1.printStackTrace();
				}
			}
		}
	}
}

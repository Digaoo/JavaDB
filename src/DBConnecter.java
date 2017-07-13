import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DBConnecter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPath;
	private DBManager dbm;

	/**
	 * Create the frame.
	 */
	public DBConnecter(Interface inter) {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
			
				inter.setupBanco();
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 125);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblCaminhoParaO = new JLabel("Caminho para o Banco de Dados:");
		contentPane.add(lblCaminhoParaO, BorderLayout.NORTH);
		
		JPanel painelCampo = new JPanel();
		contentPane.add(painelCampo, BorderLayout.CENTER);
		painelCampo.setLayout(new BorderLayout(0, 0));
		
		txtPath = new JTextField();
		txtPath.setText("/home/rodrigo/workspace/BD4Fun/database/bd.sqlite");
		painelCampo.add(txtPath);
		txtPath.setColumns(10);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		painelCampo.add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		painelCampo.add(verticalStrut_1, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String path = txtPath.getText();
				
				dbm = new DBManager(dbConnector(path));
				
				setVisible(false);
				dispose();
			}
		});
		contentPane.add(btnOk, BorderLayout.SOUTH);
		
		setAlwaysOnTop( true );
		setVisible(true);

	}
	
	/**
	 * Faz a conexão com o banco de dados, se em outro computador mudar local onde esta o arquivo sqlite
	 * @return a conexão
	 */
	public static Connection dbConnector(String path) {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+path);
			JOptionPane.showMessageDialog(null, "Conexão Bem Sucedida !");
			
			return conn;
			
		}
		catch (Exception e){
			
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
	}

	public DBManager getDBManager() {
		
		return dbm;
	}
}

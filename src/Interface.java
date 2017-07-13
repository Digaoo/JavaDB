import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Interface {

	private JFrame frame;
	private JTable tabelaTeste;
	private DBConnecter dbc = new DBConnecter(this);
	private DBManager dbm;
	private JComboBox<String> gerenciadorTabelas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		preparaJanelas();
		preparaTabela();
		
		frame.setVisible(true);
	}
	
	private void preparaJanelas() {
		
		frame = new JFrame("Dinamic Database Interface");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.addWindowListener(new WindowAdapter() {
			
            @Override
            public void windowDeactivated(WindowEvent e) {
                dbc.setAlwaysOnTop(false);
            }
            
            @Override
            public void windowActivated(WindowEvent e) {
                dbc.setAlwaysOnTop(true);
            }
        });
		
		dbc.setLocationRelativeTo(frame);
	}
	
	private void preparaTabela() {
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		gerenciadorTabelas = new JComboBox<String>();
		frame.getContentPane().add(gerenciadorTabelas, BorderLayout.NORTH);
		
		JScrollPane scrollPaneTabela = new JScrollPane();
		frame.getContentPane().add(scrollPaneTabela, BorderLayout.CENTER);
		
		tabelaTeste = new JTable();
		scrollPaneTabela.setViewportView(tabelaTeste);
	}
	
	public void setupBanco() {
		
		dbm = dbc.getDBManager();
		
		gerenciadorTabelas.setModel(new DefaultComboBoxModel<String>(dbm.getTableNames()));
		gerenciadorTabelas.setSelectedIndex(1);
		gerenciadorTabelas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				String comando = "SELECT * FROM ";
				comando+= gerenciadorTabelas.getItemAt(gerenciadorTabelas.getSelectedIndex());
				
				try {
					
					PreparedStatement pst = dbm.conexao.prepareStatement(comando);
					ResultSet rs = pst.executeQuery();
					
					tabelaTeste.setModel(DbUtils.resultSetToTableModel(rs));
					
				}
				catch (Exception ex) {
					
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		gerenciadorTabelas.setSelectedIndex(0);
		
	}

}

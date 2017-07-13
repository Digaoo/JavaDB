import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

//Jogar Conteudo do Result Set em um array para manipulação

public class DBManager {
	
	Connection conexao;
	int numTabelas;
	
	DBManager (Connection con) {
		
		conexao = con;
	}
	
	public int getNumTabelas() {
		
		String comando = "SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence'"; 
		
		try {
			
			PreparedStatement pst = conexao.prepareStatement(comando);
			ResultSet rs = pst.executeQuery();

			return rs.getInt(1);
			
		}
		catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, e);
			return -1;
		}
	}
	
	public String[] getTableNames() {
		
		int index=0;
		String[] nomes;
		
		numTabelas = getNumTabelas();
		nomes = new String[numTabelas];
		
		try {
			
			DatabaseMetaData dbmd = conexao.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, "%", null);
			
			while(rs.next()) {
				
				nomes[index] = rs.getString(3);
				index++;
			}
		}
		catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, e);
			return null;
			
		}
		
		return nomes;
		
	}
}

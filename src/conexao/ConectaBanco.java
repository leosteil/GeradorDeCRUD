package conexao;

import java.sql.Connection;
import map.MapeadorBanco;

public class ConectaBanco {
	
	public Connection devolveConexão(){
		
		FabricaConexao fabricaConexao = new FabricaConexao("jdbc:mysql://localhost/TESTE", "root", "admin");
		Connection connection =  fabricaConexao.getConnection();
	
		return connection;
	}	
}

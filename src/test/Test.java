package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.FabricaConexao;
import escreve_arquivo.escreveArquivo;
import map.MapeadorBanco;
import model.Banco;
import model.Coluna;
import model.Tabela;

public class Test {

	public static void main(String[] args) throws IOException {
		/*MapeadorBanco map = new MapeadorBanco();
		FabricaConexao fabricaConexao = new FabricaConexao("jdbc:mysql://localhost/TESTE", "root", "admin");
		Connection connection =  fabricaConexao.getConnection();
		try {
			Banco banco = map.getBanco("TESTE", connection);
			//System.out.println(banco.getNome());
			for (Tabela tabela : banco.getTabelas()) {
				/*System.out.println("\nTabela:" +tabela.getNome());
				for (Coluna coluna : tabela.getColunas()) {
					System.out.print(coluna.getNome()+" : "+coluna.getTipo());
					if (coluna.isPk())
						System.out.print("(PK)");
					if (coluna.isNotNull())
						System.out.print("(NN)");
					if (coluna.isUnique())
						System.out.print("(UN)");
					if (coluna.isAutoInc())
						System.out.print("(AI)");
					System.out.println();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		escreveArquivo arq = new escreveArquivo();
		
		arq.classeAtributosEscritor();
		
		
	}
}

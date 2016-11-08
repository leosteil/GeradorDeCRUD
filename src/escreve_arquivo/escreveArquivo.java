package escreve_arquivo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import map.MapeadorBanco;
import model.Banco;
import model.Coluna;
import model.Tabela;
import conexao.FabricaConexao;
import negocio.*;

public class escreveArquivo {
	
	/*este método escreve o nome da classe + seus respectivos atributos*/
	public static void classeAtributosEscritor() throws IOException {
        
        Negocios neg = new Negocios();
        
        MapeadorBanco map = new MapeadorBanco();
		FabricaConexao fabricaConexao = new FabricaConexao("jdbc:mysql://localhost/TESTE", "root", "101421leo0103");
		Connection connection =  fabricaConexao.getConnection();
		try {
			Banco banco = map.getBanco("TESTE", connection);
			for (Tabela tabela : banco.getTabelas()) {
				BufferedWriter buffWrite = new BufferedWriter(new FileWriter("/home/leonardo/Documentos/" + tabela.getNome()+ ".java"));
				int i = 0;
				if(i == 0){
					buffWrite.append("public class " + tabela.getNome() + "{\n");
					for (Coluna coluna : tabela.getColunas()) {
						
						String tipo = new String();
						tipo = neg.processaTipo(coluna.getTipo());
						
						buffWrite.append("\t private " +tipo + " " + coluna.getNome() + "\n");
					}
					buffWrite.append("}\n\n");
					buffWrite.close();
					i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
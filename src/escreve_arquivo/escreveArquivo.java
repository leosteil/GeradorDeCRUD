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
import conexao.ConectaBanco;
import conexao.FabricaConexao;
import negocio.*;

public class escreveArquivo {
	
	/*este método escreve o nome da classe + seus respectivos atributos*/
	public static void classeAtributosEscritor() throws IOException {
        
		MapeadorBanco map = new MapeadorBanco();
        Negocios neg = new Negocios();
        ConectaBanco conect = new ConectaBanco();
        
		Connection connection = conect.devolveConexão(); 
		try {
			Banco banco = map.getBanco("TESTE", connection);
			for (Tabela tabela : banco.getTabelas()) {
				BufferedWriter buffWrite = new BufferedWriter(new FileWriter("/home/leonardo/Documentos/" + tabela.getNome()+ ".java"));
					buffWrite.append("public class " + tabela.getNome() + "{\n");
					for (Coluna coluna : tabela.getColunas()) {
						
						String tipo = new String();
						tipo = neg.processaTipo(coluna.getTipo());
						
						buffWrite.append("\t private " +tipo + " " + coluna.getNome() + "\n");
						
					}
					buffWrite.append("}\n\n");
					buffWrite.close();
					getSetEscritor(tabela); //chamada ao método que escreve os gets e os sets na classe
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void getSetEscritor(Tabela tab) throws IOException{
		Negocios neg = new Negocios();
		
		String tipo  = new String();
		
		
		for (Coluna coluna : tab.getColunas()) {
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter("/home/leonardo/Documentos/" + tab.getNome()+ ".java", true));
			
			
			tipo = neg.processaTipo(coluna.getTipo());
			
			
			String get = "public " +tipo+ " get" +coluna.getNome()+ "() {\n"
					+"\treturn " +coluna.getNome()+";\n"
					+"}";
			
			String set = "public void set" +coluna.getNome()+ " (" +tipo + " " + coluna.getNome()+") {\n"
					+"\tthis." +coluna.getNome()+" = " + coluna.getNome() +";\n"
					+"}";
			
			buffWrite.append(get + "\n\n");
			buffWrite.append(set + "\n\n");
			buffWrite.close();
		}	
	}
}
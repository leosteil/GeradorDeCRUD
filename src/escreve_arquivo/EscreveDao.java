package escreve_arquivo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import map.MapeadorBanco;
import model.Banco;
import model.Coluna;
import model.Tabela;
import negocio.Negocios;
import conexao.ConectaBanco;	

public class EscreveDao {
	
	/*Método para escrever o nome da classe+DAO*/
	/*Este método também ficará responsável pela chamada aos demais métodos que preencherão o restante do arquivo da classe*/
	public void escreveNomeDAO() throws IOException{
		MapeadorBanco map = new MapeadorBanco();
        Negocios neg = new Negocios();
        ConectaBanco conect = new ConectaBanco();
        
		Connection connection = conect.devolveConexão();
		try {
			Banco banco = map.getBanco("TESTE", connection);
			for (Tabela tabela : banco.getTabelas()) {
				BufferedWriter buffWrite;
				buffWrite = new BufferedWriter(new FileWriter("/home/leonardo/Documentos/CRUDS/" + tabela.getNome()+ "DAO.java"));
				
				buffWrite.append("public class " + tabela.getNome() + "DAO extends Registros	<"+ tabela.getNome() +">{\n\n");

				escreveConstrutorDAO(tabela, buffWrite);
				escreveInserção(tabela, buffWrite);
				buffWrite.append("}\n\n");
				buffWrite.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/*Método para criar o construtor que inicia as strings das consultas SQL*/
	public void escreveConstrutorDAO(Tabela tab, BufferedWriter buffWrite) throws IOException {
		ArrayList<String> colList = new ArrayList<String>();
		Negocios neg = new Negocios();
		int i = 0;
		int j = 0;
		String aux = null;
		
		buffWrite.append("\tpublic " + tab.getNome()+"DAO {\n");
		
		/*trecho que cod que trata do sql de insercão*/
		buffWrite.append("\t\tsetSqlInsercao(\"INSERT INTO " + tab.getNome() + " (");
		
		for(Coluna col: tab.getColunas()){
			colList.add(col.getNome());
			i++;
			if(neg.isPKEY(col)){ //verifica aqui se a coluna é PKEY, pois abaixo será utilizada para condição do sql inserção e alteração
				aux = col.getNome();
			} 
		}
		
		System.out.println(aux);
		System.out.println(i);
		
		for(j = 0; j<i; j++){
			if(j+1 == i && !aux.equals(colList.get(j))){
				buffWrite.append(colList.get(j) + "");
			}else if(!aux.equals(colList.get(j))){ 
				buffWrite.append(colList.get(j) + ", ");
			}
			
		}
		
		buffWrite.append(") VALUES(");
		for(j = 0; j<i-1; j++){ //i-1 pq não coloca "?" pra pkey CONFERIR E VERIFICAR, POIS SE N TIVER PKEY VAI DA RUIM
			if(j+1 == i-1){
				buffWrite.append("?");
			}else{
				buffWrite.append("?, ");
			}
			
		}
		
		buffWrite.append(")\");\n");
		/*Trecho de código que trata do sql de inserção*/
		
		/*Trecho de código que trata do sql de alteração*/
		buffWrite.append("\t\tsetSqlAlteração(\"UPDATE " + tab.getNome() + " SET ");
		
		for(j = 0; j<i; j++){
			if(j+1 == i){
				buffWrite.append(colList.get(j) + " = ? " );
			}
			else buffWrite.append(colList.get(j) + " = ?, ");
			
		}
		
		buffWrite.append("WHERE "  + neg.procuraPKEY(tab) + " = ?\");\n");
		/*Trecho de código que trata do sql de alteração*/
		
		/*Trecho de código que trata do sql de exclusão*/
		/*Estarei usando como chave de exclusão a PKEY por ela ser unica*/
		buffWrite.append("\t\tsetSqlExclusao(\"DELETE FROM "+ tab.getNome() + " WHERE "+ neg.procuraPKEY(tab)+ " = ?\");\n");
		/*Trecho de código que trata do sql de exclusão*/
		
		/*Trecho de código que trata do sql de busca por PKEY*/
		buffWrite.append("\t\tsetSqlBuscaChavePrimaria(\"SELECT * FROM "+ tab.getNome()+ " WHERE "+ neg.procuraPKEY(tab)+ "= ?\");\n");
		/*Trecho de código que trata do sql de busca por PKEY*/
		
		/*Trecho de código que trata do sql de buscaTodos*/
		buffWrite.append("\t\tsetSqlBuscaTodos(\"SELECT * FROM "+ tab.getNome() +"\");\n");
		/*Trecho de código que trata do sql de buscaTodos*/
		
		/*Trecho de código que trata do sql de busca unitária*/
		buffWrite.append("\t\tsetSqlBusca("+"\""+"\""+"" +");\n");
		/*Trecho de código que trata do sql de busca unitária*/
		
		buffWrite.append("\t}\n");
	}
	
	/*Método para preencher a inserção*/
	public void escreveInserção(Tabela tab, BufferedWriter buffWrite) throws IOException{
		buffWrite.append("\n\t@Override\n \tprotected void preencherInsercao(PreparedStatement ps, "+ tab.getNome()+ " t) throws SQLException {\n");
		
	}
	
}	

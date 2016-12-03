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
				buffWrite = new BufferedWriter(new FileWriter("/home/leonardo/Documentos/CRUDS/DAOS/" + tabela.getNome().substring(0, 1).toUpperCase()+ tabela.getNome().substring(1)+ "DAO.java"));
				
				buffWrite.append("public class " + tabela.getNome().substring(0, 1).toUpperCase()+ tabela.getNome().substring(1) + "DAO extends Registros<"+ tabela.getNome().substring(0, 1).toUpperCase()+ tabela.getNome().substring(1) +">{\n\n");

				escreveConstrutorDAO(tabela, buffWrite);
				escreveInsercaoAlteracao(tabela, buffWrite);
				escreveExclusao(tabela, buffWrite);
				escrevePreencheColecao(tabela, buffWrite);
				escrevePreenche(tabela, buffWrite);
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
		
		buffWrite.append("\tpublic " + tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+"DAO() {\n");
		
		/*trecho que cod que trata do sql de insercão*/
		buffWrite.append("\t\tsetSqlInsercao(\"INSERT INTO " + tab.getNome() + " (");
		
		/*for(Coluna col: tab.getColunas()){
			if(!col.isPk()){
				buffWrite.append("\t\tps.set" + neg.processaTipo1(col.getTipo() ,1) + "("+i+",t."+col.getNome()+");\n");
			}else i--;
			
			i++;
		}*/
		
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
		buffWrite.append("\t\tsetSqlAlteracao(\"UPDATE " + tab.getNome() + " SET ");
		
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
	
	/*Método para preencher a inserção e alteração*/
	public void escreveInsercaoAlteracao(Tabela tab, BufferedWriter buffWrite) throws IOException{
		Negocios neg = new Negocios();
		int i = 1;
		
		/*INSERÇÃO*/
		buffWrite.append("\n\t@Override\n \tprotected void preencherInsercao(PreparedStatement ps, "+ tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+ " t) throws SQLException {\n");
		

		for(Coluna col: tab.getColunas()){
			if(!col.isPk()){
				buffWrite.append("\t\tps.set" + neg.processaTipo1(col.getTipo() ,1) + "("+i+",t.get"+col.getNome().substring(0, 1).toUpperCase()+ col.getNome().substring(1)+"());\n");
			}else i--;
			
			i++;
		}
		buffWrite.append("\t}\n");
		
		/*ALTERAÇÃO*/
		buffWrite.append("\n\t@Override\n \tprotected void preencherAlteracao(PreparedStatement ps, "+ tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+ " t) throws SQLException {\n");
		i = 1;
		
		for(Coluna col: tab.getColunas()){
			if(!col.isPk()){
				buffWrite.append("\t\tps.set" + neg.processaTipo1(col.getTipo() ,1) + "("+i+",t.get"+col.getNome().substring(0, 1).toUpperCase()+ col.getNome().substring(1)+"());\n");
			}else i--;
			
			i++;
		}
		buffWrite.append("\t}\n");
	}
	
	/*Método para escrever exclusão*/
	public void escreveExclusao(Tabela tab, BufferedWriter buffWrite) throws IOException{
		buffWrite.append("\n\t@Override\n \tprotected void preencherExclusao(PreparedStatement ps, "+ tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+ " t) throws SQLException {\n");
		Negocios neg = new Negocios();
		
		for(Coluna col : tab.getColunas()){
			if(col.isPk()){
				buffWrite.append("\t\tps.set"+neg.processaTipo1(col.getTipo(), 1) +"("+1+",t.get"+col.getNome().substring(0, 1).toUpperCase()+ col.getNome().substring(1)+"());\n");
			}
		}
		buffWrite.append("\t}\n");
	}
	
	/*Método para preencher coleçao*/
	public void escrevePreencheColecao(Tabela tab, BufferedWriter buffWrite) throws IOException{
		Negocios neg = new Negocios();
		
		buffWrite.append("\n\t@Override\n \tprotected Collection<"+tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+"> preencherColecao(ResultSet rs) throws SQLException {\n");
		buffWrite.append("\t\tCollection<"+tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+"> retorno = new ArrayList<"+tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+">();\n");
		buffWrite.append("\t\twhile (rs.next()) {\n");
		buffWrite.append("\t\t\t"+tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+" temp = new " + tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+"();\n");
		
		for(Coluna col : tab.getColunas()){
			if(!col.isPk())
				buffWrite.append("\t\t\ttemp.set"+col.getNome().substring(0, 1).toUpperCase()+ col.getNome().substring(1) +"(rs.get"+neg.processaTipo1(col.getTipo(), 1)+"(\""+col.getNome()+"\"));\n");
		}
		
		buffWrite.append("\t\t\tretorno.add(temp);\n");
		buffWrite.append("\t\t}\n");
		buffWrite.append("\t\treturn retorno;\n\t}\n");
	}
	
	public void escrevePreenche(Tabela tab, BufferedWriter buffWrite) throws IOException{
		Negocios neg = new Negocios();
		buffWrite.append("\n\t@Override\n \tprotected "+ tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1) + " preencher(ResultSet rs) throws SQLException {\n");
		buffWrite.append("\t\t"+tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+" " +tab.getNome() +"= new "+tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1)+"();\n");
		buffWrite.append("\t\tif (rs.next()) {\n");
		
		for(Coluna col : tab.getColunas()){
			if(!col.isPk()){
				buffWrite.append("\t\t\t"+tab.getNome() +".set"+col.getNome().substring(0, 1).toUpperCase()+ col.getNome().substring(1)+"");
				buffWrite.append("(rs.get"+neg.processaTipo1(col.getTipo(), 1)+"(\""+col.getNome()+"\"));\t\t\n");
			}
		}
		//buffWrite.append("\treturn " + tab.getNome()+"\n");
		buffWrite.append("\t\t}\n\t\treturn " + tab.getNome()+";\n\t\n\t}\n");
	}
	
}	

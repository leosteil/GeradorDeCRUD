package negocio;

import model.Coluna;
import model.Tabela;

public class Negocios {
	
	/*Este método é usado para alterar o tipo proveniente do banco em um tipo aceito em JAVA*/
	/*O segundo paremetro é usado para verificar se a função é chamada para construir uma classe...*/
	/*...ou para construir os DAOS*/
	public String processaTipo1(String tipo, int local){
		switch (tipo) {
			case "INT":
				if(local == 1){
					return "Int";
				}
				return "Integer";
			
				
			case "VARCHAR":
				return "String";
				
			case "DOUBLE":
				return "float";
				
			default:
				return "tipo indefinido";

		}	
	}
	
	/*Este método é usado para enviar as função que fazem o DAO
	public String processaTipo2(String tipo){
		switch (tipo) {
			case "INT":
				if()
				return "Int";
			
				
			case "VARCHAR":
				return "String";
				
			case "DOUBLE":
				return "float";
				
			default:
				return "tipo indefinido";

		}	
	}*/
	
	/*Procura em uma tabela a chave primaria*/
	public String procuraPKEY(Tabela tab){
		for(Coluna col : tab.getColunas()){
			if(col.isPk()){
				return col.getNome();
			}
			
		}
		return "null";
	}
	
	/*verifica se a coluna é PKEY*/
	public boolean isPKEY(Coluna col){
		if(col.isPk()){
			return true;
		}
		return false;
	}
}

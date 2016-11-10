package negocio;

import model.Coluna;
import model.Tabela;

public class Negocios {
	
	public String processaTipo(String tipo){
		switch (tipo) {
			case "INT":
				return "Integer";
			
				
			case "VARCHAR":
				return "String";
				
			case "DOUBLE":
				return "float";
				
			default:
				return "tipo indefinido";

		}	
	}
	
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

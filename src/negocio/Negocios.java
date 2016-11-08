package negocio;

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
	
}

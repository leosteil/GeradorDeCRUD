package escreve_arquivo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import negocio.Negocios;
import model.Banco;
import model.Coluna;
import model.Tabela;

public class EscreveExemplo {
	Negocios neg = new Negocios();
	
	
	public void escreveExemplo(Banco banco) throws IOException{
		
		BufferedWriter buffWrite = null;
		
		File diretorio = new File("/home/leonardo/Documentos/CRUDS/Exemplos");
		diretorio.mkdir();
		
		for (Tabela tabela : banco.getTabelas()) {

			
			buffWrite = new BufferedWriter(new FileWriter(
					diretorio +"/"+ tabela.getNome().substring(0, 1).toUpperCase()
					+ tabela.getNome().substring(1) + "Exemplo.java"));
			
			escreveCabecalho(tabela, buffWrite);
			
			buffWrite.append("public class "+tabela.getNome().substring(0, 1).toUpperCase()
					+ tabela.getNome().substring(1) + "Exemplo{\n"
					+"\tpublic static void main(String[] args) throws ClassNotFoundException, SQLException {\n"
					+ "\t\t"+tabela.getNome().substring(0, 1).toUpperCase() +tabela.getNome().substring(1)+"DAO cs"
					+ "= new " +tabela.getNome().substring(0, 1).toUpperCase() + tabela.getNome().substring(1)+"DAO();\n");
			
			escreveListaElementos(tabela, buffWrite);
			escreveExcluiTodos(tabela, buffWrite);
		
			buffWrite.append("\t}\n}");
			
			buffWrite.close();
		
		}
		
		
	}
	
	/* Método para escrever o cabeçalho: imports e package */
	public void escreveCabecalho(Tabela tab, BufferedWriter buffWrite) throws IOException {
		String string = new String();

		string = "package exemplos;\n\n"
				+ "import java.sql.SQLExecption\n\n";
		
		buffWrite.append(string);

	}
	
	public void escreveListaElementos(Tabela tab, BufferedWriter buffWriter) throws IOException{
		String tipo = new String();
		int i = 0;
		int j = 0;
		
		buffWriter.append("\n\t\tSystem.out.println(\"Listando todos os elementos...\");\n");
		buffWriter.append("\t\tfor ("+tab.getNome().substring(0, 1).toUpperCase()
									+ tab.getNome().substring(1)+ " temp : cs.buscarTodos())\n");

		buffWriter.append("\t\t\tSystem.out.println(");
		
		for(Coluna col : tab.getColunas()){
			i++;
			System.out.println("i"+i);
		}
		
		for (Coluna coluna : tab.getColunas()) {
			tipo = neg.processaTipo1(coluna.getTipo(), 0);
			if(!coluna.isPk() && !(j+1==i))
				buffWriter.append("temp.get"+ coluna.getNome().substring(0, 1).toUpperCase()
							  + coluna.getNome().substring(1)+"\n\t\t\t\t\t+ ");
			else if(!coluna.isPk() && j+1 == i){
				buffWriter.append("temp.get"+ coluna.getNome().substring(0, 1).toUpperCase()
						  +coluna.getNome().substring(1)+");\n\n\n ");
			}
			
			j++;
			System.out.println("j"+j);
		}
		
	}

	public void escreveExcluiTodos(Tabela tab, BufferedWriter buffWriter) throws IOException{
		buffWriter.append("\t\tfor ("+tab.getNome().substring(0, 1).toUpperCase()+ tab.getNome().substring(1) +" temp : cs.buscarTodos())"
							+ "\n\t\t\tcs.excluir(temp);\n");
		
	}
}


//for (tabelaTeste3 temp : cs.buscarTodos())
	//System.out.println(temp.getnomeAluno()+ " - " + temp.gettelefoneDoCara());

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

public class EscreveClasse {

	/* este método escreve o nome da classe */
	public static void escreveClasse(Banco banco) throws IOException {
		Negocios neg = new Negocios();

		for (Tabela tabela : banco.getTabelas()) {
			BufferedWriter buffWrite = null;
			
			buffWrite = new BufferedWriter(new FileWriter(
					"/home/leonardo/Documentos/CRUDS/Classes/"
							+ tabela.getNome().substring(0, 1).toUpperCase()
							+ tabela.getNome().substring(1) + ".java"));		
			
			buffWrite.append("package modelo;\n\n");

			buffWrite.append("public class "
					+ tabela.getNome().substring(0, 1).toUpperCase()
					+ tabela.getNome().substring(1) + " extends Registro {\n");

			atributosEscritor(tabela, buffWrite);
			getSetEscritor(tabela, buffWrite); 
			buffWrite.append("}");
			buffWrite.close();
		}

	}

	/* Escreve os atributos na classe */
	public static void atributosEscritor(Tabela tab, BufferedWriter buffWrite)
			throws IOException {
		String tipo = new String();
		Negocios neg = new Negocios();

		for (Coluna col : tab.getColunas()) {
			tipo = neg.processaTipo1(col.getTipo(), 0);
			buffWrite.append("\t private " + tipo + " " + col.getNome() + ";\n");
		}

		buffWrite.append("\n");
	}

	/* Este método escreve os métodos get e set de cada classe */
	public static void getSetEscritor(Tabela tab, BufferedWriter buffWrite)
			throws IOException {
		Negocios neg = new Negocios();
		String tipo = new String();

		for (Coluna coluna : tab.getColunas()) {
			tipo = neg.processaTipo1(coluna.getTipo(), 0);

			String get = "\tpublic " + tipo + " get"
					+ coluna.getNome().substring(0, 1).toUpperCase()
					+ coluna.getNome().substring(1) + "() {\n" + "\t\treturn "
					+ coluna.getNome() + ";\n" + "\t}";

			String set = "\tpublic void set"
					+ coluna.getNome().substring(0, 1).toUpperCase()
					+ coluna.getNome().substring(1) + " (" + tipo + " "
					+ coluna.getNome() + ") {\n" + "\t\tthis."
					+ coluna.getNome() + " = " + coluna.getNome() + ";\n"
					+ "\t}";

			buffWrite.append(get + "\n\n");
			buffWrite.append(set + "\n\n");
		}
	}

}
package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.FabricaConexao;
import escreve_arquivo.EscreveClasse;
import escreve_arquivo.EscreveDao;
import map.MapeadorBanco;
import model.Banco;
import model.Coluna;
import model.Tabela;

public class Test {

	public static void main(String[] args) throws IOException {
		
		/*Cria as classes*/
		EscreveClasse classe = new EscreveClasse();
		EscreveDao dao = new EscreveDao();
		
		classe.classeNomeAtributoEscritor();
		dao.escreveNomeDAO();
		
	}
}

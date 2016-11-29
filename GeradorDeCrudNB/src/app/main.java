package app;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import arquivos_fixos.LeitorArquivosFixos;
import conexao.ConectaBanco;
import escreve_arquivo.EscreveClasse;
import escreve_arquivo.EscreveDao;
import escreve_arquivo.EscreveExemplo;
import map.MapeadorBanco;
import model.Banco;


public class main {

	public static void main(String[] args) throws IOException, SQLException {
		
		/*Cria uma conexão com o banco de dados*/
		MapeadorBanco map = new MapeadorBanco();
		ConectaBanco conect = new ConectaBanco();
		Connection connection = conect.devolveConexão();
		Banco banco = map.getBanco("TESTE", connection);
		
		
		/*Instancia as classes*/
		EscreveClasse classe = new EscreveClasse();
		EscreveDao classeDAO = new EscreveDao();
		EscreveExemplo exemplos = new EscreveExemplo();
		LeitorArquivosFixos l = new LeitorArquivosFixos();
		
		/*Chama os métodos*/
		classe.escreveClasse(banco);
		classeDAO.escreveDAO(banco);
		exemplos.escreveExemplo(banco);
		
		/*Arquivos Fixos*/
		l.escreveJava();
		
	}
}

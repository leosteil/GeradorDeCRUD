package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Banco {

	private String nome;
	private List<Tabela> tabelas = new ArrayList<>();
	
	public Banco() {
		super();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Tabela> getTabelas() {
		return tabelas;
	}
	public void setTabelas(List<Tabela> tabelas) {
		this.tabelas = tabelas;
	}
	
	//Retorna todas as tabelas do banco sem a tabela excTabela
	public List<Tabela> getTabelasSem(Tabela excTabela){
		List<Tabela> list = new LinkedList<>();
		for (Tabela t : tabelas) {
			if (!t.equals(excTabela))
				list.add(t);
		}
		return list;
	}
	
}

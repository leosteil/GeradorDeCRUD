package model;

import java.util.ArrayList;
import java.util.List;

public class Tabela {

	private String nome;
	private List<Coluna> colunas = new ArrayList<>();
	
	public Tabela() {
		super();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Coluna> getColunas() {
		return colunas;
	}
	public void setColunas(List<Coluna> colunas) {
		this.colunas = colunas;
	}
	
	//Retorna a coluna conforme o nome
	public Coluna getColuna(String nome){
		for (Coluna coluna : colunas) {
			if (nome.compareTo(coluna.getNome()) == 0)
				return coluna;
		}
		return null;
	}
	
}

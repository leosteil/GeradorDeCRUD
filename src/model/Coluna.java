package model;

public class Coluna {

	private String nome;
	private String tipo;
	private int tam;
	private boolean pk;
	private boolean unique;
	private boolean notNull;
	private boolean autoInc;
	private Tabela fk;
	
	public Coluna() {
		super();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getTam() {
		return tam;
	}
	public void setTam(int tam) {
		this.tam = tam;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public boolean isUnique() {
		return unique;
	}
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	public boolean isNotNull() {
		return notNull;
	}
	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	public boolean isAutoInc() {
		return autoInc;
	}
	public void setAutoInc(boolean serial) {
		this.autoInc = serial;
	}
	public Tabela getFk() {
		return fk;
	}
	public void setFk(Tabela fk) {
		this.fk = fk;
	}
	
}

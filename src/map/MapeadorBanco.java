package map;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import model.Banco;
import model.Coluna;
import model.Tabela;

public class MapeadorBanco {

	public Banco getBanco(String nome, Connection connection) throws SQLException{
		DatabaseMetaData md = connection.getMetaData();
		ResultSet result = md.getTables(null, null, null, null);
		ResultSetMetaData rmd = result.getMetaData();
		Banco banco = new Banco();
		banco.setNome(nome);
		while (result.next()){
			for (int i = 1; i <= rmd.getColumnCount(); i++) {
				if (rmd.getColumnName(i).compareTo("TABLE_NAME") == 0){
					Tabela tabela = new Tabela();
					tabela.setNome(result.getString(i));
					banco.getTabelas().add(tabela);
				}
			}
		}
		for (Tabela tabela : banco.getTabelas()) {
			//...
			result = md.getColumns(null, null, tabela.getNome(), null);
			rmd = result.getMetaData();
			while (result.next()){
				Coluna column = new Coluna();
				for (int i = 1; i <= rmd.getColumnCount(); i++) {
					if (rmd.getColumnName(i).compareTo("COLUMN_NAME") == 0)
						column.setNome(result.getString(i));
					if (rmd.getColumnName(i).compareTo("TYPE_NAME") == 0)
						column.setTipo(result.getString(i));
					if (rmd.getColumnName(i).compareTo("COLUMN_SIZE") == 0)
						column.setTam(result.getInt(i));
					if (rmd.getColumnName(i).compareTo("NULLABLE") == 0)
						column.setNotNull(result.getInt(i) == 0);
					if (rmd.getColumnName(i).compareTo("IS_AUTOINCREMENT") == 0 && result.getString(i).compareTo("YES") == 0)
						column.setAutoInc(true);
				}
				tabela.getColunas().add(column);
			}
			result.close();
			result = md.getIndexInfo(null, null, tabela.getNome(), false, false);
			rmd = result.getMetaData();
			while (result.next()){
				String nomeColuna = null;
				boolean pk = false;
				boolean un = false;
				for (int i = 1; i <= rmd.getColumnCount(); i++) {
					if (rmd.getColumnName(i).compareTo("COLUMN_NAME") == 0)
						nomeColuna = result.getString(i);
					else if (rmd.getColumnName(i).compareTo("INDEX_NAME") == 0){
						String nomeIndice = result.getString(i);
						if (nomeIndice.compareTo("PRIMARY") == 0)
							pk = true;
						else if (nomeIndice.contains("_UNIQUE"))
							un = true;
					}
				}
				if (nomeColuna != null){
					if (pk)
						tabela.getColuna(nomeColuna).setPk(true);
					if (un)
						tabela.getColuna(nomeColuna).setUnique(true);
				}
			}
			result.close();
		}
		for (Tabela tabela : banco.getTabelas()) {
			List<Tabela> list = banco.getTabelasSem(tabela);
			for (Tabela ot : list) {
				//Pega a referencia entre uma tabela e outra
				result = md.getCrossReference(null, null, ot.getNome(), null, null, tabela.getNome());
				rmd = result.getMetaData();
				while (result.next()){
					String pkColumnName = null;
					String pkTabelaName = null;
					for (int i = 1; i <= rmd.getColumnCount(); i++) {
						if (rmd.getColumnName(i).compareTo("PKCOLUMN_NAME") == 0)
							pkColumnName = result.getString(i);
						else if (rmd.getColumnName(i).compareTo("PKTABLE_NAME") == 0)
							pkTabelaName = result.getString(i);
					}
					if (pkColumnName != null && pkTabelaName != null){
						for (Tabela at : banco.getTabelas()) {
							if (at.getNome().compareTo(pkTabelaName) == 0){
								Coluna pk = tabela.getColuna(pkColumnName);
								if (pk != null)
									pk.setFk(at);
							}
						}
					}
				}
				result.close();
			}
		}
		return banco;
	}
	
}

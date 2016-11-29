package arquivos_fixos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LeitorArquivosFixos {
	
	public String leitorTxt (String nome){
		Scanner scan = new Scanner(this.getClass().getResourceAsStream((nome)));
		StringBuilder str = new StringBuilder();
		
		while (scan.hasNextLine()){
			str.append(scan.nextLine()).append("\n");
		}
		
		
		return str.toString();	
	}

	
	public void escreveJava () throws IOException{
		BufferedWriter buffWrite = null;
		BufferedWriter buffWrite2 = null;
		
		buffWrite = new BufferedWriter(new FileWriter("/home/leonardo/Documentos/CRUDS/Classes/Registros.java"));	
		buffWrite2 = new BufferedWriter(new FileWriter("/home/leonardo/Documentos/CRUDS/Classes/Registro.java"));	

		
		String str = leitorTxt("Registros.txt");
		String str2  = leitorTxt("Registro.txt");
		
		buffWrite.append(str);
		buffWrite2.append(str2);
		
		buffWrite.close();
		buffWrite2.close();
		
	}
}

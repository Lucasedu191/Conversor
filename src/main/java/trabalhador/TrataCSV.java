package trabalhador;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import controle.InterfaceCSV;

public class TrataCSV implements Runnable {
	private InterfaceCSV controler;
	private File fileCSV;
	
	public TrataCSV(InterfaceCSV controler,File file) {
		this.controler = controler;
		this.fileCSV = file;
	}
	
	public int getQtdeRegistros() {
		try(LineNumberReader lnr = new LineNumberReader(new FileReader(fileCSV));){
			lnr.skip(Long.MAX_VALUE);
			return lnr.getLineNumber() -1;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void run() {
		try (Reader reader = new FileReader(fileCSV); ){
			CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT);
			
			controler.setContinuaLeituraCSV(true);
			
			for(CSVRecord csvRecord : parser) {
				if(parser.getCurrentLineNumber() == 1)
					continue;
				controler.addRegistroCSV(csvRecord);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		controler.setContinuaLeituraCSV(false);
		
	}
}

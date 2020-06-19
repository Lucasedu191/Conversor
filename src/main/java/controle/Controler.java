package controle;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.apache.commons.csv.CSVRecord;


import trabalhador.ParseJson;
import trabalhador.SalvaJson;
import trabalhador.TrataCSV;


public class Controler implements InterfaceCSV, InterfaceJson {
	private List<CSVRecord> filaCSV = null;
	private List<String> filaJson = null;
	private boolean continuaLeituraCSV = false;
	private File fileCSV;
	private File fileJson;
	private int quantidadeREGISTROS;
	private int RegistrosLIDOS;
	
	public Controler(File fileCSV, File fileJSON) {
		this.fileCSV = fileCSV;
		this.fileJson = fileJSON;
		this.filaCSV = new Vector<CSVRecord>();
		this.filaJson = new Vector<String>();
		
	}
	public void inicia() {
		this.trataCSV(fileCSV);
		
		this.parserJson();
		
		new SalvaJson().salva(fileJson, filaJson);
		
	}
	
	private void trataCSV(File fileCSV) {
		TrataCSV trataCSV = new TrataCSV(this, fileCSV);
		
		quantidadeREGISTROS = trataCSV.getQtdeRegistros();
		Thread tCSV = new Thread(trataCSV);
		tCSV.start();
	}


	@Override
	public synchronized boolean emOperacao() {
		return filaCSV.size() > 0 || continuaLeituraCSV;
	}
	private void parserJson() {
		Thread t1 = new Thread(new ParseJson(this));
		Thread t2 = new Thread(new ParseJson(this));
		Thread t3 = new Thread(new ParseJson(this));
		Thread t4 = new Thread(new ParseJson(this));
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public synchronized CSVRecord getCSV() {
		if (filaCSV.size() > 0)
			return filaCSV.remove(0);
		return null;
	}
	

	
	@Override
	public synchronized void addJson(String json) {
		filaJson.add(json);
	}
	
	
	
	@Override
	public synchronized void addRegistroCSV(CSVRecord csvRecord) {
		this.filaCSV.add(csvRecord);
		this.RegistrosLIDOS++;	
	}
	
	@Override
	public synchronized void setContinuaLeituraCSV(boolean terminou) {
		continuaLeituraCSV = terminou;
		System.out.println("Termino: " + continuaLeituraCSV);
		
	}
		
	public synchronized boolean isContinuaLeituraCSV() {
		return this.continuaLeituraCSV;
	}
	
	public synchronized int getQtdeRegistros() {
		return this.quantidadeREGISTROS;
	}
	
	public synchronized int getRegistrosLidos() {
		return this.RegistrosLIDOS;
	}
	

}

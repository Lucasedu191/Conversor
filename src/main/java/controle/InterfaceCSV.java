package controle;

import org.apache.commons.csv.CSVRecord;

public interface InterfaceCSV {
	public void addRegistroCSV(CSVRecord csvRecord);
	
	public void setContinuaLeituraCSV(boolean terminou);

}

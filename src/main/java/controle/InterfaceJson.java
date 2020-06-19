package controle;

import org.apache.commons.csv.CSVRecord;

public interface InterfaceJson {

	CSVRecord getCSV();

	boolean emOperacao();

	void addJson(String json);

}
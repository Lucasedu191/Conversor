package trabalhador;

import org.apache.commons.csv.CSVRecord;

import com.google.gson.Gson;


import controle.InterfaceJson;
import entidade.Registro;

public class ParseJson implements Runnable {
	
	private InterfaceJson controler;
	
	public ParseJson(InterfaceJson interfaceJson) {
		this.controler = interfaceJson;
		
	}

	@Override
	public void run() {
		
		do {
			
			CSVRecord csvRecord = controler.getCSV();
			
			if(csvRecord == null) {
				synchronized (this) {
					try {
						this.wait(200);
						System.out.println(Thread.currentThread().getName() + "Foi para Wait");
						
					} catch (InterruptedException e) {
						System.out.println("Parse data: " + e.getMessage());
					}
				}
			}else {
				String json = converteParaJson(csvRecord);
				controler.addJson(json);
				
				System.out.println(Thread.currentThread().getName()+"Colocou na fila Json");
			
			}
			
		} while(controler.emOperacao());

	}
	private String converteParaJson(CSVRecord csvRecord) {
		Registro reg = new Registro();

		reg.setNumber(csvRecord.get(0));
		reg.setGender(csvRecord.get(1));
		reg.setNameSet(csvRecord.get(2));
		reg.setTitle(csvRecord.get(3));
		reg.setGivenName(csvRecord.get(4));
		reg.setSurname(csvRecord.get(5));
		reg.setStreetAddress(csvRecord.get(6));
		reg.setCity(csvRecord.get(7));
		reg.setState(csvRecord.get(8));
		reg.setZipCode(csvRecord.get(9));
		reg.setCountryFull(csvRecord.get(10));
		reg.setEmailAddress(csvRecord.get(11));
		reg.setUsername(csvRecord.get(12));
		reg.setPassword(csvRecord.get(13));
		reg.setTelephoneNumber(csvRecord.get(14));
		reg.setBirthday(csvRecord.get(15));
		reg.setCCType(csvRecord.get(16));
		reg.setCCNumber(csvRecord.get(17));
		reg.setCVV2(csvRecord.get(18));
		reg.setCCExpires(csvRecord.get(19));
		reg.setNationalID(csvRecord.get(20));
		reg.setColor(csvRecord.get(21));
		reg.setKilograms(csvRecord.get(22));
		reg.setCentimeters(csvRecord.get(23));
		reg.setGUID(csvRecord.get(24));	
	 		
		Gson gson = new Gson();
		
		return gson.toJson(reg, Registro.class);
		
	}

}

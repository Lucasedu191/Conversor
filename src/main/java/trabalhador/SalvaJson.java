package trabalhador;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SalvaJson {
	public void salva(File fileJson, List<String> filaJson) {
		List<String> lista = filaJson;
		
		Path path = Paths.get(fileJson.getPath());
		try {
			Files.write(path, lista, StandardCharsets.UTF_8);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package InterfaceUsu;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import controle.Controler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InicialController implements javafx.fxml.Initializable {
	@FXML
	private Button btn_ArquivoCSV;
	@FXML
	private Button btn_ArquivoJSON;
	@FXML
	private Button btn_Converter;
	@FXML
	private ProgressBar PrB_Proecesso;
	@FXML
	private TextArea txtA_Status;
	@FXML
	private Button btn_ArquivoJson;
	 


	private Controler controller;
	private File fileCSV;
	private File fileJSON;
	private int totalLinhas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn_Converter.setDisable(true);

	}

	@FXML
	private void ArquivoCSV(ActionEvent event) {
		btn_Converter.setDisable(true);
		FileChooser fileChooser = new FileChooser();

		fileCSV = fileChooser.showOpenDialog(null);
	    if (fileJSON != null) {
	    	fileCSV = new File(fileChooser.toString());
	    }
		
		if (fileCSV != null)
			
		txtA_Status.appendText("\n\nARQUIVO DE ORIGEM SELECIONADO");
		
		btn_Converter.setDisable(false);
		verificaSerArquivoSelecionados();
	}

	@FXML
	private void ArquivoJson(ActionEvent event) {
		btn_Converter.setDisable(true);
		FileChooser fileChooser = new FileChooser();
	
		 fileJSON = fileChooser.showOpenDialog(null);
		if (fileJSON != null)
			txtA_Status.appendText("\n\nARQUIVO DE DESTINO SELECIONADO");
		btn_Converter.setDisable(false);
		verificaSerArquivoSelecionados();
	}

	@FXML
	private void Converter(ActionEvent event) {
		controller = new Controler(fileCSV, fileJSON);
		controller.inicia();
		btn_Converter.setDisable(true);

		do {
			
			totalLinhas = controller.getQtdeRegistros();
			if (!controller.isContinuaLeituraCSV())
				break;
		} while (totalLinhas == 0);

		this.atualizaCSV();
	}

	private void atualizaCSV() {
		Task<Void> taskCSV = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				int lidos =0;
				do {
					lidos = controller.getRegistrosLidos();
					updateProgress(lidos,totalLinhas);
				}while(controller.isContinuaLeituraCSV());
			
				updateMessage("Total de linha: " + totalLinhas + "\nLinhas lidas: " + lidos);
				return null;
			}

			@Override
			protected void succeeded() {
				updateMessage("\n Feito");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
				updateMessage("Cancelado");
			}

			@Override
			protected void failed() {
				super.failed();
				updateMessage("Falhou");
			}

		};
		taskCSV.messageProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obs, String oldMessage, String newMessage) {
				txtA_Status.appendText("\n" + taskCSV.getMessage());
			}
		});
		PrB_Proecesso.progressProperty().bind(taskCSV.progressProperty());
		
		new Thread(taskCSV).start();
	}

	public void verificaSerArquivoSelecionados() {
		if (fileCSV != null & fileJSON != null) {
			btn_Converter.setDisable(false);
		}
	}

}

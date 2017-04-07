package translator.gui.screens;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.FileChooser;
import translator.control.factory.ParseFactory;
import translator.gui.Modal;
import translator.gui.ScreensConfig;
import translator.persistence.model.LocalizationModel;
import translator.persistence.service.LangLineService;
import translator.services.ParseService;

public class OpenDialog extends Modal {
	final String errorMSG = "A fájl nem található";
	@Autowired
	ParseFactory parseFactory;
	@FXML
	TextField firstLangPath;
	@FXML
	TextField secondLangPath;
	File firstLangFile;
	File secondLangFile;
	@FXML
	Label firstErrorLabel;
	@FXML
	Label secondErrorLabel;
	@FXML
	ProgressBar progressBar;
	@Autowired
	LangLineService line;
	@FXML
	ProgressIndicator progressIndicator;
	@FXML
	Label stateLabel;
	private static final Logger logger = LogManager.getLogger(OpenDialog.class);
	@FXML
	Button exitButton;

	public OpenDialog(ScreensConfig config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	void setFirstLang(File f) {
		if (f == null)
			return;
		firstLangFile = f;
		firstLangPath.setText(firstLangFile.getAbsolutePath());
	}

	void setSecondLang(File f) {
		if (f == null)
			return;
		secondLangFile = f;
		secondLangPath.setText(secondLangFile.getAbsolutePath());
	}

	void setSecondLang() {
	}

	@FXML
	public void loadFirstLang() {
		setFirstLang(openFileDialog());

	}

	@FXML
	public void loadSecondLang() {
		setSecondLang(openFileDialog());
	}

	private File openFileDialog() {
		final FileChooser fileChooser = new FileChooser();
		return fileChooser.showOpenDialog(config.getStage());
	}

	@FXML
	public void firstDrag(DragEvent event) {
		setFirstLang(getDraggedFile(event));

	}

	@FXML
	public void secondDrag(DragEvent event) {
		setSecondLang(getDraggedFile(event));

	}

	private File getDraggedFile(DragEvent event) {
		Dragboard dragboard = event.getDragboard();
		File returnFile = null;
		if (dragboard.hasFiles()) {
			for (File file : dragboard.getFiles()) {
				returnFile = file;
				dragboard.clear();
			}
		}
		return returnFile;
	}

	@FXML
	public void checkPath(InputMethodEvent event) {

		String filePath = event.getCommitted();
		File inputFile = new File(filePath);
		if (!inputFile.exists()) {
			getLabel(event).setText(errorMSG);
		}

	}

	Label getLabel(InputMethodEvent event) {
		if (event.getSource() == firstLangPath) {
			return firstErrorLabel;
		} else
			return secondErrorLabel;
	}

	@FXML
	public void upload() {
		logger.info("upload started");

		String statetxt = "processing started";
		stateLabel.setText(statetxt);
		ParseService task2 = parseFactory.build(firstLangFile, secondLangFile);
		progressBar.progressProperty().bind(task2.progressProperty());
		task2.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				List<LocalizationModel> value = task2.getValue();
				String statetxt = String.valueOf(value.size()) + "wait for uploading";
				stateLabel.setText(statetxt);

				Task<Void> persistAllInTask = line.persistAllInTask(value);
				persistAllInTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

					@Override
					public void handle(WorkerStateEvent event) {
						logger.info("upload done");
						doneAlert();
					}
				});
				persistAllInTask.setOnFailed(new EventHandler<WorkerStateEvent>() {

					@Override
					public void handle(WorkerStateEvent event) {
						logger.warn("Something went wrong.");
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Information Dialog");
						alert.setHeaderText(null);
						alert.setContentText("Upload failed");

						alert.showAndWait();
					}
				});
				progressIndicator.progressProperty().bind(persistAllInTask.progressProperty());
				Thread persistThread = new Thread(persistAllInTask);
				persistThread.setDaemon(true);
				persistThread.start();
			}
			
		});
		task2.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				Throwable exception = task2.getException();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Process Failed");
				alert.setHeaderText(exception.getMessage());
				alert.setContentText(translator.utils.Util.getCause(exception));
				alert.setWidth(alert.getWidth()+50);
				alert.showAndWait();
				
			}
		});
		Thread th = new Thread(task2);
		th.setDaemon(true);
		th.start();
		//
		// try {
		// th.join();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// List<LangLineModel> value = task2.getValue();
		// statetxt = String.valueOf(value.size()) + "wait for uploading";
		// stateLabel.setText(statetxt);
		// Task<Void> persistAllInTask = lines.persistAllInTask(value);
		//
		// progressIndicator.progressProperty().bind(persistAllInTask.progressProperty());
		// Thread persistThread = new Thread(persistAllInTask);
		// persistThread.setDaemon(true);
		// persistThread.start();
		// doneAlert();
	}

	private void doneAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Upload is done");

		alert.showAndWait();
	}

	@FXML
	public void exit(ActionEvent event) {
		// event->
	}

}

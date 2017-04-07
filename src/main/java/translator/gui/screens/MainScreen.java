package translator.gui.screens;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import translator.gui.Presentation;
import translator.gui.ScreensConfig;
import translator.persistence.model.LocalizationModel;
import translator.persistence.service.LangLineService;

public class MainScreen extends Presentation {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainScreen.class);

	@FXML
	Label arany;
	@FXML
	Label isOnline;
	@FXML
	TextField dictionaryField;
	@FXML
	Button dictionarySearch;
	@FXML
	MenuItem openMenuItem;
	@FXML
	StackPane root;
	@FXML
	BorderPane innerRoot;
	@Autowired
	LangLineService lineService;
	@FXML
	TextArea firstLangTextArea;
	@FXML
	TextArea secondLangTextArea;
	@FXML
	ProgressIndicator translateProgressIndicator;
	LocalizationModel actualModel;
	@FXML
	Button nextButton;
	@FXML
	Button prevButton;
	@FXML
	TextField firstSearchField;
	@FXML
	ListView<LocalizationModel> firstSearchFieldResult;
	@FXML
	Button loadbutton;
	@FXML
	TextArea previewBox;
	ObservableList<LocalizationModel> seasonList;

	public MainScreen(ScreensConfig config) {
		super(config);
	}

	@FXML
	public void initialize(){
		
	}
	
	@Override
	public void postCreate() {

		refreshScreen();
		seasonList = FXCollections.observableList(lineService.listVoidRecords());
		firstSearchFieldResult.setItems(seasonList);
		firstSearchFieldResult.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<LocalizationModel>() {

					@Override
					public void changed(ObservableValue<? extends LocalizationModel> observable, LocalizationModel oldValue,
							LocalizationModel newValue) {
						modelChanged(observable, oldValue, newValue);

					}
				});
		
	}

	private void modelChanged(ObservableValue<? extends LocalizationModel> observable, LocalizationModel oldValue,
			LocalizationModel newValue) {
		String old = oldValue == null ? "null" : oldValue.toString();
		String newText = newValue == null ? "null" : newValue.toString();
		LOGGER.info("Season changed: old = " + old + ", new = " + newText + "\n");
		previewBox.setText(newText);
	}

	@FXML
	public void showOpenDialog(ActionEvent event) {
		Stage dialog = config.loadOpenDialog();
		refreshScreen();
	}

	private void refreshScreen() {
		List<LocalizationModel> listVoidRecords = lineService.listVoidRecords();
		
		if (listVoidRecords!=null && !listVoidRecords.isEmpty()) {
			actualModel = listVoidRecords.get(0);
			firstLangTextArea.setText(actualModel.getFirstLang());
			secondLangTextArea.clear();
		}

		refreshProgress();
	}

	@FXML
	public void save(ActionEvent event) {
		// egyelőre teszt
		actualModel.setSecondLang(secondLangTextArea.getText());
		lineService.update(actualModel);
		refreshProgress();
	}

	private void refreshProgress() {
		translateProgressIndicator.setProgress(getDonePercent());
	}

	private double getDonePercent() {
		double allSize = lineService.listAll().size();
		double progressSize = lineService.listVoidRecords().size();
		double dres = (allSize - progressSize) / allSize;

		return dres;
	}

	@FXML
	public void nextLine() {
		refreshScreen();
	}

	@FXML
	public void firstSearchTextChanged(InputMethodEvent event) {
	
	}

	@FXML
	public void listMouseClicked(MouseEvent event) {

	}

	@FXML
	public void load(ActionEvent event) {

	}

	@FXML public void firstSearchTextCTyped(KeyEvent event) {
		String text = firstSearchField.getText();
		LOGGER.error("valami van");
		List<LocalizationModel> listByFirstLang = lineService.listByFirstLang(text);
		LOGGER.info("expression: "+text+"; found:"+listByFirstLang.size());
		seasonList.clear();
		seasonList.addAll(listByFirstLang);
	}

}

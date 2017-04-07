package translator.gui.customUiElements;

import javafx.scene.control.ListCell;
import translator.domain.LineDomainModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public abstract class CustomListCell extends ListCell<LineDomainModel> {

	@FXML Label id;
	@FXML Label firstLang;
	@FXML Label secondLang;
	@FXML Button okButton;
	
	public CustomListCell() {
	super();
	
	}
	
	
	@Override
	protected void updateItem(LineDomainModel item, boolean empty) {
		super.updateItem(item, empty);
		
		id.setText(String.valueOf(item.getId()));
		firstLang.setText(checkLang(item.getFirstLang()));
		secondLang.setText(checkLang(item.getSecondLang()));
	}
	
	@FXML
	private void buttonOnActon(){
		buttonAction();
	}
	private String checkLang(String lang){
		return lang==null ? "":lang;
	}
	
	public  abstract void buttonAction();
}

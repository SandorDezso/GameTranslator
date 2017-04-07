package translator.gui.presentation;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import translator.gui.Modal;
import translator.gui.ScreensConfig;
import translator.uimodel.MessageModel;

public class PopupPresentation extends Modal {


    public PopupPresentation(ScreensConfig config) {
        super(config);
    }

    @Autowired
    private MessageModel model;

    @FXML
    TextField messageTf;

    @FXML
    void initialize() {
        messageTf.setText(model.getMessage());
    }

    @FXML
    void clickedOk(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void onApply(ActionEvent event) {
        model.setMessage(messageTf.getText());
    }

}

package translator.gui;

import translator.gui.modal.ModalDialog;

public abstract class Modal extends Presentation {

	  protected ModalDialog dialog;

	    public Modal(ScreensConfig config) {
	        super(config);
	    }

	    public void setDialog(ModalDialog dialog) {
	        this.dialog = dialog;
	    }

}

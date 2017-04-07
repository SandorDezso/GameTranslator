package translator.gui;

public abstract class Presentation {

    protected ScreensConfig config;

    public Presentation(ScreensConfig config) {
        this.config = config;
    }
    
     public void postCreate(){}

	
}
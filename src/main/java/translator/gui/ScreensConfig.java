package translator.gui;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import translator.gui.modal.ModalDialog;
import translator.gui.presentation.FirstPresentation;
import translator.gui.presentation.PopupPresentation;
import translator.gui.screens.MainScreen;
import translator.gui.screens.OpenDialog;
import translator.uimodel.LanguageModel;
	
@Configuration
@Lazy
public class ScreensConfig implements Observer{
	 private static final Logger logger = LogManager.getLogger(ScreensConfig.class);

	    public static final int WIDTH = 1020;
	    public static final int HEIGHT = 700;
	    public static final String STYLE_FILE = "main.css";

	    private Stage stage;
	    private Scene scene;
	    private LanguageModel lang;
	    private Pane root;

	    public void setPrimaryStage(Stage primaryStage) {
	        this.stage = primaryStage;
	    }

	    public void setLangModel(LanguageModel lang) {
	        if (this.lang != null) {
	            this.lang.deleteObserver(this);
	        }
	        lang.addObserver(this);
	        this.lang = lang;
	    }

	    public ResourceBundle getBundle() {
	        return lang.getBundle();
	    }

	    public void showMainScreen() {
	        root = new StackPane();
	        root.getStylesheets().add(STYLE_FILE);
	        root.getStyleClass().add("main-window");
	        stage.setTitle("SpringFX");
	        scene = new Scene(root, WIDTH, HEIGHT);
	        stage.setScene(scene);
	        stage.setResizable(true);

	        stage.setOnHiding(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent event) {
	                System.exit(0);
	                // TODO you could add code to open an "are you sure you want to exit?" dialog
	            }
	        });

	        stage.show();
	        
	    }

	    private void setNode(Node node) {
	        root.getChildren().setAll(node);
	    }

	    private void setNodeOnTop(Node node) {
	        root.getChildren().add(node);
	    }

	    public void removeNode(Node node) {
	        root.getChildren().remove(node);
	    }

	    void loadMain(){
	    	MainScreen mainScreen = mainScreen();
			setNode(getNode(mainScreen,getClass().getResource("../../MainScreen.fxml")));
			mainScreen.postCreate();
	    }
	    
	
	    void loadFirst() {
	    	
	        URL resource = getClass().getResource("../First.fxml");
	        if(resource!=null){
	        String path = resource.getPath();

	        logger.info(path);
	        }else
	        {System.out.println("nem lófasz");
	        	logger.warn("üres volt a resource file");
	        }
			setNode(getNode(firstPresentation(), resource));
	    }

	   public void loadSecond() {
	        setNode(getNode(secondPresentation(), getClass().getResource("../Second.fxml")));
	    }

	    void loadPopup() {
	        ModalDialog modal = new ModalDialog(popupPresentation(), getClass().getResource("../Popup.fxml"), stage.getOwner(), lang.getBundle());
	        modal.setTitle(lang.getBundle().getString("popup.title"));
	        modal.getStyleSheets().add(STYLE_FILE);
	        modal.show();
	    }
	    public Stage loadOpenDialog(){
//	    	OpenDialog modal=new OpenDialog(this);
	    	 ModalDialog modal = new ModalDialog(showOpenDialog(), getClass().getResource("../OpenDialog.fxml"), stage.getOwner(), lang.getBundle());
		        modal.setTitle(lang.getBundle().getString("popup.title"));
		        modal.getStyleSheets().add(STYLE_FILE);
		        modal.showAndWait();
		        return modal;
	    	//setNode(getNode(mainScreen(),getClass().getResource("../OpenDialog.fxml")));
	    }
	    @Bean
	    @Scope("prototype")
	    FirstPresentation firstPresentation() {
	        return new FirstPresentation(this);
	    }

	    @Bean
	    @Scope("prototype")
	    SecondPresentation secondPresentation() {
	        return new SecondPresentation(this);
	    }

	    @Bean
	    @Scope("prototype")
	    PopupPresentation popupPresentation() {
	        return new PopupPresentation(this);
	    }

	    @Bean
	    @Scope("prototype")
	    MainScreen mainScreen()
	    {
	    return new MainScreen(this);	
	    }
	    @Bean
	    @Scope("prototype")
	    OpenDialog showOpenDialog(){
	    	return new OpenDialog(this);
	    }
	    private Node getNode(final Presentation control, URL location) {
	        FXMLLoader loader = new FXMLLoader(location, lang.getBundle());
	        loader.setControllerFactory(new Callback<Class<?>, Object>() {
	            public Object call(Class<?> aClass) {
	                return control;
	            }
	        });

	        try {
	            return (Node) loader.load();
	        } catch (Exception e) {
	            logger.error("Error casting node", e);
	            return null;
	        }
	    }

	    public Stage getStage() {
	        return stage;
	    }

	    public void update(Observable o, Object arg) {
	        loadFirst();
	    }

}

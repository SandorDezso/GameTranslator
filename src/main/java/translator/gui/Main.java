package translator.gui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import translator.config.AppConfig;
import translator.uimodel.LanguageModel;

public class Main extends Application {

	 private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		logger.info("Starting application");

		Platform.setImplicitExit(true);

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Logger logger2 = LoggerFactory.getLogger("javafx");
		ScreensConfig screens = context.getBean(ScreensConfig.class);
		LanguageModel lang = context.getBean(LanguageModel.class);

		screens.setLangModel(lang);
		screens.setPrimaryStage(stage);
		screens.showMainScreen();
		screens.loadMain();	
		
	}
	
}

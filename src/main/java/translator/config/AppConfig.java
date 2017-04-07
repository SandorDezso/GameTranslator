package translator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;

import translator.control.LanguageController;
import translator.gui.ScreensConfig;
import translator.uimodel.LanguageModel;
import translator.uimodel.MessageModel;
import translator.validator.ParseValidator;
@ComponentScan("")
@Configuration
@Import({ ScreensConfig.class, PersistenceConfig.class })
public class AppConfig {

	
	@Bean
	LanguageModel languageModel() {
		return new LanguageModel();
	}

	@Bean
	LanguageController languageController() {
		return new LanguageController(languageModel());
	}

	@Bean
	MessageModel messageModel() {
		return new MessageModel();
	}
	@Bean
	public ReloadableResourceBundleMessageSource validationMessageSource(){
		 ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	     messageSource.setBasename("classpath:lang/lang");  
	     
		 return messageSource;
	}
	
	@Bean
	Validator parseValidator(){
		return new ParseValidator();
	}
}

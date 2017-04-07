package translator.control.factory;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import translator.services.ParseService;
@Component
public class ParseFactory {
	private Validator validator;
	
	@Autowired
	public ParseFactory(Validator validator) {
	this.validator=validator;
	}

	public ParseService build(File f1, File f2) {
		
		return new ParseService(validator,f1,f2);
	}
}

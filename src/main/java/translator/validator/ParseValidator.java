package translator.validator;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Qualifier("parseValidator")
public class ParseValidator implements Validator {
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	public ParseValidator() {

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String s = (String) target;

		if(s.trim().length()==0)
			errors.reject("empty.needData");
		
		if (s.startsWith("//")) {
			errors.reject("empty.comment");
		}
	}

}

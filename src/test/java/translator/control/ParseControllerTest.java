package translator.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.Validator;

import translator.config.AppConfig;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ParseControllerTest {
	@Mock
	Validator validator;
	
	@Before
	public void setUp(){
		
	}
	@Test
	public void parseWithValidLineTest(){
		
	}
	@Test
	public void parseWithCommentTest(){}
	@Test
	public void parseWithInvalidLineTest(){}
	@Test
	public void parseWithMoreSameLineTest(){}
	
}

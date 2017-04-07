package translator.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import translator.persistence.model.LocalizationModel;
import translator.persistence.service.LangLineService;

public enum SearchControll {

	firstlang {
		@Override
		public List<LocalizationModel> search(String text) {
		return lineService.listByFirstLang(text);
		}
	},
	
	secondlang {
		@Override
		public List<LocalizationModel> search(String text) {
			return lineService.listBySecondLang(text);
		}
	};
	public abstract List<LocalizationModel> search(String text);
	
	@Autowired
	LangLineService lineService;
}

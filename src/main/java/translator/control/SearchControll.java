package translator.control;

import java.util.List;

import org.springframework.stereotype.Component;

import translator.persistence.model.LocalizationModel;
import translator.persistence.service.LangLineService;

@Component
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



	public void setLineService(LangLineService lineService) {
		this.lineService = lineService;
	}

	private static LangLineService lineService;

	

}

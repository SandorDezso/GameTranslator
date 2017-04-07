package translator.persistence.service;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javafx.concurrent.Task;
import translator.persistence.dao.LineDAO;
import translator.persistence.model.LocalizationModel;


@Service
public class LangLineService {
	private static final Logger logger = LogManager.getLogger(LangLineService.class);
	@Autowired
	private LineDAO lineDao;
	
	@Transactional
	public void add(LocalizationModel line) {
		lineDao.persist(line);
	}
	
	@Transactional
	public void addAll(Collection<LocalizationModel> model){
		for(LocalizationModel line : model){
			lineDao.persist(line);
		}
	}
	public void update(LocalizationModel line){
		lineDao.update(line);
	}
	@Transactional(readOnly=true)
	public List<LocalizationModel> listAll(){
		return lineDao.findAll();
	}

	public List<LocalizationModel> listVoidRecords(){
		return lineDao.findVoidRecords();
	}
	
	@Transactional
	public LinePersistTask persistAllInTask(List<LocalizationModel> lines) {

		LinePersistTask persistTask = new LinePersistTask(lines);
		return persistTask;
	}
	
	public List<LocalizationModel> listByFirstLang(String str){
		return lineDao.findByFirstLang(str);
	}
	public class LinePersistTask extends Task<Void> {
		long progress = 0;
		List<LocalizationModel> lines;

		public LinePersistTask(List<LocalizationModel> lines) {
			this.lines = lines;
		}

		@Override
		protected Void call() throws Exception {
			try{
			updateProgress(progress, lines.size());			
		
			logger.info("transaction beginned");
			for (LocalizationModel model : lines) {
				updateProgress(progress++, lines.size());
				add(model);
			}
		
			logger.info("transacton done");}
			catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
	}
}

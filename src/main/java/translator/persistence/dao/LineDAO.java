package translator.persistence.dao;

import java.util.List;

import translator.persistence.model.LocalizationModel;

public interface LineDAO {
	public  List<LocalizationModel> findByFirstLang(String first);
	public void persist(LocalizationModel line);
	public void persistAll(List<LocalizationModel> lines);
	public List<LocalizationModel> findAll();
	public List<LocalizationModel> findVoidRecords();
	public void update(LocalizationModel line);
}

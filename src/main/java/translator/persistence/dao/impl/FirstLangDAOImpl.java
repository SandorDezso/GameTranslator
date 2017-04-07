package translator.persistence.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import translator.persistence.dao.LineDAO;
import translator.persistence.model.LocalizationModel;

@Component
@Transactional
// @Repository
public class FirstLangDAOImpl implements LineDAO {

	private static final Logger logger = LogManager.getLogger(FirstLangDAOImpl.class);

	@PersistenceContext
	private EntityManager em;

	public FirstLangDAOImpl() {
	}

	@Override
	public void persist(LocalizationModel line) {
		em.persist(line);
	}

	@Override
	public List<LocalizationModel> findAll() {
		return em.createQuery("SELECT p FROM LocalizationModel p").getResultList();

	}

	@Override
	public void persistAll(List<LocalizationModel> lines) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		for (LocalizationModel model : lines)
			em.persist(model);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocalizationModel> findVoidRecords() {
		return em.createQuery("FROM LocalizationModel l where l.secondLang IS NULL").getResultList();
	}

	@Override
	public void update(LocalizationModel line) {
		logger.info("updated line:" + line.getId());
		logger.info(line.toString());
		em.merge(line);

	}

	@Override
	public List<LocalizationModel> findByFirstLang(String first) {
		Query query = em.createQuery("FROM LocalizationModel AS l WHERE l.firstLang LIKE CONCAT('%',?1,'%')");
		query.setParameter(1, first);
		return query.getResultList();
	}

}

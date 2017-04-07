package translator.persistence.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table
public class LocalizationModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6386618276026363478L;
	@Id
	@Column(name = "id")
	private String id;
	@Lob
	@Column(name="firstLang", nullable=false)
	private String firstLang;
	@Lob
	@Column(name="secondLang", nullable=true)
	private String secondLang;

	public LocalizationModel() {
	}

	public LocalizationModel(String id, String firstLang, String secondLang) {
		this.id = id;
		this.firstLang = firstLang;
		this.secondLang = secondLang;
	}

	@Override
	public String toString() {
		return "id=" + id + ",\nfirstLang=" + firstLang + ",\nsecondLang=" + secondLang;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstLang() {
		return firstLang;
	}

	public void setFirstLang(String firstLang) {
		this.firstLang = firstLang;
	}

	public String getSecondLang() {
		return secondLang;
	}

	public void setSecondLang(String secondLang) {
		this.secondLang = secondLang;
	}

}

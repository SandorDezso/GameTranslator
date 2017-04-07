package translator.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


public class LineDomainModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6386618276026363478L;
	private Integer id;
	private String firstLang;
	private String secondLang;

	public LineDomainModel() {
	}

	public LineDomainModel(Integer id, String firstLang, String secondLang) {
		this.id = id;
		this.firstLang = firstLang;
		this.secondLang = secondLang;
	}

	@Override
	public String toString() {
		return "id=" + id + ",\nfirstLang=" + firstLang + ",\nsecondLang=" + secondLang;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

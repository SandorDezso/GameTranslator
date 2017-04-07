package translator.uimodel;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.NotSupportedException;

import translator.gui.customUiElements.CustomListCell;



public class LineUIModel  {

	private Integer id;
	private String firstLang;
	private String secondLang;

	public LineUIModel() {
	}

	public LineUIModel(Integer id, String firstLang, String secondLang) {
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

	public CustomListCell getItemView() throws NotSupportedException{
		
		throw new NotSupportedException();
	}
	
}

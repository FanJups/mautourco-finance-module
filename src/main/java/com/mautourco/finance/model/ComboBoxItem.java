package com.mautourco.finance.model;

import java.io.Serializable;

public class ComboBoxItem implements Serializable {

	private int idAgency;
	private String name;

	public int getIdAgency() {
		return idAgency;
	}

	public void setIdAgency(int idAgency) {
		this.idAgency = idAgency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

}

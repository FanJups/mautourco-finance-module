package com.mautourco.finance.model;

import java.io.Serializable;
import java.sql.Date;

public class InvHeader implements Serializable {

	private Date invDate;
	private Long resaId;
	private String currency;
	private Long idPaidBy;

	public InvHeader() {

	}

	public InvHeader(Date invDate, Long resaId, String currency, Long idPaidBy) {

		this.invDate = invDate;
		this.resaId = resaId;
		this.currency = currency;
		this.idPaidBy = idPaidBy;
	}

	public Date getInvDate() {
		return invDate;
	}

	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}

	public Long getResaId() {
		return resaId;
	}

	public void setResaId(Long resaId) {
		this.resaId = resaId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getIdPaidBy() {
		return idPaidBy;
	}

	public void setIdPaidBy(Long idPaidBy) {
		this.idPaidBy = idPaidBy;
	}

	@Override
	public String toString() {
		return String.format("InvHeader [invDate=%s, resaId=%s, currency=%s, idPaidBy=%s]", invDate, resaId, currency,
				idPaidBy);
	}

}

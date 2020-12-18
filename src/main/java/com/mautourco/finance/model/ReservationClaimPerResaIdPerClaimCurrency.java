package com.mautourco.finance.model;

import java.io.Serializable;

public class ReservationClaimPerResaIdPerClaimCurrency implements Serializable {

	private Long resaId;
	private String claimCurrency;
	private double sumClaimTotalAfterDisc;
	private int payingAgencyId;

	public ReservationClaimPerResaIdPerClaimCurrency(Long resaId, String claimCurrency, double sumClaimTotalAfterDisc,
			int payingAgencyId) {

		this.resaId = resaId;
		this.claimCurrency = claimCurrency;
		this.sumClaimTotalAfterDisc = sumClaimTotalAfterDisc;
		this.payingAgencyId = payingAgencyId;
	}

	public Long getResaId() {
		return resaId;
	}

	public void setResaId(Long resaId) {
		this.resaId = resaId;
	}

	public String getClaimCurrency() {
		return claimCurrency;
	}

	public void setClaimCurrency(String claimCurrency) {
		this.claimCurrency = claimCurrency;
	}

	public double getSumClaimTotalAfterDisc() {
		return sumClaimTotalAfterDisc;
	}

	public void setSumClaimTotalAfterDisc(double sumClaimTotalAfterDisc) {
		this.sumClaimTotalAfterDisc = sumClaimTotalAfterDisc;
	}

	public int getPayingAgencyId() {
		return payingAgencyId;
	}

	public void setPayingAgencyId(int payingAgencyId) {
		this.payingAgencyId = payingAgencyId;
	}

}

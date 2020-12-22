package com.mautourco.finance.model;

import java.io.Serializable;
import java.sql.Date;

public class Sintercl implements Serializable {

	private String source;
	private String batch;
	private String company;
	private String journal;
	private Date transacDate;
	private Date payDate;
	private String reference;
	private String glAccount;
	private String auxiliary;
	private String analytical;
	private String description;
	private String userDef;
	private String userDef2;
	private double amtRs;
	private String currCode;
	private String curr;
	private double rate;
	private double amtCurr;
	private double vatCurr;
	private double vatRs;
	private String supCode;
	private String sourceBat;
	private String vatLine;

	public Sintercl(String source, String company, String journal, Date transacDate, Date payDate, String reference,
			String auxiliary, String description, String userDef, String userDef2, double amtRs, String currCode,
			String curr, double rate, double amtCurr, double vatCurr, double vatRs, String supCode, String sourceBat) {
		this.source = source;
		this.company = company;
		this.journal = journal;
		this.transacDate = transacDate;
		this.payDate = payDate;
		this.reference = reference;
		this.auxiliary = auxiliary;
		this.description = description;
		this.userDef = userDef;
		this.userDef2 = userDef2;
		this.amtRs = amtRs;
		this.currCode = currCode;
		this.curr = curr;
		this.rate = rate;
		this.amtCurr = amtCurr;
		this.vatCurr = vatCurr;
		this.vatRs = vatRs;
		this.supCode = supCode;
		this.sourceBat = sourceBat;
	}

	public Sintercl(String source, String company, String journal, Date transacDate, Date payDate, String auxiliary,
			String description, double amtRs, String currCode, double amtCurr, String vatLine, double vatCurr,
			double vatRs, String sourceBat) {
		this.source = source;
		this.company = company;
		this.journal = journal;
		this.transacDate = transacDate;
		this.payDate = payDate;
		this.auxiliary = auxiliary;
		this.description = description;
		this.amtRs = amtRs;
		this.currCode = currCode;
		this.amtCurr = amtCurr;
		this.vatLine = vatLine;
		this.vatCurr = vatCurr;
		this.vatRs = vatRs;
		this.sourceBat = sourceBat;

	}

	public Sintercl(String source, String company, String journal, Date transacDate, Date payDate, String glAccount,
			String analytical, String description, double amtRs, String currCode, double amtCurr, String sourceBat) {
		this.source = source;
		this.company = company;
		this.journal = journal;
		this.transacDate = transacDate;
		this.payDate = payDate;
		this.glAccount = glAccount;
		this.analytical = analytical;
		this.description = description;
		this.amtRs = amtRs;
		this.currCode = currCode;
		this.amtCurr = amtCurr;
		this.sourceBat = sourceBat;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public Date getTransacDate() {
		return transacDate;
	}

	public void setTransacDate(Date transacDate) {
		this.transacDate = transacDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	public String getAuxiliary() {
		return auxiliary;
	}

	public void setAuxiliary(String auxiliary) {
		this.auxiliary = auxiliary;
	}

	public String getAnalytical() {
		return analytical;
	}

	public void setAnalytical(String analytical) {
		this.analytical = analytical;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserDef() {
		return userDef;
	}

	public void setUserDef(String userDef) {
		this.userDef = userDef;
	}

	public String getUserDef2() {
		return userDef2;
	}

	public void setUserDef2(String userDef2) {
		this.userDef2 = userDef2;
	}

	public double getAmtRs() {
		return amtRs;
	}

	public void setAmtRs(double amtRs) {
		this.amtRs = amtRs;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getAmtCurr() {
		return amtCurr;
	}

	public void setAmtCurr(double amtCurr) {
		this.amtCurr = amtCurr;
	}

	public double getVatCurr() {
		return vatCurr;
	}

	public void setVatCurr(double vatCurr) {
		this.vatCurr = vatCurr;
	}

	public double getVatRs() {
		return vatRs;
	}

	public void setVatRs(double vatRs) {
		this.vatRs = vatRs;
	}

	public String getSupCode() {
		return supCode;
	}

	public void setSupCode(String supCode) {
		this.supCode = supCode;
	}

	public String getSourceBat() {
		return sourceBat;
	}

	public void setSourceBat(String sourceBat) {
		this.sourceBat = sourceBat;
	}

	public String getVatLine() {
		return vatLine;
	}

	public void setVatLine(String vatLine) {
		this.vatLine = vatLine;
	}

	@Override
	public String toString() {
		return String.format(
				"Sintercl [source=%s, company=%s, journal=%s, transacDate=%s, payDate=%s, reference=%s, auxiliary=%s, description=%s, userDef=%s, userDef2=%s, amtRs=%s, currCode=%s, curr=%s, rate=%s, amtCurr=%s, vatCurr=%s, vatRs=%s, supCode=%s, sourceBat=%s, vatLine=%s]",
				source, company, journal, transacDate, payDate, reference, auxiliary, description, userDef, userDef2,
				amtRs, currCode, curr, rate, amtCurr, vatCurr, vatRs, supCode, sourceBat, vatLine);
	}

}

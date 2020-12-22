package com.mautourco.finance.model;

import java.io.Serializable;
import java.sql.Date;

public class SicoInt implements Serializable {

	private Date myDate;
	private Date docuDate;
	private String serviceType;
	private String serviceFor;
	private String servicePaidBy;
	private String resaType;
	private String claimType;
	private Long resaId;
	private String descr;
	private String clients;
	private String claimCurrency;
	private double netAmount;
	private double taxableAmt;
	private double vat;
	private double exchangeRate;
	private Long invoiceNo;
	private Date dateInvoiced;
	private int active;
	private String sicoCode;
	private String agency;
	private String docType;
	private String currType;
	private String cCenter;
	private String subsidiary;
	private String businessUnit;
	private int exported;
	private String ref;

	public SicoInt(Date myDate, Date docuDate, String serviceType, String serviceFor, String servicePaidBy,
			String resaType, String claimType, Long resaId, String descr, String clients, String claimCurrency,
			double netAmount, double taxableAmt, double vat, double exchangeRate, Long invoiceNo, Date dateInvoiced,
			int active, String sicoCode, String agency, String docType, String currType, String cCenter,
			String subsidiary, String businessUnit, int exported, String ref) {
		this.myDate = myDate;
		this.docuDate = docuDate;
		this.serviceType = serviceType;
		this.serviceFor = serviceFor;
		this.servicePaidBy = servicePaidBy;
		this.resaType = resaType;
		this.claimType = claimType;
		this.resaId = resaId;
		this.descr = descr;
		this.clients = clients;
		this.claimCurrency = claimCurrency;
		this.netAmount = netAmount;
		this.taxableAmt = taxableAmt;
		this.vat = vat;
		this.exchangeRate = exchangeRate;
		this.invoiceNo = invoiceNo;
		this.dateInvoiced = dateInvoiced;
		this.active = active;
		this.sicoCode = sicoCode;
		this.agency = agency;
		this.docType = docType;
		this.currType = currType;
		this.cCenter = cCenter;
		this.subsidiary = subsidiary;
		this.businessUnit = businessUnit;
		this.exported = exported;
		this.ref = ref;
	}

	public Date getMyDate() {
		return myDate;
	}

	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}

	public Date getDocuDate() {
		return docuDate;
	}

	public void setDocuDate(Date docuDate) {
		this.docuDate = docuDate;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceFor() {
		return serviceFor;
	}

	public void setServiceFor(String serviceFor) {
		this.serviceFor = serviceFor;
	}

	public String getServicePaidBy() {
		return servicePaidBy;
	}

	public void setServicePaidBy(String servicePaidBy) {
		this.servicePaidBy = servicePaidBy;
	}

	public String getResaType() {
		return resaType;
	}

	public void setResaType(String resaType) {
		this.resaType = resaType;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public Long getResaId() {
		return resaId;
	}

	public void setResaId(Long resaId) {
		this.resaId = resaId;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getClients() {
		return clients;
	}

	public void setClients(String clients) {
		this.clients = clients;
	}

	public String getClaimCurrency() {
		return claimCurrency;
	}

	public void setClaimCurrency(String claimCurrency) {
		this.claimCurrency = claimCurrency;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public double getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Long getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getDateInvoiced() {
		return dateInvoiced;
	}

	public void setDateInvoiced(Date dateInvoiced) {
		this.dateInvoiced = dateInvoiced;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getSicoCode() {
		return sicoCode;
	}

	public void setSicoCode(String sicoCode) {
		this.sicoCode = sicoCode;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getCurrType() {
		return currType;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public String getcCenter() {
		return cCenter;
	}

	public void setcCenter(String cCenter) {
		this.cCenter = cCenter;
	}

	public String getSubsidiary() {
		return subsidiary;
	}

	public void setSubsidiary(String subsidiary) {
		this.subsidiary = subsidiary;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public int getExported() {
		return exported;
	}

	public void setExported(int exported) {
		this.exported = exported;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return "SicoInt [myDate=" + myDate + ", docuDate=" + docuDate + ", serviceType=" + serviceType + ", serviceFor="
				+ serviceFor + ", servicePaidBy=" + servicePaidBy + ", resaType=" + resaType + ", claimType="
				+ claimType + ", resaId=" + resaId + ", descr=" + descr + ", clients=" + clients + ", claimCurrency="
				+ claimCurrency + ", netAmount=" + netAmount + ", taxableAmt=" + taxableAmt + ", vat=" + vat
				+ ", exchangeRate=" + exchangeRate + ", invoiceNo=" + invoiceNo + ", dateInvoiced=" + dateInvoiced
				+ ", active=" + active + ", sicoCode=" + sicoCode + ", agency=" + agency + ", docType=" + docType
				+ ", currType=" + currType + ", cCenter=" + cCenter + ", subsidiary=" + subsidiary + ", businessUnit="
				+ businessUnit + ", exported=" + exported + ", ref=" + ref + "]";
	}

}

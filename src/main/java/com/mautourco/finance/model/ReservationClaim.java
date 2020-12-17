package com.mautourco.finance.model;

import java.io.Serializable;
import java.sql.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReservationClaim implements Serializable {

	// private final SimpleLongProperty serviceId;

	private final SimpleLongProperty resaId;
	private final SimpleStringProperty serviceType;
	private final SimpleStringProperty type;
	private final SimpleStringProperty description;
	private final SimpleObjectProperty<Date> dateEffected;
	private final SimpleStringProperty serviceFrom;
	private final SimpleStringProperty serviceTo;
	private final SimpleIntegerProperty ngh;
	private final SimpleStringProperty claimType;

	private final SimpleIntegerProperty ad;
	private final SimpleDoubleProperty adRate;
	private final SimpleIntegerProperty ch;
	private final SimpleDoubleProperty chRate;
	private final SimpleIntegerProperty tn;
	private final SimpleDoubleProperty tnRate;
	private final SimpleStringProperty curr;
	private final SimpleDoubleProperty exc;

	private final SimpleDoubleProperty taxableClaim;
	private final SimpleDoubleProperty vat;
	private final SimpleDoubleProperty claimTotalAfterDisc;
	private final SimpleStringProperty payingAgency;
	private final SimpleStringProperty invJdeCode;
	private final SimpleStringProperty invCCenter;
	private final SimpleStringProperty invSubsidiary;

	// public ReservationClaim(Long serviceId, Long resaId, String serviceType,
	// String type, String description,
	// Date dateEffected, String serviceFrom, String serviceTo, String payingAgency,
	// String invJdeCode,
	// double taxableClaim, double claimTotalAfterDisc, double vat, String
	// invCCenter, String invSubsidiary) {

	public ReservationClaim(Long resaId, String serviceType, String type, String description, Date dateEffected,
			String serviceFrom, String serviceTo, Integer ngh, String claimType, Integer ad, double adRate, Integer ch,
			double chRate, Integer tn, double tnRate, String curr, double exc, double taxableClaim, double vat,
			double claimTotalAfterDisc, String payingAgency, String invJdeCode, String invCCenter,
			String invSubsidiary) {

		// this.serviceId = new SimpleLongProperty(serviceId);

		this.resaId = new SimpleLongProperty(resaId);
		this.serviceType = new SimpleStringProperty(serviceType);
		this.type = new SimpleStringProperty(type);
		this.description = new SimpleStringProperty(description);
		this.dateEffected = new SimpleObjectProperty<Date>(dateEffected);
		this.serviceFrom = new SimpleStringProperty(serviceFrom);
		this.serviceTo = new SimpleStringProperty(serviceTo);
		this.ngh = new SimpleIntegerProperty(ngh);
		this.claimType = new SimpleStringProperty(claimType);

		this.ad = new SimpleIntegerProperty(ad);
		this.adRate = new SimpleDoubleProperty(adRate);
		this.ch = new SimpleIntegerProperty(ch);
		this.chRate = new SimpleDoubleProperty(chRate);
		this.tn = new SimpleIntegerProperty(tn);
		this.tnRate = new SimpleDoubleProperty(tnRate);
		this.curr = new SimpleStringProperty(curr);
		this.exc = new SimpleDoubleProperty(exc);

		this.taxableClaim = new SimpleDoubleProperty(taxableClaim);
		this.vat = new SimpleDoubleProperty(vat);
		this.claimTotalAfterDisc = new SimpleDoubleProperty(claimTotalAfterDisc);

		this.payingAgency = new SimpleStringProperty(payingAgency);
		this.invJdeCode = new SimpleStringProperty(invJdeCode);
		this.invCCenter = new SimpleStringProperty(invCCenter);
		this.invSubsidiary = new SimpleStringProperty(invSubsidiary);

	}

	// public Long getServiceId() {
	// return serviceId.get();
	// }

	// public void setServiceId(Long serviceId) {
	// this.serviceId.set(serviceId);
	// }

	public Long getResaId() {
		return resaId.get();
	}

	public void setResaId(Long resaId) {

		this.resaId.set(resaId);
	}

	public String getServiceType() {
		return serviceType.get();
	}

	public void setServiceType(String serviceType) {

		this.serviceType.set(serviceType);
	}

	public String getType() {
		return type.get();

	}

	public void setType(String type) {

		this.type.set(type);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {

		this.description.set(description);
	}

	public Date getDateEffected() {
		return dateEffected.get();
	}

	public void setDateEffected(Date dateEffected) {

		this.dateEffected.set(dateEffected);
	}

	public String getServiceFrom() {
		return serviceFrom.get();
	}

	public void setServiceFrom(String serviceFrom) {

		this.serviceFrom.set(serviceFrom);
	}

	public String getServiceTo() {
		return serviceTo.get();
	}

	public void setServiceTo(String serviceTo) {

		this.serviceTo.set(serviceTo);
	}

	public int getNgh() {
		return ngh.get();
	}

	public SimpleIntegerProperty nghProperty() {
		return ngh;
	}

	public void setNgh(int ngh) {
		this.ngh.set(ngh);
	}

	public String getClaimType() {
		return claimType.get();
	}

	public SimpleStringProperty claimTypeProperty() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType.set(claimType);
	}

	public int getAd() {
		return ad.get();
	}

	public SimpleIntegerProperty adProperty() {
		return ad;
	}

	public void setAd(int ad) {
		this.ad.set(ad);
	}

	public double getAdRate() {
		return adRate.get();
	}

	public SimpleDoubleProperty adRateProperty() {
		return adRate;
	}

	public void setAdRate(double adRate) {
		this.adRate.set(adRate);
	}

	public int getCh() {
		return ch.get();
	}

	public SimpleIntegerProperty chProperty() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch.set(ch);
	}

	public double getChRate() {
		return chRate.get();
	}

	public SimpleDoubleProperty chRateProperty() {
		return chRate;
	}

	public void setChRate(double chRate) {
		this.chRate.set(chRate);
	}

	public int getTn() {
		return tn.get();
	}

	public SimpleIntegerProperty tnProperty() {
		return tn;
	}

	public void setTn(int tn) {
		this.tn.set(tn);
	}

	public double getTnRate() {
		return tnRate.get();
	}

	public SimpleDoubleProperty tnRateProperty() {
		return tnRate;
	}

	public void setTnRate(double tnRate) {
		this.tnRate.set(tnRate);
	}

	public String getCurr() {
		return curr.get();
	}

	public SimpleStringProperty currProperty() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr.set(curr);
	}

	public double getExc() {
		return exc.get();
	}

	public SimpleDoubleProperty excProperty() {
		return exc;
	}

	public void setExc(double exc) {
		this.exc.set(exc);
	}

	public double getTaxableClaim() {
		return taxableClaim.get();
	}

	public void setTaxableClaim(double taxableClaim) {

		this.taxableClaim.set(taxableClaim);
	}

	public double getVat() {
		return vat.get();
	}

	public void setVat(double vat) {

		this.vat.set(vat);
	}

	public double getClaimTotalAfterDisc() {
		return claimTotalAfterDisc.get();
	}

	public void setClaimTotalAfterDisc(double claimTotalAfterDisc) {

		this.claimTotalAfterDisc.set(claimTotalAfterDisc);
	}

	public String getPayingAgency() {
		return payingAgency.get();
	}

	public void setPayingAgency(String payingAgency) {

		this.payingAgency.set(payingAgency);
	}

	public String getInvJdeCode() {
		return invJdeCode.get();
	}

	public void setInvJdeCode(String invJdeCode) {

		this.invJdeCode.set(invJdeCode);
	}

	public String getInvCCenter() {
		return invCCenter.get();
	}

	public void setInvCCenter(String invCCenter) {

		this.invCCenter.set(invCCenter);
	}

	public String getInvSubsidiary() {
		return invSubsidiary.get();
	}

	public void setInvSubsidiary(String invSubsidiary) {

		this.invSubsidiary.set(invSubsidiary);
	}

}
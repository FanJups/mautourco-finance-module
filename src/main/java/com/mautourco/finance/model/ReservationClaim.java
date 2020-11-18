package com.mautourco.finance.model;

import java.io.Serializable;
import java.sql.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ReservationClaim implements Serializable {

	/*
	 * SELECT service_id, resa_id, service_type, TYPE, description, date_effected,
	 * service_from, service_to, paying_agency, inv_jde_code, taxable_claim,
	 * 
	 * 
	 * (claim_total_after_disc - taxable_claim) AS VAT, inv_c_center,
	 * claim_total_after_disc, inv_subsidiary
	 * 
	 * FROM reservation_claim WHERE date_effected BETWEEN '#datefrom#' AND
	 * '#dateto#' AND active =1 AND paying_agency_id = '#id_agency#';
	 * 
	 */

	private final SimpleLongProperty serviceId;
	private final SimpleLongProperty resaId;
	private final SimpleStringProperty serviceType;
	private final SimpleStringProperty type;
	private final SimpleStringProperty description;
	private final SimpleObjectProperty<Date> dateEffected;
	private final SimpleStringProperty serviceFrom;
	private final SimpleStringProperty serviceTo;
	private final SimpleStringProperty payingAgency;
	private final SimpleStringProperty invJdeCode;
	private final SimpleDoubleProperty taxableClaim;
	private final SimpleDoubleProperty claimTotalAfterDisc;
	private final SimpleDoubleProperty vat;
	private final SimpleStringProperty invCCenter;
	private final SimpleStringProperty invSubsidiary;

	public ReservationClaim(Long serviceId, Long resaId, String serviceType, String type, String description,
			Date dateEffected, String serviceFrom, String serviceTo, String payingAgency, String invJdeCode,
			double taxableClaim, double claimTotalAfterDisc, double vat, String invCCenter, String invSubsidiary) {

		this.serviceId = new SimpleLongProperty(serviceId);
		this.resaId = new SimpleLongProperty(resaId);
		this.serviceType = new SimpleStringProperty(serviceType);
		this.type = new SimpleStringProperty(type);
		this.description = new SimpleStringProperty(description);
		this.dateEffected = new SimpleObjectProperty<Date>(dateEffected);
		this.serviceFrom = new SimpleStringProperty(serviceFrom);
		this.serviceTo = new SimpleStringProperty(serviceTo);
		this.payingAgency = new SimpleStringProperty(payingAgency);
		this.invJdeCode = new SimpleStringProperty(invJdeCode);
		this.taxableClaim = new SimpleDoubleProperty(taxableClaim);
		this.claimTotalAfterDisc = new SimpleDoubleProperty(claimTotalAfterDisc);
		this.vat = new SimpleDoubleProperty(vat);
		this.invCCenter = new SimpleStringProperty(invCCenter);
		this.invSubsidiary = new SimpleStringProperty(invSubsidiary);

	}

	public Long getServiceId() {
		return serviceId.get();
	}

	public void setServiceId(Long serviceId) {

		this.serviceId.set(serviceId);
	}

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

	public double getTaxableClaim() {
		return taxableClaim.get();
	}

	public void setTaxableClaim(double taxableClaim) {

		this.taxableClaim.set(taxableClaim);
	}

	public double getClaimTotalAfterDisc() {
		return claimTotalAfterDisc.get();
	}

	public void setClaimTotalAfterDisc(double claimTotalAfterDisc) {

		this.claimTotalAfterDisc.set(claimTotalAfterDisc);
	}

	public double getVat() {
		return vat.get();
	}

	public void setVat(double vat) {

		this.vat.set(vat);
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

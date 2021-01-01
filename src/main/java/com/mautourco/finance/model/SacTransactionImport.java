package com.mautourco.finance.model;

import java.io.Serializable;
import java.sql.Date;

public class SacTransactionImport implements Serializable {

	private int companyCode;
	private String journal;
	private Long batchNo;
	private Long lineNo;
	private String gl;
	private String auxiliary;
	private String analytic;
	private String clientSupplier;
	private Date transactionDate;
	private Date documentDate;
	private Date paymentDueDate;
	private String referenceNo;
	private String transactionDescription;
	private String currencyGroup;
	private String currency;
	private double exchangeRate;
	private double localAmount;
	private double foreignAmount;
	private String transactionUD01;
	private String transactionUD02;
	private String transactionUD03;
	private String transactionUD04;
	private String transactionUD05;
	private double taxableAmount;
	private double taxableAmountForeign;
	private String taxAtSource;
	private double taxAtSourcePer;
	private double taxAtSourceAmt;
	private int export;

	public SacTransactionImport(int companyCode, String journal, Long batchNo, Long lineNo, String gl, String auxiliary,
			String analytic, String clientSupplier, Date transactionDate, Date documentDate, Date paymentDueDate,
			String referenceNo, String transactionDescription, String currencyGroup, String currency,
			double exchangeRate, double localAmount, double foreignAmount, String transactionUD01,
			String transactionUD02, String transactionUD03, String transactionUD04, String transactionUD05,
			double taxableAmount, double taxableAmountForeign, String taxAtSource, double taxAtSourcePer,
			double taxAtSourceAmt) {
		this.companyCode = companyCode;
		this.journal = journal;
		this.batchNo = batchNo;
		this.lineNo = lineNo;
		this.gl = gl;
		this.auxiliary = auxiliary;
		this.analytic = analytic;
		this.clientSupplier = clientSupplier;
		this.transactionDate = transactionDate;
		this.documentDate = documentDate;
		this.paymentDueDate = paymentDueDate;
		this.referenceNo = referenceNo;
		this.transactionDescription = transactionDescription;
		this.currencyGroup = currencyGroup;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		this.localAmount = localAmount;
		this.foreignAmount = foreignAmount;
		this.transactionUD01 = transactionUD01;
		this.transactionUD02 = transactionUD02;
		this.transactionUD03 = transactionUD03;
		this.transactionUD04 = transactionUD04;
		this.transactionUD05 = transactionUD05;
		this.taxableAmount = taxableAmount;
		this.taxableAmountForeign = taxableAmountForeign;
		this.taxAtSource = taxAtSource;
		this.taxAtSourcePer = taxAtSourcePer;
		this.taxAtSourceAmt = taxAtSourceAmt;

	}

	// Used for mapSacTransactionImport in TableViewDao

	public SacTransactionImport(int companyCode, String journal, Long batchNo, Long lineNo, String gl, String auxiliary,
			String analytic, String clientSupplier, Date transactionDate, Date documentDate, Date paymentDueDate,
			String referenceNo, String transactionDescription, String currencyGroup, String currency,
			double exchangeRate, double localAmount, double foreignAmount, String transactionUD01,
			String transactionUD02, String transactionUD03, double taxableAmount, double taxableAmountForeign,
			String taxAtSource, double taxAtSourcePer) {
		this.companyCode = companyCode;
		this.journal = journal;
		this.batchNo = batchNo;
		this.lineNo = lineNo;
		this.gl = gl;
		this.auxiliary = auxiliary;
		this.analytic = analytic;
		this.clientSupplier = clientSupplier;
		this.transactionDate = transactionDate;
		this.documentDate = documentDate;
		this.paymentDueDate = paymentDueDate;
		this.referenceNo = referenceNo;
		this.transactionDescription = transactionDescription;
		this.currencyGroup = currencyGroup;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		this.localAmount = localAmount;
		this.foreignAmount = foreignAmount;
		this.transactionUD01 = transactionUD01;
		this.transactionUD02 = transactionUD02;
		this.transactionUD03 = transactionUD03;
		this.taxableAmount = taxableAmount;
		this.taxableAmountForeign = taxableAmountForeign;
		this.taxAtSource = taxAtSource;
		this.taxAtSourcePer = taxAtSourcePer;

	}

	public int getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	public Long getLineNo() {
		return lineNo;
	}

	public void setLineNo(Long lineNo) {
		this.lineNo = lineNo;
	}

	public String getGl() {
		return gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getAuxiliary() {
		return auxiliary;
	}

	public void setAuxiliary(String auxiliary) {
		this.auxiliary = auxiliary;
	}

	public String getAnalytic() {
		return analytic;
	}

	public void setAnalytic(String analytic) {
		this.analytic = analytic;
	}

	public String getClientSupplier() {
		return clientSupplier;
	}

	public void setClientSupplier(String clientSupplier) {
		this.clientSupplier = clientSupplier;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getCurrencyGroup() {
		return currencyGroup;
	}

	public void setCurrencyGroup(String currencyGroup) {
		this.currencyGroup = currencyGroup;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getLocalAmount() {
		return localAmount;
	}

	public void setLocalAmount(double localAmount) {
		this.localAmount = localAmount;
	}

	public double getForeignAmount() {
		return foreignAmount;
	}

	public void setForeignAmount(double foreignAmount) {
		this.foreignAmount = foreignAmount;
	}

	public String getTransactionUD01() {
		return transactionUD01;
	}

	public void setTransactionUD01(String transactionUD01) {
		this.transactionUD01 = transactionUD01;
	}

	public String getTransactionUD02() {
		return transactionUD02;
	}

	public void setTransactionUD02(String transactionUD02) {
		this.transactionUD02 = transactionUD02;
	}

	public String getTransactionUD03() {
		return transactionUD03;
	}

	public void setTransactionUD03(String transactionUD03) {
		this.transactionUD03 = transactionUD03;
	}

	public String getTransactionUD04() {
		return transactionUD04;
	}

	public void setTransactionUD04(String transactionUD04) {
		this.transactionUD04 = transactionUD04;
	}

	public String getTransactionUD05() {
		return transactionUD05;
	}

	public void setTransactionUD05(String transactionUD05) {
		this.transactionUD05 = transactionUD05;
	}

	public double getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public double getTaxableAmountForeign() {
		return taxableAmountForeign;
	}

	public void setTaxableAmountForeign(double taxableAmountForeign) {
		this.taxableAmountForeign = taxableAmountForeign;
	}

	public String getTaxAtSource() {
		return taxAtSource;
	}

	public void setTaxAtSource(String taxAtSource) {
		this.taxAtSource = taxAtSource;
	}

	public double getTaxAtSourcePer() {
		return taxAtSourcePer;
	}

	public void setTaxAtSourcePer(double taxAtSourcePer) {
		this.taxAtSourcePer = taxAtSourcePer;
	}

	public double getTaxAtSourceAmt() {
		return taxAtSourceAmt;
	}

	public void setTaxAtSourceAmt(double taxAtSourceAmt) {
		this.taxAtSourceAmt = taxAtSourceAmt;
	}

	public int getExport() {
		return export;
	}

	public void setExport(int export) {
		this.export = export;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SacTransactionImport [companyCode=");
		builder.append(companyCode);
		builder.append(", journal=");
		builder.append(journal);
		builder.append(", batchNo=");
		builder.append(batchNo);
		builder.append(", lineNo=");
		builder.append(lineNo);
		builder.append(", gl=");
		builder.append(gl);
		builder.append(", auxiliary=");
		builder.append(auxiliary);
		builder.append(", analytic=");
		builder.append(analytic);
		builder.append(", clientSupplier=");
		builder.append(clientSupplier);
		builder.append(", transactionDate=");
		builder.append(transactionDate);
		builder.append(", documentDate=");
		builder.append(documentDate);
		builder.append(", paymentDueDate=");
		builder.append(paymentDueDate);
		builder.append(", referenceNo=");
		builder.append(referenceNo);
		builder.append(", transactionDescription=");
		builder.append(transactionDescription);
		builder.append(", currencyGroup=");
		builder.append(currencyGroup);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", exchangeRate=");
		builder.append(exchangeRate);
		builder.append(", localAmount=");
		builder.append(localAmount);
		builder.append(", foreignAmount=");
		builder.append(foreignAmount);
		builder.append(", transactionUD01=");
		builder.append(transactionUD01);
		builder.append(", transactionUD02=");
		builder.append(transactionUD02);
		builder.append(", transactionUD03=");
		builder.append(transactionUD03);
		builder.append(", transactionUD04=");
		builder.append(transactionUD04);
		builder.append(", transactionUD05=");
		builder.append(transactionUD05);
		builder.append(", taxableAmount=");
		builder.append(taxableAmount);
		builder.append(", taxableAmountForeign=");
		builder.append(taxableAmountForeign);
		builder.append(", taxAtSource=");
		builder.append(taxAtSource);
		builder.append(", taxAtSourcePer=");
		builder.append(taxAtSourcePer);
		builder.append(", taxAtSourceAmt=");
		builder.append(taxAtSourceAmt);
		builder.append(", export=");
		builder.append(export);
		builder.append("]");
		return builder.toString();
	}

}

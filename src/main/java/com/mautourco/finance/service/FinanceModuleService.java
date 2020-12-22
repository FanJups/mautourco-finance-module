package com.mautourco.finance.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import com.mautourco.finance.dao.ComboBoxDao;
import com.mautourco.finance.dao.TableViewDao;
import com.mautourco.finance.exception.FinanceModuleException;
import com.mautourco.finance.model.ComboBoxItem;
import com.mautourco.finance.model.ReservationClaim;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class FinanceModuleService {

	private TableViewDao tableViewDao = new TableViewDao();
	private ComboBoxDao comboBoxDao = new ComboBoxDao();

	private void datePickerValidationDateFrom(Optional<LocalDate> date) throws FinanceModuleException {

		if (date.isEmpty()) {
			throw new FinanceModuleException("Please, select Date From!");

		} else {

			if (isValid(date.get().toString())) {

			} else {

				throw new FinanceModuleException("Please, select a valid Date From!");

			}

		}

	}

	private void datePickerValidationDateTo(Optional<LocalDate> date) throws FinanceModuleException {

		if (date.isEmpty()) {
			throw new FinanceModuleException("Please, select Date To!");

		} else {

			if (isValid(date.get().toString())) {

			} else {

				throw new FinanceModuleException("Please, select a valid Date To!");

			}

		}

	}

	private void comboBoxValidation(Optional<ComboBoxItem> item) throws FinanceModuleException {

		if (item.isEmpty()) {
			throw new FinanceModuleException("Please, select an item!");

		}

	}

	public void validation(Optional<LocalDate> dateFrom, Optional<LocalDate> dateTo, Optional<ComboBoxItem> item)
			throws FinanceModuleException {

		datePickerValidationDateFrom(dateFrom);
		datePickerValidationDateTo(dateTo);
		comboBoxValidation(item);
	}

	public String textFieldValidation(Optional<String> value) {

		if (value.isEmpty()) {
			return "";

		} else {

			String presentValue = value.get();

			if (presentValue.trim().length() == 0) {
				return "";

			} else {

				return presentValue.trim();

			}
		}

	}

	public void stylingRowsWithNullValues(TableView<ReservationClaim> tv) {

		Callback<TableView<ReservationClaim>, TableRow<ReservationClaim>> rowFactory = new Callback<TableView<ReservationClaim>, TableRow<ReservationClaim>>() {

			@Override
			public TableRow<ReservationClaim> call(TableView<ReservationClaim> l) {
				return new TableRow<ReservationClaim>() {

					@Override
					protected void updateItem(ReservationClaim item, boolean empty) {
						super.updateItem(item, empty);

						try {
							if (Optional.ofNullable((Double) item.getClaimTotalAfterDisc()).isEmpty()
									|| Optional.ofNullable((Double) item.getTaxableClaim()).isEmpty()
									|| Optional.ofNullable((Double) item.getVat()).isEmpty()
									|| Optional.ofNullable(item.getDateEffected()).isEmpty()
									|| Optional.ofNullable(item.getDescription()).isEmpty()
									|| Optional.ofNullable(item.getInvCCenter()).isEmpty()
									|| Optional.ofNullable(item.getInvJdeCode()).isEmpty()
									|| Optional.ofNullable(item.getInvSubsidiary()).isEmpty()
									|| Optional.ofNullable(item.getPayingAgency()).isEmpty()
									|| Optional.ofNullable(item.getResaId()).isEmpty()
									|| Optional.ofNullable(item.getServiceFrom()).isEmpty()
									|| Optional.ofNullable(item.getClaimType()).isEmpty()
							// || Optional.ofNullable(item.getServiceId()).isEmpty()
									|| Optional.ofNullable(item.getServiceTo()).isEmpty()
									|| Optional.ofNullable(item.getServiceType()).isEmpty()
									|| Optional.ofNullable(item.getCurr()).isEmpty()
									|| Optional.ofNullable(item.getType()).isEmpty()) {

								setStyle("-fx-background-color: #ff2a2a;");

							} else {
								// No empty cells - No specific highlights

								setStyle("");
							}

						} catch (NullPointerException e) {

						}

					}
				};
			}
		};

		tv.setRowFactory(rowFactory);

	}

	private boolean isValid(String input) {
		DateTimeFormatter formatter =

				new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").parseStrict().toFormatter();

		try {
			LocalDate.parse(input, formatter);
			return true;
		} catch (DateTimeParseException e) {
		}

		return false;
	}

	public List<ComboBoxItem> getComboBoxItems() {
		return comboBoxDao.getData();
	}

	public List<ReservationClaim> getReservationClaims(LocalDate dateFrom, LocalDate dateTo, int idAgency,
			String serviceFilter, String typeFilter, String claimDescFilter, String fromFilter, String toFilter,
			String payingAgencyFilter, String sicoraxCodeFilter, String auxiliaryFilter, String subsidiaryFilter,
			String currFilter, boolean containsNullValues) {

		return tableViewDao.getData(dateFrom, dateTo, idAgency, serviceFilter, typeFilter, claimDescFilter, fromFilter,
				toFilter, payingAgencyFilter, sicoraxCodeFilter, auxiliaryFilter, subsidiaryFilter, currFilter,
				containsNullValues);
	}

	public void generateInvoiceNo() {
		tableViewDao.getData().stream().forEach(r ->

		{
			try {
				new TableViewDao().generateInvoiceNo(r);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		);
	}

	public void updateCloseFromZeroToOne(LocalDate dateFrom, LocalDate dateTo, int idAgency, String serviceFilter,
			String typeFilter, String claimDescFilter, String fromFilter, String toFilter, String payingAgencyFilter,
			String sicoraxCodeFilter, String auxiliaryFilter, String subsidiaryFilter, String currFilter,
			boolean containsNullValues) {
		List<ReservationClaim> dataWithNoNullValues = getReservationClaims(dateFrom, dateTo, idAgency, serviceFilter,
				typeFilter, claimDescFilter, fromFilter, toFilter, payingAgencyFilter, sicoraxCodeFilter,
				auxiliaryFilter, subsidiaryFilter, currFilter, containsNullValues);

		dataWithNoNullValues.stream().forEach(r -> new TableViewDao().updateCloseFromZeroToOne(r.getResaId()));
	}

	public void insertIntoSico(LocalDate dateFrom, LocalDate dateTo) {

		tableViewDao.getDataSicoInt(dateFrom, dateTo).stream().forEach(s ->

		{
			try {
				new TableViewDao().insertIntoSico(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		);

	}

	public void insertIntoSintercl(LocalDate dateFrom, LocalDate dateTo) {

		tableViewDao.getDataSintercl(dateFrom, dateTo).stream().forEach(s ->

		{
			try {
				new TableViewDao().insertIntoSintercl(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		);

	}

	public void insertIntoSintercl2(LocalDate dateFrom, LocalDate dateTo) {

		tableViewDao.getDataSintercl2(dateFrom, dateTo).stream().forEach(s ->

		{
			try {
				new TableViewDao().insertIntoSintercl2(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		);

	}

	public void insertIntoSintercl3(LocalDate dateFrom, LocalDate dateTo) {

		tableViewDao.getDataSintercl3(dateFrom, dateTo).stream().forEach(s ->

		{
			try {
				new TableViewDao().insertIntoSintercl3(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		);

	}

	public void updateSicoInt() {
		tableViewDao.updateSicoInt();
	}

	public void insertIntoSacTransactionImport(LocalDate dateFrom, LocalDate dateTo) {
		tableViewDao.getDataSacTransactionImport(dateFrom, dateTo).stream().forEach(s ->

		{
			try {
				new TableViewDao().insertIntoSacTransactionImport(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		);
	}

}

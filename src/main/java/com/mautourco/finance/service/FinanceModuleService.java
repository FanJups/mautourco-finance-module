package com.mautourco.finance.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.mautourco.finance.exception.FinanceModuleException;
import com.mautourco.finance.model.ComboBoxItem;
import com.mautourco.finance.model.ReservationClaim;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class FinanceModuleService {

	public void datePickerValidationDateFrom(Optional<LocalDate> date) throws FinanceModuleException {

		if (date.isEmpty()) {
			throw new FinanceModuleException("Please, select Date From!");

		} else {

			if (isValid(date.get().toString())) {

			} else {

				throw new FinanceModuleException("Please, select a valid Date From!");

			}

		}

	}

	public void datePickerValidationDateTo(Optional<LocalDate> date) throws FinanceModuleException {

		if (date.isEmpty()) {
			throw new FinanceModuleException("Please, select Date To!");

		} else {

			if (isValid(date.get().toString())) {

			} else {

				throw new FinanceModuleException("Please, select a valid Date To!");

			}

		}

	}

	public void comboBoxValidation(Optional<ComboBoxItem> item) throws FinanceModuleException {

		if (item.isEmpty()) {
			throw new FinanceModuleException("Please, select an item!");

		}

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

}

package com.mautourco.finance.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.mautourco.finance.exception.FinanceModuleException;
import com.mautourco.finance.model.ComboBoxItem;

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

				return presentValue;

			}
		}

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

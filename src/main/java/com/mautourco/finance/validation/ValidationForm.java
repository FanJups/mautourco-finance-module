package com.mautourco.finance.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.mautourco.finance.exception.ValidationException;
import com.mautourco.finance.model.ComboBoxItem;

public class ValidationForm {

	public void datePickerValidationDateFrom(Optional<LocalDate> date) throws ValidationException {

		if (date.isEmpty()) {
			throw new ValidationException("Please, select Date From!");

		} else {

			if (isValid(date.get().toString())) {

			} else {

				throw new ValidationException("Please, select a valid Date From!");

			}

		}

	}

	public void datePickerValidationDateTo(Optional<LocalDate> date) throws ValidationException {

		if (date.isEmpty()) {
			throw new ValidationException("Please, select Date To!");

		} else {

			if (isValid(date.get().toString())) {

			} else {

				throw new ValidationException("Please, select a valid Date To!");

			}

		}

	}

	public void comboBoxValidation(Optional<ComboBoxItem> item) throws ValidationException {

		if (item.isEmpty()) {
			throw new ValidationException("Please, select an Agency!");

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

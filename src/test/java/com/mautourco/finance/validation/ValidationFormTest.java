package com.mautourco.finance.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.mautourco.finance.exception.ValidationException;
import com.mautourco.finance.model.ComboBoxItem;

public class ValidationFormTest {

	ValidationForm validationForm;
	LocalDate localDate;
	ComboBoxItem comboBoxItem;

	@Before
	public void initialize() {

		validationForm = new ValidationForm();

		localDate = LocalDate.of(2012, 6, 30);

		comboBoxItem = new ComboBoxItem(130, "Agency");

	}

	@Test
	public void shouldNotThrowAnyViolationDatePickerValidationDateFrom() {

		assertThatCode(() -> validationForm.datePickerValidationDateFrom(Optional.ofNullable(localDate)))
				.doesNotThrowAnyException();

	}

	@Test(expected = ValidationException.class)
	public void shouldThrowViolationsDatePickerValidationDateFrom() throws ValidationException {

		validationForm.datePickerValidationDateFrom(Optional.ofNullable(null));
	}

	@Test
	public void shouldNotThrowAnyViolationDatePickerValidationDateTo() {

		assertThatCode(() -> validationForm.datePickerValidationDateTo(Optional.ofNullable(localDate)))
				.doesNotThrowAnyException();

	}

	@Test(expected = ValidationException.class)
	public void shouldThrowViolationsDatePickerValidationDateFromTo() throws ValidationException {

		validationForm.datePickerValidationDateTo(Optional.ofNullable(null));
	}

	@Test
	public void shouldNotThrowAnyViolationComboBoxValidation() {

		assertThatCode(() -> validationForm.comboBoxValidation(Optional.ofNullable(comboBoxItem)))
				.doesNotThrowAnyException();

	}

	@Test(expected = ValidationException.class)
	public void shouldThrowViolationsComboBoxValidation() throws ValidationException {

		validationForm.comboBoxValidation(Optional.ofNullable(null));
	}

	@Test
	public void textFieldValidationTest() {
		assertThat(validationForm.textFieldValidation(Optional.ofNullable(null))).isEqualTo("");

		assertThat(validationForm.textFieldValidation(Optional.ofNullable("  "))).isEqualTo("");

		assertThat(validationForm.textFieldValidation(Optional.ofNullable(" Agency  "))).isEqualTo("Agency");
	}

}

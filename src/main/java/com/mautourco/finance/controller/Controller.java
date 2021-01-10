package com.mautourco.finance.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.mautourco.finance.App;
import com.mautourco.finance.event.AutoCompleteBox;
import com.mautourco.finance.exception.FinanceModuleException;
import com.mautourco.finance.exception.ValidationException;
import com.mautourco.finance.model.ComboBoxItem;
import com.mautourco.finance.model.ReservationClaim;
import com.mautourco.finance.service.FinanceModuleService;
import com.mautourco.finance.validation.ValidationForm;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class Controller {

	private FinanceModuleService financeModuleService = new FinanceModuleService();

	private ValidationForm validationForm = new ValidationForm();

	@FXML
	private DatePicker dateFrom;

	@FXML
	private DatePicker dateTo;

	@FXML
	private ComboBox<ComboBoxItem> cmb1;

	@FXML
	private Button searchBtn;

	@FXML
	private TableView<ReservationClaim> tv1;

	// @FXML
	// private TableColumn<ReservationClaim, Long> serviceIdCol;

	@FXML
	private TableColumn<ReservationClaim, Long> resaIdCol;

	@FXML
	private TableColumn<ReservationClaim, String> serviceTypeCol;

	@FXML
	private TableColumn<ReservationClaim, String> typeCol;

	@FXML
	private TableColumn<ReservationClaim, String> descriptionCol;

	@FXML
	private TableColumn<ReservationClaim, Date> dateEffectedCol;

	@FXML
	private TableColumn<ReservationClaim, String> serviceFromCol;

	@FXML
	private TableColumn<ReservationClaim, String> serviceToCol;

	@FXML
	private TableColumn<ReservationClaim, Integer> nghCol;

	@FXML
	private TableColumn<ReservationClaim, String> claimTypeCol;

	@FXML
	private TableColumn<ReservationClaim, Integer> adCol;

	@FXML
	private TableColumn<ReservationClaim, Double> adRateCol;

	@FXML
	private TableColumn<ReservationClaim, Integer> chCol;

	@FXML
	private TableColumn<ReservationClaim, Double> chRateCol;

	@FXML
	private TableColumn<ReservationClaim, Integer> tnCol;

	@FXML
	private TableColumn<ReservationClaim, Double> tnRateCol;

	@FXML
	private TableColumn<ReservationClaim, String> currCol;

	@FXML
	private TableColumn<ReservationClaim, Double> excCol;

	@FXML
	private TableColumn<ReservationClaim, Double> taxableClaimCol;

	@FXML
	private TableColumn<ReservationClaim, Double> vatCol;

	@FXML
	private TableColumn<ReservationClaim, Double> claimTotalAfterDiscCol;

	@FXML
	private TableColumn<ReservationClaim, String> payingAgencyCol;

	@FXML
	private TableColumn<ReservationClaim, String> invJdeCodeCol;

	@FXML
	private TableColumn<ReservationClaim, String> invCCenterCol;

	@FXML
	private TableColumn<ReservationClaim, String> invSubsidiaryCol;

	@FXML
	private BorderPane borderPane;

	@FXML
	private TextField inputTextService;

	@FXML
	private TextField inputTextType;

	@FXML
	private TextField inputTextClaimDesc;

	@FXML
	private TextField inputTextFrom;

	@FXML
	private TextField inputTextTo;

	@FXML
	private TextField inputTextPayingAgency;

	@FXML
	private TextField inputTextSicoraxCode;

	@FXML
	private TextField inputTextAuxiliary;

	@FXML
	private TextField inputTextSubsidiary;

	@FXML
	private TextField inputTextCurr;

	@FXML
	private Button closeBtn;

	@FXML
	private Button printBtn;

	@FXML
	private Button themeBtn;

	@FXML
	private void initialize() {

		try {

			cmb1.setItems(FXCollections.observableArrayList(financeModuleService.getComboBoxItems()));

		} catch (FinanceModuleException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(searchBtn.getScene().getWindow());
			alert.setTitle("ERROR");
			alert.setHeaderText(e.getMessage());
			// alert.setContentText(e.getMessage());
			alert.showAndWait();

		}

		new AutoCompleteBox(cmb1);

		tv1.setPlaceholder(new Label("No Content"));

		// styling javafx tablerow with null values -> background color : red

		financeModuleService.stylingRowsWithNullValues(tv1);

		dateFrom.setEditable(false);
		dateTo.setEditable(false);

	}

	@FXML
	private void validateForm() {

		try {

			validationForm.datePickerValidationDateFrom(Optional.ofNullable(dateFrom.getValue()));
			validationForm.datePickerValidationDateTo(Optional.ofNullable(dateTo.getValue()));
			validationForm.comboBoxValidation(Optional.ofNullable(cmb1.getValue()));

			List<ReservationClaim> data = financeModuleService.getReservationClaims(dateFrom.getValue(),
					dateTo.getValue(), cmb1.getValue().getIdAgency(),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextService.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextType.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextClaimDesc.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextFrom.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextTo.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextPayingAgency.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextSicoraxCode.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextAuxiliary.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextSubsidiary.getText())),
					validationForm.textFieldValidation(Optional.ofNullable(inputTextCurr.getText())), true);

			/////////////////////// CREATE TABLE/////////////////////

			// serviceIdCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim,
			// Long>("serviceId"));

			resaIdCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Long>("resaId"));

			serviceTypeCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("serviceType"));

			typeCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("type"));

			descriptionCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("description"));

			dateEffectedCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Date>("dateEffected"));

			serviceFromCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("serviceFrom"));

			serviceToCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("serviceTo"));

			nghCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Integer>("ngh"));

			claimTypeCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("claimType"));

			adCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Integer>("ad"));

			adRateCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("adRate"));

			chCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Integer>("ch"));

			chRateCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("chRate"));

			tnCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Integer>("tn"));

			tnRateCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("tnRate"));

			currCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("curr"));

			excCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("exc"));

			taxableClaimCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("taxableClaim"));

			vatCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("vat"));

			claimTotalAfterDiscCol
					.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("claimTotalAfterDisc"));

			payingAgencyCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("payingAgency"));

			invJdeCodeCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("invJdeCode"));

			invCCenterCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("invCCenter"));

			invSubsidiaryCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("invSubsidiary"));

			tv1.refresh();

			tv1.setItems(FXCollections.observableArrayList(data));

		} catch (FinanceModuleException | ValidationException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(searchBtn.getScene().getWindow());
			alert.setTitle("ERROR");
			alert.setHeaderText(e.getMessage());
			// alert.setContentText(e.getMessage());
			alert.showAndWait();

		}

	}

	@FXML
	private void handleCloseButtonAction() {

		try {

			validationForm.datePickerValidationDateFrom(Optional.ofNullable(dateFrom.getValue()));
			validationForm.datePickerValidationDateTo(Optional.ofNullable(dateTo.getValue()));
			validationForm.comboBoxValidation(Optional.ofNullable(cmb1.getValue()));

			Alert alertStart = new Alert(AlertType.INFORMATION);
			alertStart.initOwner(closeBtn.getScene().getWindow());
			alertStart.setTitle("Starting Operations");
			alertStart.setHeaderText("Please, be patient while the process is going on!");
			alertStart.showAndWait();

			financeModuleService.generateInvoiceNo();

			Alert alertGenerateInvoiceNo = new Alert(AlertType.INFORMATION);
			alertGenerateInvoiceNo.initOwner(closeBtn.getScene().getWindow());
			alertGenerateInvoiceNo.setTitle("Generating Invoice Number and Updating reservation_claim table complete");

			alertGenerateInvoiceNo.setHeaderText(financeModuleService.headerTextAlertgenerateInvoiceNo());

			alertGenerateInvoiceNo.showAndWait();

			financeModuleService.updateCloseFromZeroToOne(dateFrom.getValue(), dateTo.getValue(),
					cmb1.getValue().getIdAgency(), "", "", "", "", "", "", "", "", "", "", false);

			Alert alertUpdateCloseFromZeroToOne = new Alert(AlertType.INFORMATION);
			alertUpdateCloseFromZeroToOne.initOwner(closeBtn.getScene().getWindow());
			alertUpdateCloseFromZeroToOne.setTitle("Updating reservation table complete");
			alertUpdateCloseFromZeroToOne.setHeaderText(
					financeModuleService.headerTextAlertUpdateCloseFromZeroToOne(dateFrom.getValue(), dateTo.getValue(),
							cmb1.getValue().getIdAgency(), "", "", "", "", "", "", "", "", "", "", false));
			alertUpdateCloseFromZeroToOne.showAndWait();

			financeModuleService.insertIntoSico(dateFrom.getValue(), dateTo.getValue());

			Alert alertSicoInt = new Alert(AlertType.INFORMATION);
			alertSicoInt.initOwner(closeBtn.getScene().getWindow());
			alertSicoInt.setTitle("Inserting data in sico_int and Updating reservation_claim tables complete");
			alertSicoInt
					.setHeaderText(financeModuleService.headerTextAlertSicoInt(dateFrom.getValue(), dateTo.getValue()));
			alertSicoInt.showAndWait();

			financeModuleService.insertIntoSintercl(dateFrom.getValue(), dateTo.getValue());

			Alert alertSintercl = new Alert(AlertType.INFORMATION);
			alertSintercl.initOwner(closeBtn.getScene().getWindow());
			alertSintercl.setTitle("Inserting data in sintercl table complete");
			alertSintercl.setHeaderText(
					financeModuleService.headerTextAlertSintercl(dateFrom.getValue(), dateTo.getValue()));
			alertSintercl.showAndWait();

			financeModuleService.insertIntoSintercl2(dateFrom.getValue(), dateTo.getValue());

			Alert alertSintercl2 = new Alert(AlertType.INFORMATION);
			alertSintercl2.initOwner(closeBtn.getScene().getWindow());
			alertSintercl2.setTitle("(2) Inserting data in sintercl table complete");
			alertSintercl2.setHeaderText(
					financeModuleService.headerTextAlertSintercl2(dateFrom.getValue(), dateTo.getValue()));
			alertSintercl2.showAndWait();

			financeModuleService.insertIntoSintercl3(dateFrom.getValue(), dateTo.getValue());

			Alert alertSintercl3 = new Alert(AlertType.INFORMATION);
			alertSintercl3.initOwner(closeBtn.getScene().getWindow());
			alertSintercl3.setTitle("(3) Inserting data in sintercl table complete");
			alertSintercl3.setHeaderText(
					financeModuleService.headerTextAlertSintercl3(dateFrom.getValue(), dateTo.getValue()));
			alertSintercl3.showAndWait();

			financeModuleService.updateSicoInt();

			Alert alertUpdateSicoInt = new Alert(AlertType.INFORMATION);
			alertUpdateSicoInt.initOwner(closeBtn.getScene().getWindow());
			alertUpdateSicoInt.setTitle("Updating data in sico_int table complete");
			alertUpdateSicoInt.setHeaderText(financeModuleService.headerTextAlertUpdateSicoInt());
			alertUpdateSicoInt.showAndWait();

			financeModuleService.insertIntoSacTransactionImport(dateFrom.getValue(), dateTo.getValue());

			Alert alertSacTransactionImport = new Alert(AlertType.INFORMATION);
			alertSacTransactionImport.initOwner(closeBtn.getScene().getWindow());
			alertSacTransactionImport
					.setTitle("Inserting data in sac_transaction_import and Updating sintercl tables complete");
			alertSacTransactionImport.setHeaderText(
					financeModuleService.headerTextAlertSacTransactionImport(dateFrom.getValue(), dateTo.getValue()));
			alertSacTransactionImport.showAndWait();

			financeModuleService.clean();

			Alert alertEnd = new Alert(AlertType.INFORMATION);
			alertEnd.initOwner(closeBtn.getScene().getWindow());
			alertEnd.setTitle("SUCCESS");
			alertEnd.setHeaderText("All Operations done successfully.");
			alertEnd.showAndWait();

		} catch (FinanceModuleException | ValidationException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(closeBtn.getScene().getWindow());
			alert.setTitle("ERROR");
			alert.setHeaderText(e.getMessage());
			// alert.setContentText(e.getMessage());
			alert.showAndWait();

		}

	}

	@FXML

	private void print() {

		try {

			throw new FinanceModuleException("Printing feature will be available soon");

		} catch (FinanceModuleException e) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(closeBtn.getScene().getWindow());
			alert.setTitle("Printing feature");
			alert.setHeaderText(e.getMessage());
			// alert.setContentText(e.getMessage());
			alert.showAndWait();

		}

	}

	@FXML

	private void switchTheme() {

		App.setLight(!App.isLight());

		if (App.isLight()) {

			if (themeBtn.getScene().getStylesheets().contains(App.class.getResource(App.DARK_THEME).toExternalForm())) {

				themeBtn.getScene().getStylesheets().remove(App.class.getResource(App.DARK_THEME).toExternalForm());
			}

			if (!themeBtn.getScene().getStylesheets()
					.contains(App.class.getResource(App.LIGHT_THEME).toExternalForm())) {

				themeBtn.getScene().getStylesheets().add(App.class.getResource(App.LIGHT_THEME).toExternalForm());
			}

			themeBtn.setText("GO TO DARK THEME");

		} else {

			if (themeBtn.getScene().getStylesheets()
					.contains(App.class.getResource(App.LIGHT_THEME).toExternalForm())) {

				themeBtn.getScene().getStylesheets().remove(App.class.getResource(App.LIGHT_THEME).toExternalForm());
			}

			if (!themeBtn.getScene().getStylesheets()
					.contains(App.class.getResource(App.DARK_THEME).toExternalForm())) {

				themeBtn.getScene().getStylesheets().add(App.class.getResource(App.DARK_THEME).toExternalForm());
			}

			themeBtn.setText("GO TO LIGHT THEME");

		}

	}

}

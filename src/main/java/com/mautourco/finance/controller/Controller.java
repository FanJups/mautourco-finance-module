package com.mautourco.finance.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.mautourco.finance.dao.ComboBoxDao;
import com.mautourco.finance.dao.TableViewDao;
import com.mautourco.finance.event.AutoCompleteBox;
import com.mautourco.finance.exception.FinanceModuleException;
import com.mautourco.finance.model.ComboBoxItem;
import com.mautourco.finance.model.ReservationClaim;
import com.mautourco.finance.service.FinanceModuleService;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class Controller {

	public final int ROWS_PER_PAGE = 2;

	private List<ReservationClaim> data;

	private FinanceModuleService financeModuleService = new FinanceModuleService();

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

	@FXML
	private TableColumn serviceIdCol;

	@FXML
	private TableColumn resaIdCol;

	@FXML
	private TableColumn serviceTypeCol;

	@FXML
	private TableColumn typeCol;

	@FXML
	private TableColumn descriptionCol;

	@FXML
	private TableColumn dateEffectedCol;

	@FXML
	private TableColumn serviceFromCol;

	@FXML
	private TableColumn serviceToCol;

	@FXML
	private TableColumn payingAgencyCol;

	@FXML
	private TableColumn invJdeCodeCol;

	@FXML
	private TableColumn taxableClaimCol;

	@FXML
	private TableColumn vatCol;

	@FXML
	private TableColumn invCCenterCol;

	@FXML
	private TableColumn claimTotalAfterDiscCol;

	@FXML
	private TableColumn invSubsidiaryCol;

	@FXML
	private BorderPane borderPane;

	@FXML
	private void initialize() {
		cmb1.setItems(FXCollections.observableArrayList(new ComboBoxDao().getData()));

		new AutoCompleteBox(cmb1);

		tv1.setPlaceholder(new Label("No Content"));

		dateFrom.setEditable(false);
		dateTo.setEditable(false);

	}

	@FXML
	private void printValue() {
	}

	@FXML
	private void validateForm() {

		try {
			financeModuleService.datePickerValidationDateFrom(Optional.ofNullable(dateFrom.getValue()));

			financeModuleService.datePickerValidationDateTo(Optional.ofNullable(dateTo.getValue()));

			financeModuleService.comboBoxValidation(Optional.ofNullable(cmb1.getValue().getName()));

			System.err.println("date from : " + dateFrom.getValue());

			data = new TableViewDao().getData(dateFrom.getValue(), dateTo.getValue(), cmb1.getValue().getIdAgency());

			/////////////////////// CREATE TABLE/////////////////////

			serviceIdCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Long>("serviceId"));

			resaIdCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Long>("resaId"));

			serviceTypeCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("serviceType"));

			typeCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("type"));

			descriptionCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("description"));

			dateEffectedCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Date>("dateEffected"));

			serviceFromCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("serviceFrom"));

			serviceToCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("serviceTo"));

			payingAgencyCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("payingAgency"));

			invJdeCodeCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("invJdeCode"));

			taxableClaimCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("taxableClaim"));

			vatCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("vat"));

			invCCenterCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("invCCenter"));

			claimTotalAfterDiscCol
					.setCellValueFactory(new PropertyValueFactory<ReservationClaim, Double>("claimTotalAfterDisc"));

			invSubsidiaryCol.setCellValueFactory(new PropertyValueFactory<ReservationClaim, String>("invSubsidiary"));

			// tv1.getColumns().addAll(serviceIdCol, resaIdCol, serviceTypeCol, typeCol,
			// descriptionCol, dateEffectedCol,
			// serviceFromCol, serviceToCol, payingAgencyCol, invJdeCodeCol,
			// taxableClaimCol, vatCol,
			// invCCenterCol, claimTotalAfterDiscCol, invSubsidiaryCol);

			tv1.setItems(FXCollections.observableArrayList(data));
			// borderPane.getChildren().addAll(anchor);

			// Scene scene = new Scene(new BorderPane(pagination), 500, 500);

			//////////////////////////////////////////

		} catch (FinanceModuleException e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(e.getMessage());
			// alert.setContentText(e.getMessage());
			alert.showAndWait();

		}

	}

}

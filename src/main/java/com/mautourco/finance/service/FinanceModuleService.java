package com.mautourco.finance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.mautourco.finance.dao.ComboBoxDao;
import com.mautourco.finance.dao.TableViewDao;
import com.mautourco.finance.exception.DAOException;
import com.mautourco.finance.exception.FinanceModuleException;
import com.mautourco.finance.model.ComboBoxItem;
import com.mautourco.finance.model.ReservationClaim;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class FinanceModuleService {

	private TableViewDao tableViewDao = new TableViewDao();
	private ComboBoxDao comboBoxDao = new ComboBoxDao();

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

	public List<ComboBoxItem> getComboBoxItems() throws FinanceModuleException {

		List<ComboBoxItem> comboBoxItems = null;

		try {

			comboBoxItems = comboBoxDao.getData();

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to access agencies list");

		}

		return comboBoxItems;

	}

	public List<ReservationClaim> getReservationClaims(LocalDate dateFrom, LocalDate dateTo, int idAgency,
			String serviceFilter, String typeFilter, String claimDescFilter, String fromFilter, String toFilter,
			String payingAgencyFilter, String sicoraxCodeFilter, String auxiliaryFilter, String subsidiaryFilter,
			String currFilter, boolean containsNullValues) throws FinanceModuleException {

		List<ReservationClaim> reservationClaims = null;

		try {

			reservationClaims = tableViewDao.getData(dateFrom, dateTo, idAgency, serviceFilter, typeFilter,
					claimDescFilter, fromFilter, toFilter, payingAgencyFilter, sicoraxCodeFilter, auxiliaryFilter,
					subsidiaryFilter, currFilter, containsNullValues);

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to access reservation claims list");

		}

		return reservationClaims;
	}

	public void generateInvoiceNo() throws FinanceModuleException {

		try {

			tableViewDao.getData().stream().forEach(TableViewDao::generateInvoiceNo);

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to generate Invoices");

		}

	}

	public String headerTextAlertgenerateInvoiceNo() throws FinanceModuleException {
		StringBuilder builderHeaderTextAlertgenerateInvoiceNo = new StringBuilder();

		try {
			if (tableViewDao.getData().isEmpty()) {

				builderHeaderTextAlertgenerateInvoiceNo
						.append("No generated invoice number and no updated row in reservation_claim");

			} else {

				if (TableViewDao.getNumberOfRowsInsertedIntoInvHeader() == 0) {

					if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader() == 0) {

						builderHeaderTextAlertgenerateInvoiceNo
								.append("No generated invoice number and no updated row in reservation_claim");

					} else {

						if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader() == 1) {

							builderHeaderTextAlertgenerateInvoiceNo.append("No generated invoice number and ")
									.append(TableViewDao
											.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader())
									.append(" updated row in reservation_claim");

						} else {

							builderHeaderTextAlertgenerateInvoiceNo.append("No generated invoice number and ")
									.append(TableViewDao
											.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader())
									.append(" updated rows in reservation_claim");

						}

					}

				} else {

					if (TableViewDao.getNumberOfRowsInsertedIntoInvHeader() == 1) {
						if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader() == 0) {

							builderHeaderTextAlertgenerateInvoiceNo
									.append(TableViewDao.getNumberOfRowsInsertedIntoInvHeader())
									.append(" generated invoice number and ")
									.append("no updated row in reservation_claim");

						} else {
							if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader() == 1) {

								builderHeaderTextAlertgenerateInvoiceNo
										.append(TableViewDao.getNumberOfRowsInsertedIntoInvHeader())
										.append(" generated invoice number and ")
										.append(TableViewDao
												.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader())
										.append(" updated row in reservation_claim");

							} else {

								builderHeaderTextAlertgenerateInvoiceNo
										.append(TableViewDao.getNumberOfRowsInsertedIntoInvHeader())
										.append(" generated invoice number and ")
										.append(TableViewDao
												.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader())
										.append(" updated rows in reservation_claim");

							}

						}

					} else {

						if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader() == 0) {

							builderHeaderTextAlertgenerateInvoiceNo
									.append(TableViewDao.getNumberOfRowsInsertedIntoInvHeader())
									.append(" generated invoice numbers and ")
									.append("no updated row in reservation_claim");

						} else {
							if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader() == 1) {

								builderHeaderTextAlertgenerateInvoiceNo
										.append(TableViewDao.getNumberOfRowsInsertedIntoInvHeader())
										.append(" generated invoice numbers and ")
										.append(TableViewDao
												.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader())
										.append(" updated row in reservation_claim");

							} else {

								builderHeaderTextAlertgenerateInvoiceNo
										.append(TableViewDao.getNumberOfRowsInsertedIntoInvHeader())
										.append(" generated invoice numbers and ")
										.append(TableViewDao
												.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader())
										.append(" updated rows in reservation_claim");

							}

						}

					}

				}

			}

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to generate alert's header text related to invoices generation");

		}

		return builderHeaderTextAlertgenerateInvoiceNo.toString();
	}

	public void updateCloseFromZeroToOne(LocalDate dateFrom, LocalDate dateTo, int idAgency, String serviceFilter,
			String typeFilter, String claimDescFilter, String fromFilter, String toFilter, String payingAgencyFilter,
			String sicoraxCodeFilter, String auxiliaryFilter, String subsidiaryFilter, String currFilter,
			boolean containsNullValues) throws FinanceModuleException {

		try {
			List<ReservationClaim> dataWithNoNullValues = getReservationClaims(dateFrom, dateTo, idAgency,
					serviceFilter, typeFilter, claimDescFilter, fromFilter, toFilter, payingAgencyFilter,
					sicoraxCodeFilter, auxiliaryFilter, subsidiaryFilter, currFilter, containsNullValues);

			dataWithNoNullValues.stream().map(FinanceModuleService::getResaId)
					.forEach(TableViewDao::updateCloseFromZeroToOne);

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to update reservation table");

		}

	}

	public String headerTextAlertUpdateCloseFromZeroToOne(LocalDate dateFrom, LocalDate dateTo, int idAgency,
			String serviceFilter, String typeFilter, String claimDescFilter, String fromFilter, String toFilter,
			String payingAgencyFilter, String sicoraxCodeFilter, String auxiliaryFilter, String subsidiaryFilter,
			String currFilter, boolean containsNullValues) throws FinanceModuleException {

		StringBuilder builderHeaderTextAlertUpdateCloseFromZeroToOne = new StringBuilder();

		try {

			if (tableViewDao.getData(dateFrom, dateTo, idAgency, serviceFilter, typeFilter, claimDescFilter, fromFilter,
					toFilter, payingAgencyFilter, sicoraxCodeFilter, auxiliaryFilter, subsidiaryFilter, currFilter,
					containsNullValues).isEmpty()) {

				builderHeaderTextAlertUpdateCloseFromZeroToOne.append("No updated row in reservation");

			} else {

				if (TableViewDao.getNumberOfReservationRowsUpdated() == 0) {
					builderHeaderTextAlertUpdateCloseFromZeroToOne.append("No updated row in reservation");

				} else {

					if (TableViewDao.getNumberOfReservationRowsUpdated() == 1) {

						builderHeaderTextAlertUpdateCloseFromZeroToOne
								.append(TableViewDao.getNumberOfReservationRowsUpdated())
								.append(" updated row in reservation");

					} else {

						builderHeaderTextAlertUpdateCloseFromZeroToOne
								.append(TableViewDao.getNumberOfReservationRowsUpdated())
								.append(" updated rows in reservation");

					}

				}
			}

		} catch (DAOException e) {

			throw new FinanceModuleException(
					"Failed to generate alert's header text related to updating reservation table");

		}

		return builderHeaderTextAlertUpdateCloseFromZeroToOne.toString();
	}

	public void insertIntoSico(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {

		try {

			tableViewDao.getDataSicoInt(dateFrom, dateTo).stream().forEach(TableViewDao::insertIntoSico);

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to insert data into sico_int table");

		}

	}

	public String headerTextAlertSicoInt(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {
		StringBuilder builderHeaderTextAlertSicoInt = new StringBuilder();

		try {
			if (tableViewDao.getDataSicoInt(dateFrom, dateTo).isEmpty()) {

				builderHeaderTextAlertSicoInt
						.append("No row added to sico_int and no updated row in reservation_claim ");

			} else {

				if (TableViewDao.getNumberOfRowsInsertedIntoSicoInt() == 0) {

					if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt() == 0) {

						builderHeaderTextAlertSicoInt
								.append("No row added to sico_int and no updated row in reservation_claim ");

					} else {

						if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt() == 1) {

							builderHeaderTextAlertSicoInt.append("No row added to sico_int and ")
									.append(TableViewDao
											.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt())
									.append(" updated row in reservation_claim ");

						} else {

							builderHeaderTextAlertSicoInt.append("No row added to sico_int and ")
									.append(TableViewDao
											.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt())
									.append(" updated rows in reservation_claim ");

						}

					}

				} else {

					if (TableViewDao.getNumberOfRowsInsertedIntoSicoInt() == 1) {

						if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt() == 0) {

							builderHeaderTextAlertSicoInt.append(TableViewDao.getNumberOfRowsInsertedIntoSicoInt())
									.append(" row added to sico_int and no updated row in reservation_claim ");

						} else {

							if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt() == 1) {

								builderHeaderTextAlertSicoInt.append(TableViewDao.getNumberOfRowsInsertedIntoSicoInt())
										.append(" row added to sico_int and ")
										.append(TableViewDao
												.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt())
										.append(" updated row in reservation_claim ");

							} else {

								builderHeaderTextAlertSicoInt.append(TableViewDao.getNumberOfRowsInsertedIntoSicoInt())
										.append(" row added to sico_int and ")
										.append(TableViewDao
												.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt())
										.append(" updated rows in reservation_claim ");

							}

						}

					} else {

						if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt() == 0) {

							builderHeaderTextAlertSicoInt.append(TableViewDao.getNumberOfRowsInsertedIntoSicoInt())
									.append(" rows added to sico_int and no updated row in reservation_claim ");

						} else {

							if (TableViewDao.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt() == 1) {

								builderHeaderTextAlertSicoInt.append(TableViewDao.getNumberOfRowsInsertedIntoSicoInt())
										.append(" rows added to sico_int and ")
										.append(TableViewDao
												.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt())
										.append(" updated row in reservation_claim ");

							} else {

								builderHeaderTextAlertSicoInt.append(TableViewDao.getNumberOfRowsInsertedIntoSicoInt())
										.append(" rows added to sico_int and ")
										.append(TableViewDao
												.getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt())
										.append(" updated rows in reservation_claim ");

							}

						}

					}

				}

			}

		} catch (DAOException e) {

			throw new FinanceModuleException(
					"Failed to generate alert's header text related to inserting data into sico_int table");

		}

		return builderHeaderTextAlertSicoInt.toString();
	}

	public void insertIntoSintercl(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {

		try {

			tableViewDao.getDataSintercl(dateFrom, dateTo).stream().forEach(TableViewDao::insertIntoSintercl);

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to insert data into sintercl table");

		}

	}

	public String headerTextAlertSintercl(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {

		StringBuilder builderHeaderTextAlertSintercl = new StringBuilder();

		try {

			if (tableViewDao.getDataSintercl(dateFrom, dateTo).isEmpty()) {

				builderHeaderTextAlertSintercl.append("No row added to sintercl");

			} else {

				if (TableViewDao.getNumberOfRowsInsertedIntoSintercl() == 0) {
					builderHeaderTextAlertSintercl.append("No row added to sintercl");

				} else {

					if (TableViewDao.getNumberOfRowsInsertedIntoSintercl() == 1) {

						builderHeaderTextAlertSintercl.append(TableViewDao.getNumberOfRowsInsertedIntoSintercl())
								.append(" row added into sintercl");

					} else {

						builderHeaderTextAlertSintercl.append(TableViewDao.getNumberOfRowsInsertedIntoSintercl())
								.append(" rows added into sintercl");

					}

				}
			}

		} catch (DAOException e) {

			throw new FinanceModuleException(
					"Failed to generate alert's header text related to inserting data into sintercl table");

		}

		return builderHeaderTextAlertSintercl.toString();
	}

	public void insertIntoSintercl2(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {

		try {

			tableViewDao.getDataSintercl2(dateFrom, dateTo).stream().forEach(TableViewDao::insertIntoSintercl2);

		} catch (DAOException e) {
			throw new FinanceModuleException("Failed to insert data into sintercl table");
		}

	}

	public String headerTextAlertSintercl2(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {

		StringBuilder builderHeaderTextAlertSintercl2 = new StringBuilder();

		try {

			if (tableViewDao.getDataSintercl2(dateFrom, dateTo).isEmpty()) {

				builderHeaderTextAlertSintercl2.append("No row added to sintercl");

			} else {

				if (TableViewDao.getNumberOfRowsInsertedIntoSintercl2() == 0) {
					builderHeaderTextAlertSintercl2.append("No row added to sintercl");

				} else {

					if (TableViewDao.getNumberOfRowsInsertedIntoSintercl2() == 1) {

						builderHeaderTextAlertSintercl2.append(TableViewDao.getNumberOfRowsInsertedIntoSintercl2())
								.append(" row added into sintercl");

					} else {

						builderHeaderTextAlertSintercl2.append(TableViewDao.getNumberOfRowsInsertedIntoSintercl2())
								.append(" rows added into sintercl");

					}

				}
			}

		} catch (DAOException e) {
			throw new FinanceModuleException(
					"Failed to generate alert's header text related to inserting data into sintercl table");

		}

		return (new StringBuilder().append("(2) ").append(builderHeaderTextAlertSintercl2.toString())).toString();
	}

	public void insertIntoSintercl3(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {

		try {
			tableViewDao.getDataSintercl3(dateFrom, dateTo).stream().forEach(TableViewDao::insertIntoSintercl3);

		} catch (DAOException e) {
			throw new FinanceModuleException("Failed to insert data into sintercl table");
		}

	}

	public String headerTextAlertSintercl3(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {

		StringBuilder builderHeaderTextAlertSintercl3 = new StringBuilder();

		try {

			if (tableViewDao.getDataSintercl3(dateFrom, dateTo).isEmpty()) {

				builderHeaderTextAlertSintercl3.append("No row added to sintercl");

			} else {

				if (TableViewDao.getNumberOfRowsInsertedIntoSintercl3() == 0) {
					builderHeaderTextAlertSintercl3.append("No row added to sintercl");

				} else {

					if (TableViewDao.getNumberOfRowsInsertedIntoSintercl3() == 1) {

						builderHeaderTextAlertSintercl3.append(TableViewDao.getNumberOfRowsInsertedIntoSintercl3())
								.append(" row added into sintercl");

					} else {

						builderHeaderTextAlertSintercl3.append(TableViewDao.getNumberOfRowsInsertedIntoSintercl3())
								.append(" rows added into sintercl");

					}

				}
			}

		} catch (DAOException e) {
			throw new FinanceModuleException(
					"Failed to generate alert's header text related to inserting data into sintercl table");
		}

		return (new StringBuilder().append("(3) ").append(builderHeaderTextAlertSintercl3.toString())).toString();
	}

	public void updateSicoInt() throws FinanceModuleException {

		try {
			tableViewDao.updateSicoInt();

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to update sico_int table");

		}

	}

	public String headerTextAlertUpdateSicoInt() throws FinanceModuleException {

		StringBuilder builderHeaderTextAlertupdateSicoInt = new StringBuilder();

		try {

			if (TableViewDao.getNumberOfSicoIntRowsUpdated() == 0) {

				builderHeaderTextAlertupdateSicoInt.append("No updated row in sico_int");

			} else {

				if (TableViewDao.getNumberOfSicoIntRowsUpdated() == 1) {

					builderHeaderTextAlertupdateSicoInt.append(TableViewDao.getNumberOfSicoIntRowsUpdated())
							.append(" updated row in sico_int");

				} else {

					builderHeaderTextAlertupdateSicoInt.append(TableViewDao.getNumberOfSicoIntRowsUpdated())
							.append(" updated rows in sico_int");

				}

			}

		} catch (DAOException e) {

			throw new FinanceModuleException(
					"Failed to generate alert's header text related to updating sico_int table");

		}

		return builderHeaderTextAlertupdateSicoInt.toString();
	}

	public void insertIntoSacTransactionImport(LocalDate dateFrom, LocalDate dateTo) throws FinanceModuleException {

		try {
			tableViewDao.getDataSacTransactionImport(dateFrom, dateTo).stream()
					.forEach(TableViewDao::insertIntoSacTransactionImport);

		} catch (DAOException e) {

			throw new FinanceModuleException("Failed to insert data into sac_transaction_import table");

		}

	}

	public String headerTextAlertSacTransactionImport(LocalDate dateFrom, LocalDate dateTo)
			throws FinanceModuleException {
		StringBuilder builderHeaderTextAlertSacTransactionImport = new StringBuilder();

		try {

			if (tableViewDao.getDataSacTransactionImport(dateFrom, dateTo).isEmpty()) {

				builderHeaderTextAlertSacTransactionImport
						.append("No row added to sac_transaction_import and no updated row in sintercl");

			} else {

				if (TableViewDao.getNumberOfRowsInsertedIntoSacTransactionImport() == 0) {

					if (TableViewDao.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport() == 0) {

						builderHeaderTextAlertSacTransactionImport
								.append("No row added to sac_transaction_import and no updated row in sintercl");

					} else {

						if (TableViewDao.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport() == 1) {

							builderHeaderTextAlertSacTransactionImport
									.append("No row added to sac_transaction_import and ")
									.append(TableViewDao
											.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport())
									.append(" updated row in sintercl");

						} else {

							builderHeaderTextAlertSacTransactionImport
									.append("No row added to sac_transaction_import and ")
									.append(TableViewDao
											.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport())
									.append(" updated rows in sintercl");

						}

					}

				} else {

					if (TableViewDao.getNumberOfRowsInsertedIntoSacTransactionImport() == 1) {

						if (TableViewDao.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport() == 0) {

							builderHeaderTextAlertSacTransactionImport
									.append(TableViewDao.getNumberOfRowsInsertedIntoSacTransactionImport())
									.append(" row added to sac_transaction_import and no updated row in sintercl");

						} else {

							if (TableViewDao
									.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport() == 1) {

								builderHeaderTextAlertSacTransactionImport
										.append(TableViewDao.getNumberOfRowsInsertedIntoSacTransactionImport())
										.append(" row added to sac_transaction_import and ")
										.append(TableViewDao
												.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport())
										.append(" updated row in sintercl");

							} else {

								builderHeaderTextAlertSacTransactionImport
										.append(TableViewDao.getNumberOfRowsInsertedIntoSacTransactionImport())
										.append(" row added to sac_transaction_import and ")
										.append(TableViewDao
												.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport())
										.append(" updated rows in sintercl ");

							}

						}

					} else {

						if (TableViewDao.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport() == 0) {

							builderHeaderTextAlertSacTransactionImport
									.append(TableViewDao.getNumberOfRowsInsertedIntoSacTransactionImport())
									.append(" rows added to sac_transaction_import and no updated row in sintercl ");

						} else {

							if (TableViewDao
									.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport() == 1) {

								builderHeaderTextAlertSacTransactionImport
										.append(TableViewDao.getNumberOfRowsInsertedIntoSacTransactionImport())
										.append(" rows added to sac_transaction_import and ")
										.append(TableViewDao
												.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport())
										.append(" updated row in sintercl");

							} else {

								builderHeaderTextAlertSacTransactionImport
										.append(TableViewDao.getNumberOfRowsInsertedIntoSacTransactionImport())
										.append(" rows added to sac_transaction_import and ")
										.append(TableViewDao
												.getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport())
										.append(" updated rows in sintercl ");

							}

						}

					}

				}

			}

		} catch (DAOException e) {

			throw new FinanceModuleException(
					"Failed to generate alert's header text related to inserting data into sac_transaction_import table");

		}

		return builderHeaderTextAlertSacTransactionImport.toString();
	}

	public void clean() {

		TableViewDao.setNumberOfRowsInsertedIntoInvHeader(0);

		TableViewDao.setNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader(0);

		TableViewDao.setNumberOfReservationRowsUpdated(0);

		TableViewDao.setNumberOfRowsInsertedIntoSicoInt(0);

		TableViewDao.setNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt(0);

		TableViewDao.setNumberOfRowsInsertedIntoSintercl(0);

		TableViewDao.setNumberOfRowsInsertedIntoSintercl2(0);

		TableViewDao.setNumberOfRowsInsertedIntoSintercl3(0);

		TableViewDao.setNumberOfSicoIntRowsUpdated(0);

		TableViewDao.setNumberOfRowsInsertedIntoSacTransactionImport(0);

		TableViewDao.setNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport(0);

	}

	private static Long getResaId(ReservationClaim r) {
		return r.getResaId();
	}

}

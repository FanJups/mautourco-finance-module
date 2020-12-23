package com.mautourco.finance.dao;

import static com.mautourco.finance.dao.DAOUtils.close;
import static com.mautourco.finance.dao.DAOUtils.initializePreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mautourco.finance.model.InvHeader;
import com.mautourco.finance.model.ReservationClaim;
import com.mautourco.finance.model.ReservationClaimPerResaIdPerClaimCurrency;
import com.mautourco.finance.model.SacTransactionImport;
import com.mautourco.finance.model.SicoInt;
import com.mautourco.finance.model.Sintercl;

public class TableViewDao {

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	private static final String SQL_INSERT_INV_HEADER = "INSERT INTO inv_header (resa_id,id_paidby,user_id,net_amt,currency,inv_date) VALUES(?,?,?,?,?,CURRENT_DATE);";

	private static final String SQL_INSERT_SICO_INT = "INSERT INTO sico_int (Date,Docu_Date,Serv_Type,Serv_For,"
			+ "Resa_Id,Descr,Clients,Cur_Type,Net_Amt,Tax_Amt,Vat_Amt,Exc_Rate,Inv_No,Date_Inv,"
			+ "Sico_Code,Agency,CCenter,Busi_Unit,Docu,Sub,Active,Post,Paid_By,Resa_Type,Claim_Type) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	private static final String SQL_INSERT_SINTERCL = "INSERT INTO sintercl (source,company,journal,transactdate,"
			+ "paymentdate,reference,auxiliary,description,user_def1,user_def2,amountrs,currcode,exchange_rate,curramount,"
			+ "vatcurr,vatrs,supcode,sourcebat) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	private static final String SQL_INSERT_SINTERCL2 = "INSERT INTO sintercl (source,company,journal,transactdate,"
			+ "paymentdate,auxiliary,description,amountrs,currcode,curramount,vatline,vatcurr,vatrs,sourcebat) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	private static final String SQL_INSERT_SINTERCL3 = "INSERT INTO sintercl (source,company,journal,transactdate,"
			+ "paymentdate,glaccount,analytical,description,amountrs,currcode,curramount,sourcebat) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	private static final String SQL_INSERT_SAC_TRANSACTION_IMPORT = "INSERT INTO sac_transaction_import (CompanyCode,Journal,BatchNo,LineNo,"
			+ "gl,auxiliary,analytic,ClientSupplier,TransactionDate,DocumentDate,PaymentDueDate,ReferenceNo,"
			+ "TransactionDescription,CurrencyGroup,Currency,ExchangeRate,LocalAmount,ForeignAmount,"
			+ "TransactionUD01,TransactionUD02,TransactionUD03,TransactionUD04,TransactionUD05,TaxableAmount,"
			+ "TaxableAmountForeign,TaxAtSource,TaxAtSourcePer,TaxAtSourceAmt) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	public List<ReservationClaim> getData(LocalDate dateFrom, LocalDate dateTo, int idAgency, String serviceFilter,
			String typeFilter, String claimDescFilter, String fromFilter, String toFilter, String payingAgencyFilter,
			String sicoraxCodeFilter, String auxiliaryFilter, String subsidiaryFilter, String currFilter,
			boolean containsNullValues) {

		List<ReservationClaim> reservationClaims = new ArrayList<>();

		Connection con = null;
		PreparedStatement statement = null;

		ResultSet set = null;

		try {

			con = daoFactory.getConnection();

			String noNullValues = " AND claim_total_after_disc IS NOT NULL " + "AND taxable_claim IS NOT NULL "
					+ "AND resa_id IS NOT NULL " + "AND service_type IS NOT NULL " + "AND type IS NOT NULL "
					+ "AND description IS NOT NULL " + "AND date_effected IS NOT NULL "
					+ "AND service_from IS NOT NULL " + "AND service_to IS NOT NULL " + "AND nights IS NOT NULL "
					+ "AND claim_type IS NOT NULL " + "AND claim_adult_pax IS NOT NULL "
					+ "AND claim_teen_pax IS NOT NULL " + "AND claim_child_pax IS NOT NULL "
					+ "AND claim_adult_rate IS NOT NULL " + "AND claim_teen_rate IS NOT NULL "
					+ "AND claim_child_rate IS NOT NULL " + "AND claim_currency IS NOT NULL "
					+ "AND exchange_rate IS NOT NULL " + "AND inv_jde_code IS NOT NULL "
					+ "AND inv_subsidiary IS NOT NULL " + "AND inv_c_center IS NOT NULL";

			String filter = " AND (service_type LIKE \'%" + serviceFilter + "%\' OR service_type IS NULL)"
					+ " AND (type LIKE \'%" + typeFilter + "%\' OR type IS NULL)" + " AND (description LIKE \'%"
					+ claimDescFilter + "%\' OR description IS NULL)" + " AND (service_from LIKE \'%" + fromFilter
					+ "%\' OR service_from IS NULL)" + " AND (service_to LIKE \'%" + toFilter
					+ "%\' OR service_to IS NULL)" + " AND (paying_agency LIKE \'%" + payingAgencyFilter
					+ "%\' OR paying_agency IS NULL)" + " AND (inv_jde_code LIKE \'%" + sicoraxCodeFilter
					+ "%\' OR inv_jde_code IS NULL)" + " AND (inv_c_center LIKE \'%" + auxiliaryFilter
					+ "%\' OR inv_c_center IS NULL)" + " AND (inv_subsidiary LIKE \'%" + subsidiaryFilter
					+ "%\' OR inv_subsidiary IS NULL)" + " AND (claim_currency LIKE \'%" + currFilter
					+ "%\' OR claim_currency IS NULL)";
			String query = "";

			String queryAll = "";

			if (containsNullValues) {

				query = "SELECT resa_id, service_type, TYPE, description, date_effected, service_from, service_to, nights, claim_type,"
						+ " claim_adult_pax, claim_teen_pax, claim_child_pax, claim_adult_rate, claim_teen_rate, claim_child_rate, claim_currency,"
						+ " exchange_rate, taxable_claim, (claim_total_after_disc - taxable_claim) AS vat, claim_total_after_disc, agency.`name`,"
						+ " inv_jde_code, inv_subsidiary, inv_c_center"
						+ " FROM reservation_claim, agency WHERE date_effected BETWEEN " + "\'" + dateFrom + "\'"
						+ " AND " + "\'" + dateTo + "\'"
						+ " AND reservation_claim.active = 1 AND agency.active = 1 AND reservation_claim.paying_agency_id = agency.id_agency AND booking_agency_id = "
						+ idAgency + filter;

				queryAll = "SELECT resa_id, service_type, TYPE, description, date_effected, service_from, service_to, nights, claim_type,"
						+ " claim_adult_pax, claim_teen_pax, claim_child_pax, claim_adult_rate, claim_teen_rate, claim_child_rate, claim_currency,"
						+ " exchange_rate, taxable_claim, (claim_total_after_disc - taxable_claim) AS vat, claim_total_after_disc, agency.`name`,"
						+ " inv_jde_code, inv_subsidiary, inv_c_center"
						+ " FROM reservation_claim, agency WHERE reservation_claim.active = 1 AND agency.active = 1 AND reservation_claim.paying_agency_id = agency.id_agency"
						+ filter;

			} else {

				query = "SELECT resa_id, service_type, TYPE, description, date_effected, service_from, service_to, nights, claim_type,"
						+ " claim_adult_pax, claim_teen_pax, claim_child_pax, claim_adult_rate, claim_teen_rate, claim_child_rate, claim_currency,"
						+ " exchange_rate, taxable_claim, (claim_total_after_disc - taxable_claim) AS vat, claim_total_after_disc, agency.`name`,"
						+ " inv_jde_code, inv_subsidiary, inv_c_center"
						+ " FROM reservation_claim, agency WHERE date_effected BETWEEN " + "\'" + dateFrom + "\'"
						+ " AND " + "\'" + dateTo + "\'"
						+ " AND reservation_claim.active = 1 AND agency.active = 1 AND reservation_claim.paying_agency_id = agency.id_agency AND booking_agency_id = "
						+ idAgency + noNullValues + filter;

				queryAll = "SELECT resa_id, service_type, TYPE, description, date_effected, service_from, service_to, nights, claim_type,"
						+ " claim_adult_pax, claim_teen_pax, claim_child_pax, claim_adult_rate, claim_teen_rate, claim_child_rate, claim_currency,"
						+ " exchange_rate, taxable_claim, (claim_total_after_disc - taxable_claim) AS vat, claim_total_after_disc, agency.`name`, "
						+ " inv_jde_code, inv_subsidiary, inv_c_center"
						+ " FROM reservation_claim, agency WHERE  reservation_claim.active = 1 AND agency.active = 1 AND reservation_claim.paying_agency_id = agency.id_agency"
						+ noNullValues + filter;

			}

			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {
				reservationClaims.add(map(set));
			}

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ex.printStackTrace();

		} finally {

			close(set, statement, con);

		}

		return reservationClaims;

	}

	public void updateCloseFromZeroToOne(Long resaId) {

		Connection con = null;
		PreparedStatement statement = null;

		try {

			con = daoFactory.getConnection();

			String query = "UPDATE reservation SET close = 1, resa_closed_on = CURRENT_DATE WHERE close = 0 AND active = 1 AND resa_id = "
					+ resaId;

			statement = con.prepareStatement(query);

			int status = statement.executeUpdate();

			if (status == 0)
				System.err.println("updateCloseFromZeroToOne NO UPDATE FOR " + resaId);
			else
				System.err.println("updateCloseFromZeroToOne UPDATE FOR " + resaId);

			// con.commit();

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {

			close(statement, con);
		}

	}

	public static void generateInvoiceNo(ReservationClaimPerResaIdPerClaimCurrency r) {

		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGeneratedValues = null;

		try {

			con = daoFactory.getConnection();
			preparedStatement = initializePreparedStatement(con, SQL_INSERT_INV_HEADER, true, r.getResaId(),
					r.getPayingAgencyId(), 001, r.getSumClaimTotalAfterDisc(), r.getClaimCurrency());

			int status = preparedStatement.executeUpdate();

			if (status == 0) {

				System.err.println("No row added to database");

			}

			autoGeneratedValues = preparedStatement.getGeneratedKeys();

			if (autoGeneratedValues.next()) {

				updateReservationClaimInvoiceNo(autoGeneratedValues.getLong(1));
			} else {
				System.err.println("Invoice creation failed, no auto-generated value");

			}

			// con.commit();

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {

			close(autoGeneratedValues, preparedStatement, con);

		}

	}

	public List<ReservationClaimPerResaIdPerClaimCurrency> getData() {

		List<ReservationClaimPerResaIdPerClaimCurrency> reservationClaimsPerResaIdPerClaimCurrency = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {

			String noNullValues = "claim_total_after_disc IS NOT NULL " + "AND taxable_claim IS NOT NULL "
					+ "AND resa_id IS NOT NULL " + "AND service_type IS NOT NULL " + "AND type IS NOT NULL "
					+ "AND description IS NOT NULL " + "AND date_effected IS NOT NULL "
					+ "AND service_from IS NOT NULL " + "AND service_to IS NOT NULL " + "AND nights IS NOT NULL "
					+ "AND claim_type IS NOT NULL " + "AND claim_adult_pax IS NOT NULL "
					+ "AND claim_teen_pax IS NOT NULL " + "AND claim_child_pax IS NOT NULL "
					+ "AND claim_adult_rate IS NOT NULL " + "AND claim_teen_rate IS NOT NULL "
					+ "AND claim_child_rate IS NOT NULL " + "AND claim_currency IS NOT NULL "
					+ "AND exchange_rate IS NOT NULL " + "AND inv_jde_code IS NOT NULL "
					+ "AND inv_subsidiary IS NOT NULL " + "AND inv_c_center IS NOT NULL";

			con = daoFactory.getConnection();

			String query = "SELECT resa_id, claim_currency , SUM(claim_total_after_disc) AS total, paying_agency_id FROM reservation_claim WHERE "
					+ noNullValues + " GROUP BY resa_id,claim_currency";

			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {
				reservationClaimsPerResaIdPerClaimCurrency.add(mapPerResaIdPerClaimCurrency(set));
			}

			System.err.println(" reservationClaimsPerResaIdPerClaimCurrency size "
					+ reservationClaimsPerResaIdPerClaimCurrency.size());

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {

			close(set, statement, con);

		}

		return reservationClaimsPerResaIdPerClaimCurrency;

	}

	public List<Sintercl> getDataSintercl(LocalDate dateFrom, LocalDate dateTo) {

		List<Sintercl> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;

		ResultSet set = null;

		try {

			con = daoFactory.getConnection();

			String query = "SELECT source, company, journal, " + "date AS transactdate, " + "docu_date AS paydate, "
					+ "inv_no AS reference, " + "sico_code AS auxiliary, "
					+ "CONCAT(resa_id,\' - \',LEFT(clients,30)) AS description, " + "LEFT(resa_type,20) AS user_def, "
					+ "LEFT(docu,20) AS user_def2, " + "SUM(net_amt * exc_rate) AS amt_rs, "
					+ "cur_group AS curr_code, " + "cur_type AS curr, " + "exc_rate AS rate, "
					+ "SUM(net_amt) AS amt_curr, " + "SUM(vat_amt) AS vat_curr, "
					+ "SUM(vat_amt * exc_rate) AS vat_rs, " + "sico_code AS sup_code, " + "batch AS source_bat "
					+ "FROM sico_int " + "WHERE active = 1 " + "AND date BETWEEN " + "\'" + dateFrom + "\'" + " AND "
					+ "\'" + dateTo + "\' " + "AND inv_no != 0  " + "AND userid LIKE \'%Claim%\' " + "AND post = 0 "
					+ "AND net_amt != 0 " + "GROUP BY inv_no, cur_type";

			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {
				list.add(mapSintercl(set));
			}

			System.err.println(" listSintercl size " + list.size());

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {

			close(set, statement, con);

		}

		return list;

	}

	public List<Sintercl> getDataSintercl2(LocalDate dateFrom, LocalDate dateTo) {

		List<Sintercl> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;

		ResultSet set = null;

		try {

			con = daoFactory.getConnection();

			String query = "SELECT source, company, journal, " + "date AS transactdate, " + "docu_date AS paydate, "
					+ "\'1.1.40\' AS auxiliary, " + "LEFT(agency,40) AS description, "
					+ "SUM((vat_amt * exc_rate) * -1) AS amt_rs, " + "cur_group AS curr_code, "
					+ "SUM((vat_amt * exc_rate) * -1) AS amt_curr, " + "\'1.1.4\' AS vat_line, "
					+ "SUM((((vat_amt / 15) * 100) * exc_rate) * -1) AS vat_curr, "
					+ "SUM((((vat_amt / 15) * 100) * exc_rate) * -1) AS vat_rs, " + "batch AS source_bat "
					+ "FROM sico_int " + "WHERE active = 1 " + "AND date BETWEEN " + "\'" + dateFrom + "\'" + " AND "
					+ "\'" + dateTo + "\' " + "AND inv_no != 0  " + "AND userid LIKE \'%Claim%\' " + "AND post = 0 "
					+ "GROUP BY sico_code";

			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {
				list.add(mapSintercl2(set));
			}

			System.err.println(" listSintercl2 size " + list.size());

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {

			close(set, statement, con);

		}

		return list;

	}

	public List<Sintercl> getDataSintercl3(LocalDate dateFrom, LocalDate dateTo) {

		List<Sintercl> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;

		ResultSet set = null;

		try {

			con = daoFactory.getConnection();

			String query = "SELECT source, company, journal, " + "date AS transactdate, " + "docu_date AS paydate, "
					+ "sub AS glaccount, " + "ccenter AS analytical, "
					+ "CONCAT(\'TRANSFER FROM ASTERIX - \',cur_type) AS description, "
					+ "SUM((tax_amt * exc_rate) * -1) AS amt_rs, " + "cur_group AS curr_code, "
					+ "SUM((tax_amt * exc_rate) * -1) AS amt_curr, " + "batch AS source_bat " + "FROM sico_int "
					+ "WHERE active = 1 " + "AND date BETWEEN " + "\'" + dateFrom + "\'" + " AND " + "\'" + dateTo
					+ "\' " + "AND inv_no != 0  " + "AND userid LIKE \'%Claim%\' " + "AND post = 0 "
					+ "GROUP BY sub,cur_type";

			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {
				list.add(mapSintercl3(set));
			}

			System.err.println(" listSintercl3 size " + list.size());

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {

			close(set, statement, con);

		}

		return list;

	}

	public List<SicoInt> getDataSicoInt(LocalDate dateFrom, LocalDate dateTo) {

		List<SicoInt> list = new ArrayList<>();
		PreparedStatement statement = null;
		Connection con = null;

		ResultSet set = null;

		try {

			String noNullValues = " AND rcl.claim_total_after_disc IS NOT NULL " + "AND rcl.taxable_claim IS NOT NULL "
					+ "AND rcl.resa_id IS NOT NULL " + "AND rcl.service_type IS NOT NULL " + "AND rcl.type IS NOT NULL "
					+ "AND rcl.description IS NOT NULL " + "AND rcl.date_effected IS NOT NULL "
					+ "AND rcl.service_from IS NOT NULL " + "AND rcl.service_to IS NOT NULL "
					+ "AND rcl.nights IS NOT NULL " + "AND rcl.claim_type IS NOT NULL "
					+ "AND rcl.claim_adult_pax IS NOT NULL " + "AND rcl.claim_teen_pax IS NOT NULL "
					+ "AND rcl.claim_child_pax IS NOT NULL " + "AND rcl.claim_adult_rate IS NOT NULL "
					+ "AND rcl.claim_teen_rate IS NOT NULL " + "AND rcl.claim_child_rate IS NOT NULL "
					+ "AND rcl.claim_currency IS NOT NULL " + "AND rcl.exchange_rate IS NOT NULL "
					+ "AND rcl.inv_jde_code IS NOT NULL " + "AND rcl.inv_subsidiary IS NOT NULL "
					+ "AND rcl.inv_c_center IS NOT NULL ";

			String query = "SELECT " + "r.serv_effected_date AS myDate, " + "r.serv_effected_date AS docu_date, "
					+ "rcl.service_type AS serviceType, " + "rcl.service_for AS service_for, "
					+ "rcl.service_paid_by AS service_paid_by, " + "r.resa_servtype AS resa_type, "
					+ "rcl.type AS claim_type, " + "rcl.resa_id AS resaid, " + "rcl.description AS descr, "
					+ "rcl.clients AS clients, " + "rcl.claim_currency AS claimCurrency, "
					+ "SUM(rcl.claim_total_after_disc) AS netAmount, " + "SUM(rcl.taxable_claim) AS taxableAmt, "
					+ "(SUM(rcl.claim_total_after_disc)-SUM(rcl.taxable_claim)) AS vat, "
					+ "rcl.exchange_rate AS exchangeRate, " + "rcl.invoice_no AS invoiceNo, "
					+ "rcl.date_invoiced AS dateInvoiced, " + "rcl.active AS active, "
					+ "rcl.inv_jde_code AS sico_code, " + "agency.name AS agency, " + "rcl.inv_doc_type AS docType, "
					+ "rcl.inv_curr_type AS currType, " + "rcl.inv_c_center AS cCenter, "
					+ "rcl.inv_subsidiary AS subsidiary, " + "rcl.inv_busi_unit AS businessUnit, "
					+ "rcl.inv_exported AS exported, " + "r.resa_to_ref AS ref "
					+ "FROM reservation_claim rcl, reservation r, agency " + "WHERE rcl.active = 1 "
					+ "AND rcl.resa_id = r.resa_id " + "AND r.id_agency = agency.id_agency "
					+ "AND r.serv_effected_date BETWEEN " + "\'" + dateFrom + "\'" + " AND " + "\'" + dateTo + "\' "
					+ "AND rcl.inv_exported = 0 " + "AND rcl.invoice_no != 0 AND rcl.inv_posted = 0 "
					+ "AND rcl.service_cat_desc NOT LIKE \'%DEPOSIT%\' "
					+ "AND r.resa_servtype IN (\'FIT\', \'A La Carte\', \'Airline\', \'Wedding\', \'EducTour\', \'Assistance\', \'Media\', \'Group\') "
					+ "AND rcl.claim_total_after_disc != 0 " + noNullValues
					+ "GROUP BY rcl.invoice_no, rcl.service_type, rcl.claim_currency";

			con = daoFactory.getConnection();

			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {
				list.add(mapSicoInt(set));
			}

			System.err.println(" listSicoInt size " + list.size());

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {

			close(set, statement, con);

		}

		return list;

	}

	public List<SacTransactionImport> getDataSacTransactionImport(LocalDate dateFrom, LocalDate dateTo) {

		List<SacTransactionImport> list = new ArrayList<>();

		Connection con = null;

		PreparedStatement statement = null;

		ResultSet set = null;

		try {

			String query = "SELECT * " + "FROM sintercl " + "WHERE period BETWEEN " + "\'" + dateFrom + "\'" + " AND "
					+ "\'" + dateTo + "\' AND journal = \'ID\' " + "AND dept = \'#dept#\' " + "AND posted = 0 "
					+ "AND amountrs != 0.00 " + "ORDER BY sequence";

			con = daoFactory.getConnection();

			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {
				list.add(mapSacTransactionImport(set));
			}

			System.err.println(" listTransactionImport " + list.size());

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {

			close(set, statement, con);

		}

		return list;

	}

	public static void insertIntoSico(SicoInt s) {

		Connection con = null;
		PreparedStatement preparedStatement = null;
		// ResultSet autoGeneratedValues = null;

		try {

			con = daoFactory.getConnection();
			preparedStatement = initializePreparedStatement(con, SQL_INSERT_SICO_INT, false, s.getMyDate(),
					s.getDocuDate(), s.getServiceType(), s.getServiceFor(), s.getResaId(), s.getDescr(), s.getClients(),
					s.getClaimCurrency(), s.getNetAmount(), s.getTaxableAmt(), s.getVat(), s.getExchangeRate(),
					s.getInvoiceNo(), s.getDateInvoiced(), s.getSicoCode(), s.getAgency(), s.getcCenter(),
					s.getBusinessUnit(), s.getRef(), s.getSubsidiary(), s.getActive(), s.getExported(),
					s.getServicePaidBy(), s.getResaType(), s.getClaimType());

			int status = preparedStatement.executeUpdate();

			if (status == 0) {

				System.err.println("No row added to database");

			}

			// autoGeneratedValues = preparedStatement.getGeneratedKeys();

			/*
			 * if (autoGeneratedValues.next()) {
			 * 
			 * updateReservationClaimInvoiceNoSicoInt(s.getInvoiceNo()); } else {
			 * System.err.println("Inserting into sico_int failed, no auto-generated value"
			 * );
			 * 
			 * }
			 */

			// con.commit();

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {

			close(preparedStatement, con);

		}

	}

	public static void insertIntoSintercl(Sintercl s) {

		Connection con = null;
		PreparedStatement preparedStatement = null;
		// ResultSet autoGeneratedValues = null;

		try {

			con = daoFactory.getConnection();
			preparedStatement = initializePreparedStatement(con, SQL_INSERT_SINTERCL, false, s.getSource(),
					s.getCompany(), s.getJournal(), s.getTransacDate(), s.getPayDate(), s.getReference(),
					s.getAuxiliary(), s.getDescription(), s.getUserDef(), s.getUserDef2(), s.getAmtRs(),
					s.getCurrCode(), s.getRate(), s.getAmtCurr(), s.getVatCurr(), s.getVatRs(), s.getSupCode(),
					s.getSourceBat());

			int status = preparedStatement.executeUpdate();

			if (status == 0) {

				System.err.println("No row added to database");

			}

			/*
			 * autoGeneratedValues = preparedStatement.getGeneratedKeys();
			 * 
			 * if (autoGeneratedValues.next()) {
			 * 
			 * System.err.println("Inserting into sintercl success"); } else {
			 * System.err.println("Inserting into sintercl failed, no auto-generated value"
			 * );
			 * 
			 * }
			 */

			// con.commit();

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {
			close(preparedStatement, con);

		}

	}

	public static void insertIntoSintercl2(Sintercl s) {

		Connection con = null;
		PreparedStatement preparedStatement = null;
		// ResultSet autoGeneratedValues = null;

		try {

			con = daoFactory.getConnection();
			preparedStatement = initializePreparedStatement(con, SQL_INSERT_SINTERCL2, false, s.getSource(),
					s.getCompany(), s.getJournal(), s.getTransacDate(), s.getPayDate(), s.getAuxiliary(),
					s.getDescription(), s.getAmtRs(), s.getCurrCode(), s.getAmtCurr(), s.getVatLine(), s.getVatCurr(),
					s.getVatRs(), s.getSourceBat());

			int status = preparedStatement.executeUpdate();

			if (status == 0) {

				System.err.println("No row added to database");

			}

			/*
			 * autoGeneratedValues = preparedStatement.getGeneratedKeys();
			 * 
			 * if (autoGeneratedValues.next()) {
			 * 
			 * System.err.println("2- Inserting into sintercl success"); } else {
			 * System.err.
			 * println("2- Inserting into sintercl failed, no auto-generated value");
			 * 
			 * }
			 */

			// con.commit();

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {
			close(preparedStatement, con);

		}

	}

	public static void insertIntoSintercl3(Sintercl s) {

		Connection con = null;
		PreparedStatement preparedStatement = null;
		// ResultSet autoGeneratedValues = null;

		try {

			con = daoFactory.getConnection();
			preparedStatement = initializePreparedStatement(con, SQL_INSERT_SINTERCL3, false, s.getSource(),
					s.getCompany(), s.getJournal(), s.getTransacDate(), s.getPayDate(), s.getGlAccount(),
					s.getAnalytical(), s.getAuxiliary(), s.getDescription(), s.getAmtRs(), s.getCurrCode(),
					s.getAmtCurr(), s.getSourceBat());

			int status = preparedStatement.executeUpdate();

			if (status == 0) {

				System.err.println("No row added to database");

			}

			/*
			 * autoGeneratedValues = preparedStatement.getGeneratedKeys();
			 * 
			 * if (autoGeneratedValues.next()) {
			 * 
			 * System.err.println("3- Inserting into sintercl success"); } else {
			 * System.err.
			 * println("3- Inserting into sintercl failed, no auto-generated value");
			 * 
			 * }
			 */

			// con.commit();

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {
			close(preparedStatement, con);

		}

	}

	public static void insertIntoSacTransactionImport(SacTransactionImport s) {

		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet autoGeneratedValues = null;

		try {

			con = daoFactory.getConnection();
			preparedStatement = initializePreparedStatement(con, SQL_INSERT_SAC_TRANSACTION_IMPORT, true,
					s.getCompanyCode(), s.getJournal(), s.getBatchNo(), s.getLineNo(), s.getGl(), s.getAuxiliary(),
					s.getAnalytic(), s.getClientSupplier(), s.getTransactionDate(), s.getDocumentDate(),
					s.getPaymentDueDate(), s.getReferenceNo(), s.getTransactionDescription(), s.getCurrencyGroup(),
					s.getCurrency(), s.getExchangeRate(), s.getLocalAmount(), s.getForeignAmount(),
					s.getTransactionUD01(), s.getTransactionUD02(), s.getTransactionUD03(), " ", " ",
					s.getTaxableAmount(), s.getTaxableAmountForeign(), s.getTaxAtSource(), s.getTaxAtSourcePer(), 0.00);

			int status = preparedStatement.executeUpdate();

			if (status == 0) {

				System.err.println("No row added to database");

			}

			autoGeneratedValues = preparedStatement.getGeneratedKeys();

			if (autoGeneratedValues.next()) {

				updateSintercl(s.getBatchNo().toString());
			} else {
				System.err.println("Inserting into sac_transaction_import failed, no auto-generated value");

			}

			// con.commit();
		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();

		} finally {
			close(autoGeneratedValues, preparedStatement, con);

		}

	}

	public void updateSicoInt() {

		Connection con = null;

		PreparedStatement statement = null;

		try {

			String query = "UPDATE sico_int SET post = 1 , date_exp = CURRENT_DATE  WHERE post = 0 AND journal = \'ID\' AND source = \'MT\'";
			con = daoFactory.getConnection();

			statement = con.prepareStatement(query);

			int status = statement.executeUpdate();

			if (status == 0)
				System.err.println(" updateSicoInt NO UPDATE FOR ");
			else
				System.err.println(" updateSicoInt UPDATE FOR ");

			// con.commit();

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {
			close(statement, con);
		}

	}

	private static void updateSintercl(String batch) {

		Connection con = null;

		PreparedStatement statement = null;

		try {

			String query = "UPDATE sintercl SET posted = 1 , date_exp = CURRENT_DATE  WHERE batch = \'" + batch + "\'";

			con = daoFactory.getConnection();
			statement = con.prepareStatement(query);

			int status = statement.executeUpdate();

			if (status == 0)
				System.err.println(" updateSintercl NO UPDATE FOR " + batch);
			else
				System.err.println(" updateSintercl UPDATE FOR " + batch);

			// con.commit();

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {
			close(statement, con);
		}

	}

	private static void updateReservationClaimInvoiceNo(Long invoiceNo) {

		Connection con = null;

		PreparedStatement statement = null;

		try {

			System.err.println(getInvHeader(invoiceNo));

			String query = "UPDATE reservation_claim SET invoice_no = " + invoiceNo + ", date_invoiced = \'"
					+ getInvHeader(invoiceNo).getInvDate() + "\'" + " WHERE resa_id = "
					+ getInvHeader(invoiceNo).getResaId() + " AND claim_currency = " + "\'"
					+ getInvHeader(invoiceNo).getCurrency() + "\'";
			con = daoFactory.getConnection();

			statement = con.prepareStatement(query);

			int status = statement.executeUpdate();

			if (status == 0)
				System.err.println(" updateReservationClaimInvoiceNo NO UPDATE FOR " + invoiceNo);
			else
				System.err.println(" updateReservationClaimInvoiceNo UPDATE FOR " + invoiceNo);

			// con.commit();

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {
			close(statement, con);
		}

	}

	private static void updateReservationClaimInvoiceNoSicoInt(Long invoiceNo) {

		Connection con = null;

		PreparedStatement statement = null;

		try {

			String query = "UPDATE reservation_claim SET inv_posted = 1, inv_exported = 1 WHERE invoice_no = "
					+ invoiceNo;

			con = daoFactory.getConnection();

			statement = con.prepareStatement(query);

			int status = statement.executeUpdate();

			if (status == 0)
				System.err.println(" updateReservationClaimInvoiceNoSicoInt NO UPDATE FOR " + invoiceNo);
			else
				System.err.println(" updateReservationClaimInvoiceNoSicoInt UPDATE FOR " + invoiceNo);

			// con.commit();

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {
			close(statement, con);
		}

	}

	private static InvHeader getInvHeader(Long invNo) {

		InvHeader invHeader = new InvHeader();

		Connection con = null;

		PreparedStatement statement = null;

		ResultSet set = null;

		try {

			String query = "SELECT inv_date,resa_id,currency,id_paidby FROM inv_header WHERE inv_no = " + invNo;

			con = daoFactory.getConnection();

			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {

				invHeader.setInvDate(set.getDate("inv_date"));
				invHeader.setResaId(set.getLong("resa_id"));
				invHeader.setCurrency(set.getString("currency"));
				invHeader.setIdPaidBy(set.getLong("id_paidby"));
			}

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			ex.printStackTrace();

		} finally {
			close(set, statement, con);
		}

		return invHeader;

	}

	private ReservationClaim map(ResultSet set) throws SQLException {

		return new ReservationClaim(set.getLong("resa_id"), set.getString("service_type"), set.getString("type"),
				set.getString("description"), set.getDate("date_effected"), set.getString("service_from"),
				set.getString("service_to"), set.getInt("nights"), set.getString("claim_type"),
				set.getInt("claim_adult_pax"), set.getDouble("claim_adult_rate"), set.getInt("claim_child_pax"),
				set.getDouble("claim_child_rate"), set.getInt("claim_teen_pax"), set.getDouble("claim_teen_rate"),
				set.getString("claim_currency"), set.getDouble("exchange_rate"), set.getDouble("taxable_claim"),
				set.getDouble("vat"), set.getDouble("claim_total_after_disc"), set.getString("name"),
				set.getString("inv_jde_code"), set.getString("inv_c_center"), set.getString("inv_subsidiary"));

	}

	private ReservationClaimPerResaIdPerClaimCurrency mapPerResaIdPerClaimCurrency(ResultSet set) throws SQLException {

		return new ReservationClaimPerResaIdPerClaimCurrency(set.getLong("resa_id"), set.getString("claim_currency"),
				set.getDouble("total"), set.getInt("paying_agency_id"));

	}

	private SicoInt mapSicoInt(ResultSet set) throws SQLException {

		return new SicoInt(set.getDate("myDate"), set.getDate("docu_date"), set.getString("serviceType"),
				set.getString("service_for"), set.getString("service_paid_by"), set.getString("resa_type"),
				set.getString("claim_type"), set.getLong("resaid"), set.getString("descr"), set.getString("clients"),
				set.getString("claimCurrency"), set.getDouble("netAmount"), set.getDouble("taxableAmt"),
				set.getDouble("vat"), set.getDouble("exchangeRate"), set.getLong("invoiceNo"),
				set.getDate("dateInvoiced"), set.getInt("active"), set.getString("sico_code"), set.getString("agency"),
				set.getString("docType"), set.getString("currType"), set.getString("cCenter"),
				set.getString("subsidiary"), set.getString("businessUnit"), set.getInt("exported"),
				set.getString("ref"));

	}

	private Sintercl mapSintercl(ResultSet set) throws SQLException {

		return new Sintercl(set.getString("source"), set.getString("company"), set.getString("journal"),
				set.getDate("transactdate"), set.getDate("paydate"), set.getInt("reference") + "",
				set.getString("auxiliary"), set.getString("description"), set.getString("user_def"),
				set.getString("user_def2"), set.getDouble("amt_rs"), set.getString("curr_code"), set.getString("curr"),
				set.getDouble("rate"), set.getDouble("amt_curr"), set.getDouble("vat_curr"), set.getDouble("vat_rs"),
				set.getString("sup_code"), set.getString("source_bat"));

	}

	private Sintercl mapSintercl2(ResultSet set) throws SQLException {

		return new Sintercl(set.getString("source"), set.getString("company"), set.getString("journal"),
				set.getDate("transactdate"), set.getDate("paydate"), set.getString("auxiliary"),
				set.getString("description"), set.getDouble("amt_rs"), set.getString("curr_code"),
				set.getDouble("amt_curr"), set.getString("vat_line"), set.getDouble("vat_curr"),
				set.getDouble("vat_rs"), set.getString("source_bat"));

	}

	private Sintercl mapSintercl3(ResultSet set) throws SQLException {

		return new Sintercl(set.getString("source"), set.getString("company"), set.getString("journal"),
				set.getDate("transactdate"), set.getDate("paydate"), set.getString("glAccount"),
				set.getString("analytical"), set.getString("description"), set.getDouble("amt_rs"),
				set.getString("curr_code"), set.getDouble("amt_curr"), set.getString("source_bat"));

	}

	private SacTransactionImport mapSacTransactionImport(ResultSet set) throws SQLException {

		return new SacTransactionImport(Integer.parseInt(set.getString("company")), set.getString("journal"),
				Long.parseLong(set.getString("batch")), Long.parseLong(set.getInt("sequence") + ""),
				set.getString("glaccount"), set.getString("auxiliary"), set.getString("analytical"),
				set.getString("supcode"), set.getDate("transactdate"), set.getDate("transactdate"),
				set.getDate("paydate"), set.getString("reference"), set.getString("description"),
				set.getString("currgroup"), set.getString("currcode"), set.getDouble("exchange_rate"),
				set.getDouble("amountrs"), set.getDouble("curramount"), set.getString("User_Def1"),
				set.getString("User_Def2"), set.getString("source"), set.getDouble("vatrs"), set.getDouble("vatcurr"),
				set.getString("vatline"), set.getDouble("vatperc"));

	}

}

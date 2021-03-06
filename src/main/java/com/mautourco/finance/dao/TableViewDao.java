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

import com.mautourco.finance.exception.DAOException;
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

	private static int numberOfRowsInsertedIntoInvHeader = 0;

	private static int numberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader = 0;

	private static int numberOfReservationRowsUpdated = 0;

	private static int numberOfRowsInsertedIntoSicoInt = 0;

	private static int numberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt = 0;

	private static int numberOfRowsInsertedIntoSintercl = 0;

	private static int numberOfRowsInsertedIntoSintercl2 = 0;

	private static int numberOfRowsInsertedIntoSintercl3 = 0;

	private static int numberOfSicoIntRowsUpdated = 0;

	private static int numberOfRowsInsertedIntoSacTransactionImport = 0;

	private static int numberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport = 0;

	public static int getNumberOfRowsInsertedIntoInvHeader() {
		return numberOfRowsInsertedIntoInvHeader;
	}

	public static int getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader() {
		return numberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader;
	}

	public static int getNumberOfReservationRowsUpdated() {
		return numberOfReservationRowsUpdated;
	}

	public static int getNumberOfRowsInsertedIntoSicoInt() {
		return numberOfRowsInsertedIntoSicoInt;
	}

	public static int getNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt() {
		return numberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt;
	}

	public static int getNumberOfRowsInsertedIntoSintercl() {
		return numberOfRowsInsertedIntoSintercl;
	}

	public static int getNumberOfRowsInsertedIntoSintercl2() {
		return numberOfRowsInsertedIntoSintercl2;
	}

	public static int getNumberOfRowsInsertedIntoSintercl3() {
		return numberOfRowsInsertedIntoSintercl3;
	}

	public static int getNumberOfSicoIntRowsUpdated() {
		return numberOfSicoIntRowsUpdated;
	}

	public static int getNumberOfRowsInsertedIntoSacTransactionImport() {
		return numberOfRowsInsertedIntoSacTransactionImport;
	}

	public static int getNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport() {
		return numberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport;
	}

	public static void setNumberOfRowsInsertedIntoInvHeader(int numberOfRowsInsertedIntoInvHeader) {
		TableViewDao.numberOfRowsInsertedIntoInvHeader = numberOfRowsInsertedIntoInvHeader;
	}

	public static void setNumberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader(
			int numberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader) {
		TableViewDao.numberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader = numberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader;
	}

	public static void setNumberOfReservationRowsUpdated(int numberOfReservationRowsUpdated) {
		TableViewDao.numberOfReservationRowsUpdated = numberOfReservationRowsUpdated;
	}

	public static void setNumberOfRowsInsertedIntoSicoInt(int numberOfRowsInsertedIntoSicoInt) {
		TableViewDao.numberOfRowsInsertedIntoSicoInt = numberOfRowsInsertedIntoSicoInt;
	}

	public static void setNumberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt(
			int numberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt) {
		TableViewDao.numberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt = numberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt;
	}

	public static void setNumberOfRowsInsertedIntoSintercl(int numberOfRowsInsertedIntoSintercl) {
		TableViewDao.numberOfRowsInsertedIntoSintercl = numberOfRowsInsertedIntoSintercl;
	}

	public static void setNumberOfRowsInsertedIntoSintercl2(int numberOfRowsInsertedIntoSintercl2) {
		TableViewDao.numberOfRowsInsertedIntoSintercl2 = numberOfRowsInsertedIntoSintercl2;
	}

	public static void setNumberOfRowsInsertedIntoSintercl3(int numberOfRowsInsertedIntoSintercl3) {
		TableViewDao.numberOfRowsInsertedIntoSintercl3 = numberOfRowsInsertedIntoSintercl3;
	}

	public static void setNumberOfSicoIntRowsUpdated(int numberOfSicoIntRowsUpdated) {
		TableViewDao.numberOfSicoIntRowsUpdated = numberOfSicoIntRowsUpdated;
	}

	public static void setNumberOfRowsInsertedIntoSacTransactionImport(
			int numberOfRowsInsertedIntoSacTransactionImport) {
		TableViewDao.numberOfRowsInsertedIntoSacTransactionImport = numberOfRowsInsertedIntoSacTransactionImport;
	}

	public static void setNumberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport(
			int numberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport) {
		TableViewDao.numberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport = numberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport;
	}

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

			String noNullValues = " AND reservation_claim.claim_total_after_disc IS NOT NULL "
					+ "AND reservation_claim.taxable_claim IS NOT NULL " + "AND reservation_claim.resa_id IS NOT NULL "
					+ "AND reservation_claim.service_type IS NOT NULL " + "AND reservation_claim.type IS NOT NULL "
					+ "AND reservation_claim.description IS NOT NULL "
					+ "AND reservation_claim.date_effected IS NOT NULL "
					+ "AND reservation_claim.service_from IS NOT NULL "
					+ "AND reservation_claim.service_to IS NOT NULL " + "AND reservation_claim.nights IS NOT NULL "
					+ "AND reservation_claim.claim_type IS NOT NULL "
					+ "AND reservation_claim.claim_adult_pax IS NOT NULL "
					+ "AND reservation_claim.claim_teen_pax IS NOT NULL "
					+ "AND reservation_claim.claim_child_pax IS NOT NULL "
					+ "AND reservation_claim.claim_adult_rate IS NOT NULL "
					+ "AND reservation_claim.claim_teen_rate IS NOT NULL "
					+ "AND reservation_claim.claim_child_rate IS NOT NULL "
					+ "AND reservation_claim.claim_currency IS NOT NULL "
					+ "AND reservation_claim.exchange_rate IS NOT NULL "
					+ "AND reservation_claim.inv_jde_code IS NOT NULL "
					+ "AND reservation_claim.inv_subsidiary IS NOT NULL "
					+ "AND reservation_claim.inv_c_center IS NOT NULL";

			StringBuilder builderFilter = new StringBuilder();

			String serviceFilterSql = serviceFilter.equals("")
					? " AND (reservation_claim.service_type LIKE \'%" + serviceFilter
							+ "%\' OR reservation_claim.service_type IS NULL)"
					: " AND (reservation_claim.service_type LIKE \'%" + serviceFilter + "%\')";

			String typeFilterSql = typeFilter.equals("")
					? " AND (reservation_claim.type LIKE \'%" + typeFilter + "%\' OR reservation_claim.type IS NULL)"
					: " AND (reservation_claim.type LIKE \'%" + typeFilter + "%\')";

			String claimDescFilterSql = claimDescFilter.equals("")
					? " AND (reservation_claim.description LIKE \'%" + claimDescFilter
							+ "%\' OR reservation_claim.description IS NULL)"
					: " AND (reservation_claim.description LIKE \'%" + claimDescFilter + "%\')";

			String fromFilterSql = fromFilter.equals("")
					? " AND (reservation_claim.service_from LIKE \'%" + fromFilter
							+ "%\' OR reservation_claim.service_from IS NULL)"
					: " AND (reservation_claim.service_from LIKE \'%" + fromFilter + "%\')";

			String toFilterSql = toFilter.equals("")
					? " AND (reservation_claim.service_to LIKE \'%" + toFilter
							+ "%\' OR reservation_claim.service_to IS NULL)"
					: " AND (reservation_claim.service_to LIKE \'%" + toFilter + "%\')";

			String payingAgencyFilterSql = payingAgencyFilter.equals("")
					? " AND (agency.name LIKE \'%" + payingAgencyFilter + "%\' OR agency.name IS NULL)"
					: " AND (agency.name LIKE \'%" + payingAgencyFilter + "%\')";

			String sicoraxCodeFilterSql = sicoraxCodeFilter.equals("")
					? " AND (reservation_claim.inv_jde_code LIKE \'%" + sicoraxCodeFilter
							+ "%\' OR reservation_claim.inv_jde_code IS NULL)"
					: " AND (reservation_claim.inv_jde_code LIKE \'%" + sicoraxCodeFilter + "%\')";

			String auxiliaryFilterSql = auxiliaryFilter.equals("")
					? " AND (reservation_claim.inv_c_center LIKE \'%" + auxiliaryFilter
							+ "%\' OR reservation_claim.inv_c_center IS NULL)"
					: " AND (reservation_claim.inv_c_center LIKE \'%" + auxiliaryFilter + "%\')";

			String subsidiaryFilterSql = subsidiaryFilter.equals("")
					? " AND (reservation_claim.inv_subsidiary LIKE \'%" + subsidiaryFilter
							+ "%\' OR reservation_claim.inv_subsidiary IS NULL)"
					: " AND (reservation_claim.inv_subsidiary LIKE \'%" + subsidiaryFilter + "%\')";

			String currFilterSql = currFilter.equals("")
					? " AND (reservation_claim.claim_currency LIKE \'%" + currFilter
							+ "%\' OR reservation_claim.claim_currency IS NULL)"
					: " AND (reservation_claim.claim_currency LIKE \'%" + currFilter + "%\')";

			builderFilter.append(serviceFilterSql).append(typeFilterSql).append(claimDescFilterSql)
					.append(fromFilterSql).append(toFilterSql).append(payingAgencyFilterSql)
					.append(sicoraxCodeFilterSql).append(auxiliaryFilterSql).append(subsidiaryFilterSql)
					.append(currFilterSql);

			String filter = builderFilter.toString();

			String query = "";

			String queryAll = "";

			if (containsNullValues) {

				query = "SELECT reservation_claim.resa_id, reservation_claim.service_type, reservation_claim.type, reservation_claim.description, "
						+ "reservation_claim.date_effected, reservation_claim.service_from, reservation_claim.service_to, reservation_claim.nights, reservation_claim.claim_type,"
						+ " reservation_claim.claim_adult_pax, reservation_claim.claim_teen_pax, reservation_claim.claim_child_pax,"
						+ " reservation_claim.claim_adult_rate, reservation_claim.claim_teen_rate, reservation_claim.claim_child_rate, reservation_claim.claim_currency,"
						+ " reservation_claim.exchange_rate, reservation_claim.taxable_claim, (reservation_claim.claim_total_after_disc - reservation_claim.taxable_claim) AS vat,"
						+ " reservation_claim.claim_total_after_disc, agency.name,"
						+ " reservation_claim.inv_jde_code, reservation_claim.inv_subsidiary, reservation_claim.inv_c_center"
						+ " FROM reservation_claim, agency WHERE reservation_claim.date_effected BETWEEN " + "\'"
						+ dateFrom + "\'" + " AND " + "\'" + dateTo + "\'"
						+ " AND reservation_claim.active = 1 AND agency.active = 1 AND reservation_claim.paying_agency_id = agency.id_agency AND booking_agency_id = "
						+ idAgency + filter;

				queryAll = "SELECT reservation_claim.resa_id, reservation_claim.service_type, reservation_claim.type, reservation_claim.description, "
						+ "reservation_claim.date_effected, reservation_claim.service_from, reservation_claim.service_to, reservation_claim.nights, reservation_claim.claim_type,"
						+ " reservation_claim.claim_adult_pax, reservation_claim.claim_teen_pax, reservation_claim.claim_child_pax,"
						+ " reservation_claim.claim_adult_rate, reservation_claim.claim_teen_rate, reservation_claim.claim_child_rate, reservation_claim.claim_currency,"
						+ " reservation_claim.exchange_rate, reservation_claim.taxable_claim, (reservation_claim.claim_total_after_disc - reservation_claim.taxable_claim) AS vat,"
						+ " reservation_claim.claim_total_after_disc, agency.name,"
						+ " reservation_claim.inv_jde_code, reservation_claim.inv_subsidiary, reservation_claim.inv_c_center"
						+ " FROM reservation_claim, agency WHERE reservation_claim.active = 1 AND agency.active = 1 AND reservation_claim.paying_agency_id = agency.id_agency"
						+ filter;

			} else {

				query = "SELECT reservation_claim.resa_id, reservation_claim.service_type, reservation_claim.type, reservation_claim.description, "
						+ "reservation_claim.date_effected, reservation_claim.service_from, reservation_claim.service_to, reservation_claim.nights, reservation_claim.claim_type,"
						+ " reservation_claim.claim_adult_pax, reservation_claim.claim_teen_pax, reservation_claim.claim_child_pax,"
						+ " reservation_claim.claim_adult_rate, reservation_claim.claim_teen_rate, reservation_claim.claim_child_rate, reservation_claim.claim_currency,"
						+ " reservation_claim.exchange_rate, reservation_claim.taxable_claim, (reservation_claim.claim_total_after_disc - reservation_claim.taxable_claim) AS vat,"
						+ " reservation_claim.claim_total_after_disc, agency.name,"
						+ " reservation_claim.inv_jde_code, reservation_claim.inv_subsidiary, reservation_claim.inv_c_center"
						+ " FROM reservation_claim, agency WHERE reservation_claim.date_effected BETWEEN " + "\'"
						+ dateFrom + "\'" + " AND " + "\'" + dateTo + "\'"
						+ " AND reservation_claim.active = 1 AND agency.active = 1 AND reservation_claim.paying_agency_id = agency.id_agency AND booking_agency_id = "
						+ idAgency + noNullValues + filter;

				queryAll = "SELECT reservation_claim.resa_id, reservation_claim.service_type, reservation_claim.type, reservation_claim.description, "
						+ "reservation_claim.date_effected, reservation_claim.service_from, reservation_claim.service_to, reservation_claim.nights, reservation_claim.claim_type,"
						+ " reservation_claim.claim_adult_pax, reservation_claim.claim_teen_pax, reservation_claim.claim_child_pax,"
						+ " reservation_claim.claim_adult_rate, reservation_claim.claim_teen_rate, reservation_claim.claim_child_rate, reservation_claim.claim_currency,"
						+ " reservation_claim.exchange_rate, reservation_claim.taxable_claim, (reservation_claim.claim_total_after_disc - reservation_claim.taxable_claim) AS vat,"
						+ " reservation_claim.claim_total_after_disc, agency.name,"
						+ " reservation_claim.inv_jde_code, reservation_claim.inv_subsidiary, reservation_claim.inv_c_center"
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
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

		} finally {

			close(set, statement, con);

		}

		return reservationClaims;

	}

	public static void updateCloseFromZeroToOne(Long resaId) {

		Connection con = null;
		PreparedStatement statement = null;

		try {

			con = daoFactory.getConnection();

			String query = "UPDATE reservation SET close = 1, resa_closed_on = CURRENT_DATE WHERE close = 0 AND active = 1 AND resa_id = "
					+ resaId;

			statement = con.prepareStatement(query);

			if (!statement.execute()) {

				int updatedRows = statement.getUpdateCount();
				if (updatedRows == 0) {

					System.err.println("updateCloseFromZeroToOne NO UPDATE FOR " + resaId);

				} else {

					System.err.println("updateCloseFromZeroToOne UPDATE FOR " + resaId);

				}

				TableViewDao.numberOfReservationRowsUpdated += updatedRows;

			}

			con.commit();

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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

			con.commit();

			if (status == 0) {

				throw new DAOException("No row added to inv_header table");

			}

			autoGeneratedValues = preparedStatement.getGeneratedKeys();

			if (autoGeneratedValues.next()) {

				TableViewDao.numberOfRowsInsertedIntoInvHeader++;

				updateReservationClaimInvoiceNo(autoGeneratedValues.getLong(1));
			} else {

				throw new DAOException("Invoice creation failed, no auto-generated value");

			}

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(e);

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
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

		} finally {

			close(set, statement, con);

		}

		return list;

	}

	public static void insertIntoSico(SicoInt s) {

		Connection con = null;
		PreparedStatement preparedStatement = null;

		try {

			con = daoFactory.getConnection();
			preparedStatement = initializePreparedStatement(con, SQL_INSERT_SICO_INT, false, s.getMyDate(),
					s.getDocuDate(), s.getServiceType(), s.getServiceFor(), s.getResaId(), s.getDescr(), s.getClients(),
					s.getClaimCurrency(), s.getNetAmount(), s.getTaxableAmt(), s.getVat(), s.getExchangeRate(),
					s.getInvoiceNo(), s.getDateInvoiced(), s.getSicoCode(), s.getAgency(), s.getcCenter(),
					s.getBusinessUnit(), s.getRef(), s.getSubsidiary(), s.getActive(), s.getExported(),
					s.getServicePaidBy(), s.getResaType(), s.getClaimType());

			int status = preparedStatement.executeUpdate();

			con.commit();

			if (status == 0) {

				throw new DAOException("No row added to sico_int table");

			} else {

				TableViewDao.numberOfRowsInsertedIntoSicoInt++;

				updateReservationClaimInvoiceNoSicoInt(s.getInvoiceNo());

			}

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(e);

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

			con.commit();

			if (status == 0)

				throw new DAOException("No row added to sintercl");

			else
				TableViewDao.numberOfRowsInsertedIntoSintercl++;

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(e);

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

			con.commit();

			if (status == 0)

				throw new DAOException("No row added to sintercl");

			else
				TableViewDao.numberOfRowsInsertedIntoSintercl2++;

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(e);

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

			con.commit();

			if (status == 0)

				throw new DAOException("No row added to sintercl");

			else
				TableViewDao.numberOfRowsInsertedIntoSintercl3++;

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(e);

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

			con.commit();

			if (status == 0) {

				throw new DAOException("No row added to sac_transaction_import");

			}

			autoGeneratedValues = preparedStatement.getGeneratedKeys();

			if (autoGeneratedValues.next()) {

				TableViewDao.numberOfRowsInsertedIntoSacTransactionImport++;

				updateSintercl(s.getBatchNo().toString());
			} else {

				throw new DAOException("Inserting into sac_transaction_import failed, no auto-generated value");

			}

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(e);

		} finally {
			close(autoGeneratedValues, preparedStatement, con);

		}

	}

	public void updateSicoInt() {

		Connection con = null;

		PreparedStatement statementUpdate = null;

		try {

			String queryUpdate = "UPDATE sico_int SET post = 1 , date_exp = CURRENT_DATE  WHERE post = 0 AND journal = \'ID\' AND source = \'MT\'";

			con = daoFactory.getConnection();

			statementUpdate = con.prepareStatement(queryUpdate);

			if (!statementUpdate.execute()) {
				TableViewDao.numberOfSicoIntRowsUpdated = statementUpdate.getUpdateCount();
			}

			con.commit();

			System.err.println(" sico_int updated rows : " + TableViewDao.numberOfSicoIntRowsUpdated);

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

		} finally {
			close(statementUpdate, con);
		}

	}

	private static void updateSintercl(String batch) {

		Connection con = null;

		PreparedStatement statement = null;

		try {

			String query = "UPDATE sintercl SET posted = 1 , date_exp = CURRENT_DATE  WHERE batch = \'" + batch + "\'";

			con = daoFactory.getConnection();
			statement = con.prepareStatement(query);

			if (!statement.execute()) {

				int updatedRows = statement.getUpdateCount();

				if (updatedRows == 0) {

					System.err.println(" updateSintercl NO UPDATE FOR " + batch);

				} else {

					System.err.println(" updateSintercl UPDATE FOR " + batch);

				}
				TableViewDao.numberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport += updatedRows;
			}

			con.commit();

			System.err.println(" sintercl updated rows : "
					+ TableViewDao.numberOfSinterclRowsUpdatedAfterInsertingIntoSacTransactionImport);

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

		} finally {
			close(statement, con);
		}

	}

	private static void updateReservationClaimInvoiceNo(Long invoiceNo) {

		Connection con = null;

		PreparedStatement statement = null;

		try {

			con = daoFactory.getConnection();

			// System.err.println(getInvHeader(invoiceNo));

			String query = "UPDATE reservation_claim SET invoice_no = " + invoiceNo + ", date_invoiced = \'"
					+ getInvHeader(invoiceNo).getInvDate() + "\'" + " WHERE resa_id = "
					+ getInvHeader(invoiceNo).getResaId() + " AND claim_currency = " + "\'"
					+ getInvHeader(invoiceNo).getCurrency() + "\'";

			statement = con.prepareStatement(query);

			if (!statement.execute()) {

				int updatedRows = statement.getUpdateCount();

				if (updatedRows == 0) {

					System.err.println(" updateReservationClaimInvoiceNo NO UPDATE FOR " + invoiceNo);

				} else {

					System.err.println(" updateReservationClaimInvoiceNo UPDATE FOR " + invoiceNo);

				}

				TableViewDao.numberOfReservationClaimRowsUpdatedAfterInsertingIntoInvHeader += updatedRows;

			}

			con.commit();

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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

			if (!statement.execute()) {

				int updatedRows = statement.getUpdateCount();

				if (updatedRows == 0) {

					System.err.println(" updateReservationClaimInvoiceNoSicoInt NO UPDATE FOR " + invoiceNo);

				} else {

					System.err.println(" updateReservationClaimInvoiceNoSicoInt UPDATE FOR " + invoiceNo);

				}
				TableViewDao.numberOfReservationClaimRowsUpdatedAfterInsertingIntoSicoInt += updatedRows;

			}

			con.commit();

		} catch (SQLException ex) {

			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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
				throw new DAOException(e1);
			}

			throw new DAOException(ex);

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

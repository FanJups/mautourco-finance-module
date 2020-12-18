package com.mautourco.finance.dao;

import static com.mautourco.finance.dao.DAOUtils.initializePreparedStatement;

import java.io.IOException;
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

public class TableViewDao {

	private DAOFactory daoFactory = DAOFactory.getInstance();

	private static final String SQL_INSERT_INV_HEADER = "INSERT INTO inv_header (resa_id,id_paidby,user_id,net_amt,currency,inv_date) VALUES(?,?,?,?,?,CURRENT_DATE);";

	public List<ReservationClaim> getData(LocalDate dateFrom, LocalDate dateTo, int idAgency, String serviceFilter,
			String typeFilter, String claimDescFilter, String fromFilter, String toFilter, String payingAgencyFilter,
			String sicoraxCodeFilter, String auxiliaryFilter, String subsidiaryFilter, String currFilter,
			boolean containsNullValues) {

		List<ReservationClaim> reservationClaims = new ArrayList<>();

		try {

			Connection con = daoFactory.getConnection();

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

			PreparedStatement statement = con.prepareStatement(query);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				reservationClaims.add(map(set));
			}

			statement.close();
			set.close();

			return reservationClaims;

		} catch (SQLException | IOException ex) {

			ex.printStackTrace();

			return null;
		}

	}

	public void updateCloseFromZeroToOne(Long resaId) {

		try {

			Connection con = daoFactory.getConnection();

			String query = "UPDATE reservation SET close = 1, resa_closed_on = CURRENT_DATE WHERE close = 0 AND active = 1 AND resa_id = "
					+ resaId;

			PreparedStatement statement = con.prepareStatement(query);

			int status = statement.executeUpdate();

			if (status == 0)
				System.err.println("updateCloseFromZeroToOne NO UPDATE FOR " + resaId);
			else
				System.err.println("updateCloseFromZeroToOne UPDATE FOR " + resaId);

			statement.close();

		} catch (SQLException | IOException ex) {

			ex.printStackTrace();

		}

	}

	public void generateInvoiceNo(ReservationClaimPerResaIdPerClaimCurrency r) throws SQLException {

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

		} catch (SQLException | IOException e) {

			e.printStackTrace();

		} finally {
			autoGeneratedValues.close();
			preparedStatement.close();
			con.close();

		}

	}

	public List<ReservationClaimPerResaIdPerClaimCurrency> getData() {

		List<ReservationClaimPerResaIdPerClaimCurrency> reservationClaimsPerResaIdPerClaimCurrency = new ArrayList<>();

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

			Connection con = daoFactory.getConnection();

			String query = "SELECT resa_id, claim_currency , SUM(claim_total_after_disc) AS total, paying_agency_id FROM reservation_claim WHERE "
					+ noNullValues + " GROUP BY resa_id,claim_currency";

			PreparedStatement statement = con.prepareStatement(query);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				reservationClaimsPerResaIdPerClaimCurrency.add(mapPerResaIdPerClaimCurrency(set));
			}

			statement.close();
			set.close();

			System.err.println(" reservationClaimsPerResaIdPerClaimCurrency size "
					+ reservationClaimsPerResaIdPerClaimCurrency.size());

			return reservationClaimsPerResaIdPerClaimCurrency;

		} catch (SQLException | IOException ex) {

			ex.printStackTrace();

			return null;
		}

	}

	private void updateReservationClaimInvoiceNo(Long invoiceNo) {

		try {

			Connection con = daoFactory.getConnection();

			String query = "UPDATE reservation_claim SET invoice_no = " + invoiceNo + ", date_invoiced = \'"
					+ getInvHeader(invoiceNo).getInvDate() + "\'" + " WHERE resa_id = "
					+ getInvHeader(invoiceNo).getResaId() + " AND claim_currency = " + "\'"
					+ getInvHeader(invoiceNo).getCurrency() + "\'";

			PreparedStatement statement = con.prepareStatement(query);

			int status = statement.executeUpdate();

			if (status == 0)
				System.err.println(" updateReservationClaimInvoiceNoNO UPDATE FOR " + invoiceNo);
			else
				System.err.println(" updateReservationClaimInvoiceNo UPDATE FOR " + invoiceNo);

			statement.close();

		} catch (SQLException | IOException ex) {

			ex.printStackTrace();

		}

	}

	private InvHeader getInvHeader(Long invNo) {

		InvHeader invHeader = null;

		try {

			Connection con = daoFactory.getConnection();

			String query = "SELECT inv_date,resa_id,currency,id_paidby FROM inv_header WHERE inv_no = " + invNo;

			PreparedStatement statement = con.prepareStatement(query);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				invHeader = new InvHeader(set.getDate("inv_date"), set.getLong("resa_id"), set.getString("currency"),
						set.getLong("id_paidby"));
			}

			statement.close();
			set.close();

			return invHeader;

		} catch (SQLException | IOException ex) {

			ex.printStackTrace();

			return null;
		}

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

}

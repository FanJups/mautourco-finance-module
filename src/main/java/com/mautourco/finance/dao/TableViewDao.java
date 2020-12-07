package com.mautourco.finance.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mautourco.finance.model.ReservationClaim;

public class TableViewDao {

	private DaoFactory daoFactory = DaoFactory.getInstance();

	/*
	 * String fields : Service/Type/Claim Desc/From/To/Paying Agency/Sicorax
	 * Code/Auxiliary/Subsidiary
	 * 
	 */

	public List<ReservationClaim> getData(LocalDate dateFrom, LocalDate dateTo, int idAgency, String serviceFilter,
			String typeFilter, String claimDescFilter, String fromFilter, String toFilter, String payingAgencyFilter,
			String sicoraxCodeFilter, String auxiliaryFilter, String subsidiaryFilter) {

		List<ReservationClaim> reservationClaims = new ArrayList<>();

		try {

			Connection con = daoFactory.getConnection();
			String query = "SELECT service_id, resa_id, service_type, type, description, date_effected,"
					+ " service_from, service_to, paying_agency, inv_jde_code, taxable_claim, "
					+ "(claim_total_after_disc - taxable_claim) AS vat, inv_c_center, claim_total_after_disc, inv_subsidiary "
					+ "FROM reservation_claim WHERE date_effected BETWEEN " + "\'" + dateFrom + "\'" + " AND " + "\'"
					+ dateTo + "\'" + " AND active =1 AND paying_agency_id =" + idAgency + " AND (service_type LIKE \'%"
					+ serviceFilter + "%\' OR service_type IS NULL)" + " AND (type LIKE \'%" + typeFilter
					+ "%\' OR type IS NULL)" + " AND (description LIKE \'%" + claimDescFilter
					+ "%\' OR description IS NULL)" + " AND (service_from LIKE \'%" + fromFilter
					+ "%\' OR service_from IS NULL)" + " AND (service_to LIKE \'%" + toFilter
					+ "%\' OR service_to IS NULL)" + " AND (paying_agency LIKE \'%" + payingAgencyFilter
					+ "%\' OR paying_agency IS NULL)" + " AND (inv_jde_code LIKE \'%" + sicoraxCodeFilter
					+ "%\' OR inv_jde_code IS NULL)" + " AND (inv_c_center LIKE \'%" + auxiliaryFilter
					+ "%\' OR inv_c_center IS NULL)" + " AND (inv_subsidiary LIKE \'%" + subsidiaryFilter
					+ "%\' OR inv_subsidiary IS NULL)";

			String queryAll = "SELECT service_id, resa_id, service_type, type, description, date_effected,"
					+ " service_from, service_to, paying_agency, inv_jde_code, taxable_claim, "
					+ "(claim_total_after_disc - taxable_claim) AS vat, inv_c_center, claim_total_after_disc, inv_subsidiary "
					+ "FROM reservation_claim WHERE  (service_type LIKE \'%" + serviceFilter
					+ "%\' OR service_type IS NULL)" + " AND (type LIKE \'%" + typeFilter + "%\' OR type IS NULL)"
					+ " AND (description LIKE \'%" + claimDescFilter + "%\' OR description IS NULL)"
					+ " AND (service_from LIKE \'%" + fromFilter + "%\' OR service_from IS NULL)"
					+ " AND (service_to LIKE \'%" + toFilter + "%\' OR service_to IS NULL)"
					+ " AND (paying_agency LIKE \'%" + payingAgencyFilter + "%\' OR paying_agency IS NULL)"
					+ " AND (inv_jde_code LIKE \'%" + sicoraxCodeFilter + "%\' OR inv_jde_code IS NULL)"
					+ " AND (inv_c_center LIKE \'%" + auxiliaryFilter + "%\' OR inv_c_center IS NULL)"
					+ " AND (inv_subsidiary LIKE \'%" + subsidiaryFilter + "%\' OR inv_subsidiary IS NULL)";

			PreparedStatement statement = con.prepareStatement(query);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				reservationClaims.add(map(set));
			}

			statement.close();
			set.close();

			// Return the List
			return reservationClaims;

		} catch (SQLException | IOException ex) {

			ex.printStackTrace();

			return null;
		}

	}

	private ReservationClaim map(ResultSet set) throws SQLException {

		return new ReservationClaim(set.getLong("service_id"), set.getLong("resa_id"), set.getString("service_type"),
				set.getString("type"), set.getString("description"), set.getDate("date_effected"),
				set.getString("service_from"), set.getString("service_to"), set.getString("paying_agency"),
				set.getString("inv_jde_code"), set.getDouble("taxable_claim"), set.getDouble("claim_total_after_disc"),
				set.getDouble("vat"), set.getString("inv_c_center"), set.getString("inv_subsidiary"));

	}

}

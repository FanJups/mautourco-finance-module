package com.mautourco.finance.dao;

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

	public List<ReservationClaim> getData(LocalDate dateFrom, LocalDate dateTo, int idAgency) {

		List<ReservationClaim> reservationClaims = new ArrayList<>();

		try {

			Connection con = daoFactory.getConnection();
			String query = " SELECT service_id, resa_id, service_type, TYPE, description, date_effected,"
					+ " service_from, service_to, paying_agency, inv_jde_code, taxable_claim, "
					+ "(claim_total_after_disc - taxable_claim) AS vat, inv_c_center, claim_total_after_disc, inv_subsidiary "
					+ "FROM reservation_claim WHERE date_effected BETWEEN " + "\'" + dateFrom + "\'" + " AND " + "\'"
					+ dateTo + "\'" + " AND active =1 AND paying_agency_id =" + idAgency;

			String queryTest = " SELECT service_id, resa_id, service_type, TYPE, description, date_effected,"
					+ " service_from, service_to, paying_agency, inv_jde_code, taxable_claim, "
					+ "(claim_total_after_disc - taxable_claim) AS vat, inv_c_center, claim_total_after_disc, inv_subsidiary "
					+ "FROM reservation_claim WHERE paying_agency_id =" + 130;

			PreparedStatement statement = con.prepareStatement(query);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				reservationClaims.add(map(set));
			}

			statement.close();
			set.close();

			// Return the List
			return reservationClaims;

		} catch (SQLException ex) {

			ex.printStackTrace();

			return null;
		}

	}

	private ReservationClaim map(ResultSet set) throws SQLException {

		ReservationClaim reservationClaim = new ReservationClaim(set.getLong("service_id"), set.getLong("resa_id"),
				set.getString("service_type"), set.getString("type"), set.getString("description"),
				set.getDate("date_effected"), set.getString("service_from"), set.getString("service_to"),
				set.getString("paying_agency"), set.getString("inv_jde_code"), set.getDouble("taxable_claim"),
				set.getDouble("claim_total_after_disc"), set.getDouble("vat"), set.getString("inv_c_center"),
				set.getString("inv_subsidiary"));

		return reservationClaim;

	}

}

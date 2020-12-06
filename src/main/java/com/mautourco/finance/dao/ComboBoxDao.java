package com.mautourco.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mautourco.finance.model.ComboBoxItem;

public class ComboBoxDao {

	private DaoFactory daoFactory = DaoFactory.getInstance();

	public List<ComboBoxItem> getData() {

		List<ComboBoxItem> options = new ArrayList<>();

		try {

			Connection con = daoFactory.getConnection();
			String query = "select id_agency, name from agency where active = 1 AND id_agency != 5172 order by name";
			PreparedStatement statement = con.prepareStatement(query);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				options.add(map(set));
			}

			statement.close();
			set.close();

			// Return the List
			return options;

		} catch (SQLException ex) {

			ex.printStackTrace();

			return null;
		}
	}

	private ComboBoxItem map(ResultSet set) throws SQLException {

		ComboBoxItem comboBoxItem = new ComboBoxItem();

		comboBoxItem.setIdAgency(set.getInt("id_agency"));

		comboBoxItem.setName(set.getString("name"));

		return comboBoxItem;

	}

}

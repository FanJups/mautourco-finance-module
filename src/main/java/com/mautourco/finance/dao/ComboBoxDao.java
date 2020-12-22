package com.mautourco.finance.dao;

import static com.mautourco.finance.dao.DAOUtils.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mautourco.finance.model.ComboBoxItem;

public class ComboBoxDao {

	private DAOFactory daoFactory = DAOFactory.getInstance();

	public List<ComboBoxItem> getData() {

		List<ComboBoxItem> options = new ArrayList<>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {

			con = daoFactory.getConnection();
			String query = "select id_agency, name from agency where active = 1 order by name";
			statement = con.prepareStatement(query);

			set = statement.executeQuery();

			while (set.next()) {
				options.add(map(set));
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

		return options;
	}

	private ComboBoxItem map(ResultSet set) throws SQLException {

		return new ComboBoxItem(set.getInt("id_agency"), set.getString("name"));

	}

}

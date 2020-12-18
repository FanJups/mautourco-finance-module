package com.mautourco.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtils {

	public static PreparedStatement initializePreparedStatement(Connection connexion, String sql,
			boolean returnGeneratedKeys, Object... objets) throws SQLException {

		PreparedStatement preparedStatement = connexion.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

		for (int i = 0; i < objets.length; i++) {
			preparedStatement.setObject(i + 1, objets[i]);
		}
		return preparedStatement;
	}

}

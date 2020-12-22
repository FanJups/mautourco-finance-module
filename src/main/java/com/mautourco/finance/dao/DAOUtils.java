package com.mautourco.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	/* Fermeture silencieuse du resultset */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();

			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}

	/* Fermeture silencieuse du statement */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();

			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

	/* Fermeture silencieuse de la connexion */

	public static void close(Connection connexion) {
		if (connexion != null) {
			try {
				connexion.close();

			} catch (SQLException e) {
				System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	/* Fermetures silencieuses du statement et de la connexion */

	public static void close(Statement statement, Connection connexion) {
		close(statement);
		close(connexion);
	}

	/* Fermetures silencieuses du resultset,du statement et de la connexion */
	public static void close(ResultSet resultset, Statement statement, Connection connexion) {
		close(resultset);
		close(statement);
		close(connexion);
	}

}

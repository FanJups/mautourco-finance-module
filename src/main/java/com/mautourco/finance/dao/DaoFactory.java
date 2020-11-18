package com.mautourco.finance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {

	private static DaoFactory instance = new DaoFactory();

	private DaoFactory() {
	}

	public static DaoFactory getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException {

		Connection con = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/asterix?useTimezone=true&serverTimezone=UTC", "root", "");

		return con;

	}

}

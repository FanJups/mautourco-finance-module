package com.mautourco.finance.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mautourco.finance.properties.PropertiesReader;

public class DaoFactory {

	private static DaoFactory instance = new DaoFactory();

	private DaoFactory() {
	}

	public static DaoFactory getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException, IOException {

		PropertiesReader propertiesReader = new PropertiesReader("application.properties");
		String url = propertiesReader.getProperty("datasource.url");
		String username = propertiesReader.getProperty("datasource.username");
		String password = propertiesReader.getProperty("datasource.password");

		Connection con = DriverManager.getConnection(url, username, password);

		return con;

	}

}

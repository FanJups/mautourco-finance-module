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

		PropertiesReader propertiesReaderAppProfile;
		String url;
		String username;
		String password;

		String profile = new PropertiesReader("application.properties").getProperty("profile");

		String appFile = "application-" + profile + ".properties";

		propertiesReaderAppProfile = new PropertiesReader(appFile);

		url = propertiesReaderAppProfile.getProperty("datasource.url");
		username = propertiesReaderAppProfile.getProperty("datasource.username");
		password = propertiesReaderAppProfile.getProperty("datasource.password");

		return DriverManager.getConnection(url, username, password);

	}

}

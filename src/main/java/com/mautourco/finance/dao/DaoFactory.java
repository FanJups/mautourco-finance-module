package com.mautourco.finance.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mautourco.finance.properties.PropertiesReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DAOFactory {

	// java.lang.module.FindException: Module metrics.healthchecks not found,
	// required by com.zaxxer.hikari

	private static HikariDataSource dataSource = null;

	private static int numberOfConnections = 0;

	private DAOFactory(HikariDataSource dataSource) {

		this.dataSource = dataSource;
	}

	public static DAOFactory getInstance() {

		try {

			PropertiesReader propertiesReaderAppProfile;
			String url;
			String username;
			String password;

			String profile;

			profile = new PropertiesReader("application.properties").getProperty("profile").trim();

			String appFile = "application-" + profile + ".properties";

			propertiesReaderAppProfile = new PropertiesReader(appFile);

			url = propertiesReaderAppProfile.getProperty("datasource.url").trim();
			username = propertiesReaderAppProfile.getProperty("datasource.username").trim();
			password = propertiesReaderAppProfile.getProperty("datasource.password").trim();

			HikariConfig config = new HikariConfig();

			config.setJdbcUrl(url);
			config.setUsername(username);
			config.setPassword(password);
			config.setAutoCommit(false);
			config.setMinimumIdle(100);
			config.setMaximumPoolSize(1000);
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

			dataSource = new HikariDataSource(config);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DAOFactory instance = new DAOFactory(dataSource);
		return instance;

	}

	Connection getConnection() throws SQLException {

		DAOFactory.numberOfConnections++;
		System.err.println("Connections : " + DAOFactory.numberOfConnections);
		return dataSource.getConnection();
	}

}

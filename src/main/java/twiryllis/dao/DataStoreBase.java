package twiryllis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twiryllis.common.Constant;

/**
 * Base class that holds information for connecting to the database
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public abstract class DataStoreBase {

	protected static final Logger log = LogManager.getLogger(DataStoreBase.class);
	protected Connection connection;

	public DataStoreBase(String url, String userId, String password) {
		try {
			this.connection = DriverManager.getConnection(url, userId, password);
			Constant.DBInfo.URL = url;
			Constant.DBInfo.USER_ID = userId;
			Constant.DBInfo.PASSWORD = password;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}

	public DataStoreBase() {
		try {
			this.connection = DriverManager.getConnection(Constant.DBInfo.URL, Constant.DBInfo.USER_ID,
					Constant.DBInfo.PASSWORD);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}

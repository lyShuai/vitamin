/**
 * Copyright 2012 Vitamin
 */
package com.lyShuai.vitamin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.lyShuai.vitamin.util.Constants;

/**
 * Database Connection Pool for Vitamin.
 * 
 * @author liuys
 * @time Nov 9, 2012 2:27:52 PM
 */
public class ConnectionPoolUtil {
	private final static String COMMONS_DBCP = "jdbc:apache:commons:dbcp:";
	private final static String COMMONS_DBCP_POOL_DRIVER = "org.apache.commons.dbcp.PoolingDriver";

	private static GenericObjectPool connectionPool = null;
	private static PoolingDriver poolingDriver = null;

	private static String driver = null;
	private static String connectUrl = null;
	private static String username = null;
	private static String password = null;
	private static Integer maxActive = null;
	private static Integer maxWait = null;

	// load properties from jdbc.properties
	static {
		try {
			QueryLoader loader = QueryLoader.instance();
			Map<String, String> properties = loader.load(Constants.DATABASE_FILE);
			driver = properties.get(Constants.DATABASE_DRIVER);
			connectUrl = properties.get(Constants.DATABASE_URL);
			username = properties.get(Constants.DATABASE_USERNAME);
			password = properties.get(Constants.DATABASE_PASSWORD);
			maxActive = Integer.parseInt(properties.get(Constants.DATABASE_MAX_ACTIVE));
			maxWait = Integer.parseInt(properties.get(Constants.DATABASE_MAX_WAIT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Init vitamin database connection pool.
	 */
	public static void init() throws ClassNotFoundException, SQLException {
		Class.forName(getDriver());
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(getConntectUrl(), getUsername(), getPassword());
		connectionPool = new GenericObjectPool();
		connectionPool.setMaxActive(getMaxActive());
		connectionPool.setMaxWait(getMaxWait());
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, "SELECT * FROM dual", true, true);
		connectionPool.setFactory(poolableConnectionFactory);
		Class.forName(COMMONS_DBCP_POOL_DRIVER);
		poolingDriver = (PoolingDriver) DriverManager.getDriver(COMMONS_DBCP);
		poolingDriver.registerPool(Constants.TOPIC, connectionPool);
	}

	/**
	 * Get connection from pool.
	 * 
	 * @return
	 */
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		if (connectionPool == null)
			init();
		return DriverManager.getConnection(COMMONS_DBCP + Constants.TOPIC);
	}

	public static void showDriverStatus() {
		System.out.println(connectionPool.getNumActive());
		System.out.println(connectionPool.getNumIdle());
	}

	/**
	 * Destory vitamin database connection pool.
	 * 
	 * @throws SQLException
	 */
	public static void destory() throws SQLException {
		poolingDriver.closePool(Constants.TOPIC);
	}

	/**
	 * Get driver for database connection.
	 * 
	 * @return
	 */
	public static String getDriver() {
		return driver;
	}

	/**
	 * Get vitamin database's connection url.
	 * 
	 * @return
	 */
	protected static String getConntectUrl() {
		return connectUrl;
	}

	/**
	 * Get vitamin database's username.
	 * 
	 * @return
	 */
	protected static String getUsername() {
		return username;
	}

	/**
	 * Get vitamin database's password.
	 * 
	 * @return
	 */
	protected static String getPassword() {
		return password;
	}

	/**
	 * Get the max of active connections.
	 * 
	 * @return
	 */
	protected static int getMaxActive() {
		return maxActive;
	}

	/**
	 * Get the max of wait connections.
	 * 
	 * @return
	 */
	protected static int getMaxWait() {
		return maxWait;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10000; i++) {
			Connection conn = ConnectionPoolUtil.getConnection();
			QueryRunner runner = new QueryRunner();
			ColumnListHandler<Integer> handler = new ColumnListHandler<Integer>();
			List<Integer> integers = runner.query(conn, "SELECT * FROM dual", handler);
			System.out.println(integers.get(0));
			ConnectionPoolUtil.showDriverStatus();
			System.out.println("=====================================");
		}
		ConnectionPoolUtil.destory();
	}
}

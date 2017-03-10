/**
 * 
 */
package com.mobius.searchCalls.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobius.searchCalls.properties.SearchCallsProperties;
public class BasePhoenixDao {
	
	private static Logger log = LoggerFactory.getLogger(BasePhoenixDao.class);
	
	// connection
	private Connection conn = null;
	// callableStatement
	private CallableStatement cstmt = null;
	// statment
	private Statement stmt = null;
	
	/**
	 * BaseDao
	 *
	 */
	public BasePhoenixDao(){
		try{
			log.info("Start init HBase Phoenix for FraudDetection!");
			SearchCallsProperties properties = SearchCallsProperties.getInstance();
			
			//phoenix.jdbc.driver=org.apache.phoenix.jdbc.PhoenixDriver
			//		phoenix.jdbc.url=jdbc:phoenix:mars02-db01,mars02-db02,mars02-db03:2182
			
			Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
			
			log.info("Driver init finished!");
			log.info("phoenix.jdbc.url: " + properties.dbDriver);
			
			System.out.println("URL init finished!");
			System.out.println("phoenix.jdbc.url: " + properties.dbUrl);
			
			
			conn = DriverManager.getConnection(properties.dbUrl);
			stmt = conn.createStatement();
			stmt.setQueryTimeout(600);
			log.info(" create connection and statement finished!");

		} catch (Exception e) {
			
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * get connection
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return conn;
	}
	/**
	 * execute callableStatment
	 * @param sql
	 * @throws SQLException
	 */
	public void executeCallableStatemen(String sql) throws SQLException{
		if(cstmt != null){
			cstmt.close();
			cstmt = null;
		}
		cstmt = conn.prepareCall(sql);
	}


	public Statement getStatement() {
		return stmt;
	}
	/**
	 * execute sql query
	 * 
	 * @param sql
	 * @return ResultSet
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery(sql);
		
			return rs;
		} else
			return null;
	}

	
	public ResultSet executePQuery(String sql) throws SQLException {
		
		PreparedStatement statement = conn.prepareStatement(sql);
		
		
		if (statement != null) {
		
			System.out.println("Execute the prepared statement");
			
			ResultSet rs = statement.executeQuery(sql);
		
			return rs;
		} else
			return null;
	}
	
	
	/**
	 * execute sql update
	 * 
	 * @param sql
	 */
	public void executeUpdate(String sql) throws SQLException {
		if (stmt != null){
			stmt.executeUpdate(sql);
			conn.commit();
		}
	}
	/**
	 * execute batch update
	 * @throws SQLException
	 */
	public void executeBatchUpdate() throws SQLException{
		if(stmt != null){
			stmt.executeBatch();
			conn.commit();
		}
	}

	/**
	 * realse stmt and prepstmt
	 * @throws SQLException
	 */
	public void releaseStmt() throws SQLException{
		if (stmt != null) {
			stmt.close();
			stmt = null;
			stmt = conn.createStatement();
		}
	}
	/**
	 * close connection
	 * @throws SQLException 
	 */
	public void close() throws SQLException  {
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}

		if (cstmt != null){
			cstmt.close();
			cstmt = null;
		}
		conn.close();
		conn = null;
	}
}
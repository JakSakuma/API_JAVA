package com.mut.dbacess;
import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Mysql {

	// Parametros de Conexao
	String server;
	String port;
	String service_name;
	
	String type;

	// Parametros de Autenticacao
	String user;
	String passwd;

	// Loggers
	private static final Logger logger = LoggerFactory.getLogger(Mysql.class);

	abstract void obterConfiguracoes();
	
	public Connection connectMysql(String uniqueID) {
		obterConfiguracoes();
		
		String dbURL = "jdbc:mysql://" + server + ":" + port + "/" + service_name + "?noAccessToProcedureBodies=true&useSSL=false";
		logger.debug("{} - Connection String: {}", uniqueID, dbURL);
		
		Connection con = null;
		try {
			logger.debug("{} - Connection user/pass: {}/{}", uniqueID, user, passwd);
			logger.debug("{} - Connection user/pass: {}/{}", uniqueID, user, "xxxxxx");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(dbURL, user, passwd);
		} catch (Exception e) {
			logger.error("{} - Connection with database failed: {}", uniqueID, e);
		}
		
		if ( con != null) {
			logger.debug("{} - Connection with database established.", uniqueID);
		}
		
		return con;
	}	
}
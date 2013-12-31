package com.gb.OASurveyCalc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private Connection connection = null;
	private String driverName /*= "com.mysql.jdbc.Driver"*/;
	private String serverName /*= "192.168.0.27"*/;
	private String serverPort /*= "3306"*/;
	private String dbName /*= "dvd_collection"*/;
	private String username;
	private String password;
	
	private String uri;
	
	public DatabaseConnector(){
		driverName = "com.mysql.jdbc.Driver";
		serverName = "192.168.0.27";
		serverPort = "3306";
		dbName = "Highlander_Survey";
		username = "garrett";
		password = "tacos^g2";
		uri = "jdbc:mysql://"+serverName+":"+serverPort+"/"+dbName;
	}
	
	public DatabaseConnector(String dN, String sN, String sP, String dbN, String uN, String pwd){
		driverName = dN;
		serverName = sN;
		serverPort = sP;
		dbName = dbN;
		username = uN;
		password = pwd;
		uri = "jdbc:mysql://"+serverName+":"+serverPort+"/"+dbName;
	}
	
	public boolean checkConnection(){
		try{
			Class.forName(driverName);
			connection = DriverManager.getConnection(uri, username, password);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ClassNotFoundException: "+e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseConnector dbCon = new DatabaseConnector();
		System.out.println(dbCon.checkConnection());
	}

}

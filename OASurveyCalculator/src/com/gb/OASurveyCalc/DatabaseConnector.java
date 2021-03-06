package com.gb.OASurveyCalc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

final class MyComparator implements Comparator<String>{

	@Override
	public int compare(String arg0, String arg1) {
			String subARG0 = arg0.substring(7);
			String subARG1 = arg1.substring(7);
			try{
				return Integer.valueOf(subARG0) - Integer.valueOf(subARG1);
			} catch(NumberFormatException e){
				return 0;
			}
	}
}
	

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
		serverName = "68.74.152.126";
		serverPort = "3306";
		dbName = "Highlander_Survey";
		username = "garrett";
		password = "garrett902";
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
	
	public boolean createConnection(){
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
	
	public void insert(String statement){
		if(statement.contains("INSERT")){ //JERRY RIGGED WAY TO MAKE SURE THEY ONLY INSERT WHEN CALLING THIS FUNCTION
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(statement); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ResultSet getResult(String query){
		ResultSet rs=null;
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return rs;
	}
	
	public void createAnswerTable(String tableName){
		try {
			Statement stmt = connection.createStatement();
			String update = "CREATE TABLE IF NOT EXISTS "+tableName+" LIKE sample_answer_table";
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getTableList(){
		ArrayList<String> tableNames = new ArrayList<String>();
		try {
			
			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet rs = metadata.getTables(null, null, null, new String[]{"TABLE"});
			while(rs.next()){
				if(rs.getString(3).startsWith("answer_")){
					tableNames.add(rs.getString(3));
				}
			}
			Comparator<String> comparator = new MyComparator();
			Collections.sort(tableNames,comparator);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tableNames;
	}
	
	public void clearTable(String tableName){
		try {
			Statement stmt = connection.createStatement();
			String update = "TRUNCATE TABLE "+tableName;
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseConnector dbCon = new DatabaseConnector();
		System.out.println(dbCon.createConnection());
		
		dbCon.getTableList();
		//dbCon.createAnswerTable("answer_1");
		/*ResultSet r = dbCon.getResult("SELECT * FROM trash_table WHERE 1=2");
		
		try {
			while(r.next()){
				System.out.println(r.getString(2));
			}
			ResultSetMetaData rmd = r.getMetaData();
			System.out.println(rmd.getColumnCount());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//dbCon.insert("INSERT INTO trash_table SET trash=\"abcdefghij\"");
		//dbCon.clearTable("trash_table");
		dbCon.closeConnection();
	}

}

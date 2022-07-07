package com.employees.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Database {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/test";//userdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	
	String memoryURL = "jdbc:sqlite::memory:";

	private Connection conn;
	public static void main (String [] args)
	{
		Database db = new Database();
		try {
			String query = "SELECT * FROM users";
			ResultSet rs = db.getResultSet(query);
			if (rs==null)
			{
				System.out.println ("No data");
				return;
			}
			while (rs.next())
			{
				System.out.println (rs.getString(1));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	public Connection getConnection() {
		try {
			if (conn !=null)
				return conn;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			/*
			String jdbcURL = "jdbc:mysql://localhost:3306/test";
			String jdbcUsername = "root";
			String jdbcPassword = "";
			*/
			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
			/*
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
			*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public int execute (String sql)
	{
		try {
			if (conn==null)
				getConnection();
			
			PreparedStatement statement = conn.prepareStatement(sql);
			return statement.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e);
			//e.printStackTrace();
			return -1;
			// TODO Auto-generated catch block
			
		}finally {
			close();
		}
		
		
	}
	public int execute (String sql, Object...values)
	{
		try {
			if (conn==null)
				getConnection();
			
			PreparedStatement statement = conn.prepareStatement(sql);
			int row=1;
			for (Object obj:values)
			{
				statement.setObject(row++, obj);
			}
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
			  int newId = rs.getInt(1);
			  return newId;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}finally {
			close();
		}
		
		return -1;
	}
	public PreparedStatement prepare (String sql)
	{
		try {
			if (conn==null)
				getConnection();
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			return statement;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}finally {
			close();
		}
		
		return null;
	}
	
	
	
	public ResultSet getResultSet (String query, Object...values)
	{
		ResultSet rs=null;
		try {
			if (conn==null)
				getConnection();
			
			PreparedStatement statement = conn.prepareStatement(query);
			int row=1;
			if (values!=null && values.length>0)
			{				
				for (Object obj:values)
				{
					statement.setObject(row++, obj);
				}
			}
			return statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}finally {
			//close();
		}
		return rs;
	}
	public boolean close ()
	{
		try {
			if (conn ==null)
				return true;
			conn.close();
			conn=null;
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

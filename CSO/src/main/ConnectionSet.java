package main;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class ConnectionSet {
	final static String protocol = "jdbc:mysql://localhost:3306/cso";
	final static String userid="root";
	final static String password="yashgta5";
	private Statement smt;
	public Connection  cn;

	public  boolean ConnectionSetup(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			 this.cn = (Connection) DriverManager.getConnection(protocol, userid, password);
			smt = (Statement) cn.createStatement();
			return true;
			}catch (Exception e) {
			System.out.println(e);
			return false;
		}
		}
	
	public void close(){
		try {
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean queryUpdate(String sql){
		try {
			smt.executeUpdate(sql);
			
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	public ResultSet queryRetrive(String sql) {
			 try {
			ResultSet	rs = (ResultSet) smt.executeQuery(sql);
			//close();
				return rs;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			System.out.println(e);
			System.out.println("yehi hai-ConnectionSet.java");
			}
			return null;
			
			
	}
	
}

package main.Login;
import java.sql.*;

import main.Global;

public class dbConnector {
	public static Connection conn = null;
	public dbConnector()
	{
		try
		{
			Class.forName(Global.jdbc_driver); // Register JDBC driver
			conn = DriverManager.getConnection( Global.db_url , Global.db_user , Global.db_pass ); // Connect to Database 
			//System.out.println("Database Selected ");
		}catch(SQLException e)
		{
			//Exceptions in JDBC
			System.err.println("Exception  "+e.getMessage());
		}
		catch(Exception e)
		{
			//Exceptions in Class.forName()
			System.err.println("Exception  "+e.getMessage());
		}
		finally
		{
			//Close all connections
			
		}
	}
	
	public ResultSet executeQuery(String qry)
	{
		ResultSet rs =null;
		Statement stmt = null;
		try{
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(qry);
			}
			catch(Exception exp)
			{
				System.err.println("In qry : " +qry);
			}
			finally
			{
				try {
					stmt.close();
				} catch (SQLException exp) {
					// TODO Auto-generated catch block
					exp.printStackTrace();
				}
			}
		return rs;
		
	}

}

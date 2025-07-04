package main;
import java.sql.*;

class dbConnector{

	static Connection conn = null;
	static Statement stmt = null;
	public static void main(String[] args) 
	{
		
		try
		{
			Class.forName(Global.jdbc_driver); // Register JDBC driver
			conn = DriverManager.getConnection( Global.db_url , Global.db_user , Global.db_pass ); // Connect to Database 

			System.out.println("Database Selected ");
			
			//Create a statement 
			stmt  = conn.createStatement();
			
			//Execute a query
			String qry ;
			//qry = "SHOW TABLES";
			//qry = "SELECT * FROM EXAM";
			
			//qry = "delete from Question";
		//	stmt.executeUpdate(qry);
			//qry = "desc question";
			//qry = "SELECT eid,ename FROM exam";
			
			qry = "SELECT S.sid,S.first_name,S.last_name,R.Total_Marks "
					+ "FROM student S,examstudenttable E,result R "
					+ "WHERE E.eid = "+11+" AND S.sid = E.sid  AND R.estid = E.estid "
					+ "ORDER BY(S.sid)";
			
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next())
			{
				String name = rs.getString(2);
			//  String name = rs.getString(2);
				System.out.println(" "+name+", ");
			}
			rs.close(); 
		
		}catch(SQLException e)
		{//Exceptions in JDBC
			System.err.println("Exception  "+e.getMessage());
		}
		catch(Exception e)
		{//Exceptions in Class.forName()
			System.err.println("Exception  "+e.getMessage());
		}
		finally
		{
			//Close all connections
			try // Close stmt
			{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException e){}
			try // Close conn
			{
				if(conn!=null)
					conn.close();
			}catch(SQLException e){}
		}

			//Close all connections
			
		
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

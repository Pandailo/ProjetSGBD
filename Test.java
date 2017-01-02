import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Test
{
	public class Central_mem
	{
		int nbbuffers;
		int taillebuff;
		public Central_mem(int n,int t)
		{
			nbbuffers=n;
			taillebuff=t;
		}
		
	}
	public class BD
	{
		String un;
		String pw;
		Connection connect;
		String add;
		public BD(String uname,String pwd,String addr)
		{
			try 
			{
            			Class.forName("oracle.jdbc.driver.OracleDriver");
        		} 
        			catch (ClassNotFoundException ex) 
        			{
            				Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        			}
			un = uname;
			pw=pwd;
			add=addr;
			try 
        		{
            			connect = DriverManager.getConnection(add,un,pw);
        		} 
        			catch (SQLException ex) 
        			{
            				Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        			}
		}
		public int Exec(String req)
		{
			//A n'utiliser que sur des requêtes non interrogatives
			//Retourne le nombre de lignes traitées par la requête
			Statement stmt = connect.createStatement() ;
			return stmt.executeUpdate(req);
		} 
	}
	public static void main(String[] args)
	{
	
	}
}

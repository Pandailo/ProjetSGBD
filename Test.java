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
		/*&quot;jdbc:oracle:thin:@butor:1521:ensb2016, Login, passwd);
		Connection de l’extérieur : @ufrsciencestech.u-bourgogne.fr :25561
		*/
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
		public int Exec(/*String req*/)
		{
			Statement stmt = null;
        		try 
        		{
            			stmt = connect.createStatement();
        		} 
        			catch (SQLException ex) 
        			{
            				Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        			}
        			String req = "UPDATE alakon SET att=3 WHERE att=12";
       				int nb = 0;
        			try 
        			{
           				nb = stmt.executeUpdate(req);
        			} 
        				catch (SQLException ex) 
        				{
            					Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        				}
        				System.out.println(nb);
        				return nb;
        
	} 
		
	}
	public static void main(String[] args)
	{
		Test t=new Test();
		BD database;
		if(args[0]!="fac")
		{
			database=new t.BD("yv965015","yv965015","jdbc:oracle:thin:@ufrsciencestech.u-bourgogne.fr:25561");
		}
		else
			database=new t.BD("yv965015","yv965015","jdbc:oracle:thin:@butor:1521:ensb2016");
		int n=database.Exec(/*"Create table PROJET (nometu VARCHAR2(25),pnometu VARCHAR(25),NoEtu INT);"*/);
		System.out.println(n);
	}
}

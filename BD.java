/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package projet;
import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author profil
 */
public class BD
{
    
    String un;
    String pw;
    Connection connect;
    String add;
    /*
    "jdbc:oracle:thin:@butor:1521:ensb2016, Login, passwd");
    Connection de l’extérieur : @ufrsciencestech.u-bourgogne.fr :25561
    */
    public BD()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            connect = DriverManager.getConnection("jdbc:oracle:thin:@butor:1521:ensb2016","yv965015","yv965015");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public BD(String uname,String pwd,String addr)
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String[] Inter(String req)
    {
        String[] res = null;
        int nbco=0;
        int taille=0;
        String tmp="";
        try
        {
            Statement stmt = connect.createStatement() ;
            ResultSet rset = stmt.executeQuery(req);
            ResultSetMetaData rsmd=rset.getMetaData();
            nbco=rsmd.getColumnCount();
            rset = stmt.executeQuery(req);
            String ntable="";
            String[] temp=req.split("from ");
            int nb=0;
            String tempo=temp[1];
            String[] temp2=tempo.split(" ");
            ntable=temp2[0];
            String count="select count(*) from "+ntable;
            ResultSet rset2 = stmt.executeQuery(count);
            rset2.next();
            taille=rset2.getInt(1);
            rset2.close();
            rset=stmt.executeQuery(req);
            //GETROW = NB DE ROW TRAITE taille=rset.getRow();
            
            res=new String[taille];
            int j=0;
            while(rset.next())
            {    res[j]="";
            for(int i=1;i<=nbco;i++)
            {
                res[j]+=""+rsmd.getColumnName(i)+"=";
                if(i<=nbco-1)
                    res[j]+=rset.getString(i)+",";
                else
                    res[j]+=rset.getString(i);
            }
            j++;
            }
            rset.close() ;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    public int Exec(String req)
    {
        Statement stmt = null;
        try
        {
            stmt = connect.createStatement();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        int nb = 0;
        try
        {
            nb = stmt.executeUpdate(req);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nb;
        
    }
    public int Hash(String table,String att,int modulo)
    {
        String req="";
        int attbase;
        int nb=0;
        String[] res=Inter("select "+att+" from "+table);
        for(int i=0;i<res.length;i++)
        {
            String[] res2=Inter("select numero from "+table);
            String[] temp2=res2[i].split("=");
            int num=parseInt(temp2[1]);
            String[] temp=res[i].split("=");
            attbase=parseInt(temp[1]);
            req="UPDATE "+table+" SET BUCKET="+(attbase%modulo)+" WHERE numero="+num;
            Statement stmt = null;
            try
            {
                stmt = connect.createStatement();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
            try
            {
                nb = stmt.executeUpdate(req);
                stmt.executeUpdate("commit");
            }
            catch (SQLException ex)
            {
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nb;
    }
    
    
}

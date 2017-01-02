package projet;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yann Véron
 */
public class projet 
{

    void connect() 
    {
        String login = "yv965015";
        String pass = "yv965015";
        Connection connect = null;
        try 
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(projet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            connect = DriverManager.getConnection("jdbc:oracle:thin:@butor:1521:ensb2016", login, pass);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(projet.class.getName()).log(Level.SEVERE, null, ex);
        }
        DatabaseMetaData dbmd = null;
        try 
        {
            dbmd = connect.getMetaData();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(projet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            dbmd.getDefaultTransactionIsolation();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(projet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    void update(Connection connect) 
    {
        Statement stmt = null;
        try {
            stmt = connect.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(projet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String req = "UPDATE alakon SET att=3 WHERE att=12";
        int nb = 0;
        try {
            nb = stmt.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(projet.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(nb);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    projet Projet=new projet();

    }

}

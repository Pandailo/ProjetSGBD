/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package projet;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author profil
 */
public class Projet
{
//Les bukets sont des attributs des tables -> les Ri bouts de relations sont récup via leur numéro de bucket
    /**
     * @param args the command line arguments
     */
    /* if(args[0]!="fac")
    {
    database=new BD("yv965015","yv965015","jdbc:oracle:thin:@ufrsciencestech.u-bourgogne.fr:25561");
    }
    else
    database=new BD("yv965015","yv965015","jdbc:oracle:thin:@butor:1521:ensb2016");
    */
    public static void main(String[] args)
    {
        BD database;
        Central_mem Memory;
        String[] r;
        String[] s;
        String[] res=new String[8];
        String reqR="select * from projet";
        String reqS="select * from projet_";
        database = new BD();
        int modulo=5;
        /* r[0] ="nom=Jean,pnom=bite,age=12";
        s[1]="nom=Jean,pnom=Bon,age=25";
        r[2]="nom=Louis,pnom=Louis,age=20";
        s[0]="nom=AHAHA,prenom=You,age=21";
        r[1]="nom=Tuture,prenom=Arthur,age=50";
        s[2]="nom=Urdnot,prenom=Grunt,age=20";
        */
        r=database.Inter(reqR);
        s=database.Inter(reqS);
        int nblig,nblig2;
        nblig=nblig2=0;
        nblig=database.Hash("PROJET","AGE",modulo);
        nblig2=database.Hash("PROJET_","AGETU",modulo);
        Memory=new Central_mem(modulo+1,10,r,s);
        res=Memory.Traitement("AGE", "AGETU", "r");
        /*for(int i=0;i<res.length;i++)
        System.out.println(res[i]);
        */
        
        System.out.println("Table 1 :");
        for(int i=0;i<r.length;i++)
        {
            System.out.println(r[i]);
        }
        System.out.println("Table 2");
        for(int i=0;i<s.length;i++)
        {
            System.out.println(s[i]);
        }
        System.out.println("Join :");
        for(int i=0;i<res.length;i++)
        {
            if(res[i]!=null)
                System.out.println(res[i]);
        }
    }
    
}

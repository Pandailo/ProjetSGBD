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
public class Central_mem
{
    int nbbuffers;
    int taillebuff;//Nombre de tuples qui tiennent en un buffer
    String[][] tab_buff;
    String[] R,S;
    public Central_mem(int n,int t,String[] Re,String[] Se)
    {
        nbbuffers=n;
        taillebuff=t;
        tab_buff=new String[nbbuffers][taillebuff];
        R=Re;
        S=Se;
        for(int i=0;i<nbbuffers;i++)
        {
            for(int j=0;j<taillebuff;j++)
                tab_buff[i][j]="";
        }
    }
    void chargerR()
    {
        int k=0;
        while(k<nbbuffers-1)
        {
            int cpt=0;
            for(int i=0;i<R.length;i++)
            {
                int buf=0;
                //chargement R
                String[] parts=R[i].split(",");
                String[] parts2;
                for(int j=0;j<parts.length;j++)
                {
                    parts2=parts[j].split("=");
                    if(parts2[0].equals("bucket")||parts2[0].equals("BUCKET"))
                    {
                        buf=Integer.parseInt(parts2[1]);
                        if(buf==k)
                        {
                            tab_buff[buf][cpt]=R[i];
                            cpt++;
                        }
                    }
                }
                
            }
            k++;
        }
        
        
        
    }
    void chargerS(int i)
    {
        int buf=0;
        int cpt=0;
        //chargement R
        for(int k=0;k<S.length;k++)
        {
            String[] parts=S[k].split(",");
            String[] parts2;
            for(int j=0;j<parts.length;j++)
            {
                parts2=parts[j].split("=");
                
                if(parts2[0].equals("bucket")||parts2[0].equals("BUCKET"))
                {
                    
                    buf=Integer.parseInt(parts2[1]);
                    
                    if(buf==i)
                    {
                        tab_buff[nbbuffers-1][cpt]=S[k];
                        cpt++;
                    }
                    
                }
            }
            
        }
    }
    String[] Traitement(String attR, String attS,String First)
    {
        String[] res;
        int taille_max=0;
        if(First.equals("R")||First.equals("r"))
        {
            for(int i=0;i<R.length;i++)
            {
                taille_max++;
            }
        }
        else
        {
            for(int i=0;i<S.length;i++)
            {
                taille_max++;
            }
        }
        res=new String[taille_max];
        chargerR();
        int compt=0;
        int buff=0;
        String valS=null;
        String valR=null;
        while(buff<nbbuffers-1)
        {
            //Pour chaque buffer
            chargerS(buff);
            //Detection valeur de jointure R
            for(int cpt=0;cpt<taillebuff;cpt++)
            {
                //cpt de tuples de R
                String attr[]=tab_buff[buff][cpt].split(",");
                for(int at=0;at<attr.length;at++)
                {
                    String valat[]=attr[at].split("=");
                    if(valat[0].equals(attR))
                    {
                        valR=valat[1];
                        //Pour chaque tuple du bucket buf chargé de R , on vérifie les égalités sur S
                        for(int nbtS =0;nbtS<taillebuff;nbtS++)
                        {
                            //pour chaque tuple nbtS de Sbuf chargé en mémoire centrale à nbbuffers-1
                            String atts[]=tab_buff[nbbuffers-1][nbtS].split(",");
                            for(int atS=0;atS<atts.length;atS++)
                            {
                                //valeur att S
                                String[] valats=atts[atS].split("=");
                                if(valats[0].equals(attS))
                                {
                                    valS=valats[1];
                                    //System.out.println("ValR : "+valR+" valS :"+valS);
                                    if(valR.equals(valS))
                                    {
                                        if(First.equals("R")||First.equals("r"))
                                        {
                                            res[compt]=tab_buff[buff][cpt];
                                            compt++;
                                        }
                                        else
                                        {
                                            if(tab_buff[nbbuffers-1][cpt]!=null||tab_buff[nbbuffers-1][cpt]!="")
                                            res[compt]=tab_buff[nbbuffers-1][nbtS];
                                            compt++;
                                        }
                                    }
                                }
                            }
                            
                        }
                    }
                }
            }
            buff++;
        }
        return res;
    }
}

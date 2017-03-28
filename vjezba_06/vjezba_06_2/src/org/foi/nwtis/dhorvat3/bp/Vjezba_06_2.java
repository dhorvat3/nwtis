/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.bp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dhorvat3.konfiguracije.bp.BP_Konfiguracija;
/**
 *
 * @author Davorin Horvat
 */
public class Vjezba_06_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Niste upisali naziv!");
            return;
        }
        
        String datoteka = args[0];
        BP_Konfiguracija bp_konf = new BP_Konfiguracija(datoteka);

//        try {
//            Class.forName(bp_konf.getDriverDatabase());
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Vjezba_06_2.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }

        String query = "select * from polaznici";

        try (Connection c = DriverManager.getConnection
                    (bp_konf.getServerDatabase() + bp_konf.getUserDatabase(), 
                    bp_konf.getUserUsername(), 
                    bp_konf.getUserPassword());) {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                System.out.println("Ime: " + rs.getString("ime") + " Prezime: " + rs.getString("prezime"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Vjezba_06_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.konfiguracije.bp;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dhorvat3.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author Davorin Horvat
 */
public class BP_Konfiguracija implements BP_Sucelje{
    private Konfiguracija konf;
    private static final String server_database = "server.database";
    private static final String admin_username = "admin.username";
    private static final String admin_password = "admin.password";
    private static final String admin_database = "admin.database";
    private static final String user_username = "user.username";
    private static final String user_password = "user.password";
    private static final String user_database = "user.database";
    private static final String driver_database_odbc = "driver.database.odbc";
    private static final String driver_database_mysql = "driver.database.mysql";
    private static final String driver_database_postgresql = "driver.database.postgresql";
    private static final String driver_database_derby = "driver.database.derby";
    private static final String drive_database = "driver.database";
    
    public BP_Konfiguracija(String datoteka){
        try{
            this.konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(BP_Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getAdminDatabase() {
        return konf.dajPostavku(admin_database);
    }

    @Override
    public String getAdminPassword() {
        return konf.dajPostavku(admin_password);
    }

    @Override
    public String getAdminUsername() {
        return konf.dajPostavku(admin_username);
    }

    @Override
    public String getDriverDatabase(String bp_url) {
        String[] urlParts = bp_url.split(":");
        if(urlParts == null || urlParts.length < 3){
            return null;
        }
        String driver = konf.dajPostavku(drive_database+"."+urlParts[1]);
        return driver;
    }

    @Override
    public String getDriverDatabase() {
        return getDriverDatabase(getServerDatabase());
    }

    @Override
    public Properties getDriversDatabase() {
        Properties drivers = new Properties();
        for(Map.Entry<Object, Object> entry: konf.dajSvePostavke().entrySet()){
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if(key.startsWith(drive_database)){
                drivers.put(key, value);
            }
        }
        return drivers;
    }

    @Override
    public String getServerDatabase() {
        return konf.dajPostavku(server_database);
    }

    @Override
    public String getUserDatabase() {
        return konf.dajPostavku(user_database);
    }

    @Override
    public String getUserPassword() {
        return konf.dajPostavku(user_password);
    }

    @Override
    public String getUserUsername() {
        return konf.dajPostavku(user_username);
    }
    
}

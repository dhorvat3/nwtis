/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.foi.nwtis.dhorvat3.rest.klijenti.GMKlijent;
import org.foi.nwtis.dhorvat3.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPodaci;

/**
 *
 * @author Davorin Horvat
 */
public class MeteoHelper {
    
    public static Lokacija dajLokaciju(String adresa){
        GMKlijent gmk = new GMKlijent();
        return gmk.getGeoLocation(adresa);
    }
    
    public static String dajAdresu(float lattitude, float longitude){
        GMKlijent gmk = new GMKlijent();
        return gmk.getGeoAddress(lattitude, longitude);
    }
    
    public static MeteoPodaci dajMeteoPodatke(String latitude, String longitude, String apiKey){
        OWMKlijent owmk = new OWMKlijent(apiKey);
        return owmk.getRealTimeWeather(latitude, longitude);
    }
    
    public static ArrayList<Lokacija> dajSveLokacije(Statement statement) throws SQLException{
        ArrayList<Lokacija> lokacije = new ArrayList<>();
        
        ResultSet rs;
        
        String query = "SELECT * FROM adrese";
        rs = statement.executeQuery(query);
        while(rs.next()){
            Lokacija loc = new Lokacija();
            loc.setId(rs.getInt("id"));
            loc.setLatitude(rs.getString("latitude"));
            loc.setLongitude(rs.getString("longitude"));
            lokacije.add(loc);
        }
        
        return lokacije;
    }
    
    public static int dajIdLokacije(int id, Statement statement) throws SQLException {
        ResultSet rs;
        String query = "SELECT * FROM uredaji WHERE id=" + id;
        rs = statement.executeQuery(query);
        int idAdresa = 0;
        while(rs.next()){
            idAdresa = rs.getInt("id_adresa");
        }
        
        return idAdresa;
    }
}

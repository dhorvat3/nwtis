/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.ws.serveri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.rest.klijenti.GMKlijent;
import org.foi.nwtis.dhorvat3.web.DodajUredjaj;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPodaci;
import org.foi.nwtis.dhorvat3.web.podaci.Uredjaj;
import org.foi.nwtis.dhorvat3.web.slusaci.SlusacAplikacije;

/**
 * pruža SOAP web servis za meteorološke podatke spremljenih IoT uređaja.
 * Operacije se temelje na podacima koje se nalaze u tablici METEO u bazi
 * podataka.
 *
 * @author Davorin Horvat
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    private ServletContext context;
    private Statement statement;

    /**
     * Web service operation Dohvaća sve uređaje pohranjene u bazi podataka.
     *
     * @return
     */
    @WebMethod(operationName = "dajSveUredjaje")
    public java.util.List<Uredjaj> dajSveUredjaje() {
        context = SlusacAplikacije.getContext();
        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
        String url = konfig.dajPostavku("server.database") + konfig.dajPostavku("user.database");
        String user = konfig.dajPostavku("user.username");
        String pass = konfig.dajPostavku("user.password");

        try {
            Class.forName(konfig.dajPostavku("driver.database.derby"));
            Connection connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Uredjaj> uredjaji = new ArrayList<>();

        try {
            String sql = "SELECT * FROM UREDAJI";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Lokacija geoloc = new Lokacija(rs.getString("LATITUDE"), rs.getString("LONGITUDE"));
                uredjaji.add(new Uredjaj(rs.getInt("ID"), rs.getString("NAZIV"), geoloc));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return uredjaji;
    }

    /**
     * Web service operation Dodaje uređaj u bazu podataka. Dohvaća njegove
     * geolokacijske podatke s obzirom na adresu.
     *
     * @param uredjaj
     * @param adresa
     * @return
     */
    @WebMethod(operationName = "dodajUredjaj")
    public Boolean dodajUredjaj(@WebParam(name = "uredjaj") Uredjaj uredjaj, @WebParam(name = "adresa") String adresa) {
        context = SlusacAplikacije.getContext();
        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
        String url = konfig.dajPostavku("server.database") + konfig.dajPostavku("user.database");
        String user = konfig.dajPostavku("user.username");
        String pass = konfig.dajPostavku("user.password");

        try {
            Class.forName(konfig.dajPostavku("driver.database.derby"));
            Connection connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        GMKlijent gmk = new GMKlijent();

        if (!"".equals(adresa)) {
            Lokacija lokacija = gmk.getGeoLocation(adresa);

            String sql = "INSERT INTO UREDAJI (ID, NAZIV, LATITUDE, LONGITUDE, VRIJEME_KREIRANJA) VALUES (" + getMaxID("UREDAJI") + " , '" + uredjaj.getNaziv() + "', " + lokacija.getLatitude() + ", " + lokacija.getLongitude() + ", CURRENT_TIMESTAMP)";
            System.out.println("SQL: " + sql);
            try {
                statement.executeUpdate(sql);
                System.out.println("--- UREDAJ --- Uređaj dodan u bazu!");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(DodajUredjaj.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Web service operation Daje sve podatke za uređaj u određenom vremenskom
     * intervalu. Ako su parametri from i to prazni dohvaća sve meteopodatke o
     * uređaju.
     *
     * @param id
     * @param from
     * @param to
     * @return
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaUredjaj")
    public java.util.List<MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(@WebParam(name = "id") int id, @WebParam(name = "from") long from, @WebParam(name = "to") long to) {
        List<MeteoPodaci> mp = new ArrayList<>();
        context = SlusacAplikacije.getContext();
        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
        String url = konfig.dajPostavku("server.database") + konfig.dajPostavku("user.database");
        String user = konfig.dajPostavku("user.username");
        String pass = konfig.dajPostavku("user.password");

        try {
            Class.forName(konfig.dajPostavku("driver.database.derby"));
            Connection connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*Calendar fromCal = Calendar.getInstance();
        fromCal.setTimeInMillis(from);*/
        Date fromDate = new Date((long) from);
        String fromString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fromDate);
        Date toDate = new Date((long) to);
        String toString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(toDate);

        String sql = "";
        if (from != 0 && to != 0) {
            sql = "SELECT * FROM METEO WHERE ID = " + id + " AND PREUZETO > '" + fromString + "' AND PREUZETO < '" + toString + "'";
        } else {
            sql = "SELECT * FROM METEO WHERE ID = " + id;
        }
        System.out.println("SQL: " + sql);
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MeteoPodaci meteo = new MeteoPodaci();
                meteo.setPressureValue(rs.getFloat("TLAK"));
                meteo.setPressureUnit("hPa");
                meteo.setTemperatureValue(rs.getFloat("TEMP"));
                meteo.setTemperatureUnit("Celsius");
                meteo.setTemperatureMin(rs.getFloat("TEMPMIN"));
                meteo.setTemperatureMax(rs.getFloat("TEMPMAX"));
                meteo.setHumidityValue(rs.getFloat("VLAGA"));
                meteo.setHumidityUnit("%");
                meteo.setWeatherValue(rs.getString("VRIJEMEOPIS"));
                meteo.setLastUpdate(rs.getDate("PREUZETO"));
                mp.add(meteo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mp;
    }

    /**
     * Dohvaća slijedeći ID u tablici.
     *
     * @param table
     * @return
     */
    private int getMaxID(String table) {
        try {
            String query = "SELECT MAX(ID) FROM " + table;
            System.out.println("SQL: " + query);
            ResultSet rs = statement.executeQuery(query);
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(DodajUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}

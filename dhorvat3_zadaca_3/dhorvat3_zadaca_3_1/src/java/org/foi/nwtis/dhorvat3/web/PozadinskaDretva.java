/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPodaci;

/**
 * u pozadinskoj dretvi preuzimaju se u pravilnim intervalima meteorološki podaci putem REST web servisa openweathermap.org za izabrani skup IoT uređaja i pohranjuju se u tablicu u bazi podataka 
 * @author Davorin Horvat
 */
public class PozadinskaDretva extends Thread {

    private Connection connection;
    private Statement statement, statement2, statement3;
    private Konfiguracija konfig = null;
    private boolean kraj = false;
    private int intervalSpavanja;
    private ResultSet rs;

    public PozadinskaDretva(ServletContext context) {
        konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
        intervalSpavanja = Integer.parseInt(konfig.dajPostavku("intervalDretveZaMeteoPodatke"));
    }

    @Override
    public void interrupt() {
        this.kraj = true;
        try {
            connection.close();
            if (statement != null) {
                statement.close();
            }
            if (statement2 != null) {
                statement2.close();
            }
            if (statement3 != null) {
                statement3.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PozadinskaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String url = konfig.dajPostavku("server.database") + konfig.dajPostavku("user.database");
        String user = konfig.dajPostavku("user.username");
        String pass = konfig.dajPostavku("user.password");
        String apiKey = konfig.dajPostavku("apikey");

        try {
            Class.forName(konfig.dajPostavku("driver.database.derby"));
            connection = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PozadinskaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!kraj) {
            System.out.println("Korak dretve!");
            String sql = "SELECT * FROM UREDAJI";
            try {

                statement = connection.createStatement();
                rs = statement.executeQuery(sql);

                while (rs.next()) {
                    statement3 = connection.createStatement();
                    OWMKlijent owmk = new OWMKlijent(apiKey);
                    if (rs.getString("LATITUDE") != null && rs.getString("LONGITUDE") != null) {
                        MeteoPodaci mp = owmk.getRealTimeWeather(rs.getString("LATITUDE"), rs.getString("LONGITUDE"));
                        String upit = "";
                        upit = "INSERT INTO METEO VALUES (default, " + rs.getInt("ID") + ", ";
                        upit += "'NEMA PODATAKA!" + "', ";
                        upit += rs.getString("LATITUDE") + ", ";
                        upit += rs.getString("LONGITUDE") + ", ";
                        upit += "'" + mp.getWeatherNumber() + "', ";
                        upit += "'" + mp.getWeatherValue() + "', ";
                        upit += mp.getTemperatureValue() + ", ";
                        upit += mp.getTemperatureMin() + ", ";
                        upit += mp.getTemperatureMax() + ", ";
                        upit += mp.getHumidityValue() + ", ";
                        upit += mp.getPressureValue() + ", ";
                        upit += "0.0, ";
                        upit += "0.0, ";
                        /*upit += mp.getWindSpeedName() + ", ";
                        upit += mp.getWindDirectionName() + ", ";*/
                        upit += "now()";
                        upit += ")";
                        System.out.println("SQL: " + upit);
                        statement3.executeUpdate(upit);
                        statement3.close();
                        sleep(300);
                    }
                }
                sleep(intervalSpavanja * 1000);
            } catch (SQLException | InterruptedException | NullPointerException ex) {
                Logger.getLogger(PozadinskaDretva.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    statement.close();
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PozadinskaDretva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
}

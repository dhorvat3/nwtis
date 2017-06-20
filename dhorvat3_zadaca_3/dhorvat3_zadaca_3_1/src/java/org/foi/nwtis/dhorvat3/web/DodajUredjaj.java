/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.dhorvat3.rest.klijenti.GMKlijent;
import org.foi.nwtis.dhorvat3.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPodaci;

/**
 * u korisničkom dijelu potrebno je unositi pojedinačne IoT uređaje za koje će
 * se preuzimati metorološki podaci.
 *
 * @author Davorin Horvat
 */
@WebServlet(name = "DodajUredjaj", urlPatterns = {"/DodajUredjaj"})
public class DodajUredjaj extends HttpServlet {

    private ServletContext context = null;
    private String apiKey = "";
    private String meteoString = "";
    private String lokacijaString = "";
    private String spremiString = "";
    private String greskaString = "";
    private Lokacija lokacija = null;
    private Statement statement;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * Obrađuje korisnikove zahtjeve
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        context = request.getSession().getServletContext();
        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
        String url = konfig.dajPostavku("server.database") + konfig.dajPostavku("user.database");
        String user = konfig.dajPostavku("user.username");
        String pass = konfig.dajPostavku("user.password");
        greskaString = "";
        try {
            Class.forName(konfig.dajPostavku("driver.database.derby"));
            System.out.println("url " + url);
            System.out.println("user " + user);
            System.out.println("pass: " + pass);
            Connection connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DodajUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }

        apiKey = konfig.dajPostavku("apikey");
        String naziv = "";
        String adresa = "";
        String akcija = "";

        if (request.getParameter("geoLokacija") != null) {
            akcija = "geoLokacija";
        } else if (request.getParameter("spremi") != null) {
            akcija = "spremi";
        } else if (request.getParameter("meteoPodaci") != null) {
            akcija = "meteoPodaci";
        } else {
            akcija = "greska";
        }

        naziv = request.getParameter("naziv");
        adresa = request.getParameter("adresa");
        adresa = new String(adresa.getBytes("ISO-8859-1"), "UTF-8");

        switch (akcija) {
            case "geoLokacija":
                geoLokacija(naziv, adresa);
                request.setAttribute("lokacija", lokacijaString);

                break;
            case "spremi":
                spremi(naziv, adresa);
                request.setAttribute("meteoPodaci", meteoString);
                request.setAttribute("lokacija", lokacijaString);
                request.setAttribute("spremi", spremiString);
                break;
            case "meteoPodaci":
                meteoPodaci(naziv, adresa);
                request.setAttribute("meteoPodaci", meteoString);
                request.setAttribute("lokacija", lokacijaString);
                break;
            case "greska":
                request.setAttribute("greska", "Pogreška u radu sustava!");
                break;
        }
        request.setAttribute("greska", greskaString);
        request.setAttribute("adresa", adresa);
        request.setAttribute("naziv", naziv);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Dohvaća geolokacijske podatke za proslijeđenu adresu.
     * @param naziv
     * @param adresa 
     */
    private void geoLokacija(String naziv, String adresa) {
        GMKlijent gmk = new GMKlijent();

        if (!"".equals(adresa)) {
            lokacija = gmk.getGeoLocation(adresa);

            lokacijaString = "Latitude: " + lokacija.getLatitude();
            lokacijaString += " Longitude: " + lokacija.getLongitude();
        } else {
            greskaString = "Nije unesena adresa!";
        }
    }

    /**
     * Sprema IoT uređaj u bazu podataka.
     * @param naziv
     * @param adresa 
     */
    private void spremi(String naziv, String adresa) {
        boolean ispravno = true;
        if (lokacija == null) {
            greskaString = "Nisu dohvaćeni lokalizacijski parametri! <br />";
            ispravno = false;
        }
        if ("".equals(naziv)) {
            greskaString += "Naziv je prazan! <br />";
            ispravno = false;
        }
        if ("".equals(adresa)) {
            greskaString += "Adresa je prazna! <br />";
            ispravno = false;
        }
        if (ispravno) {
            String sql = "INSERT INTO UREDAJI (ID, NAZIV, LATITUDE, LONGITUDE, VRIJEME_KREIRANJA) VALUES (" + getMaxID("UREDAJI") + " , '" + naziv + "', " + lokacija.getLatitude() + ", " + lokacija.getLongitude() + ", now())";
            System.out.println("SQL: " + sql);
            try {
                statement.executeUpdate(sql);
                System.out.println("--- UREDAJ --- Uređaj dodan u bazu!");
                spremiString = "<p>Uređaj dodan!</p>";
            } catch (SQLException ex) {
                Logger.getLogger(DodajUredjaj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Dohvaća meteo podatke za određeni uređaj.
     * @param naziv
     * @param adresa 
     */
    private void meteoPodaci(String naziv, String adresa) {
        if (lokacija == null) {
            greskaString = "Nisu dohvaćenje lokacijski parametri! <br />";
        } else {
            String lat = lokacija.getLatitude();
            String lon = lokacija.getLongitude();
            OWMKlijent owmk = new OWMKlijent(apiKey);
            MeteoPodaci mp = owmk.getRealTimeWeather(lat, lon);
            String temp = mp.getTemperatureValue().toString();
            String vlaga = mp.getHumidityValue().toString();
            String tlak = mp.getPressureValue().toString();
            String izlazak = mp.getSunSet().toString();
            String tempUnit = mp.getTemperatureUnit();
            String vlagaUnit = mp.getHumidityUnit();
            String tlakUnit = mp.getPressureUnit();

            meteoString = "<label>Temp:</label><p class=\"output\">" + temp + " " + tempUnit + "</p><br />";
            meteoString += "<label>Vlaga:</label><p class=\"output\">" + vlaga + vlagaUnit + "</p><br />";
            meteoString += "<label>Tlak:</label><p class=\"output\">" + tlak + " " + tlakUnit + "</p><br />";
        }

    }

    /**
     * Dohvaća slijdeći ID za upis u bazu podataka.
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.dhorvat3.rest.klijenti.GMKlijent;
import org.foi.nwtis.dhorvat3.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPodaci;

/**
 *
 * @author Davorin Horvat
 */
@WebServlet(name = "DodajUredjaj", urlPatterns = {"/DodajUredjaj"})
public class DodajUredjaj extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String naziv = "";
        String adresa = "";
        String akcija = "meteoPodaci";
        //TODO preuzeti stvarnu akciju
        naziv = request.getParameter("naziv");
        adresa = request.getParameter("adresa");
        
        switch(akcija){
            case "geoLokacija":
                geoLokacija(naziv, adresa);
                break;
            case "spremi":
                spremi(naziv, adresa);
                break;
            case "meteoPodaci":
                meteoPodaci(naziv, adresa);
                break;
        }
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

    private void geoLokacija(String naziv, String adresa) {
        System.out.println("Adresa: " + adresa);
        
        GMKlijent gmk = new GMKlijent();
        
        Lokacija loc = gmk.getGeoLocation(adresa);
        System.out.println("Latitude: " + loc.getLatitude());
        System.out.println("Longitude: " + loc.getLongitude());
        
        //TODO prikaži podatke u index.jsp
        //TODO zapamti unesenu adresu i geolokaciju
        //TODO riješi problem hrvatskih znakova
    }

    private void spremi(String naziv, String adresa) {
        //TODO upiši podatke o uređaju u bazu
    }

    private void meteoPodaci(String naziv, String adresa) {
        String apikey = "c4da75e15a5cf5831514c92b85add116";
        //TODO pročitaj APIKEY iz konfiguracijske datoteke
        String lat = "46.305746";
        String lon = "16.3366066";
        //TODO popuni stvarne geo lokacijske podatke za adresu
        OWMKlijent owmk = new OWMKlijent(apikey);
        MeteoPodaci mp =  owmk.getRealTimeWeather(lat, lon);
        String temp = mp.getTemperatureValue().toString();
        String vlaga = mp.getHumidityValue().toString();
        String tlak = mp.getPressureValue().toString();
        String izlazak = mp.getSunSet().toString();
        
        System.out.println("Temperatura: " + temp);
        System.out.println("Vlaga: " + vlaga);
        System.out.println("Tlak: " + tlak);
        System.out.println("Izlazak: " + izlazak);
        
    }

}

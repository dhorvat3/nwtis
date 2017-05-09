/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.ws.klijenti;

/**
 *
 * @author Davorin Horvat
 */
public class MeteoWSKlijent {

    public static java.util.List<org.foi.nwtis.dhorvat3.ws.serveri.Uredjaj> dajSveUredjaje() {
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveUredjaje();
    }

    public static java.util.List<org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(int id, long from, long to) {
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveMeteoPodatkeZaUredjaj(id, from, to);
    }

    public static Boolean dodajUredjaj(org.foi.nwtis.dhorvat3.ws.serveri.Uredjaj uredjaj, java.lang.String adresa) {
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dodajUredjaj(uredjaj, adresa);
    }

    
    
}

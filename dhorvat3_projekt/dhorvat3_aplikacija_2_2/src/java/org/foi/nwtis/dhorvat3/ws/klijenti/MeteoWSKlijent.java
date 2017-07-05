/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.ws.klijenti;

import org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci;
import org.foi.nwtis.dhorvat3.ws.serveri.NeuspjesnaPrijava_Exception;

/**
 *
 * @author Davorin Horvat
 */
public class MeteoWSKlijent {

    public static String dajAdresuZaUredaj(float lattitude, float longitude, java.lang.String username, java.lang.String password) throws NeuspjesnaPrijava_Exception {
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajAdresuZaUredaj(lattitude, longitude, username, password);
    }

    public static MeteoPodaci dajNajnovijeMeteoPodatkeZaUredaj(int id, java.lang.String username, java.lang.String password) throws NeuspjesnaPrijava_Exception {
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajNajnovijeMeteoPodatkeZaUredaj(id, username, password);
    }

    public static java.util.List<org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci> dajNekolikoMeteoPodatakaZaUredaj(int id, int n, java.lang.String username, java.lang.String password) throws NeuspjesnaPrijava_Exception {
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajNekolikoMeteoPodatakaZaUredaj(id, n, username, password);
    }

    public static java.util.List<org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci> dajZaRazdobljeMeteoPodatkeZaUredaj(int id, long from, long to, java.lang.String username, java.lang.String password) throws NeuspjesnaPrijava_Exception {
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajZaRazdobljeMeteoPodatkeZaUredaj(id, from, to, username, password);
    }

    public static MeteoPodaci dajZadnjeMeteoPodatkeZaUredaj(int id, java.lang.String username, java.lang.String password) throws NeuspjesnaPrijava_Exception {
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajZadnjeMeteoPodatkeZaUredaj(id, username, password);
    }
    
    
}

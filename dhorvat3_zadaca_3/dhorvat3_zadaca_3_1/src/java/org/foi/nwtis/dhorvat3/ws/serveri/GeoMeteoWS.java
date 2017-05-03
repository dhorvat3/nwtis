/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.ws.serveri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPodaci;
import org.foi.nwtis.dhorvat3.web.podaci.Uredjaj;

/**
 *
 * @author Davorin Horvat
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "dajSveUredjaje")
    public java.util.List<Uredjaj> dajSveUredjaje() {
        ArrayList<Uredjaj> uredjaji = new ArrayList<>();
        
        int i = 0;
        Lokacija geoloc = new Lokacija("0.0", "0,0");
        for (; i < 10; i++) {
            uredjaji.add(new Uredjaj(i, "IoT" + i, geoloc));
        }
        
        return uredjaji;
    }

    /**
     * Web service operation
     * @param uredjaj
     * @return 
     */
    @WebMethod(operationName = "dodajUredjaj")
    public Boolean dodajUredjaj(@WebParam(name = "uredjaj") Uredjaj uredjaj) {
        //TODO Dovršiti upis u bazu podataka
        return null;
    }

    /**
     * Web service operation
     * @param id
     * @param from
     * @param to
     * @return 
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaUredjaj")
    public java.util.List<MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(@WebParam(name = "id") int id, @WebParam(name = "from") long from, @WebParam(name = "to") long to) {
        List<MeteoPodaci> mp = new ArrayList<>();
        //TODO dovršiti preuzimanje meteo podataka iz baze podataka
        
        mp.add(new MeteoPodaci(new Date(), new Date(), 19.1f, 5.2f, 25.5f, "C", 55.8f, "%", 998.8f, "hPa", 0.0f, "", 0.0f, "", "", 1, "", "ok", 0.0f, "", "", 7, "", "", new Date()));
        mp.add(new MeteoPodaci(new Date(), new Date(), 19.2f, 5.2f, 25.5f, "C", 55.8f, "%", 998.8f, "hPa", 0.0f, "", 0.0f, "", "", 1, "", "ok", 0.0f, "", "", 7, "", "", new Date()));
        mp.add(new MeteoPodaci(new Date(), new Date(), 19.3f, 5.2f, 25.5f, "C", 55.8f, "%", 998.8f, "hPa", 0.0f, "", 0.0f, "", "", 1, "", "ok", 0.0f, "", "", 7, "", "", new Date()));
        mp.add(new MeteoPodaci(new Date(), new Date(), 19.4f, 5.2f, 25.5f, "C", 55.8f, "%", 998.8f, "hPa", 0.0f, "", 0.0f, "", "", 1, "", "ok", 0.0f, "", "", 7, "", "", new Date()));
        mp.add(new MeteoPodaci(new Date(), new Date(), 19.5f, 5.2f, 25.5f, "C", 55.8f, "%", 998.8f, "hPa", 0.0f, "", 0.0f, "", "", 1, "", "ok", 0.0f, "", "", 7, "", "", new Date()));
        mp.add(new MeteoPodaci(new Date(), new Date(), 19.6f, 5.2f, 25.5f, "C", 55.8f, "%", 998.8f, "hPa", 0.0f, "", 0.0f, "", "", 1, "", "ok", 0.0f, "", "", 7, "", "", new Date()));
        mp.add(new MeteoPodaci(new Date(), new Date(), 19.7f, 5.2f, 25.5f, "C", 55.8f, "%", 998.8f, "hPa", 0.0f, "", 0.0f, "", "", 1, "", "ok", 0.0f, "", "", 7, "", "", new Date()));
        
        return mp;
    }
    
    
}

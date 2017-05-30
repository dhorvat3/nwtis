/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.ejb.sb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.foi.nwtis.dhorvat3.rest.klijenti.GMKlijent;
import org.foi.nwtis.dhorvat3.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPrognoza;

/**
 *
 * @author Davorin Horvat
 */
@Stateless
@LocalBean
public class MeteoIoTKlijent {

    private String api_key = "c4da75e15a5cf5831514c92b85add116";

    public void postaviKorisnickePodatke(String apiKey) {
        this.api_key = apiKey;
    }

    public Lokacija dajLokaciju(String adresa) {
        GMKlijent gmk = new GMKlijent();
        return gmk.getGeoLocation(adresa);
    }
    
    public String dajAdresu(float lattitude, float longitude){
        GMKlijent gmk = new GMKlijent();
        return gmk.getGeoAddress(lattitude, longitude);
    }

    public MeteoPrognoza[] dajMeteoPrognoze(int id, String adresa) {
        adresa = adresa.replaceAll("\\s+", "+");
        Lokacija l = dajLokaciju(adresa);
        OWMKlijent owmk = new OWMKlijent(api_key);
        
        return owmk.getWeatherForecast(id, l.getLatitude(), l.getLongitude());
    }
    
    
}

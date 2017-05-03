/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.dhorvat3.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import org.foi.nwtis.dhorvat3.ejb.sb.MeteoOsvjezivac;
import org.foi.nwtis.dhorvat3.ejb.sb.MeteoPrognosticar;
import org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci;

/**
 *
 * @author Davorin Horvat
 */
@Named(value="meteoPrognoza")
@SessionScoped
public class MeteoPrognoza implements Serializable{

    @EJB
    private MeteoOsvjezivac meteoOsvjezivac;

    @EJB
    private MeteoPrognosticar meteoPrognosticar;

    
    private String odabranaAdresa;
    private List<String> adrese;
    private List<MeteoPodaci> meteoPodaci;
    
    /**
     * Creates a new instance of MeteoPrognoza
     */
    public MeteoPrognoza() {
        odabranaAdresa = "TEST";
        dodajAdresu();
    }

    public String getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(String odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }

    public List<String> getAdrese() {
        adrese = meteoPrognosticar.getAdrese();
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public List<MeteoPodaci> getMeteoPodaci() {
        meteoPodaci = meteoPrognosticar.getMeteoPodaci();
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }
    
    public String dodajAdresu(){
        String msg = "Dodana adresa: " + odabranaAdresa;
        meteoOsvjezivac.sendJMSMessageToNWTiS_vjezba_12(msg);
        return "";
    }

    
}

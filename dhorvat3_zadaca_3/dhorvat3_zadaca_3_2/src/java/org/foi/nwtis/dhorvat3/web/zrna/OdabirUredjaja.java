/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.dhorvat3.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.dhorvat3.ws.serveri.Uredjaj;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "odabirUredjaja")
@RequestScoped
public class OdabirUredjaja {

    private List<Uredjaj> uredaji;
    private String id;
    
    /**
     * Creates a new instance of OdabirUredjaja
     */
    public OdabirUredjaja() {
    }

    public List<Uredjaj> getUredaji() {
        uredaji = MeteoWSKlijent.dajSveUredjaje();
        return uredaji;
    }

    public void setUredaji(List<Uredjaj> uredaj) {
        this.uredaji = uredaj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
}

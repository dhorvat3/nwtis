/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.ejb.sb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci;

/**
 *
 * @author Davorin Horvat
 */
@Stateful
@LocalBean
public class MeteoPrognosticar {

    private List<String> adrese = new ArrayList<>();
    private List<MeteoPodaci> meteoPodaci = new ArrayList<MeteoPodaci>();

    public List<String> getAdrese() {
        return adrese;
    }

    public void setAdrese(List<String> adrese) {
        this.adrese = adrese;
    }

    public List<MeteoPodaci> getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }
    
    public List<String> dajAdrese(){
        return this.getAdrese();
    }
}

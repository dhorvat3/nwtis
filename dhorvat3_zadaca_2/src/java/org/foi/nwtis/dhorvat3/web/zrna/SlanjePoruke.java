/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "slanjePoruke")
@RequestScoped
public class SlanjePoruke {
    private String posluzitelj;
    private String salje;
    private String prima;
    private String predmet;
    private String sadrzaj;
    /**
     * Creates a new instance of SlanjePoruke
     */
    public SlanjePoruke() {
    }

    public String getPosluzitelj() {
        return posluzitelj;
    }

    public void setPosluzitelj(String posluzitelj) {
        this.posluzitelj = posluzitelj;
    }

    public String getSalje() {
        return salje;
    }

    public void setSalje(String salje) {
        this.salje = salje;
    }

    public String getPrima() {
        return prima;
    }

    public void setPrima(String prima) {
        this.prima = prima;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }
    
    public String saljiPoruku(){
        //TODO dodaj za slanje poruke prema primjeru s predavanja koje je priložen uz zadaću
        this.sadrzaj = "";
        this.predmet = "";
        this.prima = "";
        this.salje = "";
        return "PoslanaPoruka";
    }
    
    public String promjenaJezika(){
        return "PromjenaJezika";
    }
    
    public String pregledPoruka(){
        return "PregledPoruka";
    }
}

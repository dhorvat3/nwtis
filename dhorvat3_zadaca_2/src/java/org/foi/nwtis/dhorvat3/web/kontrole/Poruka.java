/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.kontrole;

import java.util.Date;

/**
 *
 * @author Davorin Horvat
 */
public class Poruka {
    private String id;
    private Date vrijemeSlanja;
    private Date vrijemePrijema;
    private String salje;
    private String predmet;
    private String sadrzaj;
    private String vrsta;

    public Poruka(String id, Date vrijemeSlanja, Date vrijemePrijema, String salje, String predmet, String sadrzaj, String vrsta) {
        this.id = id;
        this.vrijemeSlanja = vrijemeSlanja;
        this.vrijemePrijema = vrijemePrijema;
        this.salje = salje;
        this.predmet = predmet;
        this.sadrzaj = sadrzaj;
        this.vrsta = vrsta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getVrijemeSlanja() {
        return vrijemeSlanja;
    }

    public void setVrijemeSlanja(Date vrijemeSlanja) {
        this.vrijemeSlanja = vrijemeSlanja;
    }

    public Date getVrijemePrijema() {
        return vrijemePrijema;
    }

    public void setVrijemePrijema(Date vrijemePrijema) {
        this.vrijemePrijema = vrijemePrijema;
    }

    public String getSalje() {
        return salje;
    }

    public void setSalje(String salje) {
        this.salje = salje;
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

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }
    
    
}

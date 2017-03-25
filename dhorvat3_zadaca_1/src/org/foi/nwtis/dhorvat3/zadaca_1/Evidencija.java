/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa evidencije sustava.
 *
 * @author Davorin Horvat
 */
public class Evidencija implements Serializable {

    int ukupnoZahtjeva = 0;
    int brojUspjesnihZahtjeva = 0;
    int brojPrekinutihZahtjeva = 0;
    ArrayList<ZahtjeviAdresa> zahtjeviZaAdrese = new ArrayList();

    //TODO dovršiti za ostale podakte 
    public int getUkupnoZahtjeva() {
        return ukupnoZahtjeva;
    }

    public void setUkupnoZahtjeva(int ukupnoZahtjeva) {
        this.ukupnoZahtjeva = ukupnoZahtjeva;
    }

    public int getBrojUspjesnihZahtjeva() {
        return brojUspjesnihZahtjeva;
    }

    public void setBrojUspjesnihZahtjeva(int brojUspjesnihZahtjeva) {
        this.brojUspjesnihZahtjeva = brojUspjesnihZahtjeva;
    }

    public int getBrojPrekinutihZahtjeva() {
        return brojPrekinutihZahtjeva;
    }

    public void setBrojPrekinutihZahtjeva(int brojPrekinutihZahtjeva) {
        this.brojPrekinutihZahtjeva = brojPrekinutihZahtjeva;
    }

    public ArrayList<ZahtjeviAdresa> getZahtjeviZaAdrese() {
        return zahtjeviZaAdrese;
    }

    public void setZahtjeviZaAdrese(ArrayList<ZahtjeviAdresa> zahtjeviZaAdrese) {
        this.zahtjeviZaAdrese = zahtjeviZaAdrese;
    }

}

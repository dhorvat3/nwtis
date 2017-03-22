/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.konfiguracije;

import java.util.Properties;

/**
 *
 * @author grupa_3
 */
public abstract class KonfiguracijaApstraktna implements Konfiguracija{
    protected String datoteka;
    protected Properties postavke = new Properties();

    public KonfiguracijaApstraktna(String datoteka) {
        this.datoteka = datoteka;
    }

    @Override
    public void dodajKonfiguraciju(Properties postavke) {
        this.postavke.putAll(postavke);
    }

    @Override
    public void kopirajKonfiguraciju(Properties postavke) {
        this.postavke.clear();
        dodajKonfiguraciju(postavke);
    }

    @Override
    public Properties dajSvePostavke() {
        return this.postavke;
    }

    @Override
    public boolean obrisiSvePostavke() {
        if(this.postavke.isEmpty()){
            return false;
        } else {
            this.postavke.clear();
            return true;
        }
    }

    @Override
    public String dajPostavku(String postavka) {
        return this.postavke.getProperty(postavka);
    }

    @Override
    public boolean spremiPostavku(String postavka, String vrijednost) {
        if(postojiPostavka(postavka)){
            return false;
        } else {
            this.postavke.setProperty(postavka, vrijednost);
            return true;
        }
    }

    @Override
    public boolean azurirajPostavku(String postavka, String vrijednost) {
        if(!postojiPostavka(postavka)){
            return false;
        } else {
            this.postavke.setProperty(postavka, vrijednost);
            return true;
        }
    }

    @Override
    public boolean postojiPostavka(String postavka) {
        return this.postavke.contains(postavka);
    }

    @Override
    public boolean obrisiPostavku(String postavka) {
        if(!postojiPostavka(postavka)){
            return false;
        } else {
            this.postavke.remove(postavka);
            return true;
        }
    }
    
    public static Konfiguracija kreirajKonfiguraciju(String datoteka) throws NemaKonfiguracije, NeispravnaKonfiguracija {
        if(datoteka == null || datoteka.length() == 0){
            throw new NeispravnaKonfiguracija("Naziv datoteke nije ispravan");
        }
        
        Konfiguracija konfiguracija;
        
        if(datoteka.toLowerCase().endsWith(".txt")){
            konfiguracija = new KonfiguracijaTxt(datoteka);
        } else if(datoteka.toLowerCase().endsWith(".xml")){
            konfiguracija = new KonfiguracijaXml(datoteka);
        } else {
            konfiguracija = new KonfiguracijaBin(datoteka);
        }
        
        konfiguracija.spremiKonfiguraciju();
        return konfiguracija;
    }
    public static Konfiguracija preuzmiKonfiguraciju(String datoteka) throws NemaKonfiguracije, NeispravnaKonfiguracija {
        if(datoteka == null || datoteka.length() == 0){
            throw new NeispravnaKonfiguracija("Naziv datoteke nije ispravan");
        }
        
        Konfiguracija konfiguracija;
        
        if(datoteka.toLowerCase().endsWith(".txt")){
            konfiguracija = new KonfiguracijaTxt(datoteka);
        } else if(datoteka.toLowerCase().endsWith(".xml")){
            konfiguracija = new KonfiguracijaXml(datoteka);
        } else {
            konfiguracija = new KonfiguracijaBin(datoteka);
        }
        konfiguracija.ucitajKonfiguraciju();
        return konfiguracija;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.podaci;

/**
 *
 * @author Davorin Horvat
 */
public class Dnevnik {
    private int id;
    private int tip;
    private String opis;
    private Korisnik korisnik;
    private String vrijeme;

    public Dnevnik() {
    }

    public Dnevnik(int id, int tip, String opis, Korisnik korisnik, String vrijeme) {
        this.id = id;
        this.tip = tip;
        this.opis = opis;
        this.korisnik = korisnik;
        this.vrijeme = vrijeme;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }
    
    
}

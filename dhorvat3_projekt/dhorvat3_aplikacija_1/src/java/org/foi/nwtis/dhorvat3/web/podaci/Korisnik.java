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
public class Korisnik {

    private String korisnickoIme;
    private String ime;
    private String prezime;
    private String ipAdresa;
    private String sesijaId;
    private int vrsta;

    public Korisnik(String korisnickoIme, String ime, String prezime, String ipAdresa, String sesijaId, int vrsta) {
        this.korisnickoIme = korisnickoIme;
        this.ime = ime;
        this.prezime = prezime;
        this.ipAdresa = ipAdresa;
        this.sesijaId = sesijaId;
        this.vrsta = vrsta;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIpAdresa() {
        return ipAdresa;
    }

    public void setIpAdresa(String ipAdresa) {
        this.ipAdresa = ipAdresa;
    }

    public String getSesijaId() {
        return sesijaId;
    }

    public void setSesijaId(String sesijaId) {
        this.sesijaId = sesijaId;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.util.Date;

/**
 * Klasa za zahtjeve adresa.
 *
 * @author Davorin Horvat
 */
public class ZahtjeviAdresa {

    String Korisnik;
    String adresa;
    boolean provjereno;
    Date zadnjaProvjera;

    public ZahtjeviAdresa() {
    }

    public ZahtjeviAdresa(String Korisnik, String adresa, boolean provjereno, Date zadnjaProvjera) {
        this.Korisnik = Korisnik;
        this.adresa = adresa;
        this.provjereno = provjereno;
        this.zadnjaProvjera = zadnjaProvjera;
    }

    public String getKorisnik() {
        return Korisnik;
    }

    public void setKorisnik(String Korisnik) {
        this.Korisnik = Korisnik;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public boolean isProvjereno() {
        return provjereno;
    }

    public void setProvjereno(boolean provjereno) {
        this.provjereno = provjereno;
    }

    public Date getZadnjaProvjera() {
        return zadnjaProvjera;
    }

    public void setZadnjaProvjera(Date zadnjaProvjera) {
        this.zadnjaProvjera = zadnjaProvjera;
    }

}

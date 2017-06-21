/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.foi.nwtis.dhorvat3.rest.klijenti.KorisniciKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "registracija")
@SessionScoped
public class Registracija implements Serializable{
    
    private Korisnik korisnik = new Korisnik();
    private String status = "";
    
    /**
     * Creates a new instance of Registracija
     */
    public Registracija() {
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getStatus() {
        return status;
    }
    
    
    
    public String registracija(){
        KorisniciKlijent klijent = new KorisniciKlijent();
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        job.add("korisnickoIme", korisnik.getKorisnickoIme());
        job.add("ime", korisnik.getIme());
        job.add("prezime", korisnik.getPrezime());
        job.add("password", korisnik.getPassword());
        
        String response = klijent.noviKorisnik(job.build());
        
        if("0".equals(response)){
            status = "Registracija neuspješna!";
            return "Neuspjela";
        } else {
            status = "Registracija uspješna";
            return "Uspjela";
        }
    }
}

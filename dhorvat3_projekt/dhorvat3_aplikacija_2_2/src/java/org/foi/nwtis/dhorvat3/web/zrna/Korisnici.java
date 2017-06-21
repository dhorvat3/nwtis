/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.foi.nwtis.dhorvat3.rest.klijenti.KorisniciKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "korisnici")
@SessionScoped
public class Korisnici implements Serializable {

    private ArrayList<Korisnik> korisnici = new ArrayList<>();
    
    /**
     * Creates a new instance of Korisnici
     */
    public Korisnici() {
    }

    public ArrayList<Korisnik> getKorisnici() {
        korisnici.clear();
        KorisniciKlijent klijent = new KorisniciKlijent();
        Object response = klijent.getJson();
        
        JsonReader reader = Json.createReader(new StringReader((String) response));
        
        JsonArray ja = reader.readArray();
        
        for(int i = 0; i < ja.size(); i++){
            Korisnik korisnik = new Korisnik();
            JsonObject job = ja.getJsonObject(i);
            
            korisnik.setId(job.getInt("uid"));
            korisnik.setKorisnickoIme(job.getString("korime"));
            korisnik.setIme(job.getString("ime"));
            korisnik.setPrezime(job.getString("prezime"));
            
            korisnici.add(korisnik);
        }
        
        return korisnici;
    }

    public void setKorisnici(ArrayList<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }
    
    
    
}

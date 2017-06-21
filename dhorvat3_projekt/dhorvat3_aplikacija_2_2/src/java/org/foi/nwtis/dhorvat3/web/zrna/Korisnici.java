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
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
    private Korisnik profil = new Korisnik();
    
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

    public Korisnik getProfil() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        profil = (Korisnik) korisnik;
        
        return profil;
    }

    public void setProfil(Korisnik profil) {
        this.profil = profil;
    }
    
    public String azuriraj(){
        KorisniciKlijent klijent = new KorisniciKlijent();
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        job.add("id", profil.getId());
        job.add("korisnickoIme", profil.getKorisnickoIme());
        job.add("ime", profil.getIme());
        job.add("prezime", profil.getPrezime());
        job.add("password", profil.getPassword());
        
        String response = klijent.azurirajKorisnika(job.build());
        
        
        if("0".equals(response)){
            return "Neuspjesno";
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = context.getSessionMap();
            sessionMap.put("korisnik", profil);
            return "Uspjesno";
        }
    }
    
    
}

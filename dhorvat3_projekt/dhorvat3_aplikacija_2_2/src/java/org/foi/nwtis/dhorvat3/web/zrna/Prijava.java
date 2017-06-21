/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.foi.nwtis.dhorvat3.rest.klijenti.KorisniciKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "prijava")
@SessionScoped
public class Prijava implements Serializable{

    private String username;
    private String password;
    private Korisnik korisnik = new Korisnik();
    private String status = "";

    /**
     * Creates a new instance of Prijava
     */
    public Prijava() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }
    


    public String prijava() {
        KorisniciKlijent klijent = new KorisniciKlijent();
        Object response = klijent.getUser(username);

        JsonReader reader = Json.createReader(new StringReader((String) response));
        ///JsonArray ja = reader.readArray();
        JsonObject job = reader.readObject();
        
        if (job.containsKey("greska")) {
            reader.close();
            klijent.close();
            status = "Pogrešno korisničko ime";
            return "PogresnoKorisnicko";
        }

        korisnik.setId(job.getInt("uid"));
        korisnik.setKorisnickoIme(job.getString("korime"));
        korisnik.setIme(job.getString("ime"));
        korisnik.setPrezime(job.getString("prezime"));
        korisnik.setPassword(job.getString("pass"));

        System.out.println("Pass: " + password);
        System.out.println("Input pass: " + korisnik.getPassword());
        if (password.equals(korisnik.getPassword())) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = context.getSessionMap();
            sessionMap.put("korisnik", korisnik);
            
            reader.close();
            klijent.close();
            status = "Uspjesna prijava";
            return "IspravanLogin";
        } else {
            reader.close();
            klijent.close();
            status = "Neispravna lozinka";
            return "PogresanPass";
        }
    }
}

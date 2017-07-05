/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import org.foi.nwtis.dhorvat3.rest.klijenti.KorisniciKlijent;
import org.foi.nwtis.dhorvat3.rest.klijenti.UredajiKlijent;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.Uredjaj;
import org.foi.nwtis.dhorvat3.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci;
import org.foi.nwtis.dhorvat3.ws.serveri.NeuspjesnaPrijava_Exception;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "odabirUredjaja")
@RequestScoped
public class OdabirUredjaja {
    
    private List<Uredjaj> uredaji = new ArrayList<>();
    private int id;
    private List<MeteoPodaci> meteoPodaci = new ArrayList<>();
    private String from;
    private String to;
    private Uredjaj noviUredaj = new Uredjaj();
    private String adresa;

    /**
     * Creates a new instance of OdabirUredjaja
     */
    public OdabirUredjaja() {
    }

    public List<Uredjaj> getUredaji() {
        uredaji.clear();
        UredajiKlijent klijent = new  UredajiKlijent();
        Object response = klijent.getJson();
        
        JsonReader reader = Json.createReader(new StringReader((String) response));
        
        JsonArray ja = reader.readArray();
        
        for (int i = 0; i < ja.size(); i++) {
            Uredjaj uredjaj = new Uredjaj();
            JsonObject job = ja.getJsonObject(i);
            
            Lokacija lokacija = new Lokacija();
            
            JsonObject lok = job.getJsonObject("geoloc");
            lokacija.setLatitude(lok.getString("latitude"));
            lokacija.setLongitude(lok.getString("longitude"));
            
            uredjaj.setId(job.getInt("id"));
            uredjaj.setNaziv(job.getString("naziv"));
            uredjaj.setGeoloc(lokacija);
            
            uredaji.add(uredjaj);
        }
        
        return uredaji;
    }

    public void setUredaji(List<Uredjaj> uredaji) {
        this.uredaji = uredaji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MeteoPodaci> getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Uredjaj getNoviUredaj() {
        return noviUredaj;
    }

    public void setNoviUredaj(Uredjaj noviUredaj) {
        this.noviUredaj = noviUredaj;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    
    public void dohvatiMeteoPodatke() {
        System.out.println("Dohvaćanje meteo!");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        meteoPodaci.clear();
        
        UredajiKlijent klijent = new UredajiKlijent();
        Object response = klijent.getJson(id);
        
        JsonReader reader = Json.createReader(new StringReader((String) response));
        
        //JsonArray ja = reader.readArray();
        JsonObject job = reader.readObject();
        
        noviUredaj.setNaziv(job.getString("naziv"));
        
        JsonObject geoloc = job.getJsonObject("geoloc");
        
        try {
            adresa = MeteoWSKlijent.dajAdresuZaUredaj(Float.parseFloat(geoloc.getString("latitude")), Float.parseFloat(geoloc.getString("longitude")), profil.getKorisnickoIme(), profil.getPassword());
        } catch (NeuspjesnaPrijava_Exception ex) {
            Logger.getLogger(OdabirUredjaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            List<MeteoPodaci> dohvaceni;
            dohvaceni = MeteoWSKlijent.dajNekolikoMeteoPodatakaZaUredaj(id, 10, profil.getKorisnickoIme(), profil.getPassword());
            
            
            //System.out.println("Dohvaceni: " + dohvaceni.getPressureUnit());
            for (MeteoPodaci meteoPodaci1 : dohvaceni) {
                meteoPodaci.add(meteoPodaci1);
            }
            
        } catch (NeuspjesnaPrijava_Exception ex) {
            Logger.getLogger(OdabirUredjaja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String azurirajUredaj() {
        Uredjaj uredaj = new Uredjaj();
        uredaj.setId(id);
        uredaj.setNaziv(noviUredaj.getNaziv());
        
        try {
            uredaj.setAdresa(new String(adresa.getBytes("ISO-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OdabirUredjaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        UredajiKlijent klijent = new UredajiKlijent();
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        job.add("id", uredaj.getId());
        job.add("naziv", uredaj.getNaziv());
        job.add("adresa", uredaj.getAdresa());
        
        String response = klijent.azurirajUredaj(job.build());
        
        if("0".equals(response)){
            return "Neuspjesno";
        } else {
            return "Uspjesno";
        }
        
    }
    
    public String noviUredaj() {
        Uredjaj uredaj = new Uredjaj();
        uredaj.setNaziv(noviUredaj.getNaziv());
        uredaj.setAdresa(adresa);
        
        UredajiKlijent klijent = new UredajiKlijent();
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        job.add("naziv", uredaj.getNaziv());
        job.add("adresa", uredaj.getAdresa());
        
        noviUredaj.setNaziv("");
        adresa = "";
        
        String response = klijent.noviUredaj(job.build());
        
        if("0".equals(response))
            return "Neuspjesno";
        else 
            return "Uspjesno";
    }
    
}

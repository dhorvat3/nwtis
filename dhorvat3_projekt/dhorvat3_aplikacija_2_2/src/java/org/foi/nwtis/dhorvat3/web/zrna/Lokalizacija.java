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
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.foi.nwtis.dhorvat3.rest.klijenti.KorisniciKlijent;
import org.foi.nwtis.dhorvat3.web.kontrole.Izbornik;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "lokalizacija")
@SessionScoped
public class Lokalizacija implements Serializable {

    private static final ArrayList<Izbornik> izbornikJezika = new ArrayList();
    private String odabraniJezik = "hr";

    static {
        izbornikJezika.add(new Izbornik("hrvatski", "hr"));
        izbornikJezika.add(new Izbornik("engleski", "en"));
    }

    /**
     * Creates a new instance of Lokalizacija
     */
    public Lokalizacija() {

    }

    public String odjava() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        if (korisnik == null)
            return "NijePrijavljen";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        return "Odjava";
    }

    public ArrayList<Izbornik> getIzbornikJezika() {
        return izbornikJezika;
    }

    public String getOdabraniJezik() {
        UIViewRoot UIVR = FacesContext.getCurrentInstance().getViewRoot();
        if (UIVR != null) {
            Locale lokalniJezik = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            odabraniJezik = lokalniJezik.getLanguage();
        }

        return odabraniJezik;
    }

    public void setOdabraniJezik(String odabraniJezik) {
        this.odabraniJezik = odabraniJezik;
        Locale lokalniJezik = new Locale(odabraniJezik);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(lokalniJezik);
    }

    public Object odaberiJezik() {
        setOdabraniJezik(odabraniJezik);
        return "PromjenaJezika";
    }

    public Object registracija() {
        return "RegistracijaKorisnika";
    }
    
    public Object socket() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        if (korisnik == null)
            return "NijePrijavljen";
        return "PregledSocket";
    }
    
    public Object iot() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        if (korisnik == null)
            return "NijePrijavljen";
        return "PregledIoT";
    }
    
    public Object pregledMail() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        if (korisnik == null)
            return "NijePrijavljen";
        return "PregledMail";
    }

    public Object prijava() {
        return "PrijavaKorisnika";
    }

    public Object pocetna() {
        return "Pocetna";
    }
    
    public Object odabirUredjaja() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        if (korisnik == null)
            return "NijePrijavljen";
        return "OdabirUredjaja";
    }
    
    public Object korisnici() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        if (korisnik == null)
            return "NijePrijavljen";
        return "PregledKorisnika";
    }
    
    public Object dnevnik() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        if (korisnik == null)
            return "NijePrijavljen";
        return "PregledDnevnika";
    }
}

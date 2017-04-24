/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.util.ArrayList;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.dhorvat3.web.kontrole.Izbornik;
import org.foi.nwtis.dhorvat3.web.kontrole.Poruka;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "pregledPoruka")
@RequestScoped
public class PregledPoruka {

    private String posluzitelj;
    private String korisnik;
    private String lozinka;
    
    private ArrayList<Izbornik> mape = new ArrayList<>();
    private String odabranaMapa;
    private ArrayList<Poruka> poruke = new ArrayList<>();
    private int ukupnoPorukaMapa = 0;
    private int brojPirkazanihPoruka = 0;
    private int pozicijaOdPoruka = 0;
    private int pozicijaDoPoruke = 0;
    private String traziPoruke;
    
    /**
     * Creates a new instance of PregledPoruka
     */
    public PregledPoruka() {
        preuzmiMape();
        prethodnePoruke();
    }
    
    void preuzmiMape(){
        //TODO promjeni sa stvanim preuzimanjem mapa
        mape.add(new Izbornik("INBOX", "IZBORNIK"));
        mape.add(new Izbornik("NWTiS_poruke", "NWTiS_poruke"));
        mape.add(new Izbornik("NWTiS_ostale", "NWTiS_ostale"));
        mape.add(new Izbornik("Sent", "Sent"));
        mape.add(new Izbornik("Spam", "Spam"));
    }
    
    void preuzmiPoruke(){
        poruke.clear();
        //TODO promjeni sa stvarnim preuzimanjem poruka
        //TODO razmisli o optimiranju preuzimanja poruka
        int i = 0;
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        poruke.add(new Poruka(Integer.toString(i++), new Date(), new Date(), "pero@localhost", "P " + Integer.toString(i), "Poruka " + Integer.toString(i), "0"));
        
        ukupnoPorukaMapa = poruke.size();
    }
    
    public String promjenaMape(){
        this.preuzmiPoruke();
        return "PromjenaMape";
    }
    
    public String traziPoruke(){
        this.preuzmiPoruke();
        return "FiltirajPoruke";
    }
    
    public String prethodnePoruke(){
        this.preuzmiPoruke();
        return "PrethodnePoruke";
    }
    
    public String sljedecePoruke(){
         this.preuzmiPoruke();
         return "SljedecePoruke";
    }
    
    public String promjenaJezika(){
       return "PromjenaJezika";
    }
    
    public String saljiPoruku(){
       return "SaljiPoruku";
    }

    public ArrayList<Izbornik> getMape() {
        return mape;
    }

    public String getOdabranaMapa() {
        return odabranaMapa;
    }

    public ArrayList<Poruka> getPoruke() {
        return poruke;
    }

    public int getUkupnoPoukaMapa() {
        return ukupnoPorukaMapa;
    }

    public String getTraziPoruke() {
        return traziPoruke;
    }

    public void setOdabranaMapa(String odabranaMapa) {
        this.odabranaMapa = odabranaMapa;
    }

    public void setUkupnoPorukaMapa(int ukupnoPorukaMapa) {
        this.ukupnoPorukaMapa = ukupnoPorukaMapa;
    }

    public void setTraziPoruke(String traziPoruke) {
        this.traziPoruke = traziPoruke;
    }
    
    
    
}

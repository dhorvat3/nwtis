/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.foi.nwtis.dhorvat3.socket.klijenti.SocketClient;
import org.foi.nwtis.dhorvat3.web.kontrole.Izbornik;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "iot")
@SessionScoped
public class Iot implements Serializable {

    private ArrayList<Izbornik> uredaji = new ArrayList<>();
    private Izbornik uredaj;
    private String statusNaredbe;
    
    /**
     * Creates a new instance of Iot
     */
    public Iot() {
    }

    public ArrayList<Izbornik> getUredaji() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; IoT_Master LIST;");
            odgovor = odgovor.replace("{", "");
            String[] parts = odgovor.split("}");
            uredaji.clear();
            for (String part : parts) {
                String regex = "IoT ([1-9]{1,6}) (\"[\\w\\s]+\")";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(part);
                if(matcher.matches()){
                    uredaji.add(new Izbornik(matcher.group(2), matcher.group(1)));
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return uredaji;
    }

    public void setUredaji(ArrayList<Izbornik> uredaji) {
        this.uredaji = uredaji;
    }

    public Izbornik getUredaj() {
        return uredaj;
    }

    public void setUredaj(Izbornik uredaj) {
        this.uredaj = uredaj;
    }

    public String getStatusNaredbe() {
        return statusNaredbe;
    }

    public void setStatusNaredbe(String statusNaredbe) {
        this.statusNaredbe = statusNaredbe;
    }
    
    
    
    public void ucitajIoT(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; IoT_Master LOAD;");
            
            if("OK 10;".equals(odgovor)){
                statusNaredbe = "Uspješno izvršeno.";
            } else {
                statusNaredbe = "Neuspješno izvršeno.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void brisiIoT(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; IoT_Master CLEAR;");
            
            if("OK 10;".equals(odgovor)){
                statusNaredbe = "Uspješno izvršeno.";
            } else {
                statusNaredbe = "Neuspješno izvršeno.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

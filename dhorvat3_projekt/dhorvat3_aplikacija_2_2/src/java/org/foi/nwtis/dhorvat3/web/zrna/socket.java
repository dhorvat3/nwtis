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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.foi.nwtis.dhorvat3.socket.klijenti.SocketClient;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "socket")
@SessionScoped
public class socket implements Serializable {
    
    private String status;
    private String statusNaredbe = "";
    private String statusIoT;

    /**
     * Creates a new instance of socket
     */
    public socket() {
    }

    public String getStatusIoT() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        try {
            klijent.connect();
            statusIoT = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; IoT_Master STATUS;");
            
            if("OK 24;".equals(statusIoT)){
                statusIoT = "IoT Master blokiran.";
            } else if("OK 25".equals(statusIoT)){
                statusIoT = "IoT Master aktivan.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return statusIoT;
    }

    public void setStatusIoT(String statusIoT) {
        this.statusIoT = statusIoT;
    }

    
    
    public String getStatus() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        try {
            klijent.connect();
            status = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; STATUS;");
            
            if("OK 13;".equals(status)){
                status = "Server privremeno ne preuzima podatke.";
            } else if("OK 14;".equals(status)){
                status = "Server preuzima podatke.";
            } else if("OK 15;".equals(status)){
                status = "Server ne preuzima podatke i korisničke komande.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusNaredbe() {
        return statusNaredbe;
    }

    public void setStatusNaredbe(String statusNaredbe) {
        this.statusNaredbe = statusNaredbe;
    }
    
    
    
    public void zaustaviServer(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; STOP;");
            
            if("OK 10;".equals(odgovor)){
                statusNaredbe = "Uspješno izvršeno.";
            } else {
                statusNaredbe = "Neuspješno izvršeno.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pokreniServer(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; START;");
            
            if("OK 10;".equals(odgovor)){
                statusNaredbe = "Uspješno izvršeno.";
            } else {
                statusNaredbe = "Neuspješno izvršeno.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pauzirajServer(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; PAUSE;");
            
            if("OK 10;".equals(odgovor)){
                statusNaredbe = "Uspješno izvršeno.";
            } else {
                statusNaredbe = "Neuspješno izvršeno.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pokreniIoT(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; IoT_Master START;");
            
            if("OK 10;".equals(odgovor)){
                statusNaredbe = "Uspješno izvršeno.";
            } else {
                statusNaredbe = "Neuspješno izvršeno.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zaustaviIoT(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; IoT_Master STOP;");
            
            if("OK 10;".equals(odgovor)){
                statusNaredbe = "Uspješno izvršeno.";
            } else {
                statusNaredbe = "Neuspješno izvršeno.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void aktivirajIoT(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; IoT_Master WORK;");
            
            if("OK 10;".equals(odgovor)){
                statusNaredbe = "Uspješno izvršeno.";
            } else {
                statusNaredbe = "Neuspješno izvršeno.";
            }
        } catch (IOException ex) {
            Logger.getLogger(socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void blokirajIoT(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = context.getSessionMap();
        Object korisnik = sessionMap.get("korisnik");
        
        Korisnik profil = (Korisnik) korisnik;
        
        SocketClient klijent = new SocketClient(15580, "localhost");
        
        try {
            klijent.connect();
            String odgovor = klijent.sendMessage("USER " + profil.getKorisnickoIme() + "; PASSWD " + profil.getPassword() + "; IoT_Master WAIT;");
            
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

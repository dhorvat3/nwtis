/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;
import org.foi.nwtis.dhorvat3.web.podaci.Dnevnik;
import org.foi.nwtis.dhorvat3.web.podaci.Izbornik;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
@ManagedBean(name = "pregled")
@SessionScoped
public class Pregled {

    private ArrayList<Izbornik> menu = new ArrayList();
    private List<Korisnik> korisnici = new ArrayList();
    private List<Dnevnik> zahtjevi = new ArrayList();
    private int trenutnaStranicaKorisnika = 1;
    private int trenutnaStranicaZahtjev = 1;
    private ExternalContext context;
    private int korak = 0;
    private Statement statement;

    /**
     * Creates a new instance of Menu
     */
    public Pregled() {
        context = FacesContext.getCurrentInstance().getExternalContext();
        Konfiguracija konfig = (Konfiguracija) context.getApplicationMap().get("Server_Konfig");
        korak = Integer.parseInt(konfig.dajPostavku("broj_zapisa"));
        try {
            statement = Helper.getStatement((ServletContext) context.getContext());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Pregled.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Izbornik> getMenu() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        menu.clear();
        menu.add(new Izbornik("Početna", "/dhorvat3_aplikacija_1/Kontroler"));

        if (session.getAttribute("korisnik") != null) {
            menu.add(new Izbornik("Pregled korisnika", "/dhorvat3_aplikacija_1/PregledKorisnika"));
            menu.add(new Izbornik("Pregled dnevnika", "/dhorvat3_aplikacija_1/PregledDnevnika"));
            menu.add(new Izbornik("Pregled zahtjeva", "/dhorvat3_aplikacija_1/PregledZahtjeva"));
            menu.add(new Izbornik("Odjava", "/dhorvat3_aplikacija_1/Odjava"));
        } else {
            menu.add(new Izbornik("Prijava", "/dhorvat3_aplikacija_1/Prijava"));
        }

        return menu;
    }

    public void setMenu(ArrayList<Izbornik> menu) {
        this.menu = menu;
    }

    public List<Korisnik> getKorisnici() {
        if (trenutnaStranicaKorisnika == 1 || korisnici.isEmpty()) {
            korisnici.clear();

            try {
                korisnici = dohvatiKorisnike(trenutnaStranicaKorisnika);
            } catch (SQLException ex) {
                Logger.getLogger(Pregled.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public int getTrenutnaStranicaKorisnika() {
        return trenutnaStranicaKorisnika;
    }

    public void setTrenutnaStranicaKorisnika(int trenutnaStranica) {
        this.trenutnaStranicaKorisnika = trenutnaStranica;
    }

    public List<Dnevnik> getZahtjevi() {
        if(trenutnaStranicaZahtjev == 1 || zahtjevi.isEmpty()){
            zahtjevi.clear();
            try {
                zahtjevi = dohvatiZahtjeve(trenutnaStranicaZahtjev);
            } catch (SQLException ex) {
                Logger.getLogger(Pregled.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return zahtjevi;
    }

    public void setZahtjevi(List<Dnevnik> zahtjevi) {
        this.zahtjevi = zahtjevi;
    }

    public int getTrenutnaStranicaZahtjev() {
        return trenutnaStranicaZahtjev;
    }

    public void setTrenutnaStranicaZahtjev(int trenutnaStranicaZahtjev) {
        this.trenutnaStranicaZahtjev = trenutnaStranicaZahtjev;
    }
    
    

    public void slijedeciKorisnici() {
        korisnici.clear();
        try {
            korisnici = dohvatiKorisnike(++trenutnaStranicaKorisnika);
            if (korisnici.isEmpty()) {
                trenutnaStranicaKorisnika--;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pregled.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void prethodniKorisnici() {
        korisnici.clear();
        try {
            if (trenutnaStranicaKorisnika > 1) {
                korisnici = dohvatiKorisnike(--trenutnaStranicaKorisnika);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pregled.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Korisnik> dohvatiKorisnike(int stranica) throws SQLException {
        List<Korisnik> stranicaKorisnika = new ArrayList();
        ResultSet rs;
        stranica--;

        String sql = "SELECT * FROM KORISNICI ORDER BY ID OFFSET " + (stranica * korak) + " ROWS FETCH FIRST " + korak + " ROWS ONLY";
        rs = statement.executeQuery(sql);
        while (rs.next()) {
            String username = rs.getString("korime");
            String name = rs.getString("ime");
            String surname = rs.getString("prezime");

            Korisnik k = new Korisnik(username, name, surname, "", "", 0);
            stranicaKorisnika.add(k);

        }
        return stranicaKorisnika;
    }
    
    public void prethodniZahtjevi(){
        zahtjevi.clear();
        try {
            if(trenutnaStranicaZahtjev > 1)
                zahtjevi = dohvatiZahtjeve(--trenutnaStranicaZahtjev);
        } catch (SQLException ex) {
            Logger.getLogger(Pregled.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void slijedeciZahtjevi(){
        zahtjevi.clear();
        try {
            zahtjevi = dohvatiZahtjeve(++trenutnaStranicaZahtjev);
            if (zahtjevi.isEmpty()) 
                trenutnaStranicaZahtjev--;
        } catch (SQLException ex) {
            Logger.getLogger(Pregled.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Dnevnik> dohvatiZahtjeve(int stranica) throws SQLException{
        List<Dnevnik> stranicaZahtjeva = new ArrayList();
        ResultSet rs;
        stranica--;
        
        String sql = "SELECT * FROM dnevnik WHERE tip=1 ORDER BY ID OFFSET " + (stranica * korak) + " ROWS FETCH FIRST " + korak + " ROWS ONLY";
        rs = statement.executeQuery(sql);
        while(rs.next()){
            int id = rs.getInt("id");
            int tip = rs.getInt("tip");
            String opis = rs.getString("opis");
            String vrijeme = rs.getString("vrijeme");
            
            int idKorisnik = rs.getInt("id_korisnik");
            
            ResultSet rsKorisnik;
            Korisnik korisnik = null;
            try {
                Statement statementZ = Helper.getStatement((ServletContext) context.getContext());
                String sqlKorisnik = "SELECT korime, ime, prezime FROM korisnici WHERE id=" + idKorisnik;
                rsKorisnik = statementZ.executeQuery(sqlKorisnik);
                
                while(rsKorisnik.next()){
                    String username = rsKorisnik.getString("korime");
                    String name = rsKorisnik.getString("ime");
                    String surname = rsKorisnik.getString("prezime");
                    korisnik = new Korisnik(username, name, surname, "", "", 0);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Pregled.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            Dnevnik d = new Dnevnik();
            d.setId(id);
            d.setTip(tip);
            d.setKorisnik(korisnik);
            d.setOpis(opis);
            d.setVrijeme(vrijeme);
            stranicaZahtjeva.add(d);
        }
        
        return stranicaZahtjeva;
    }
}

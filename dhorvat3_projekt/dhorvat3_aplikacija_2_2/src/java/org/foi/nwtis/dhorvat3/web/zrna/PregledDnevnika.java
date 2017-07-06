/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.dhorvat3.web.podaci.Dnevnik;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "pregledDnevnika")
@RequestScoped
public class PregledDnevnika {

    private List<Dnevnik> dnevnik = new ArrayList<>();
    private int trenutnaStranica = 1;
    private int korak = 5;
    private Statement statement;
    private ExternalContext context;
    
    /**
     * Creates a new instance of PregledDnevnika
     */
    public PregledDnevnika() {
        context = FacesContext.getCurrentInstance().getExternalContext();
        String datoteka = context.getRealPath("/WEB-INF") + File.separator + "NWTiS.db.config_1.xml";
        BP_Konfiguracija bp_konfig = new BP_Konfiguracija(datoteka);
        String url = bp_konfig.getServerDatabase() + bp_konfig.getUserDatabase();
        String user = bp_konfig.getUserUsername();
        String password = bp_konfig.getUserPassword();
        try {
            Class.forName(bp_konfig.getDriverDatabase());
            Connection connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PregledDnevnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Dnevnik> getDnevnik() {
        if(trenutnaStranica == 1 || dnevnik.isEmpty()){
            dnevnik.clear();
            try {
                dnevnik = dohvatiDnevnik(trenutnaStranica);
            } catch (SQLException ex) {
                Logger.getLogger(PregledDnevnika.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return dnevnik;
    }

    public void setDnevnik(List<Dnevnik> dnevnik) {
        this.dnevnik = dnevnik;
    }

    public int getTrenutnaStranica() {
        return trenutnaStranica;
    }

    public void setTrenutnaStranica(int trenutnaStranica) {
        this.trenutnaStranica = trenutnaStranica;
    }

    public int getKorak() {
        return korak;
    }

    public void setKorak(int korak) {
        this.korak = korak;
    }
    
    private List<Dnevnik> dohvatiDnevnik(int stranica) throws SQLException {
        List<Dnevnik> stranicaZahtjeva = new ArrayList<>();
        ResultSet rs;
        
        String sql = "SELECT * FROM dnevnik ORDER BY ID OFFSET " + (stranica * korak) + " ROWS FETCH FIRST " + korak + " ROWS ONLY";
        rs = statement.executeQuery(sql);
        while(rs.next()){
            Dnevnik zapis = new Dnevnik();
            
            zapis.setId(rs.getInt("id"));
            zapis.setKorisnik(rs.getString("korisnicko_ime"));
            zapis.setUrl(rs.getString("url"));
            zapis.setDatum(rs.getString("preuzeto"));
            
            stranicaZahtjeva.add(zapis);
        }
        
        return stranicaZahtjeva;
    }
    
    public void prethodniDnevnika() {
        dnevnik.clear();
        try {
            if(trenutnaStranica > 1)
                dnevnik = dohvatiDnevnik(--trenutnaStranica);
        } catch (SQLException ex) {
            Logger.getLogger(PregledDnevnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void slijedeciDnevnik() {
        dnevnik.clear();
        try {
            dnevnik = dohvatiDnevnik(++trenutnaStranica);
            if(dnevnik.isEmpty())
                trenutnaStranica--;
        } catch (SQLException ex) {
            Logger.getLogger(PregledDnevnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

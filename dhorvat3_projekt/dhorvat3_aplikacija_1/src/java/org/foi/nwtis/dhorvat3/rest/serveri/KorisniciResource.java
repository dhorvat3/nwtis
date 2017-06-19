/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.rest.serveri;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;
import org.foi.nwtis.dhorvat3.web.slusaci.SlusacAplikacije;

/**
 * REST Web Service
 *
 * @author Davorin Horvat
 */
public class KorisniciResource {

    private String korisnickoIme;

    /**
     * Creates a new instance of KorisniciResource
     */
    private KorisniciResource(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    /**
     * Get instance of the KorisniciResource
     */
    public static KorisniciResource getInstance(String korisnickoIme) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of KorisniciResource class.
        return new KorisniciResource(korisnickoIme);
    }

    /**
     * Retrieves representation of an instance of org.foi.nwtis.dhorvat3.rest.serveri.KorisniciResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Korisnik korisnik = new Korisnik();
        
        try {
            Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
            String sql = "SELECT id, korime, ime, prezime FROM korisnici WHERE korime='" + this.korisnickoIme + "'";
            ResultSet rs = statement.executeQuery(sql);    
            
            while (rs.next()){
                korisnik.setId(rs.getInt("id"));
                korisnik.setKorisnickoIme(rs.getString("korime"));
                korisnik.setIme(rs.getString("ime"));
                korisnik.setPrezime(rs.getString("prezime"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(KorisniciResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        job.add("uid", korisnik.getId());
        job.add("korime", korisnik.getKorisnickoIme());
        job.add("ime", korisnik.getIme());
        job.add("prezime", korisnik.getPrezime());
        
        return job.build().toString();
    }

    /**
     * PUT method for updating or creating an instance of KorisniciResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource KorisniciResource
     */
    @DELETE
    public void delete() {
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.rest.serveri;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.Uredaj;
import org.foi.nwtis.dhorvat3.web.slusaci.SlusacAplikacije;

/**
 * REST Web Service
 *
 * @author Davorin Horvat
 */
public class UredajiResource {

    private String id;

    /**
     * Creates a new instance of UredajiResource
     */
    private UredajiResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the UredajiResource
     */
    public static UredajiResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of UredajiResource class.
        return new UredajiResource(id);
    }

    /**
     * Retrieves representation of an instance of org.foi.nwtis.dhorvat3.rest.serveri.UredajiResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        try {
            Helper.log(1, 2, "REST: Dohvaćanje jednog uređaja", Helper.getStatement(SlusacAplikacije.getContext()) );
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UredajiResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Uredaj uredaj = new Uredaj();
        
        try{
            Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
            String sql = "SELECT u.id, u.naziv, a.latitude, a.longitude FROM uredaji u LEFT JOIN adrese a ON u.id_adresa=a.id WHERE u.id=" + this.id;
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()){
                uredaj.setId(rs.getInt("id"));
                uredaj.setNaziv(rs.getString("naziv"));
                uredaj.setGeoloc(new Lokacija(rs.getString("latitude"), rs.getString("longitude")));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UredajiResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        job.add("id", uredaj.getId());
        job.add("naziv", uredaj.getNaziv());
        
        JsonObjectBuilder jobGeoloc = Json.createObjectBuilder();
        jobGeoloc.add("latitude", uredaj.getGeoloc().getLatitude());
        jobGeoloc.add("longitude", uredaj.getGeoloc().getLongitude());
        job.add("geoloc", jobGeoloc);
        
        return job.build().toString();
    }

    /**
     * PUT method for updating or creating an instance of UredajiResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource UredajiResource
     */
    @DELETE
    public void delete() {
    }
}

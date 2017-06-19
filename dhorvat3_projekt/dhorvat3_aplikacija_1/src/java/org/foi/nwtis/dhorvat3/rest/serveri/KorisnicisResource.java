/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.rest.serveri;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;
import org.foi.nwtis.dhorvat3.web.slusaci.SlusacAplikacije;

/**
 * REST Web Service
 *
 * @author Davorin Horvat
 */
@Path("/korisniciREST")
public class KorisnicisResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of KorisnicisResource
     */
    public KorisnicisResource() {
    }

    /**
     * Retrieves representation of an instance of org.foi.nwtis.dhorvat3.rest.serveri.KorisnicisResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        ArrayList<Korisnik> korisnici = new ArrayList<>();
        int i = 0;
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        try {
            Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
            String sql = "SELECT id, korime, ime, prezime FROM korisnici";
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()){
                Korisnik korisnik = new Korisnik();
                korisnik.setId(rs.getInt("id"));
                korisnik.setIme(rs.getString("ime"));
                korisnik.setKorisnickoIme(rs.getString("korime"));
                korisnik.setPrezime(rs.getString("prezime"));
                
                
                korisnici.add(korisnik);
            }
            
            
            
            for (Korisnik korisnik : korisnici) {
                i++;
                JsonObjectBuilder job = Json.createObjectBuilder();
                
                job.add("rbr", i);
                job.add("uid", korisnik.getId());
                job.add("korime", korisnik.getKorisnickoIme());
                job.add("ime", korisnik.getIme());
                job.add("prezime", korisnik.getPrezime());
                
                jab.add(job);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(KorisniciResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jab.build().toString();
    }

    /**
     * POST method for creating an instance of KorisniciResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {korisnickoIme}
     */
    @Path("{korisnickoIme}")
    public KorisniciResource getKorisniciResource(@PathParam("korisnickoIme") String korisnickoIme) {
        return KorisniciResource.getInstance(korisnickoIme);
    }
}

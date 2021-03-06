/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.rest.serveri;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.Uredjaj;

/**
 * REST Web Service
 *
 * @author Davorin Horvat
 */
public class MeteoRESTResource {

    private String id;

    /**
     * Creates a new instance of MeteoRESTResource
     */
    private MeteoRESTResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the MeteoRESTResource
     */
    public static MeteoRESTResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MeteoRESTResource class.
        return new MeteoRESTResource(id);
    }

    /**
     * Retrieves representation of an instance of org.foi.nwtis.dhorvat3.rest.serveri.MeteoRESTResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO dovršiti da se podaci čitaju iz baze
        ArrayList<Uredjaj> uredjaji = new ArrayList<>();
        int i = 0;
        Lokacija geoloc = new Lokacija("0.0", "0.0");
        for (; i < 10; i++) {
            uredjaji.add(new Uredjaj(i, "IoT" + i, geoloc));
        }
        
        for (Uredjaj uredjaj : uredjaji) {
            if(uredjaj.getId() == Integer.parseInt(this.id)){
                JsonObjectBuilder job = Json.createObjectBuilder();
                job.add("uid", uredjaj.getId());
                job.add("naziv", uredjaj.getNaziv());
                job.add("lat", uredjaj.getGeoloc().getLatitude());
                job.add("lon", uredjaj.getGeoloc().getLongitude());
                
                return job.build().toString();
            }
        }
        
        return "Nepostoji uređaj sa id: " + this.id;
    }

    /**
     * PUT method for updating or creating an instance of MeteoRESTResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource MeteoRESTResource
     */
    @DELETE
    public void delete() {
    }
}

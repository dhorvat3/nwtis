/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.rest.klijenti;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 * Jersey REST client generated for REST resource:KorisnicisResource
 * [/korisniciREST]<br>
 * USAGE:
 * <pre>
 *        KorisniciKlijent client = new KorisniciKlijent();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Davorin Horvat
 */
public class KorisniciKlijent {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8088/dhorvat3_aplikacija_1/webresources";

    public KorisniciKlijent() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("korisniciREST");
    }

    public String noviKorisnik(Object requestEntity) throws ClientErrorException {
            return webTarget.path("noviKorisnik").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
        }

    public String azurirajKorisnika(Object requestEntity) throws ClientErrorException {
        return webTarget.path("azurirajKorisnika").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
    }

    public String getJson() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }
    
    public String getUser(String username){
        WebTarget resource = webTarget.path(username);
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
    }

    public void close() {
        client.close();
    }
    
}

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
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.Uredaj;
import org.foi.nwtis.dhorvat3.web.slusaci.SlusacAplikacije;

/**
 * REST Web Service
 *
 * @author Davorin Horvat
 */
@Path("/uredajiREST")
public class UredajisResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UredajisResource
     */
    public UredajisResource() {
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.dhorvat3.rest.serveri.UredajisResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        try {
            Helper.log(1, 2, "REST: Dohvati uredaje", Helper.getStatement(SlusacAplikacije.getContext()));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UredajisResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Uredaj> uredaji = new ArrayList();
        int i = 0;
        JsonArrayBuilder jab = Json.createArrayBuilder();

        try {
            Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
            String sql = "SELECT u.id, u.naziv, a.latitude, a.longitude, a.adresa FROM uredaji u LEFT JOIN adrese a ON u.id_adresa=a.id";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Uredaj uredaj = new Uredaj();

                uredaj.setId(rs.getInt("id"));
                uredaj.setNaziv(rs.getString("naziv"));
                uredaj.setGeoloc(new Lokacija(rs.getString("latitude"), rs.getString("longitude")));
                uredaj.setAdresa(rs.getString("adresa"));

                uredaji.add(uredaj);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UredajisResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Uredaj uredaj : uredaji) {
            i++;
            JsonObjectBuilder job = Json.createObjectBuilder();

            job.add("rbr", i);
            job.add("id", uredaj.getId());
            job.add("naziv", uredaj.getNaziv());
            
            JsonObjectBuilder jobGeoloc = Json.createObjectBuilder();
            jobGeoloc.add("latitude", uredaj.getGeoloc().getLatitude());
            jobGeoloc.add("longitude", uredaj.getGeoloc().getLongitude());
            job.add("geoloc", jobGeoloc);

            jab.add(job);
        }

        return jab.build().toString();
    }

    /**
     * POST method for creating an instance of UredajiResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/noviUredaj")
    public int noviUredaj(final Uredaj uredaj) {
        if ("".equals(uredaj.getNaziv()) || "".equals(uredaj.getGeoloc().getLatitude()) || "".equals(uredaj.getGeoloc().getLongitude())) {
            return 0;
        } else {
            try {
                if (checkName(uredaj.getNaziv())) {
                    Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
                    uredaj.setId(Helper.getMaxId("uredaji", Helper.getStatement(SlusacAplikacije.getContext())));
                    String sql = "INSERT INTO uredaji(id, naziv, vrijeme_kreiranja, id_adresa) VALUES ("+ uredaj.getId() +", '"+ uredaj.getNaziv() +"', now(), "+ getIdAdrese(uredaj.getAdresa(), uredaj.getGeoloc().getLatitude(), uredaj.getGeoloc().getLongitude()) +")";
                    int uspijesno = statement.executeUpdate(sql);
                    
                    if(uspijesno != 0){
                        Helper.log(1, 2, "REST: Novi uredaj", Helper.getStatement(SlusacAplikacije.getContext()));
                        return 1;
                    } else
                        return 0;
                                
                } else {
                    return 0;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UredajisResource.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/azurirajUredaj")
    public int azurirajUredaj(final Uredaj uredaj){
        try{
            Helper.log(1, 2, "REST: Azuriraj uredaj", Helper.getStatement(SlusacAplikacije.getContext()));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UredajisResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            if(!checkUredaj(uredaj.getId(), uredaj.getNaziv()) || "".equals(uredaj.getGeoloc().getLatitude()) || "".equals(uredaj.getGeoloc().getLongitude())){
                return 0;
            } else {
                Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
                String sql = "UPDATE uredaji SET naziv='"+ uredaj.getNaziv() +"', id_adresa="+ getIdAdrese(uredaj.getAdresa(), uredaj.getGeoloc().getLatitude(), uredaj.getGeoloc().getLongitude()) +" WHERE id=" + uredaj.getId();
                int uspjesno = statement.executeUpdate(sql);
                
                if(uspjesno != 0)
                    return 1;
                else 
                    return 0;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UredajisResource.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public UredajiResource getUredajiResource(@PathParam("id") String id) {
        return UredajiResource.getInstance(id);
    }
    
    private boolean checkUredaj(int id, String naziv) throws ClassNotFoundException, SQLException{
        boolean postoji = false;
        Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
        String sql = "SELECT * FROM uredaji WHERE id=" + id;
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            postoji = true;
        }
        if(postoji){
            String sqlUredaj = "SELECT * FROM uredaji WHERE naziv='"+ naziv +"'";
            ResultSet rsUredaj = statement.executeQuery(sqlUredaj);
            
            while(rsUredaj.next()){
                if(rsUredaj.getInt("id") != id)
                    postoji = false;
            }
        }
        return postoji;
    }

    private boolean checkName(String name) throws SQLException, ClassNotFoundException {
        Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
        String sql = "SELECT * FROM uredaji WHERE naziv='" + name + "'";
        ResultSet rs = statement.executeQuery(sql);
        int id = 0;

        while (rs.next()) {
            id = rs.getInt("id");
        }
        return id == 0;
    }
    
    private int getIdAdrese(String adresa, String latitude, String longitude) throws ClassNotFoundException, SQLException{
        int id = 0;
        Statement statement = Helper.getStatement(SlusacAplikacije.getContext());
        String sqlAdrese = "SELECT * FROM adrese WHERE adresa='"+ adresa +"'";
        ResultSet rs = statement.executeQuery(sqlAdrese);
        while(rs.next())
            id = rs.getInt("id");
        if(id == 0){
            String sqlInsert = "INSERT INTO adrese(adresa, latitude, longitude) VALUES ('"+ adresa +"', "+ latitude +", "+ longitude +")";
            statement.executeUpdate(sqlInsert);
            ResultSet rsInsert = statement.getGeneratedKeys();
            if(rsInsert.next())
                id = rsInsert.getInt(1);
        }
        
        
        return id;
    }
}

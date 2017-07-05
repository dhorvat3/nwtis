/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.ws.serveri;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;
import org.foi.nwtis.dhorvat3.socket.helper.MeteoHelper;
import org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPodaci;
import org.foi.nwtis.dhorvat3.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Davorin Horvat
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    private ServletContext context;
    private Statement statement;

    /**
     * Web service operation
     *
     * @param id
     * @param username
     * @param password
     * @return
     * @throws org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava
     */
    @WebMethod(operationName = "dajZadnjeMeteoPodatkeZaUredaj")
    public MeteoPodaci dajZadnjeMeteoPodatkeZaUredaj(@WebParam(name = "id") int id, @WebParam(name = "username") String username, @WebParam(name = "password") String password) throws NeuspjesnaPrijava {
        MeteoPodaci meteoPodaci = new MeteoPodaci();
        context = SlusacAplikacije.getContext();

        try {
            statement = Helper.getStatement(context);
            int idKorisnik = Helper.dohvatiId(username, password, statement);
            Helper.log(idKorisnik, 2, "SOAP: Dohvati zadnje meteo podatke za uredaj: " + id, statement);

            String sql = "SELECT * FROM meteo WHERE id=" + id + " ORDER BY id DESC FETCH FIRST 1 ROWS ONLY";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                meteoPodaci.setTemperatureValue(rs.getFloat("temp"));
                meteoPodaci.setTemperatureMin(rs.getFloat("tempmin"));
                meteoPodaci.setTemperatureMax(rs.getFloat("tempmax"));
                meteoPodaci.setTemperatureUnit("°C");
                meteoPodaci.setPressureValue(rs.getFloat("tlak"));
                meteoPodaci.setPressureUnit("hPa");
                meteoPodaci.setHumidityValue(rs.getFloat("vlaga"));
                meteoPodaci.setHumidityUnit("%");
                meteoPodaci.setLastUpdate(rs.getDate("preuzeto"));

            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return meteoPodaci;
    }

    /**
     * Web service operation
     *
     * @param id
     * @param n
     * @param username
     * @param password
     * @return
     * @throws org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava
     */
    @WebMethod(operationName = "dajNekolikoMeteoPodatakaZaUredaj")
    public java.util.List<MeteoPodaci> dajNekolikoMeteoPodatakaZaUredaj(@WebParam(name = "id") int id, @WebParam(name = "n") int n, @WebParam(name = "username") String username, @WebParam(name = "password") String password) throws NeuspjesnaPrijava {
        List<MeteoPodaci> sviMeteoPodaci = new ArrayList<>();
        context = SlusacAplikacije.getContext();

        try {
            statement = Helper.getStatement(context);
            int idKorisnik = Helper.dohvatiId(username, password, statement);
            Helper.log(idKorisnik, 2, "SOAP: Daj nekoliko za id: " + id + " n: " + n, statement);

            //String sql = "SELECT * FROM meteo WHERE id=" + id + " ORDER BY id DESC FETCH FIRST " + n + " ROWS ONLY";
            String sql = "SELECT * FROM meteo WHERE id=" + MeteoHelper.dajIdLokacije(id, statement) + " ORDER BY id DESC LIMIT " + n;
            System.out.println("SQL " + sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MeteoPodaci meteoPodaci = new MeteoPodaci();

                meteoPodaci.setTemperatureValue(rs.getFloat("temp"));
                meteoPodaci.setTemperatureMin(rs.getFloat("tempmin"));
                meteoPodaci.setTemperatureMax(rs.getFloat("tempmax"));
                meteoPodaci.setTemperatureUnit("°C");
                meteoPodaci.setPressureValue(rs.getFloat("tlak"));
                meteoPodaci.setPressureUnit("hPa");
                meteoPodaci.setHumidityValue(rs.getFloat("vlaga"));
                meteoPodaci.setHumidityUnit("%");
                meteoPodaci.setLastUpdate(rs.getDate("preuzeto"));

                sviMeteoPodaci.add(meteoPodaci);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sviMeteoPodaci;
    }

    /**
     * Web service operation
     *
     * @param id
     * @param from
     * @param to
     * @param username
     * @param password
     * @return
     * @throws org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava
     */
    @WebMethod(operationName = "dajZaRazdobljeMeteoPodatkeZaUredaj")
    public java.util.List<MeteoPodaci> dajZaRazdobljeMeteoPodatkeZaUredaj(@WebParam(name = "id") int id, @WebParam(name = "from") long from, @WebParam(name = "to") long to, @WebParam(name = "username") String username, @WebParam(name = "password") String password) throws NeuspjesnaPrijava {
        List<MeteoPodaci> sviMeteoPodaci = new ArrayList<>();
        context = SlusacAplikacije.getContext();

        try {
            statement = Helper.getStatement(context);
            int idKorisnik = Helper.dohvatiId(username, password, statement);
            Helper.log(idKorisnik, 2, "SOAP: dohvati za razdoblje id: " + id, statement);
            Date fromDate = new Date(from);
            String fromString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fromDate);
            Date toDate = new Date(to);
            String toString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(toDate);

            String sql;
            if (from != 0 && to != 0) {
                sql = "SELECT * FROM METEO WHERE ID = " + id + " AND PREUZETO > '" + fromString + "' AND PREUZETO < '" + toString + "'";
            } else {
                sql = "SELECT * FROM METEO WHERE ID = " + id;
            }
            System.out.println("SOAP SQL: " + sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MeteoPodaci meteoPodaci = new MeteoPodaci();

                meteoPodaci.setTemperatureValue(rs.getFloat("temp"));
                meteoPodaci.setTemperatureMin(rs.getFloat("tempmin"));
                meteoPodaci.setTemperatureMax(rs.getFloat("tempmax"));
                meteoPodaci.setTemperatureUnit("°C");
                meteoPodaci.setPressureValue(rs.getFloat("tlak"));
                meteoPodaci.setPressureUnit("hPa");
                meteoPodaci.setHumidityValue(rs.getFloat("vlaga"));
                meteoPodaci.setHumidityUnit("%");
                meteoPodaci.setLastUpdate(rs.getDate("preuzeto"));

                sviMeteoPodaci.add(meteoPodaci);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sviMeteoPodaci;
    }

    /**
     * Web service operation
     *
     * @param id
     * @param username
     * @param password
     * @return
     * @throws org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava
     */
    @WebMethod(operationName = "dajNajnovijeMeteoPodatkeZaUredaj")
    public MeteoPodaci dajNajnovijeMeteoPodatkeZaUredaj(@WebParam(name = "id") int id, @WebParam(name = "username") String username, @WebParam(name = "password") String password) throws NeuspjesnaPrijava {
        MeteoPodaci meteoPodaci = new MeteoPodaci();
        context = SlusacAplikacije.getContext();

        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
        String apiKey = konfig.dajPostavku("apikey");

        try {
            statement = Helper.getStatement(context);
            int idKorisnik = Helper.dohvatiId(username, password, statement);
            Helper.log(idKorisnik, 2, "SOAP: Najnoviji meteo id: " + id, statement);

            String sqlUredaj = "SELECT id_adresa FROM uredaji WHERE id=" + id;
            ResultSet rsUredaj = statement.executeQuery(sqlUredaj);
            while (rsUredaj.next()) {
                int idAdresa = rsUredaj.getInt("id_adresa");
                String sqlAdresa = "SELECT * FROM adrese WHERE id=" + idAdresa;
                Statement statementA = Helper.getStatement(context);
                ResultSet rsAdresa = statementA.executeQuery(sqlAdresa);
                while (rsAdresa.next()) {
                    String latitude = rsAdresa.getString("latitude");
                    String longitude = rsAdresa.getString("longitude");

                    meteoPodaci = MeteoHelper.dajMeteoPodatke(latitude, longitude, apiKey);
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return meteoPodaci;
    }

    /**
     * Web service operation
     *
     * @param lattitude
     * @param longitude
     * @param username
     * @param password
     * @return
     */
    @WebMethod(operationName = "dajAdresuZaUredaj")
    public String dajAdresuZaUredaj(@WebParam(name = "lattitude") float lattitude, @WebParam(name = "longitude") float longitude, @WebParam(name = "username") String username, @WebParam(name = "password") String password) throws NeuspjesnaPrijava {
        String adresa = "";
        context = SlusacAplikacije.getContext();
        
        try{
            statement = Helper.getStatement(context);
            int idKorisnik = Helper.dohvatiId(username, password, statement);
            Helper.log(idKorisnik, 2, "SOAP: Adresa lat:" + lattitude + " lon: " + longitude, statement);
            
            adresa = MeteoHelper.dajAdresu(lattitude, longitude);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return adresa;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.dhorvat3.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci;
import org.foi.nwtis.dhorvat3.ws.serveri.Uredjaj;

/**
 * Drugi projekt sastoji se od korisničkog dijela u kojem se mogu obaviti dvije
 * aktivnosti:
 *
 * unositi pojedinačne IoT uređaje za koje će se preuzimati metorološki podaci.
 * Unese se naziv IoT uređaja i adresa. Postoje dva gumba "Upiši SOAP" i "Upiši
 * REST". Svaki od njih pokreće akciju koja poziva određenu operaciju web
 * servisa (SOAP i REST) iz prvog projekta. preuzeti IoT uređaje za koje se
 * prikupljaju meteorološki podaci u prvom projektu te se prikazuju u obliku
 * padajućeg izbornika s mogućim odabirom više elemenata. Postoje elementi za
 * unos od i do intervala. Ako se odabere samo jedan IoT uređaj tada se može
 * aktivirati gumb "Preuzmi SOAP" kojim se pokreće akcija koja će preuzeti sve
 * pohranjenje meteorološke podatke za odabrani IoT uređaj putem SOAP web
 * servisa iz prvog projekta. Preuzeti meteorološki podaci prikazuju se u obliku
 * tablice.
 *
 * Ako se odabere minimalno dva IoT uređaja tada se može aktivirati gumb
 * "Preuzmi REST" kojim se pokreće akcija koja će preuzeti putem REST web
 * servisa iz prvog projekta preuzimaju važaći meteorološki podaci za odabrane
 * IoT uređaje. Preuzeti meteorološki podaci prikazuju se u obliku tablice.
 *
 *
 * @author Davorin Horvat
 */
@Named(value = "odabirUredjaja")
@RequestScoped
public class OdabirUredjaja {

    private List<Uredjaj> uredaji;
    private List<String> id;
    private List<MeteoPodaci> meteoPodaci;
    private String from;
    private String to;
    private Uredjaj noviUredjaj = new Uredjaj();
    private String adresa;

    /**
     * Creates a new instance of OdabirUredjaja
     */
    public OdabirUredjaja() {

    }

    /**
     * Dohvaćanje svih uređaja.
     * @return 
     */
    public List<Uredjaj> getUredaji() {
        uredaji = MeteoWSKlijent.dajSveUredjaje();
        return uredaji;
    }

    public void setUredaji(List<Uredjaj> uredaj) {
        this.uredaji = uredaj;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<MeteoPodaci> getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Uredjaj getNoviUredjaj() {
        return noviUredjaj;
    }

    public void setNoviUredjaj(Uredjaj noviUredjaj) {
        this.noviUredjaj = noviUredjaj;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     * preuzeti IoT uređaje za koje se prikupljaju meteorološki podaci u prvom
     * projektu te se prikazuju u obliku padajućeg izbornika s mogućim odabirom
     * više elemenata. Postoje elementi za unos od i do intervala.
     */
    public void dohvatiMeteopodatke() {
        System.out.println("dohvacanje");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy. hh:mm");
        Date fromDate;
        Date toDate;
        try {
            fromDate = dateFormat.parse(from);
            toDate = dateFormat.parse(to);
        } catch (ParseException ex) {
            Logger.getLogger(OdabirUredjaja.class.getName()).log(Level.SEVERE, null, ex);
            fromDate = null;
            toDate = null;
        }
        meteoPodaci = new ArrayList<>();
        for (String identifikacija : id) {
            List<MeteoPodaci> dohvaceni;
            long fromUnix = 0;
            if (fromDate != null) {
                fromUnix = (long) fromDate.getTime();
            }
            long toUnix = 0;
            if (toDate != null) {
                toUnix = (long) toDate.getTime();
            }
            dohvaceni = MeteoWSKlijent.dajSveMeteoPodatkeZaUredjaj(Integer.parseInt(identifikacija), fromUnix, toUnix);

            for (MeteoPodaci meteo : dohvaceni) {
                meteoPodaci.add(meteo);
            }
        }
    }

    /**
     * unositi pojedinačne IoT uređaje za koje će se preuzimati metorološki
     * podaci. Unese se naziv IoT uređaja i adresa. Postoje dva gumba "Upiši
     * SOAP" i "Upiši REST". Svaki od njih pokreće akciju koja poziva određenu
     * operaciju web servisa (SOAP i REST) iz prvog projekta.
     */
    public void dodajUredjaj() {
        boolean dodan = false;
        dodan = MeteoWSKlijent.dodajUredjaj(noviUredjaj, adresa);

        if (dodan) {
            getUredaji();
        }
    }

}

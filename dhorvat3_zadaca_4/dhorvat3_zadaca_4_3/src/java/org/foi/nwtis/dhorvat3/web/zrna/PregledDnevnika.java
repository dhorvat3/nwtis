/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.foi.nwtis.dhorvat3.ejb.eb.Dnevnik;
import org.foi.nwtis.dhorvat3.ejb.sb.DnevnikFacade;
import org.foi.nwtis.dhorvat3.web.kontrole.Izbornik;

/**
 * Pregled dnevnika obavlja se u posebnom pogledu. Mogu se unijeti podaci za
 * filtiranje na bazi; vremena od-do, ipadrese, trajanja i statusa. Može se
 * unijeti od ni jednog pa do svih podataka. Nakon pokretanja pregleda (putem
 * gumba Prikaži) prikazuju se filtirani podaci iz dnevnika.
 *
 * @author Davorin Horvat
 */
@Named(value = "pregledDnevnika")
@SessionScoped
public class PregledDnevnika implements Serializable {

    @EJB
    private DnevnikFacade dnevnikFacade;

    private String vrijemeOd;
    private String vrijemeDo;
    private String ipAdresa;
    private int status;
    private List<Izbornik> statusi = new ArrayList<>();
    private List<Dnevnik> rezultati = new ArrayList<>();

    /**
     * Creates a new instance of PregledDnevnika
     */
    public PregledDnevnika() {
        statusi.add(new Izbornik("Preuzmi raspolozive", "1"));
        statusi.add(new Izbornik("Dodaj uređaj", "2"));
        statusi.add(new Izbornik("Preuzmi uređaj", "3"));
        statusi.add(new Izbornik("Ažuriraj uređaj", "4"));
        statusi.add(new Izbornik("Prikaži prognozu", "5"));
    }

    public List<Dnevnik> getRezultati() {
        return rezultati;
    }

    public void setRezultati(List<Dnevnik> rezultati) {
        this.rezultati = rezultati;
    }

    public String getVrijemeOd() {
        return vrijemeOd;
    }

    public void setVrijemeOd(String vrijemeOd) {
        this.vrijemeOd = vrijemeOd;
    }

    public String getVrijemeDo() {
        return vrijemeDo;
    }

    public void setVrijemeDo(String vrijemeDo) {
        this.vrijemeDo = vrijemeDo;
    }

    public String getIpAdresa() {
        return ipAdresa;
    }

    public void setIpAdresa(String ipAdresa) {
        this.ipAdresa = ipAdresa;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Izbornik> getStatusi() {
        return statusi;
    }

    public void setStatusi(List<Izbornik> statusi) {
        this.statusi = statusi;
    }

    /**
     * Pretraživanje dnevnika po parametrima.
     */
    public void pretrazi() {
        rezultati.clear();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        List<Dnevnik> sviZapisi = dnevnikFacade.findAll();
        System.out.println("Ulaz" + vrijemeOd + " " + vrijemeDo);
        for (Dnevnik dnevnik : sviZapisi) {
            boolean ispravan = true;
            Date datumOd = null;
            Date datumDo = null;
            try {
                if (vrijemeOd != null && !"".equals(vrijemeOd)) {
                    datumOd = format.parse(vrijemeOd);
                }
                if (vrijemeDo != null && !"".equals(vrijemeDo)) {
                    datumDo = format.parse(vrijemeDo);
                }
            } catch (ParseException ex) {
                Logger.getLogger(PregledDnevnika.class.getName()).log(Level.SEVERE, null, ex);
            }

            //System.out.println("Datum: " + dnevnik.getVrijeme().getTime()/1000);
            long vrijeme = dnevnik.getVrijeme().getTime() / 1000;

            if (datumOd != null) {
                long tOd = datumOd.getTime() / 1000;
                System.out.println("IF");
                if (vrijeme < tOd) {
                    System.out.println("Neispravno vrijeme OD");
                    ispravan = false;
                }
            }
            if (datumDo != null) {
                long tDo = datumDo.getTime() / 1000;
                if (vrijeme > tDo) {
                    System.out.println("Neispravno vrijeme Do");
                    ispravan = false;
                }
            }
            if (status != 0) {
                if (dnevnik.getStatus() != status) {
                    System.out.println("Neispravno status");
                    ispravan = false;
                }
            }
            if (ipAdresa != null && !"".equals(ipAdresa)) {
                if (!dnevnik.getIpadresa().equals(ipAdresa)) {
                    System.out.println("Neipsravno ip");
                    ispravan = false;
                }
            }
            if (ispravan) {
                rezultati.add(dnevnik);
            }
        }
    }

}

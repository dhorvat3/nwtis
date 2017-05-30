/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.foi.nwtis.dhorvat3.ejb.eb.Promjene;
import org.foi.nwtis.dhorvat3.ejb.sb.PromjeneFacade;

/**
 * Pregled promjena obavlja se u posebnom pogledu. Mogu se unijeti podaci za
 * filtiranje na bazi; id, naziv. Može se unijeti od ni jednog pa do svih
 * podataka. Nakon pokretanja pregleda (putem gumba Prikaži) prikazuju se
 * filtirani podaci iz promjena.
 *
 * @author Davorin Horvat
 */
@Named(value = "pregledPromjena")
@SessionScoped
public class PregledPromjena implements Serializable {

    @EJB
    private PromjeneFacade promjeneFacade;

    private int id;
    private String naziv;
    private List<Promjene> rezultati = new ArrayList<>();

    /**
     * Creates a new instance of PregledPromjena
     */
    public PregledPromjena() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Promjene> getRezultati() {
        return rezultati;
    }

    public void setRezultati(List<Promjene> rezultati) {
        this.rezultati = rezultati;
    }

    /**
     * Pretraživanje promjena po parametrima.
     */
    public void pretrazi() {
        rezultati.clear();
        List<Promjene> sviZapisi = promjeneFacade.findAll();
        System.out.println("Naziv: " + naziv);
        System.out.println("ID: " + id);
        for (Promjene promjene : sviZapisi) {
            boolean ispravan = true;
            if (id > 0) {
                if (promjene.getId() != id) {
                    ispravan = false;
                }
            }
            if (naziv != null && !"".equals(naziv)) {
                if (!promjene.getNaziv().equals(naziv)) {
                    ispravan = false;
                }
            }
            System.out.println("ispravan" + ispravan);
            if (ispravan) {
                rezultati.add(promjene);
            }
        }
    }
}

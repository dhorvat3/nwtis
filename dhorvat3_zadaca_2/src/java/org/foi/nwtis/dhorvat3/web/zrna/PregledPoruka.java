/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.web.kontrole.Izbornik;
import org.foi.nwtis.dhorvat3.web.kontrole.Poruka;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "pregledPoruka")
@RequestScoped
public class PregledPoruka {

    private String posluzitelj;
    private String korisnik;
    private String lozinka;

    private ArrayList<Izbornik> mape = new ArrayList<>();
    private String odabranaMapa;
    private ArrayList<Poruka> poruke = new ArrayList<>();
    private int ukupnoPorukaMapa = 0;
    private int brojPirkazanihPoruka = 0;
    private int pozicijaOdPoruka = 0;
    private int pozicijaDoPoruke = 0;
    private String traziPoruke;

    private Store store;

    /**
     * Creates a new instance of PregledPoruka
     */
    public PregledPoruka() {
        try {
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            Konfiguracija konfig = (Konfiguracija) context.getAttribute("Mail_Konfig");

            this.posluzitelj = konfig.dajPostavku("mail.server");
            this.korisnik = konfig.dajPostavku("mail.usernameView");
            this.lozinka = konfig.dajPostavku("mail.passwordView");

            Properties properties = System.getProperties();
            properties.put("mail.imap.host", this.posluzitelj);
            Session session = Session.getInstance(properties, null);

            store = session.getStore("imap");
            store.connect(this.posluzitelj, this.korisnik, this.lozinka);

            preuzmiMape();
            prethodnePoruke();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void preuzmiMape() {
        try {
            Folder[] folders = store.getDefaultFolder().list();
            for (Folder f : folders) {
                mape.add(new Izbornik(f.getName() + "-" + f.getMessageCount(), f.getName()));
            }

        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void preuzmiPoruke() {

        poruke.clear();
        //TODO razmisli o optimiranju preuzimanja poruka

        try {
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();

            for (int i = 0; i < messages.length; i++) {

                Date vrijemeSlanja = messages[i].getSentDate();
                Date vrijemePrijema = messages[i].getReceivedDate();
                String salje = messages[i].getFrom()[0].toString();
                String predmet = messages[i].getSubject();
                String sadrzaj = messages[i].getContent().toString();
                String vrsta = messages[i].getContentType();

                poruke.add(new Poruka(String.valueOf(i), vrijemeSlanja, vrijemePrijema, salje, predmet, sadrzaj, vrsta));
            }
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }

        ukupnoPorukaMapa = poruke.size();
    }

    public String promjenaMape() {
        this.preuzmiPoruke();
        return "PromjenaMape";
    }

    public String traziPoruke() {
        this.preuzmiPoruke();
        return "FiltirajPoruke";
    }

    public String prethodnePoruke() {
        this.preuzmiPoruke();
        return "PrethodnePoruke";
    }

    public String sljedecePoruke() {
        this.preuzmiPoruke();
        return "SljedecePoruke";
    }

    public String promjenaJezika() {
        return "PromjenaJezika";
    }

    public String saljiPoruku() {
        return "SaljiPoruku";
    }

    public ArrayList<Izbornik> getMape() {
        return mape;
    }

    public String getOdabranaMapa() {
        return odabranaMapa;
    }

    public ArrayList<Poruka> getPoruke() {
        return poruke;
    }

    public int getUkupnoPorukaMapa() {
        return ukupnoPorukaMapa;
    }

    public String getTraziPoruke() {
        return traziPoruke;
    }

    public void setOdabranaMapa(String odabranaMapa) {
        this.odabranaMapa = odabranaMapa;
    }

    public void setUkupnoPorukaMapa(int ukupnoPorukaMapa) {
        this.ukupnoPorukaMapa = ukupnoPorukaMapa;
    }

    public void setTraziPoruke(String traziPoruke) {
        this.traziPoruke = traziPoruke;
    }

}

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
import javax.faces.bean.ViewScoped;
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
 * Kod odabira pregleda primljenih email poruka prikazuje se padajući izbornik u
 * kojem su elementi mape (direktoriji) iz email korisničkog računa (radi se o
 * objektima klasa javax.mail.Folder i javax.mail.Store) i gump za promjenu
 * mape. Ispod toga je dio za pretraživanje email poruka koji se sastoji od
 * pripadajuće labele, jedno linijskog unosa teksa i gumba za pretraživanje. U
 * izborniku kod prikaza pojedine mape treba uz naziv mape dodati i ukupan broj
 * poruka u njojm npr: INBOX - 14. Na početku je odabrana mapa INBOX. Slijedi
 * tablica s prikazom informacija o n (određen konfiguracijom, npr. brojPoruka)
 * najsvježijih poruka (objekti klase javax.mail.Message) iz odabrane mape i na
 * koje je primijenjeno pretraživanje. NE SMIJU se čitati sve poruke iz mape
 * nego samo onoliko koliko je potrebno. Ispod tablice prikazuje se ukupan broj
 * email poruka u izabranoj mapi, gumb za prethodne i sljedeće poruke (ako nema
 * treba pojedini gumb blokirati). Aktiviranjem pojedinog gumba treba prikazati
 * izabrani skup email poruka. U zaglavlju treba biti poveznica na izbor jezika
 * i poveznica na slanje email poruke.
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
    private String odabranaMapa = "INBOX";
    private ArrayList<Poruka> poruke = new ArrayList<>();
    private int ukupnoPorukaMapa = 1;
    private int brojPirkazanihPoruka = 0;
    private int pozicijaOdPoruka = 1;
    private int pozicijaDoPoruke = 1;
    private int korakPoruka = 0;
    private String traziPoruke;

    private int naprijed = -1;
    private Store store;
    private ServletContext context;

    /**
     * Dohvaća podatke o mail serveru i spaja se na server
     *
     * Creates a new instance of PregledPoruka
     */
    public PregledPoruka() {
        try {
            context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
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
            preuzmiPoruke();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Dohvaća mape korisnika na mail serveru.
     */
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

    /**
     * Dohvaća mailove korisnika iz mape, sa paginacijom, pretraživanje
     * dohvaćenih poruka.
     */
    void preuzmiPoruke() {
        poruke.clear();
        int numberOfMessages = 0;
        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Mail_Konfig");
        this.korakPoruka = Integer.parseInt(konfig.dajPostavku("mail.numMessages"));
        if (context.getAttribute("pozicijaOd") != null) {
            this.pozicijaOdPoruka = (int) context.getAttribute("pozicijaOd");
        }

        try {
            Folder folder = store.getFolder(odabranaMapa);
            folder.open(Folder.READ_ONLY);
            numberOfMessages = folder.getMessageCount();
            poruke.clear();

            if (!(numberOfMessages < 1)) {
                if (naprijed == 1) {
                    pozicijaOdPoruka += korakPoruka;
                    pozicijaDoPoruke = pozicijaOdPoruka + korakPoruka;
                } else if (naprijed == 0) {
                    pozicijaOdPoruka -= korakPoruka;
                    if (pozicijaOdPoruka <= 1) {
                        pozicijaOdPoruka = 1;
                    }
                    pozicijaDoPoruke = pozicijaOdPoruka + korakPoruka;
                } else if (naprijed == -1) {
                    pozicijaDoPoruke += korakPoruka;
                } else if (naprijed == 2) {
                    pozicijaDoPoruke = pozicijaOdPoruka + korakPoruka;
                }

                if (pozicijaDoPoruke > numberOfMessages + 1) {
                    pozicijaDoPoruke = numberOfMessages + 1;
                    pozicijaOdPoruka = pozicijaDoPoruke - korakPoruka;
                }
                if (pozicijaOdPoruka < 1) {
                    pozicijaOdPoruka = 1;
                    pozicijaDoPoruke = 1 + korakPoruka;
                }
                System.out.println("Pozicija od: " + pozicijaOdPoruka);
                System.out.println("Pozicija do: " + pozicijaDoPoruke);
                context.removeAttribute("pozicijaOd");
                context.setAttribute("pozicijaOd", pozicijaOdPoruka);
                Message[] messages = folder.getMessages(pozicijaOdPoruka, pozicijaDoPoruke - 1);

                for (int i = 0; i < messages.length; i++) {
                    System.out.println("Traži: " + traziPoruke);
                    Date vrijemeSlanja = messages[i].getSentDate();
                    Date vrijemePrijema = messages[i].getReceivedDate();
                    String salje = messages[i].getFrom()[0].toString();
                    String predmet = messages[i].getSubject();
                    String sadrzaj = messages[i].getContent().toString();
                    String vrsta = messages[i].getContentType();

                    if (traziPoruke != "") {
                        if (sadrzaj.toLowerCase().contains(traziPoruke.toLowerCase())) {
                            poruke.add(new Poruka(String.valueOf(i), vrijemeSlanja, vrijemePrijema, salje, predmet, sadrzaj, vrsta));
                        }
                    } else {
                        poruke.add(new Poruka(String.valueOf(i), vrijemeSlanja, vrijemePrijema, salje, predmet, sadrzaj, vrsta));
                    }
                }
                traziPoruke = "";
            }
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }

        ukupnoPorukaMapa = numberOfMessages;
    }

    public String promjenaMape() {
        this.preuzmiPoruke();
        return "PromjenaMape";
    }

    public String traziPoruke() {
        naprijed = 2;
        this.preuzmiPoruke();
        return "FiltirajPoruke";
    }

    public String prethodnePoruke() {
        this.naprijed = 0;
        this.preuzmiPoruke();
        return "PrethodnePoruke";
    }

    public String sljedecePoruke() {
        this.naprijed = 1;
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

    public int getPozicijaOdPoruka() {
        return pozicijaOdPoruka;
    }

    public void setPozicijaOdPoruka(int pozicijaOdPoruka) {
        this.pozicijaOdPoruka = pozicijaOdPoruka;
    }

    public int getPozicijaDoPoruke() {
        return pozicijaDoPoruke;
    }

    public void setPozicijaDoPoruke(int pozicijaDoPoruke) {
        this.pozicijaDoPoruke = pozicijaDoPoruke;
    }

}

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
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.web.kontrole.Izbornik;
import org.foi.nwtis.dhorvat3.web.podaci.Poruka;

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
    private String odabranaMapa = "INBOX";
    private ArrayList<Poruka> poruke = new ArrayList<>();
    private int ukuponoPorukaMapa = 1;
    private int pozicijaOdPoruke = 1;
    private int pozicijaDoPoruke = 1;
    private int korakPoruka = 0;
    private String traziPoruke = "";
    
    private int naprijed = -1;
    private Store store;
    private ServletContext context;

    /**
     * Creates a new instance of PregledPoruka
     */
    public PregledPoruka() {
        try {
            context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            Konfiguracija konfig = (Konfiguracija) context.getAttribute("Mail_Konfig");
            
            posluzitelj = konfig.dajPostavku("mail.server");
            korisnik = konfig.dajPostavku("mail.usernameView");
            lozinka = konfig.dajPostavku("mail.passwordView");
            
            Properties properties = System.getProperties();
            properties.put("mail.imap.host", posluzitelj);
            Session session = Session.getInstance(properties, null);
            
            store = session.getStore("imap");
            store.connect(posluzitelj, korisnik, lozinka);
            
            preuzmiMape();
            preuzmiPoruke();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void preuzmiMape(){
        try {
            Folder[] folders = store.getDefaultFolder().list();
            for (Folder folder : folders) {
                mape.add(new Izbornik(folder.getName() + "-" + folder.getMessageCount(), folder.getName()));
            }
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void preuzmiPoruke(){
        poruke.clear();
        int numberOfMessages = 0;
        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Mail_Konfig");
        korakPoruka = Integer.parseInt(konfig.dajPostavku("mail.numMessages"));
        if(context.getAttribute("pozicijaOd") != null){
            pozicijaOdPoruke = (int) context.getAttribute("pozicijaOd");
        }
        
        try {
            Folder folder = store.getFolder(odabranaMapa);
            folder.open(Folder.READ_ONLY);
            numberOfMessages = folder.getMessageCount();
            poruke.clear();
            
            if(!(numberOfMessages < 1)){
                switch (naprijed) {
                    case 1:
                        pozicijaOdPoruke += korakPoruka;
                        pozicijaDoPoruke = pozicijaOdPoruke + korakPoruka;
                        break;
                    case 0:
                        pozicijaOdPoruke -= korakPoruka;
                        if(pozicijaOdPoruke <= 1)
                            pozicijaOdPoruke = 1;
                        pozicijaDoPoruke = pozicijaOdPoruke + korakPoruka;
                        break;
                    case -1:
                        pozicijaDoPoruke += korakPoruka;
                        break;
                    case 2:
                        pozicijaDoPoruke = pozicijaOdPoruke + korakPoruka;
                        break;
                }
                
                if(pozicijaDoPoruke > numberOfMessages + 1){
                    pozicijaDoPoruke = numberOfMessages + 1;
                    pozicijaOdPoruke = pozicijaDoPoruke - korakPoruka;
                }
                
                if(pozicijaOdPoruke < 1){
                    pozicijaOdPoruke = 1;
                    pozicijaDoPoruke = 1 + korakPoruka;
                }
                
                if(pozicijaDoPoruke > numberOfMessages + 1){
                    pozicijaDoPoruke = numberOfMessages + 1;
                }
                
                System.out.println("Pozicija od: " + pozicijaOdPoruke);
                System.out.println("Pozicija do: " + pozicijaDoPoruke);
                context.removeAttribute("pozicijaOd");
                context.setAttribute("pozicijaOd", pozicijaOdPoruke);
                
                Message[] messages = folder.getMessages(pozicijaOdPoruke, pozicijaDoPoruke - 1);
                
                int id = 0;
                for (Message message : messages) {
                    
                    Date vrijemeSlanje = message.getSentDate();
                    Date vrijemePrijema = message.getReceivedDate();
                    String salje = message.getFrom()[0].toString();
                    String predmet = message.getSubject();
                    String sadrzaj = message.getContent().toString();
                    String vrsta = message.getContentType();
                    
                    if(!"".equals(traziPoruke)){
                        if(sadrzaj.toLowerCase().contains(traziPoruke.toLowerCase())){
                            poruke.add(new Poruka(String.valueOf(id), vrijemeSlanje, vrijemePrijema, salje, predmet, sadrzaj, vrsta));
                        }
                    } else {
                        poruke.add(new Poruka(String.valueOf(id), vrijemeSlanje, vrijemePrijema, salje, predmet, sadrzaj, vrsta));
                    }
                    id++;
                }
                traziPoruke = "";
            }
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ukuponoPorukaMapa = numberOfMessages;
    }
    
    public Object brisiPoruke() {
        try {
            Folder folder = store.getFolder(odabranaMapa);
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();
            Flags deleted = new Flags(Flags.Flag.DELETED);
            folder.setFlags(messages, deleted, true);
            folder.expunge();
            preuzmiMape();
            preuzmiPoruke();
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "ObrisanePoruke";
    }
    
    public Object promjenaMape() {
        pozicijaOdPoruke = 1;
        naprijed = -1;
        context.removeAttribute("pozicijaOd");
        context.setAttribute("pozicijaOd", pozicijaOdPoruke);
        preuzmiPoruke();
        return "PromjenaMape";
    }
    
    public Object traziPoruke() {
        naprijed = 2;
        preuzmiPoruke();
        return "FiltrirajPoruke";
    }
    
    public Object prethodnePoruke() {
        naprijed = 0;
        preuzmiPoruke();
        return "PrethodnePoruke";
    }
    
    public  Object slijedecePoruke() {
        naprijed = 1;
        preuzmiPoruke();
        return "SlijedecePoruke";
    }

    public String getOdabranaMapa() {
        return odabranaMapa;
    }

    public void setOdabranaMapa(String odabranaMapa) {
        this.odabranaMapa = odabranaMapa;
    }

    public int getUkuponoPorukaMapa() {
        return ukuponoPorukaMapa;
    }

    public void setUkuponoPorukaMapa(int ukuponoPorukaMapa) {
        this.ukuponoPorukaMapa = ukuponoPorukaMapa;
    }

    public int getPozicijaOdPoruke() {
        return pozicijaOdPoruke;
    }

    public void setPozicijaOdPoruke(int pozicijaOdPoruke) {
        this.pozicijaOdPoruke = pozicijaOdPoruke;
    }

    public int getPozicijaDoPoruke() {
        return pozicijaDoPoruke;
    }

    public void setPozicijaDoPoruke(int pozicijaDoPoruke) {
        this.pozicijaDoPoruke = pozicijaDoPoruke;
    }

    public String getTraziPoruke() {
        return traziPoruke;
    }

    public void setTraziPoruke(String traziPoruke) {
        this.traziPoruke = traziPoruke;
    }

    public ArrayList<Izbornik> getMape() {
        return mape;
    }

    public ArrayList<Poruka> getPoruke() {
        return poruke;
    }
    
    
}

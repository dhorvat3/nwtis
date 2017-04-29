/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;

/**
 * Kod odabira slanja email poruka slijedi prikaz tablice unutar koje je obrazac
 * za unos podataka poruke (primatelj, pošiljatelj, predmet, sadržaj poruke). U
 * podnožju tablice nalazi se gumb za slanje email poruke. U zaglavlju pogleda
 * treba biti poveznica na izbor jezika i poveznica na pregled primljenih email
 * poruka.
 *
 * @author Davorin Horvat
 */
@Named(value = "slanjePoruke")
@RequestScoped
public class SlanjePoruke {

    private String posluzitelj;
    private String salje;
    private String prima;
    private String predmet;
    private String sadrzaj;

    /**
     * Dohvaća podatke o mail serveru.
     * Creates a new instance of SlanjePoruke
     */
    public SlanjePoruke() {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Mail_Konfig");
        this.posluzitelj = konfig.dajPostavku("mail.server");
    }

    public String getPosluzitelj() {
        return posluzitelj;
    }

    public void setPosluzitelj(String posluzitelj) {
        this.posluzitelj = posluzitelj;
    }

    public String getSalje() {
        return salje;
    }

    public void setSalje(String salje) {
        this.salje = salje;
    }

    public String getPrima() {
        return prima;
    }

    public void setPrima(String prima) {
        this.prima = prima;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    /**
     * Šalje unesenu poruku na zadanu adresu.
     * @return 
     */
    public String saljiPoruku() {
        try {
            Properties properties = System.getProperties();
            properties.put("mail.mstp.host", this.posluzitelj);

            Session session = Session.getInstance(properties, null);

            MimeMessage message = new MimeMessage(session);

            Address fromAddress = new InternetAddress(this.salje);
            message.setFrom(fromAddress);

            Address toAddress = new InternetAddress(this.prima);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(this.predmet);
            message.setText(this.sadrzaj);
            message.setSentDate(new Date());

            Transport.send(message);

        } catch (AddressException ex) {
            Logger.getLogger(SlanjePoruke.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SlanjePoruke.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.sadrzaj = "";
        this.predmet = "";
        this.prima = "";
        this.salje = "";
        return "PoslanaPoruka";
    }

    public String promjenaJezika() {
        return "PromjenaJezika";
    }

    public String pregledPoruka() {
        return "PregledPoruka";
    }
}

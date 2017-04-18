/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.dretve;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;

/**
 *
 * @author Davorin Horvat
 */
public class ObradaPoruka extends Thread{

    private ServletContext sc = null;
    private boolean stop = false;
    
    @Override
    public void interrupt() {
        stop = true;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        Konfiguracija konf = (Konfiguracija) sc.getAttribute("Mail_Konfig");
        String server = konf.dajPostavku("mail.server");
        String port = konf.dajPostavku("mail.port");
        String korisnik = konf.dajPostavku("mail.usernameThread");
        String lozinka = konf.dajPostavku("mail.passwordThread");
        int trajanjeCiklusa = Integer.parseInt(konf.dajPostavku("mail.timeSecThread"));
        //TODO i za ostale pareametre
        int trajanjeObrade = 0;
        //TODO odredi trajanje obrade
        int redniBrojCiklusa = 0;

        while (!stop) {
            redniBrojCiklusa++;
            System.out.println("Ciklus dretve ObradaPoruka: " + redniBrojCiklusa);
            try {

                // Start the session
                java.util.Properties properties = System.getProperties();
                properties.put("mail.smtp.host", server);
                Session session = Session.getInstance(properties, null);

                // Connect to the store
                Store store = session.getStore("imap");
                store.connect(server, korisnik, lozinka);

                // Open the INBOX folder
                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_ONLY);
                
                Message[] messages = folder.getMessages();
                for(int i=0; i < messages.length; ++i){
                    //TODO dovršiti čitanje, obradu i prebacivanje u mape
                }
                
                sleep(trajanjeCiklusa * 1000 - trajanjeObrade);
            } catch (InterruptedException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setSc(ServletContext sc){
        this.sc = sc;
    }
}

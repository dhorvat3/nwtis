/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;

/**
 * Kreira se konstruktor klase u koji se prenose podaci konfiguracije. Služi za
 * provjeru zadanih adresa u pravilnim vremenskim ciklusima. Potrebno je voditi
 * brigu o međusobnom isključivanju dretvi kod pristupa evidenciji rada i sl.
 *
 * @author Davorin Horvat
 */
public class ProvjeraAdresa extends Thread {

    Konfiguracija konfig;
    Evidencija evidencija;

    public ProvjeraAdresa(Konfiguracija konfig) {
        this.konfig = konfig;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        int trajanjeSpavanja = Integer.parseInt(konfig.dajPostavku("intervaAdresneDretve"));

        while (true) {
            //System.out.println(this.getClass());
            long trenutnoVrijeme = System.currentTimeMillis();
            //TODO dovršite sami
            long vrijemeZavrsetka = System.currentTimeMillis();

            try {
                sleep(trajanjeSpavanja - (vrijemeZavrsetka - trenutnoVrijeme));
            } catch (InterruptedException ex) {
                Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (evidencija != null) {
                for (ZahtjeviAdresa red : evidencija.getZahtjeviZaAdrese()) {
                    boolean ispravno = false;
                    String reURL = "((https|http?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?)";
                    Pattern p = Pattern.compile(reURL);
                    Matcher m = p.matcher(red.getAdresa());
                    if (m.matches()) {
                        HttpURLConnection connection = null;
                        try {
                            URL url = new URL(red.getAdresa());
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("HEAD");
                            int code = connection.getResponseCode();
                            //System.out.println("CODE: " + code);
                            if (code == 200) {
                                red.setProvjereno(true);
                            }
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            ispravno = InetAddress.getByName(red.getAdresa()).isReachable(MIN_PRIORITY);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(ProvjeraAdresa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (ispravno) {
                            red.setProvjereno(true);
                        }
                    }
                }
            }
            //TODO razmisliti kako izaći iz beskonačne petlje

            //TODO razmisliti kako izaći iz beskonačne petlje
        }
    }

    public void setEvidencija(Evidencija evidencija) {
        this.evidencija = evidencija;
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}

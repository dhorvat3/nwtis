/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;

/**
 *
 * @author Davorin Horvat
 */
public class RezervnaDretva extends Thread {

    private Konfiguracija konfig;

    public RezervnaDretva(Konfiguracija konfig) {
        this.konfig = konfig;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        int trajanjeSpavanje = Integer.parseInt(konfig.dajPostavku("intervaAdresneDretve"));

        while (true) {
            //System.out.println(this.getClass());

            long trenutnoVrijeme = System.currentTimeMillis();
            //TODO dovršite sami
            long vrijemeZavršetka = System.currentTimeMillis();
            try {
                sleep(trajanjeSpavanje - (vrijemeZavršetka - trenutnoVrijeme));
            } catch (InterruptedException ex) {
                Logger.getLogger(RezervnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //TODO razmisliti kako izaći iz beskonačne petlje
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}

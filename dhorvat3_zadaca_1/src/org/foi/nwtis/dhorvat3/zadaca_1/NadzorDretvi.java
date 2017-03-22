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
public class NadzorDretvi extends Thread {

    Konfiguracija konfig;

    public NadzorDretvi(Konfiguracija konfig) {
        this.konfig = konfig;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        int trajanjeSpavanja = Integer.parseInt(konfig.dajPostavku("intervalNadzorneDretve"));

        while (true) {
            System.out.println(this.getClass());
            long trenutnoVrijeme = System.currentTimeMillis();
            //TODO dovršite sami
            //TODO provjerite trajanje pojedine aktivne radne dretve iz kolekcije
            //TODO obrisati dretvu iz kolekcije aktivnih radnih dretvi ako traje više nego što smije
            long vrijemeZavrsetka = System.currentTimeMillis();

            try {
                sleep(trajanjeSpavanja - (vrijemeZavrsetka - trenutnoVrijeme));
            } catch (InterruptedException ex) {
                Logger.getLogger(NadzorDretvi.class.getName()).log(Level.SEVERE, null, ex);
            }

            //TODO razmisliti kako izaći iz beskonačne petlje
        }
    }

@Override
        public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

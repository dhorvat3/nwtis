/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;

/**
 * Kreira se konstruktor klase u koji se prenose podaci konfiguracije. Služi za
 * serijalizaciju podataka. Izvršava serijalizaciju evidencije prema potrebi, a
 * u međuvremenu čeka. Potrebno je voditi brigu o međusobnom isključivanju
 * dretvi kod pristupa evidenciji rada i sl.
 *
 * @author Davorin Horvat
 */
public class SerijalizatorEvidencije extends Thread {

    Konfiguracija konfig;

    public SerijalizatorEvidencije(Konfiguracija konfig) {
        this.konfig = konfig;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        //TODO dovršite sami
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}

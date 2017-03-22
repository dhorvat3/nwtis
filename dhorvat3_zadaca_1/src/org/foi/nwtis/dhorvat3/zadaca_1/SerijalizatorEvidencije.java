/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;

/**
 *
 * @author Davorin Horvat
 */
public class SerijalizatorEvidencije extends Thread{
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dhorvat3.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author grupa_3
 */
public class Vjezba_03_4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Konfiguracija konfiguracija = null;

        if (args.length < 1 || args.length > 3) {
            System.out.println("Broj argumenata ne nije dobar");
            return;
        }

        if (args.length == 1) {
            try {
                konfiguracija = KonfiguracijaApstraktna.kreirajKonfiguraciju(args[0]);
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NeispravnaKonfiguracija ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                konfiguracija = KonfiguracijaApstraktna.preuzmiKonfiguraciju(args[0]);
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NeispravnaKonfiguracija ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (args.length == 2) {
            System.out.println(konfiguracija.dajPostavku(args[1]));
        }

        if (args.length == 3) {
            konfiguracija.spremiPostavku(args[1], args[2]);
            try {
                konfiguracija.spremiKonfiguraciju();
            } catch (NemaKonfiguracije ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NeispravnaKonfiguracija ex) {
                Logger.getLogger(Vjezba_03_4.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

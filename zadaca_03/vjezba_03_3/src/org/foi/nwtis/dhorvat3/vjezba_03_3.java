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
public class vjezba_03_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Broj argumenata ne nije dobar");
            return;
        }
        
        try {
            KonfiguracijaApstraktna.kreirajKonfiguraciju(args[0]);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(vjezba_03_3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(vjezba_03_3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

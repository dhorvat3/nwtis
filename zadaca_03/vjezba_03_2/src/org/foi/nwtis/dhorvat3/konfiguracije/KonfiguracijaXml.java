/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.konfiguracije;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grupa_3
 */
public class KonfiguracijaXml extends KonfiguracijaApstraktna {

    public KonfiguracijaXml(String datoteka) {
        super(datoteka);
    }
    
    @Override
    public void ucitajKonfiguraciju() throws NemaKonfiguracije, NeispravnaKonfiguracija {
        if(datoteka == null || datoteka.length() == 0){
            throw new NeispravnaKonfiguracija("Naziv datoteke nije ispravan");
        }
        
        File file = new File(datoteka);
        try {
            FileInputStream fis = new FileInputStream(file);
            this.postavke.loadFromXML(fis);
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaXml.class.getName()).log(Level.SEVERE, null, ex);
            throw new NemaKonfiguracije("Čitanje datoteke nije uspijelo!");
        }
    }

    @Override
    public void ucitajKonfiguraciju(String datoteka) throws NemaKonfiguracije, NeispravnaKonfiguracija {
        this.datoteka = datoteka;
        ucitajKonfiguraciju();
    }

    @Override
    public void spremiKonfiguraciju() throws NemaKonfiguracije, NeispravnaKonfiguracija {
        if(datoteka == null || datoteka.length() == 0){
            throw new NeispravnaKonfiguracija("Naziv datoteke nije ispravan");
        }
        
        File file = new File(datoteka);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            this.postavke.storeToXML(fos, "NWTiS - dhorvat - " + new Date());
        } catch (IOException ex) {
            Logger.getLogger(KonfiguracijaXml.class.getName()).log(Level.SEVERE, null, ex);
            throw new NemaKonfiguracije("Spremanje datoteke nije uspijelo!");
        }
    }

    @Override
    public void spremiKonfiguraciju(String datoteka) throws NemaKonfiguracije, NeispravnaKonfiguracija {
        this.datoteka = datoteka;
        spremiKonfiguraciju();
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dhorvat3.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.dhorvat3.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.dhorvat3.web.dretve.ObradaPoruka;

/**
 * Web application lifecycle listener.
 * Slušač aplikacije starta pozadinsku dretvu koja provjerava na poslužitelju
 * @author Davorin Horvat
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {

    ObradaPoruka obradaPoruka = null;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String datoteka = context.getRealPath("/WEB-INF") + File.separator + context.getInitParameter("konfiguracija");
        
        BP_Konfiguracija bp_konf = new BP_Konfiguracija(datoteka);
        context.setAttribute("BP_Konfig", bp_konf);
        
        try {
            Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            context.setAttribute("Mail_Konfig", konfig);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        obradaPoruka = new ObradaPoruka();
        obradaPoruka.setServletContext(context);
        obradaPoruka.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(obradaPoruka != null){
            obradaPoruka.interrupt();
        }
    }
}

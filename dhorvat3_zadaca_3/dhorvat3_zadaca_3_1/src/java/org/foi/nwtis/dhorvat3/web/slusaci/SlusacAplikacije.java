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
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dhorvat3.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.dhorvat3.web.PozadinskaDretva;

/**
 * Web application lifecycle listener.
 *
 * @author Davorin Horvat
 */
public class SlusacAplikacije implements ServletContextListener {
    
    private PozadinskaDretva pozadinskaDretva = null;
    private static ServletContext context = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        
        String datoteka = context.getRealPath("/WEB-INF") + File.separator + context.getInitParameter("konfiguracija");
        context.setAttribute("BP_Konfig", datoteka);
        System.out.println("Učitana konfiguracija");
        
        Konfiguracija konfig = null;
        
        try{
            konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            context.setAttribute("Server_Konfig", konfig);
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pozadinskaDretva = new PozadinskaDretva(context);
        pozadinskaDretva.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }

    public static ServletContext getContext() {
        return context;
    }
    
    
}

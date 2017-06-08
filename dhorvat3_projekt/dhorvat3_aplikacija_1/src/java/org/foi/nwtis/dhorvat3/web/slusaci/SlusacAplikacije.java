/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.slusaci;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dhorvat3.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.dhorvat3.socket.SocketServer;
import org.foi.nwtis.dhorvat3.socket.helper.SocketClient;

/**
 * Web application lifecycle listener.
 *
 * @author Davorin Horvat
 */
public class SlusacAplikacije implements ServletContextListener {
    private SocketServer socketServer = null;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String datoteka = context.getRealPath("/WEB-INF") + File.separator + context.getInitParameter("konfiguracija");
        
        BP_Konfiguracija bp_konfig = new BP_Konfiguracija(datoteka);
        context.setAttribute("BP_Konfig", bp_konfig);
        
        socketServer = new SocketServer(context);
        socketServer.start();
        
        SocketClient socketClient = new SocketClient();
        try {
            socketClient.connect();
            socketClient.sendMessage("USER pero; PASSWD pass;");
            
        } catch (IOException ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            socketServer.zaustaviServer();
        } catch (IOException ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.socket.dretve.MeteoDretva;
import org.foi.nwtis.dhorvat3.socket.dretve.ObradaZahtjeva;

/**
 *
 * @author Davorin Horvat
 */
public class SocketServer extends Thread {

    private ServletContext context = null;
    private boolean kraj = true;
    private int port = 5589;
    private ServerSocket serverSocket = null;
    private MeteoDretva meteoDretva = null;

    public SocketServer(int port, ServletContext context, MeteoDretva meteoDretva) {
        this.port = port;
        this.context = context;
        this.meteoDretva = meteoDretva;
    }

    public SocketServer(ServletContext context, MeteoDretva meteoDretva) {
        this.context = context;
        this.meteoDretva = meteoDretva;
    }

    @Override
    public void interrupt() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {
            System.out.println("--- SOCKET SERVER RUN ---");
            pokreniServer();
            while (!kraj) {
                System.out.println("--- SOCKET KORAK ---");

                ObradaZahtjeva obradaZahtjeva = new ObradaZahtjeva(serverSocket.accept(), context, meteoDretva);
                obradaZahtjeva.start();

            }
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    void pokreniServer() throws IOException {
        kraj = false;
        serverSocket = new ServerSocket(port);
    }

    public void zaustaviServer() throws IOException {
        kraj = true;

        
    }

}

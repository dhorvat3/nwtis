/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.socket.dretve.ObradaZahtjeva;

/**
 *
 * @author Davorin Horvat
 */
public class SocketServer extends Thread {
    private ServletContext context = null;
    private boolean kraj = true;
    private int port = 4258;
    private ServerSocket serverSocket = null;

    public SocketServer(int port, ServletContext context) {
        this.port = port;
        this.context = context;
    }

    public SocketServer(ServletContext context) {
        this.context = context;
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
                
                ObradaZahtjeva obradaZahtjeva = new ObradaZahtjeva(serverSocket.accept(), context);
                obradaZahtjeva.start();
                
            }
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

        serverSocket.close();
    }

}

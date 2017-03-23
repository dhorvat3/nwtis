/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Apstraktna klasa koja sadrži metode za spajanje i prekid veze sa serverom.
 * @author Davorin Horvat
 */
public abstract class KorisnikApstraktni implements Korisnik{
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private Socket socket = null;
    
    /**
     * Povezivanje sa serverom
     * @param nazivServera
     * @param port 
     */
    public void pokreniKorisnika(String nazivServera, int port){
        try{
            socket = new Socket(nazivServera, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(KorisnikApstraktni.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Prekidanje veze sa serverom
     */
    public void ugasiKorisnika(){
        try {
            if(inputStream != null){
                inputStream.close();
            }
            if(outputStream != null){
                outputStream.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(KorisnikApstraktni.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
}

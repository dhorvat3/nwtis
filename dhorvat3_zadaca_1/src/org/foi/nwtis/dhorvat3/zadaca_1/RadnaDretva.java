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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Davorin Horvat
 */
public class RadnaDretva extends Thread {

    private Socket socket;

    public RadnaDretva(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        //TODO preuzeti trenutno vrijeme u milisekundama
        System.out.println(this.getClass());

        String sintaksa_admin = "^USER ([^\\s]+); PASSWD ([^\\s]+); (PAUSE|STOP|START|STAT);$";
        String sintaksa_korisnik = "USER ([^\\s]+); (ADD|TEST|WAIT) ([^\\s]+);";
        String sintaksa_korisnik_2 = "USER ([^\\s]+); TEST ([^\\s]+);";
        String sintaksa_korisnik_3 = "USER ([^\\s]+); WAIT ([^\\s]+);";

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                int znak = inputStream.read();
                if (znak == -1) {
                    break;
                }
                stringBuffer.append((char) znak);
            }
            System.out.println("Primljena naredba: " + stringBuffer);

            //TODO provjeri ispravnost primljenog zahtjeva
            Pattern pattern = Pattern.compile(sintaksa_admin);
            Matcher matcher = pattern.matcher(stringBuffer);
            boolean status = matcher.matches();
            if (status) {
                //TODO dovršiti za admina
            } else {
                pattern = Pattern.compile(sintaksa_korisnik);
                matcher = pattern.matcher(stringBuffer);
                status = matcher.matches();
                if(status){
                    //TODO dovršiti za korisnika
                }
            }

            outputStream.write("OK;".getBytes());
            outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}

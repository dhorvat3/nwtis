/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private HashMap<String, Object> adrese = null;
    private String datotekaAdmin;
    //private AtomicBoolean pause;
    private AtomicBoolean pause;

    public RadnaDretva(Socket socket, HashMap<String, Object> adrese, String datotekaAdmin, AtomicBoolean pause) {
        this.socket = socket;
        this.adrese = adrese;
        this.datotekaAdmin = datotekaAdmin;
        this.pause = pause;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        //TODO preuzeti trenutno vrijeme u milisekundama
        System.out.println(this.getClass());

        String sintaksa_admin = "USER ([^\\s]+); PASSWD ([^\\s]+); (PAUSE|STOP|START|STAT);";
        String sintaksa_korisnik = "USER ([^\\s]+); (ADD|TEST|WAIT) ([^\\s]+);";

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String odgovor = null;

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
            if (matcher.matches()) {
                //TODO dovršiti za admina
                odgovor = obradiAdministratora(matcher);
            } else {
                if(!pause.get()){
                    pattern = Pattern.compile(sintaksa_korisnik);
                    matcher = pattern.matcher(stringBuffer);
                    odgovor = obradiKorisnika(matcher);
                } else {
                    odgovor = "Server u stanju PAUSE!";
                }
            }
            if(odgovor == null){
                odgovor = "Neispravna naredba! Naredba: " + stringBuffer;
            }
            outputStream.write(odgovor.getBytes());
            outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String obradiKorisnika(Matcher matcher) {
        String odgovor = null;
        if (matcher.matches()) {
            if (matcher.group(2).equals("ADD")) {
                adrese.put(matcher.group(1), matcher.group(3));
                odgovor = "OK; " + adrese.toString();
            } else if(matcher.group(2).equals("WAIT")){
                try {
                    RadnaDretva.sleep(2000);
                    odgovor = "OK;";
                } catch (InterruptedException ex) {
                    odgovor = "ERROR 13; Dretva nije uspijela izvrsiti naredbu!";
                    //Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                odgovor = "Nije implementirano";
            }
        }
        System.out.println("Odgovor: " + odgovor);
        return odgovor;
    }
    
    private String obradiAdministratora(Matcher matcher){
        String odgovor = null;
        if(provjeriPodatke(matcher.group(1), matcher.group(2))){
            if(matcher.group(3).equals("PAUSE")){
                if(pause.get()){
                    odgovor = "ERROR 01; Server je vec u stanju PAUSE!";
                } else {
                    pause.set(true);
                    odgovor = "OK;";
                }
            }
            if(matcher.group(3).endsWith("START")){
                if(pause.get()){
                    pause.set(false);
                    odgovor = "OK;";
                } else {
                    odgovor = "ERROR 02; Server nije u stanju PAUSE!";
                }
            }
        } else {
            odgovor = "ERROR 00; Pogresno korisnicko ime ili lozinka!";
        }
        
        return odgovor;
    }

    private boolean provjeriPodatke(String username, String pass){
        File file = new File(datotekaAdmin);
        boolean prijavljen = false;
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                String[] korisnik = line.split(";");
                if(username.equals(korisnik[0]) && pass.equals(korisnik[1])){
                    prijavljen = true;
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return prijavljen;
    }
    
    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}

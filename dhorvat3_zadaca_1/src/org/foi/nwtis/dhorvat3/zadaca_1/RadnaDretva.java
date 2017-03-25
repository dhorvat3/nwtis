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
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Kreiranje konstruktora klase i metode za prijenos potrebnih podataka. Dretva
 * iz dobivene veze na socketu preuzima tokove za ulazne i izlazne podatke prema
 * korisniku. Dretva preuzima podatke koje šalje korisnik putem ulaznog toka
 * podataka, provjerava korektnost komandi iz zahtjeva. Preporučuje se koristiti
 * dopuštene izraze. Za prvo testiranje servera može se koristiti primjer s 4.
 * predavanja Primjer33_3.java. Na kraju dretva šalje podatke korisniku putem
 * izlaznog toka podataka. Za svaku vrstu komande kreira se posebna metoda koja
 * odrađuje njenu funkcionalnost.
 *
 * @author Davorin Horvat
 */
public class RadnaDretva extends Thread {

    private Socket socket;
    private Evidencija evidencija = null;
    private String datotekaAdmin;
    private AtomicBoolean pause;

    public RadnaDretva(Socket socket, Evidencija evidencija, String datotekaAdmin, AtomicBoolean pause) {
        this.socket = socket;
        this.evidencija = evidencija;
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
        String sintaksa_korisnik = "USER ([^\\s]+); (ADD|TEST|WAIT) ([^\\s]+|.*);";

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String odgovor = "";
        Matcher matcher = null;

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

            Pattern pattern = Pattern.compile(sintaksa_admin);
            matcher = pattern.matcher(stringBuffer);
            if (matcher.matches()) {
                odgovor = obradiAdministratora(matcher);
            } else {
                if (!pause.get()) {
                    pattern = Pattern.compile(sintaksa_korisnik);
                    matcher = pattern.matcher(stringBuffer);
                    odgovor = obradiKorisnika(matcher);
                } else {
                    odgovor = "Server u stanju PAUSE!";
                }
            }
            if ("".equals(odgovor)) {
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
                if (odgovor.equals("OK;") && "STOP".equals(matcher.group(3))) {
                    System.exit(0);
                }
            } catch (IOException ex) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Obrađuje naredbe poslane od strane klijenta sustava
     *
     * @param matcher
     * @return Odgovor korisniku
     */
    private String obradiKorisnika(Matcher matcher) {
        String odgovor = "";
        if (matcher.matches()) {
            if (matcher.group(2).equals("ADD")) {
                ZahtjeviAdresa zahtjev = new ZahtjeviAdresa(matcher.group(1), matcher.group(3), false, null);
                evidencija.getZahtjeviZaAdrese().add(zahtjev);
                odgovor = "OK; "; //+ adrese;
            } else if (matcher.group(2).equals("WAIT")) {
                try {
                    RadnaDretva.sleep(2000);
                    odgovor = "OK;";
                } catch (InterruptedException ex) {
                    odgovor = "ERROR 13; Dretva nije uspijela izvrsiti naredbu!";
                }
            } else if (matcher.group(2).equals("TEST")) {
                odgovor = "ERROR 12; Adresa nije upisana u sustav!";
                for (ZahtjeviAdresa red : evidencija.getZahtjeviZaAdrese()) {
                    if (red.getAdresa().equals(matcher.group(3))) {
                        odgovor = red.isProvjereno() ? "OK; YES" : "OK; NO";
                        break;
                    }
                }
            } else {
                odgovor = "ERROR 99; Nepostojeća naredba!";
            }
        }
        System.out.println("Odgovor: " + odgovor);
        return odgovor;
    }

    /**
     * Obrađuje naredbe poslane od strane administratora sustava.
     *
     * @param matcher
     * @return Odgovor korisniku
     */
    private String obradiAdministratora(Matcher matcher) {
        String odgovor = null;
        if (provjeriPodatke(matcher.group(1), matcher.group(2))) {
            if (matcher.group(3).equals("PAUSE")) {
                if (pause.get()) {
                    odgovor = "ERROR 01; Server je vec u stanju PAUSE!";
                } else {
                    pause.set(true);
                    odgovor = "OK;";
                }
            }
            if (matcher.group(3).equals("START")) {
                if (pause.get()) {
                    pause.set(false);
                    odgovor = "OK;";
                } else {
                    odgovor = "ERROR 02; Server nije u stanju PAUSE!";
                }
            }
            if (matcher.group(3).equals("STOP")) {
                //TODO SERIJALIZACIJA PODATAKA
                odgovor = "OK;";
            }
        } else {
            odgovor = "ERROR 00; Pogresno korisnicko ime ili lozinka!";
        }

        return odgovor;
    }

    /**
     * Provjerava korisnikove podatke u datoteci administratora
     *
     * @param username
     * @param pass
     * @return ispravno
     */
    private boolean provjeriPodatke(String username, String pass) {
        File file = new File(datotekaAdmin);
        boolean prijavljen = false;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] korisnik = line.split(";");
                if (username.equals(korisnik[0]) && pass.equals(korisnik[1])) {
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

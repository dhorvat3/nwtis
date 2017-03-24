/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dhorvat3.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.NemaKonfiguracije;

/**
 * Prvo se provjeravaju upisane opcije, preporučuje se koristiti dopuštene izraze.
 * Učitavaju se postavke iz datoteke. Server kreira i pokreće nadzornu dretvu (klasa NadzorDretvi),
 * kreira i pokreće adresnu dretvu (klasa ProvjeraAdresa), kreira i pokreće 
 * rezervnu dretvu (klasa RezervnaDretva), kreira i pokreće dretvu za serijalizaciju evidencije 
 * (klasa SerijalizatorEvidencije). Otvara se ServerSocket (slično primjerima ClientTester.java 
 * i TinyHttpd.java s 4. predavanja) na izabranom portu i čeka zahtjev korisnika u beskonačnoj petlji. 
 * Kada se korisnik spoji na otvorenu vezu, kreira se objekt dretve klase RadnaDretva, veza se predaje 
 * objektu i pokreće se izvršavanje dretve. Dretve opslužuju zahtjev korisnika. Dretva nakon što obradi 
 * pridruženi zahtjev korisnika završava svoj rad i briše se.  Ako nema raspoložive radne dretve, izvođenje 
 * se prebacuje na rezervnu dretvu koja vraća korisniku informaciju o nepostojanju slobodne radne dretve 
 * tako da korisniku vraća odgovor ERROR 20; tekst (tekst objašnjava razlog pogreške). Nakon toga server 
 * ponovno čeka na uspostavljanje veze i postupak se nastavlja. 
 * @author Davorin Horvat
 */
public class ServerSustava {
    private HashMap<String, Object> adrese = new HashMap<>();
    private AtomicBoolean pause = new AtomicBoolean(false);
    
    public static void main(String[] args) {
        String sintaksa = "^-konf ([^\\s]+\\.(?i))(txt|xml|bin)( +-load)?$";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        String p = builder.toString().trim();
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher matcher = pattern.matcher(p);
        boolean status = matcher.matches();
        if (status) {
            int poc = 0;
            int kraj = matcher.groupCount();
            for (int i = poc; i <= kraj; i++) {
                System.out.println(i + ". " + matcher.group(i));
            }
            ServerSustava server = new ServerSustava();
            String nazivDatoteke = matcher.group(1) + matcher.group(2);
            boolean trebaUcitatiEvidenciju = false;
            System.out.println();
            if (matcher.group(3) != null) {
                if(" -load".equals(matcher.group(3))){
                    trebaUcitatiEvidenciju = true;
                } else {
                    try {
                        Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
                        String evidDatoteka = konfig.dajPostavku("evidDatoteka");
                        server.serializirajPodatke(evidDatoteka);
                    } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
                        Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            server.pokreniServer(nazivDatoteke, trebaUcitatiEvidenciju);
        } else {
            System.out.println("Pogrešna naredba!");
        }
    }

    private void serializirajPodatke(String nazivDatoteke){
        Evidencija evidencija = new Evidencija();
        evidencija.setBrojPrekinutihZahtjeva(3);
        evidencija.setBrojUspjesnihZahtjeva(5);
        evidencija.setUkupnoZahtjeva(3);
        evidencija.setZahtjeviZaAdrese(null);
        
        try {
            FileOutputStream outputStream = new FileOutputStream(nazivDatoteke);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            
            objectOutputStream.writeObject(evidencija);
            objectOutputStream.close();
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void pokreniServer(String nazivDatoteke, boolean trebaUcitatiEvidenciju) {
        try {
            Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
            int port = Integer.parseInt(konfig.dajPostavku("port"));
            ServerSocket serverSocket = new ServerSocket(port);
            //Učitavanje evidencije
            if (trebaUcitatiEvidenciju) {
                String evidDatoteka = konfig.dajPostavku("evidDatoteka");

                if (evidDatoteka == null || evidDatoteka.length() == 0) {
                    //TODO regex naziva datoteke
                    System.out.println("Naziv datoteke nije ispravan;");
                }

                Evidencija evidencija = new Evidencija();
                File f = new File(evidDatoteka);
                if (f.exists() && !f.isDirectory()) {
                    try {
                        Logger.getLogger(ServerSustava.class.getName()).log(Level.FINE, null, "Deserijalizacija podataka.");
                        try (FileInputStream inputStream = new FileInputStream(evidDatoteka); ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                            evidencija = (Evidencija) objectInputStream.readObject();
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Logger.getLogger(ServerSustava.class.getName()).log(Level.INFO, null, "Deserijalizacija završena");
                    Logger.getLogger(ServerSustava.class.getName()).log(Level.INFO, null, "Broj prekinutih zahtjeva: " + evidencija.getBrojPrekinutihZahtjeva());
                    Logger.getLogger(ServerSustava.class.getName()).log(Level.INFO, null, "Broj uspješnih zahtjeva: " + evidencija.getBrojUspjesnihZahtjeva());
                    Logger.getLogger(ServerSustava.class.getName()).log(Level.INFO, null, "Ukupno zahtjeva: " + evidencija.getUkupnoZahtjeva());
                    Logger.getLogger(ServerSustava.class.getName()).log(Level.INFO, null, "Zahtjevi za adrese: " + evidencija.getZahtjeviZaAdrese());
                } else {
                    System.out.println("Datoteka ne postoji!");
                }
            }

            /*NadzorDretvi nadzorDretvi = new NadzorDretvi(konfig);
            nadzorDretvi.start();
            RezervnaDretva rezervnaDretva = new RezervnaDretva(konfig);
            rezervnaDretva.start();
            ProvjeraAdresa provjeraAdresa = new ProvjeraAdresa(konfig);
            provjeraAdresa.start();
            SerijalizatorEvidencije serijalizatorEvidencije = new SerijalizatorEvidencije(konfig);
            serijalizatorEvidencije.start();*/

            while (true) {
                Socket socket = serverSocket.accept();
                RadnaDretva radnaDretva = new RadnaDretva(socket, adrese, konfig.dajPostavku("adminDatoteka"), pause);
                

                //TODO dodaj dretvu u kolekciju aktivnih radnih dretvi
                radnaDretva.start();
                
                //TODO treba provjeriti ima li "mjesta" za novu radnu dretvu
            }
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija | IOException ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

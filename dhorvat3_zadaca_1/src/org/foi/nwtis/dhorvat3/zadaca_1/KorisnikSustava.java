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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Prvo se provjeravaju upisane opcije, preporučuje se koristiti dopuštene
 * izraze. Na temelju opcije kreira se objekt potrebne klase
 * AdministratorSustava, KlijentSustava ili PregledSustava, te se nastavlja s
 * izvršavanjem tog objekta.
 *
 * @author Davorin Horvat
 */
public class KorisnikSustava {

    public static void main(String[] args) {
        KorisnikSustava korisnik = new KorisnikSustava();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]).append(" ");
        }
        String parametri = stringBuilder.toString().trim();

        Korisnik user = korisnik.provjeriParametre(parametri);
        Scanner scanner = new Scanner(System.in);
        if (user != null) {
            String naredba = null;
            String odgovor = null;
            while (odgovor == null || "exit".equals(naredba)) {
                if (user instanceof PregledSustava) {
                    naredba = parametri;
                } else {
                    System.out.println("Unesite naredbu: ");
                    naredba = scanner.nextLine();
                }
                odgovor = user.posaljiNaredbu(naredba);
                System.out.println("Odgovor: " + odgovor);
            }
        }
    }

    /**
     * Provjerava unesene parametre za pokretanje korisnika -admin -server
     * [ipadresa | adresa] -port port -u korisnik -p lozinka [-pause | -start |
     * -stop | -stat ] Pokretanje administratora sustava -korisnik -s [ipadresa
     * | adresa] -port port -u korisnik [[-a | -t] URL] | [-w nnn] Pokretanje
     * korisnika sustava -prikaz -s datoteka Pokretanje pregleda sustava
     *
     * @param parametri
     * @return
     */
    //TODO Vratiti objek klase koja implementira sučelje korisnik?
    private Korisnik provjeriParametre(String parametri) {
        System.out.println(parametri);

        //TODO Srediti [[-a | -t] URL] | [-w nnn]
        if (parametri.startsWith("-korisnik")) {
            String reKorisnik = "-korisnik.*?-s.*?"; //-korinsnik
            String reIP = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])";//Ipv4
            String rePort = ".*?-port.*?"; //-port
            String rePortNum = "(\\d{4})"; //port
            String reU = ".*?-u.*?"; //-u
            String reUsername = "((?:[a-z][a-z]*[0-9]*[a-z0-9_,-]*))"; // Username

            Pattern pattern = Pattern.compile(reKorisnik + reIP + rePort + rePortNum + reU + reUsername);
            Matcher matcher = pattern.matcher(parametri);
            if (matcher.matches()) {
                if (matcher.group(1) != null) {
                    System.out.println("Server: " + matcher.group(1));
                }
                if (matcher.group(2) != null) {
                    System.out.println("PORT: " + matcher.group(2));
                }
                if (matcher.group(3) != null) {
                    System.out.println("USERNAME: " + matcher.group(3));
                }
                KlijentSustava klijentSustava = new KlijentSustava();
                klijentSustava.pokreniKorisnika(matcher.group(1), Integer.parseInt(matcher.group(2)));

                return klijentSustava;
            } else {
                System.out.println("Regex greška");
                return null;
            }
            //TODO Srediti za akcije na serveru
        } else if (parametri.startsWith("-admin")) {
            String reAdmin = "-admin.*?-server.*?"; //-korinsnik
            //TODO ili adresa
            String reIP = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])";//Ipv4
            String rePort = ".*?-port.*?"; //-port
            String rePortNum = "(\\d{4})"; //port
            String reU = ".*?-u.*?"; //-u
            String reUsername = "((?:[a-z][a-z]*[0-9]*[a-z0-9_,-]*))"; // Username
            String reP = ".*?-p.*?"; //-p
            String rePass = "((?:[a-z][a-z]*[0-9]*[a-z0-9_,-]*))"; //TODO pass posebni znakovi
            String reAkcija = ".*?(-pause|-start|stop|stat)?";

            Pattern pattern = Pattern.compile(reAdmin + reIP + rePort + rePortNum + reU + reUsername + reP + rePass + reAkcija);
            Matcher matcher = pattern.matcher(parametri);
            if (matcher.matches()) {
                if (matcher.group(1) != null) {
                    System.out.println("Adresa: " + matcher.group(1));
                }
                if (matcher.group(2) != null) {
                    System.out.println("Port: " + matcher.group(2));
                }
                if (matcher.group(3) != null) {
                    System.out.println("Username: " + matcher.group(3));
                }
                if (matcher.group(4) != null) {
                    System.out.println("Pass: " + matcher.group(4));
                }
                if (matcher.group(5) != null) {
                    System.out.println("Akcija: " + matcher.group(5));
                }
                AdministratorSustava administratorSustava = new AdministratorSustava();
                administratorSustava.pokreniKorisnika(matcher.group(1), Integer.parseInt(matcher.group(2)));
                return administratorSustava;
            } else {
                return null;
            }

        } else if (parametri.startsWith("-prikaz")) {
            String rePrikaz = "-prikaz.*?-s.*?"; //-prikaz -s
            String reDatoteka = "([^\\s]+\\.(?i))(txt|xml|bin)";

            Pattern pattern = Pattern.compile(rePrikaz + reDatoteka);
            Matcher matcher = pattern.matcher(parametri);
            if (matcher.matches()) {
                if (matcher.group(1) != null) {
                    System.out.println("Ime datoteke: " + matcher.group(1));
                }
                if (matcher.group(2) != null) {
                    System.out.println("Ekstenzja: " + matcher.group(2));
                }
                return new PregledSustava();
            } else {
                return null;
            }
        }
        System.out.println("Prvi parametar greška");
        return null;
    }
}

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
public class KorisnikSustava {

    public static void main(String[] args) {
        KorisnikSustava korisnik = new KorisnikSustava();
        
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < args.length; i++){
            stringBuilder.append(args[i]).append(" ");
        }
        String parametri = stringBuilder.toString().trim();
        
        int status = korisnik.provjeriParametre(parametri);

        if (status == 0) {
            System.out.println("Greška u parametrima!");
        } else if(status == 1){
            System.out.println("Pregled sustava");
        } else if (status == 2) {
            System.out.println("Korisnik sustava");
        } else if (status == 3) {
            System.out.println("Administrator sustava");
        } else {
            System.out.println("Greška kod provjere!");
        }
        //-admin -server [ipadresa | adresa] -port port -u korisnik -p lozinka [-pause | -start | -stop | -stat ]
        //TODO dovrši ostale paremetre
        //-admin -server localhost -port 8000
        /*String sintaksa = "^-admin -server ([^\\s]+) -port ([\\d]{4})$";

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]).append(" ");
        }
        String p = stringBuilder.toString().trim();
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher matcher = pattern.matcher(p);
        boolean status = matcher.matches();
        if (status) {
            int poc = 0;
            int kraj = matcher.groupCount();
            for (int i = poc; i < kraj; i++) {
                System.out.println(i + ". " + matcher.group());
            }

            String nazivServera = matcher.group(1);
            int port = Integer.parseInt(matcher.group(2));

            KorisnikSustava korisnikSustava = new KorisnikSustava();
            korisnikSustava.pokreniKorisnika(nazivServera, port);
        } else {
            System.out.println("Pogrešno unesena naredba!");
        }*/
    }

    //TODO vraća objek koji implementira sučelje za komunikaciju sa korisnikom
    private void pokreniKorisnika(String nazivServera, int port) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Socket socket = null;
        try {
            socket = new Socket(nazivServera, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String zahtjev = "USER pero; PASSWD 123456; PAUSE; ";
            outputStream.write(zahtjev.getBytes());
            outputStream.flush();
            socket.shutdownOutput();

            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                int znak = inputStream.read();
                if (znak == -1) {
                    break;
                }
                stringBuffer.append((char) znak);
            }
            System.out.println("Primljeni odgovor: " + stringBuffer);
        } catch (IOException ex) {
            Logger.getLogger(KorisnikSustava.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(KorisnikSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //TODO Vratiti objek klase koja implementira sučelje korisnik?
    private int provjeriParametre(String parametri) {
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
            //boolean status = matcher.matches();
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
                return 2;
            } else {
                System.out.println("Regex greška");
                return 0;
            }
        } else if(parametri.startsWith("-admin")){
            String reAdmin = "-admin.*?-server.*?"; //-korinsnik
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
            if(matcher.matches()){
                if(matcher.group(1) != null){
                    System.out.println("Adresa: " + matcher.group(1));
                }
                if(matcher.group(2) != null){
                    System.out.println("Port: " + matcher.group(2));
                }
                if(matcher.group(3) != null){
                    System.out.println("Username: " + matcher.group(3));
                }
                if(matcher.group(4) != null){
                    System.out.println("Pass: " + matcher.group(4));
                }
                if(matcher.group(5) != null){
                    System.out.println("Akcija: " + matcher.group(5));
                }
                return 3;
            }else {
                return 0;
            }
            
        } else if (parametri.startsWith("-prikaz")){
            String rePrikaz = "-prikaz.*?-s.*?"; //-korinsnik
            String reDatoteka = "([^\\s]+\\.(?i))(txt|xml|bin)";
            
            Pattern pattern = Pattern.compile(rePrikaz + reDatoteka);
            Matcher matcher = pattern.matcher(parametri);
            if(matcher.matches()){
                if(matcher.group(1) != null){
                    System.out.println("Ime datoteke: " + matcher.group(1));
                }
                if(matcher.group(2) != null){
                    System.out.println("Ekstenzja: " + matcher.group(2));
                }
                return 1;
            } else {
                return 0;
            }
        }
        System.out.println("Prvi parametar greška");
        return 0;
    }
}

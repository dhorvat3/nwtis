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
        //-admin -server [ipadresa | adresa] -port port -u korisnik -p lozinka [-pause | -start | -stop | -stat ]
        //TODO dovrši ostale paremetre
        //-admin -server localhost -port 8000
        String sintaksa = "^-admin -server ([^\\s]+) -port ([\\d]{4})$";

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
        }
    }

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
            while (true){
                int znak = inputStream.read();
                if(znak == -1){
                    break;
                }
                stringBuffer.append((char) znak);
            }
            System.out.println("Primljeni odgovor: " + stringBuffer);
        } catch (IOException ex) {
            Logger.getLogger(KorisnikSustava.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(inputStream != null){
                    inputStream.close();
                }
                
                if(outputStream != null){
                    outputStream.close();
                }
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(KorisnikSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

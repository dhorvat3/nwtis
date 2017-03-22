/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dhorvat3.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author Davorin Horvat
 */
public class ServerSustava {
    public static void main(String[] args){
        String sintaksa = "^-konf ([^\\s]+\\.(?i))(txt|xml|bin)( +-load)?$";
        
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++){
            builder.append(args[i]).append(" ");
        }
        String p = builder.toString().trim();
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher matcher = pattern.matcher(p);
        boolean status = matcher.matches();
        if(status){
            int poc = 0;
            int kraj = matcher.groupCount();
            for (int i = poc; i <= kraj; i++){
                System.out.println(i + ". " + matcher.group(i));
            }
            
            String nazivDatoteke = matcher.group(1) + matcher.group(2);
            boolean trebaUcitatiEvidenciju = false;
            if(matcher.group(3) != null){
                trebaUcitatiEvidenciju = true;
            }
            
            ServerSustava server = new ServerSustava();
            server.pokreniServer(nazivDatoteke, trebaUcitatiEvidenciju);
        } else {
            System.out.println("Pogrešna naredba!");
        }
    }
    
    private void pokreniServer(String nazivDatoteke, boolean trebaUcitatiEvidenciju){
        try {
            Konfiguracija konfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(nazivDatoteke);
            int port = Integer.parseInt(konfig.dajPostavku("port"));
            ServerSocket serverSocket = new ServerSocket(port);
            
            NadzorDretvi nadzorDretvi = new NadzorDretvi(konfig);
            nadzorDretvi.start();
            RezervnaDretva rezervnaDretva = new RezervnaDretva(konfig);
            rezervnaDretva.start();
            ProvjeraAdresa provjeraAdresa = new ProvjeraAdresa(konfig);
            provjeraAdresa.start();
            SerijalizatorEvidencije serijalizatorEvidencije = new SerijalizatorEvidencije(konfig);
            serijalizatorEvidencije.start();
            
            while(true){
                Socket socket = serverSocket.accept();
                RadnaDretva radnaDretva = new RadnaDretva(socket);
                
                //TODO dodaj dretvu u kolekciju aktivnih radnih dretvi
                
                radnaDretva.start();
                
                //TODO treba provjeriti ima li "mjesta" za novu radnu dretvu
            }
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija | IOException ex) {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

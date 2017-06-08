/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.dretve;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;

/**
 *
 * @author Davorin Horvat
 */
public class ObradaZahtjeva extends Thread{

    private Socket client = null;
    private String zahtjev = null;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ServletContext context;
    
    
    public ObradaZahtjeva(Socket client, ServletContext context){
        this.client = client;
        this.context = context;
    }
    
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            String regexMain = "USER ([\\w]+); PASSWD ([\\w]+);\\s?(IoT|IoT_Master)?\\s?([\\w]+)?;?";
            String odgovor = "";
            
            StringBuffer naredba = Helper.getInputStreamString(inputStream);
            
            System.out.println("Naredba: " + naredba);
            
            Pattern pattern = Pattern.compile(regexMain);
            Matcher matcher = pattern.matcher(naredba);
            if(matcher.matches()){
                if(matcher.group(3) == null){
                    System.out.println("--- OBRADA --- samo korisnik: " + matcher.group(1) + " " + matcher.group(2));
                    try {
                        int id = AdminObrada.autentikacija(matcher.group(1), matcher.group(2), context);
                        System.out.println("--- OBRADA --- ID: " + id);
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if(matcher.groupCount() < 4){
                    System.out.println("--- OBRADA --- Nije unesen zadnji argument");
                } else if (matcher.group(3).equals("IoT_Master")){
                    System.out.println("--- OBRADA --- IoT_Master: " + matcher.group(1) + " " + matcher.group(3) + " " + matcher.group(4));
                } else if (matcher.group(3).equals("IoT")){
                    System.out.println("--- OBRADA --- IoT: " + matcher.group(1) + " " + matcher.group(3) + " " + matcher.group(4));
                } else {
                    System.out.println("--- OBRADA --- Nepoznata naredba!");
                }
            } else {
                System.out.println("--- OBRADA --- Nepoznato");
            }
            
            if(naredba.toString().equals("")){
                odgovor = "Prazna naredba";
            } else {
                odgovor = "Nije prazna";
            }
            
            outputStream.write(odgovor.getBytes());
            outputStream.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(inputStream != null)
                    inputStream.close();
                if(outputStream != null)
                    outputStream.close();
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

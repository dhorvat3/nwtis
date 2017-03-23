/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Prvo se provjeravaju upisane opcije, preporučuje se koristiti dopuštene izraze. 
 * Objekt klase spaja se na server i šalje komandu(e) u zahtjevu. Primljeni odgovori 
 * se ispisuju na ekranu korisnika. Za svaku vrstu opcija kreira se posebna metoda 
 * koja odrađuje njenu funkcionalnost.
 * @author Davorin Horvat
 */
public class AdministratorSustava extends KorisnikApstraktni{
    private String naredba;
    private Pattern pattern;
    private Matcher matcher;
    /**
     * Provjerava korisnikovu naredbu.
     * Moguće naredbe:
     * USER korisnik; PASSWD lozinka; PAUSE;
     * USER korisnik; PASSWD lozinka; START;
     * USER korisnik; PASSWD lozinka; STOP; 
     * @return ispravna naredba
     */
    private boolean provjeriNaredbu(){
        String reUser = "USER.*?";
        String reKorisnik = "((?:[a-z][a-z]*[0-9]*[a-z0-9_,-]*));.*?";
        String rePasswd = "PASSWD.*?";
        String reLozinka = "((?:[a-z][a-z]*[0-9]*[a-z0-9_,-]*));.*?";
        String reNaredba = "(PAUSE|START|STOP);";
        
        pattern = Pattern.compile(reUser + reKorisnik + rePasswd + reLozinka + reNaredba);
        matcher = pattern.matcher(naredba);
        if(matcher.matches()){
            return true;
        }
        System.out.println("Naredba: " + naredba);
        return false;
    }
    
    private boolean provjeriNaredbu(String naredba){
        this.naredba = naredba;
        return provjeriNaredbu();
    }
    
    @Override
    public String posaljiNaredbu(String naredba){
        if(provjeriNaredbu(naredba)){
            System.out.println("Ispravna naredba");
            try{
                if(getOutputStream() != null){
                    getOutputStream().write(naredba.getBytes());
                    getOutputStream().flush();
                    getSocket().shutdownOutput();
                    
                    StringBuffer stringBuffer = new StringBuffer();
                    while(true){
                        int znak = getInputStream().read();
                        if(znak == -1){
                            break;
                        }
                        stringBuffer.append((char) znak);
                    }
                    ugasiKorisnika();
                    return stringBuffer.toString();
                }
            } catch (IOException ex) {
                Logger.getLogger(AdministratorSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Neispravna naredba!");
        return null;
    }
}

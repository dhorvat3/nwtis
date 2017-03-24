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
 * Prvo se provjeravaju upisane opcije, preporučuje se koristiti dopuštene izraze. 
 * Spaja na server i nakon toga šalje komandu serveru putem socketa  i traži izvršavanja 
 * određene akcije. Primljeni odgovor se ispisuju na ekranu korisnika.
 * @author Davorin Horvat
 */
public class KlijentSustava extends KorisnikApstraktni{
    private String naredba;
    private Pattern pattern;
    private Matcher matcher;
    
    
    /**
     * Provjerava korisnikovu naredbu.
     * Moguće naredbe:
     * USER korisnik; ADD adresa;
     * USER korisnik; TEST adresa;
     * USER korisnik; WAIT nnnnn; 
     * @return ispravna naredba?
     */
    private boolean provjeriNaredbu(){
        String reUser = "USER.*?"; //USER 
        String reKorisnik = "((?:[a-z][a-z]*[0-9]*[a-z0-9_,-]*));.*?";
        String reNaredba = "(ADD|TEST|WAIT).*?";
        String reVrijednost = "(((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])|(\\d{4}));";
        
        pattern = Pattern.compile(reUser + reKorisnik + reNaredba + reVrijednost);
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
    
    /**
     * Metoda obrađuje naredbu. Ako je ispravno upisana šalje ju na server.
     * @param naredba
     * @return Odgovor sa servera
     */
    //TODO bacanje iznimki umjesto null vrijednosti
    @Override
    public String posaljiNaredbu(String naredba){
        if(provjeriNaredbu(naredba)){
            String akcija = matcher.group(2);
            String vrijednost = matcher.group(3);
            
            if(vrijednost.length() == 4 && !akcija.equals("WAIT"))
                return "Naredbi " + akcija + " nije proslijeđena adresa!";
            
            //System.out.println("Ispravna naredba");
            try {
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
                } else {
                    return "Greška kod slanja zahtjeva!";
                }
            } catch (IOException ex) {
                Logger.getLogger(KlijentSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "Greška kod slanja zahtjeva!";
        }
        
        return "Neispravna naredba!";
    }
}

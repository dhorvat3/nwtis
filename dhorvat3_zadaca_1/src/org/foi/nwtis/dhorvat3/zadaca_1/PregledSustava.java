/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Prvo se provjeravaju upisane opcije, preporučuje se koristiti dopuštene izraze. 
 * Otvara i čita datoteku sa serijaliziranim podacima evidencije i ispisuje ih u 
 * prikladnom/čitljivom i formatiranom obliku na ekran/standardni izlaz korisnika.
 * @author Davorin Horvat
 */
public class PregledSustava implements Korisnik{
    private String naredba;
    private Pattern pattern;
    private Matcher matcher;
    
    private boolean provjeriNaredbu(){
        return false;
    }
    
    private boolean provjeriNaredbu(String naredba){
        this.naredba = naredba;
        return provjeriNaredbu();
    }

    @Override
    public String posaljiNaredbu(String naredba) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

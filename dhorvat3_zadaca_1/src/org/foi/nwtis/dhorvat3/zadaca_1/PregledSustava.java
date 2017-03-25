/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Prvo se provjeravaju upisane opcije, preporučuje se koristiti dopuštene
 * izraze. Otvara i čita datoteku sa serijaliziranim podacima evidencije i
 * ispisuje ih u prikladnom/čitljivom i formatiranom obliku na ekran/standardni
 * izlaz korisnika.
 *
 * @author Davorin Horvat
 */
public class PregledSustava implements Korisnik {

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public Evidencija posaljiNaredbu(String naredba) {
        //System.out.println("Pregled naredba: " + naredba);
        String rePrikaz = "-prikaz.*?-s.*?"; //-prikaz -s
        String reDatoteka = "([^\\s]+\\.(?i))(txt|xml|bin)";
        pattern = Pattern.compile(rePrikaz + reDatoteka);
        matcher = pattern.matcher(naredba);
        if (matcher.matches()) {
            return deserijalizirajBin(matcher.group(1) + matcher.group(2));
        }
        return null;
    }

    /**
     * Deserijalizacija binarne datoteke
     *
     * @param nazivDatoteke
     * @return Evidencija iz datoteke
     */
    private Evidencija deserijalizirajBin(String nazivDatoteke) {
        Evidencija evidencija = new Evidencija();
        File f = new File(nazivDatoteke);
        if (f.exists() && !f.isDirectory()) {
            try {
                FileInputStream inputStream = new FileInputStream(nazivDatoteke);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                evidencija = (Evidencija) objectInputStream.readObject();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PregledSustava.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(PregledSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
            return evidencija;

        }
        return null;
    }
}

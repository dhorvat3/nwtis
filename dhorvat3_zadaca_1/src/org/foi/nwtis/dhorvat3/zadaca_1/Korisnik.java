/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.zadaca_1;

/**
 * Sučelje korisnika. Implementiraju ga klase koje obrađuju Administratora ili
 * Klijetna sustava
 *
 * @author Davorin Horvat
 */
public interface Korisnik {

    /**
     * Pošalji naredbu serveru
     *
     * @param naredba
     * @return Odgovor sa servera
     */
    public Object posaljiNaredbu(String naredba);
}

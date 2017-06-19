/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.rest.klijenti;

import org.foi.nwtis.dkermek.ws.serveri.StatusUredjaja;

/**
 *
 * @author Davorin Horvat
 */
public class IoTKlijent {

    public static Boolean autenticirajGrupuIoT(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.autenticirajGrupuIoT(korisnickoIme, korisnickaLozinka);
    }

    public static Boolean dodajNoviUredjajGrupi(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idUredjaj, java.lang.String nazivUredjaj, java.lang.String adresaUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.dodajNoviUredjajGrupi(korisnickoIme, korisnickaLozinka, idUredjaj, nazivUredjaj, adresaUredjaj);
    }

    public static boolean aktivirajUredjajGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.aktivirajUredjajGrupe(korisnickoIme, korisnickaLozinka, idUredjaj);
    }

    public static boolean blokirajUredjajGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.blokirajUredjajGrupe(korisnickoIme, korisnickaLozinka, idUredjaj);
    }

    public static boolean obrisiUredjajGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.obrisiUredjajGrupe(korisnickoIme, korisnickaLozinka, idUredjaj);
    }

    public static StatusUredjaja dajStatusUredjajaGrupe(java.lang.String korisnickoIme, java.lang.String korisnickaLozinka, int idUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.dajStatusUredjajaGrupe(korisnickoIme, korisnickaLozinka, idUredjaj);
    }

}

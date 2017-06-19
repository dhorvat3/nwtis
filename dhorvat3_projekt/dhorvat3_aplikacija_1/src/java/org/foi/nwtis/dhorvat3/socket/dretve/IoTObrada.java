/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.dretve;

import org.foi.nwtis.dhorvat3.rest.klijenti.IoTKlijent;
import org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava;
import org.foi.nwtis.dkermek.ws.serveri.StatusUredjaja;

/**
 *
 * @author Davorin Horvat
 */
public final class IoTObrada {

    protected static String dodajUredaj(String username, String password, int id, String naziv, String adresa) throws NeuspjesnaPrijava {
        if (!IoTKlijent.autenticirajGrupuIoT(username, password)) {
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        }

        return IoTKlijent.dodajNoviUredjajGrupi(username, password, id, naziv, adresa) ? "OK 10;" : "ERR 30;";
    }

    protected static String aktivirajUredaj(String username, String password, int id) throws NeuspjesnaPrijava {
        if (!IoTKlijent.autenticirajGrupuIoT(username, password)) {
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu!");
        }

        return IoTKlijent.aktivirajUredjajGrupe(username, password, id) ? "OK 10;" : "ERR 31;";
    }

    protected static String blokirajUredaj(String username, String password, int id) throws NeuspjesnaPrijava {
        if (!IoTKlijent.autenticirajGrupuIoT(username, password)) {
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu!");
        }
        return IoTKlijent.blokirajUredjajGrupe(username, password, id) ? "OK 10;" : "ERR 32;";
    }

    protected static String brisiUredaj(String username, String password, int id) throws NeuspjesnaPrijava {
        if (!IoTKlijent.autenticirajGrupuIoT(username, password)) {
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        }
        return IoTKlijent.obrisiUredjajGrupe(username, password, id) ? "OK 10;" : "ERR 33;";
    }

    protected static String statusUredaja(String username, String password, int id) throws NeuspjesnaPrijava {
        if (!IoTKlijent.autenticirajGrupuIoT(username, password)) {
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        }

        StatusUredjaja status = IoTKlijent.dajStatusUredjajaGrupe(username, password, id);

        if (status.value().equals("AKTIVAN")) {
            return "OK 35;";
        }
        if (status.value().equals("BLOKIRAN")) {
            return "OK 34";
        }
        return "";
    }
}

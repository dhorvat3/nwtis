/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.dretve;

import java.util.List;
import org.foi.nwtis.dhorvat3.rest.klijenti.IoTMasterKlijent;
import org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava;
import org.foi.nwtis.dkermek.ws.serveri.StatusKorisnika;
import org.foi.nwtis.dkermek.ws.serveri.Uredjaj;

/**
 *
 * @author Davorin Horvat
 */
public final class IoTMasterObrada {
    
    protected static String registrirajGrupu(String username, String password) throws NeuspjesnaPrijava{
        if(!IoTMasterKlijent.autenticirajGrupuIoT(username, password))
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        
        return IoTMasterKlijent.registrirajGrupuIoT(username, password) ? "OK 10;" : "ERR 20;";
    }
    
    protected static String odjaviGrupu(String username, String password) throws NeuspjesnaPrijava{
        if(!IoTMasterKlijent.autenticirajGrupuIoT(username, password))
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        
        return IoTMasterKlijent.deregistrirajGrupuIoT(username, password) ? "OK 10;" : "ERR 21;";
    }
    
    protected static String aktivirajGrupu(String username, String password) throws NeuspjesnaPrijava{
        if(!IoTMasterKlijent.autenticirajGrupuIoT(username, password))
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        
        return IoTMasterKlijent.aktivirajGrupuIoT(username, password) ? "OK 10;" : "ERR 22;";
    }
    
    protected static String blokirajGrupu(String username, String password) throws NeuspjesnaPrijava{
        if(!IoTMasterKlijent.autenticirajGrupuIoT(username, password))
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        
        return IoTMasterKlijent.blokirajGrupuIoT(username, password) ? "OK 10;" : "ERR 23;";
    }
    
    protected static String ucitajUredaje(String username, String password) throws NeuspjesnaPrijava{
        if(!IoTMasterKlijent.autenticirajGrupuIoT(username, password))
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        return IoTMasterKlijent.ucitajSveUredjajeGrupe(username, password) ? "OK 10;" : "";
    }
    
    protected static String brisiUredaje(String username, String password) throws NeuspjesnaPrijava{
        if(!IoTMasterKlijent.autenticirajGrupuIoT(username, password))
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        
        return IoTMasterKlijent.obrisiSveUredjajeGrupe(username, password) ? "OK 10;" : "" ;
    }
    
    protected static String statusGrupe(String username, String password) throws NeuspjesnaPrijava{
        if(!IoTMasterKlijent.autenticirajGrupuIoT(username, password))
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        
        StatusKorisnika status = IoTMasterKlijent.dajStatusGrupeIoT(username, password);
        
        if(status.value().equals("BLOKIRAN"))
            return "OK 24;";
        if(status.value().equals("AKTIVAN"))
            return "OK 25";
        return "";
    }
    
    protected static String dajUredaje(String username, String password) throws NeuspjesnaPrijava{
        if(!IoTMasterKlijent.autenticirajGrupuIoT(username, password))
            throw new NeuspjesnaPrijava("Neispravni podaci za prijavu");
        
        List<Uredjaj> uredaji = IoTMasterKlijent.dajSveUredjajeGrupe(username, password);
        String odgovor = "OK 10;";
        
        for (Uredjaj uredjaj : uredaji) {            
            odgovor += "{IoT " + uredjaj.getId() + " ";
            odgovor += "\"" + uredjaj.getNaziv() + "\"}";
        }
        
        odgovor += ";";
        
        return odgovor;
    }
}

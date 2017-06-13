/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.dhorvat3.web.podaci.Izbornik;

/**
 *
 * @author Davorin Horvat
 */
@ManagedBean(name="menu")
@RequestScoped
public class Menu {
    private ArrayList<Izbornik> menu = new ArrayList();
    /**
     * Creates a new instance of Menu
     */
    public Menu() {
    }

    public ArrayList<Izbornik> getMenu() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        menu.clear();
        menu.add(new Izbornik("Početna", "/dhorvat3_aplikacija_1/Kontroler"));
        
        if(session.getAttribute("korisnik") != null){
            menu.add(new Izbornik("Pregled korisnika", "/dhorvat3_aplikacija_1/PregledKorisnika"));
            menu.add(new Izbornik("Pregled dnevnika", "/dhorvat3_aplikacija_1/PregledDnevnika"));
            menu.add(new Izbornik("Pregled zahtjeva", "/dhorvat3_aplikacija_1/PregledZahtjeva"));
            menu.add(new Izbornik("Odjava", "/dhorvat3_aplikacija_1/Odjava"));
        } else 
            menu.add(new Izbornik("Prijava", "/dhorvat3_aplikacija_1/Prijava"));
        
        
        return menu;
    }

    public void setMenu(ArrayList<Izbornik> menu) {
        this.menu = menu;
    }
    
    
    
}

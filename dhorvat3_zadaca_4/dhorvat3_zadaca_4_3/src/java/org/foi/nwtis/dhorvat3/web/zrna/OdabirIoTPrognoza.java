/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.dhorvat3.ejb.eb.Dnevnik;
import org.foi.nwtis.dhorvat3.ejb.eb.Promjene;
import org.foi.nwtis.dhorvat3.ejb.eb.Uredaji;
import org.foi.nwtis.dhorvat3.ejb.sb.DnevnikFacade;
import org.foi.nwtis.dhorvat3.ejb.sb.MeteoIoTKlijent;
import org.foi.nwtis.dhorvat3.ejb.sb.PromjeneFacade;
import org.foi.nwtis.dhorvat3.ejb.sb.UredajiFacade;
import org.foi.nwtis.dhorvat3.web.kontrole.Izbornik;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPrognoza;
import org.foi.nwtis.dhorvat3.web.podaci.Uredjaj;

/**
 *
 * @author Davorin Horvat
 */
@Named(value = "odabirIoTPrognoza")
@SessionScoped
public class OdabirIoTPrognoza implements Serializable {

    @EJB
    private PromjeneFacade promjeneFacade;

    @EJB
    private DnevnikFacade dnevnikFacade;

    @EJB
    private MeteoIoTKlijent meteoIoTKlijent;

    @EJB
    private UredajiFacade uredajiFacade;
    
    
    

    private String noviId;
    private String noviNaziv;
    private String noviAdresa;
    private List<Izbornik> raspoloziviIoT = new ArrayList<>();
    private List<Izbornik> odabraniIoT = new ArrayList<>();
    private List<String> popisRaspoloziviIoT = new ArrayList<>();
    private List<String> popisOdabraniIoT = new ArrayList<>();
    private List<MeteoPrognoza> meteoPrognoze = new ArrayList<>();
    private boolean azuriranje = false;
    private boolean prognoze = false;
    private boolean prikazPrognoze = false;
    private String azurirajId;
    private String azurirajNaziv;
    private String azurirajAdresa;
    private String gumbPregledPrognoza = "Pregled prognoza";

    /**
     * Creates a new instance of OdabirIoTPrognoza
     */
    public OdabirIoTPrognoza() {
    }

    public boolean isPrikazPrognoze() {
        return prikazPrognoze;
    }

    public void setPrikazPrognoze(boolean prikazPrognoze) {
        this.prikazPrognoze = prikazPrognoze;
    }

    public String getNoviId() {
        return noviId;
    }

    public void setNoviId(String noviId) {
        this.noviId = noviId;
    }

    public String getNoviNaziv() {
        return noviNaziv;
    }

    public void setNoviNaziv(String noviNaziv) {
        this.noviNaziv = noviNaziv;
    }

    public String getNoviAdresa() {
        return noviAdresa;
    }

    public void setNoviAdresa(String noviAdresa) {
        this.noviAdresa = noviAdresa;
    }

    public List<Izbornik> getRaspoloziviIoT() {
        preuzmiRaspoloziveIoTUredaje();
        return raspoloziviIoT;
    }

    public void setRaspoloziviIoT(List<Izbornik> raspoloziviIoT) {
        this.raspoloziviIoT = raspoloziviIoT;
    }

    public List<Izbornik> getOdabraniIoT() {
        return odabraniIoT;
    }

    public void setOdabraniIoT(List<Izbornik> odabraniIoT) {
        this.odabraniIoT = odabraniIoT;
    }

    public List<String> getPopisRaspoloziviIoT() {
        return popisRaspoloziviIoT;
    }

    public void setPopisRaspoloziviIoT(List<String> popisRaspoloziviIoT) {
        this.popisRaspoloziviIoT = popisRaspoloziviIoT;
    }

    public List<String> getPopisOdabraniIoT() {
        return popisOdabraniIoT;
    }

    public void setPopisOdabraniIoT(List<String> popisOdabraniIoT) {
        this.popisOdabraniIoT = popisOdabraniIoT;
    }

    public List<MeteoPrognoza> getMeteoPrognoze() {
        return meteoPrognoze;
    }

    public void setMeteoPrognoze(List<MeteoPrognoza> meteoPrognoze) {
        this.meteoPrognoze = meteoPrognoze;
    }

    public boolean isAzuriranje() {
        return azuriranje;
    }

    public void setAzuriranje(boolean azuriranje) {
        this.azuriranje = azuriranje;
    }

    public boolean isPrognoze() {
        return prognoze;
    }

    public void setPrognoze(boolean prognoze) {
        this.prognoze = prognoze;
    }

    public String getAzurirajId() {
        return azurirajId;
    }

    public void setAzurirajId(String azurirajId) {
        this.azurirajId = azurirajId;
    }

    public String getAzurirajNaziv() {
        return azurirajNaziv;
    }

    public void setAzurirajNaziv(String azurirajNaziv) {
        this.azurirajNaziv = azurirajNaziv;
    }

    public String getAzurirajAdresa() {
        return azurirajAdresa;
    }

    public void setAzurirajAdresa(String azurirajAdresa) {
        this.azurirajAdresa = azurirajAdresa;
    }

    public String getGumbPregledPrognoza() {
        return gumbPregledPrognoza;
    }

    public void setGumbPregledPrognoza(String gumbPregledPrognoza) {
        this.gumbPregledPrognoza = gumbPregledPrognoza;
    }

    private void preuzmiRaspoloziveIoTUredaje(){
        Date vrijemePocetak = new Date();
        List<Uredaji> uredaji = uredajiFacade.findAll();
        raspoloziviIoT.clear();
        azuriranje = odabraniIoT.size() == 1;
        System.out.println("-- BROJ: " + odabraniIoT.size());
        System.out.println("--- AZURIRANJE: " + azuriranje);
        for (Uredaji uredaj : uredaji) {
            boolean postoji = false;
            for (Izbornik izbornik : odabraniIoT) {
                if(izbornik.getVrijednost().equals(uredaj.getId().toString()))
                    postoji = true;
            }
            if(!postoji)
                raspoloziviIoT.add(new Izbornik(uredaj.getNaziv(), uredaj.getId().toString()));
        }
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime())/1000;
        
        zapisiDnevnik(razlika, vrijemePocetak, 1);
        
    }
    
    public String dodajIoTUredaj(){
        Date vrijemePocetak = new Date();
        Lokacija l = meteoIoTKlijent.dajLokaciju(noviAdresa);
        float lat = Float.parseFloat(l.getLatitude());
        float lng = Float.parseFloat(l.getLongitude());
        Uredaji uredaji = new Uredaji(Integer.parseInt(noviId), noviNaziv, lat, lng, 0, new Date(), new Date());
        uredajiFacade.create(uredaji);
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime())/1000;
        
        zapisiDnevnik(razlika, vrijemePocetak, 2);
        return "";
    }
    
    public String preuzmiIoTUredjaj(){
        Date vrijemePocetak = new Date();
        prognoze = true;
        for(Iterator<Izbornik> iterator = raspoloziviIoT.iterator(); iterator.hasNext();){
            Izbornik element = iterator.next();
            //System.out.println("OBRADA: " + element.getVrijednost());
            if(popisRaspoloziviIoT.contains(element.getVrijednost())){
                odabraniIoT.add(element);
                iterator.remove();
            }
        }
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime())/1000;
        
        zapisiDnevnik(razlika, vrijemePocetak, 3);
        return "";
    }
    
    public String azurirajIoTUredjaj(){
        Date vrijemePocetak = new Date();
        Uredaji uredaj = uredajiFacade.find(Integer.parseInt(azurirajId));
        if(uredaj.getNaziv() == null ? azurirajNaziv != null : !uredaj.getNaziv().equals(azurirajNaziv)){
            uredaj.setNaziv(azurirajNaziv);
        }
        if(azurirajAdresa != null){
            Lokacija l = meteoIoTKlijent.dajLokaciju(azurirajAdresa);
            if(uredaj.getLatitude() != Float.parseFloat(l.getLatitude())){
                uredaj.setLatitude(Float.parseFloat(l.getLatitude()));
            }
            if(uredaj.getLongitude() != Float.parseFloat(l.getLongitude())){
                uredaj.setLongitude(Float.parseFloat(l.getLongitude()));
            }
        }
        odabraniIoT.get(0).setLabela(uredaj.getNaziv());
        uredajiFacade.edit(uredaj);
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime())/1000;
        
        Promjene promjene = new Promjene();
        
        promjene.setId(uredaj.getId());
        promjene.setNaziv(uredaj.getNaziv());
        promjene.setLatitude(uredaj.getLatitude());
        promjene.setLongitude(uredaj.getLongitude());
        promjene.setStatus(uredaj.getStatus());
        promjene.setVrijemePromjene(new Date());
        promjene.setVrijemeKreiranja(uredaj.getVrijemeKreiranja());
        
        promjeneFacade.create(promjene);
        
        zapisiDnevnik(razlika, vrijemePocetak, 4);
        return "";
    }
    
    public String prikaziUredaj(){
        Date vrijemePocetak = new Date();
        String id = odabraniIoT.get(0).getVrijednost();
        System.out.println("--- ID: " + id);
        List<Uredaji> uredaji = uredajiFacade.findAll();
        for (Uredaji uredaj : uredaji) {
            if(uredaj.getId().toString().equals(id)){
                azurirajId = uredaj.getId().toString();
                azurirajNaziv = uredaj.getNaziv();
                azurirajAdresa = meteoIoTKlijent.dajAdresu(uredaj.getLatitude(), uredaj.getLongitude());
            }
        }
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime())/1000;
        
        zapisiDnevnik(razlika, vrijemePocetak, 5);
        return "";
    }
    
    public String vratiIoTUredjaj(){
        azuriranje = odabraniIoT.size() == 1;
        for (Iterator<Izbornik> iterator = odabraniIoT.iterator(); iterator.hasNext();) {
            Izbornik next = iterator.next();
            if(popisOdabraniIoT.contains(next.getVrijednost())){
                raspoloziviIoT.add(next);
                iterator.remove();
            }
        }
        prognoze = !odabraniIoT.isEmpty();
        if(!prognoze){
            gumbPregledPrognoza = "Pregled prognoza";
            prikazPrognoze = false;
        }
        return "";
    }
    
    public String prikaziPrognozu(){
        Date vrijemePocetak = new Date();
        prikazPrognoze = true;
        meteoPrognoze.clear();
        List<Uredaji> uredaji = uredajiFacade.findAll();
        for (Izbornik izbornik : odabraniIoT) {
            for (Uredaji uredaj : uredaji) {
                if(izbornik.getVrijednost().equals(uredaj.getId().toString())){
                    String adresa = meteoIoTKlijent.dajAdresu(uredaj.getLatitude(), uredaj.getLongitude());
                    for (MeteoPrognoza meteoPrognoza : meteoIoTKlijent.dajMeteoPrognoze(uredaj.getId(), adresa)) {
                        meteoPrognoze.add(meteoPrognoza);
                    }
                }
            }
        }
        gumbPregledPrognoza = "Ažuriraj prognoze";
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime())/1000;
        
        zapisiDnevnik(razlika, vrijemePocetak, 6);
        return "";
    }
    
    /**
     * Statusi:
     * 1 - Preuzmi raspoložive IoT uređaje
     * 2 - Dodaj IoT uređaj
     * 3 - Preuzmi IoT Uređaj
     * 4 - Ažuriraj IoT uređaj
     * 5 - Prikaži prognozu
     * @param razlika
     * @param vrijemePocetak
     * @param status 
     */
    private void zapisiDnevnik(int razlika, Date vrijemePocetak, int status){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = request.getHeader("X-FORWARDED-FOR");
        if(ip == null){
            ip = request.getRemoteAddr();
        }
        String url = request.getRequestURI();
        
        Dnevnik dnevnik = new Dnevnik();
        dnevnik.setTrajanje(razlika);
        dnevnik.setIpadresa(ip);
        dnevnik.setVrijeme(vrijemePocetak);
        dnevnik.setUrl(url);
        dnevnik.setKorisnik("aa");
        dnevnik.setStatus(status);
        dnevnikFacade.create(dnevnik);
    }
}

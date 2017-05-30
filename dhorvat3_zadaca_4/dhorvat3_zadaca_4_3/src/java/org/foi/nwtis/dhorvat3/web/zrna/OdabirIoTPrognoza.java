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
 * Session bean za obradu promjene IoT uređaja. ostupak počinje unosom novog IoT
 * uređaja (id, naziv, adresa) i pregledom postojećih IoT uređaja (u obliku
 * padajućeg izbornika) iz ORM (tablica uredjaji). Dodavanje novog IoT uređaja
 * (putem gumba Dodaj IoT uređaj) Ajaxom pokreće preuzimanje lokacijskih
 * podataka za upisanu adresu, spremanje objekta u ORM (upis u tablicu
 * uredjaji), spremanje objekta u ORM (tablica promjene) i ažuriranjem pregleda
 * raspoloživih IoT uređaja. Iz raspoloživih IoT uređaja može se odabrati jedan
 * ili više IoT uređaja te (putem gumba Preuzmi) Ajaxom dodati u popis odabranih
 * IoT uređaja. IoT uređaj(i) koji se odabire(u) više se ne pojavljuje u
 * pregledu raspoloživih IoT uređaja. Moguće je u popisu raspoloživih IoT
 * uređaja odabrati jedan i pokrenuti njegovo ažuriranje (putem gumba Ažuriraj)
 * u istom pogledu. Dio za ažuriranje je to sada bio sakriven. Nakon ažuriranja
 * podataka (putem gumba Upiši) ažurira se objekt u ORM (tablica uredjaji),
 * sprema se objekt u ORM (tablica promjene), sakriva se dio za ažuriranje i
 * vraća se na odabir IoT uređaja. Kod popisa odabranih IoT uređaja može se
 * maknuti (putem gumba Vrati) jedan ili više IoT uređaja putem Ajaxa (i tada se
 * on(i) ponovno pojavljuje(u) u pregledu raspoloživih IoT uređaja). Nakon
 * pokretanja prognoza (putem gumba Prognoze) putem Ajaxa, preuzimaju se
 * prognoze vremena za sve odabrane IoT uređaje i 5 dana prognoze. Gumb Prognoze
 * mijenja naziv u Zatvori prognoze. Zatim se prikazuju podaci prognoza (IoT
 * uređaj, adresa iz prognoze, datum, temp,...) u bloku s tablicom (taj dio je
 * prethodno bio sakriven) i nakon završetka pregleda prognoza (putem gumba
 * Zatvori prognoze) cijeli blok se sakriva.
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

    /**
     * Dohvaća sve raspoložive IoT uređaje iz baze podataka. Zapisuje u dnevnik
     * korisnikovu akciju (status 1 - Preuzimanje)
     */
    private void preuzmiRaspoloziveIoTUredaje() {
        Date vrijemePocetak = new Date();
        List<Uredaji> uredaji = uredajiFacade.findAll();
        raspoloziviIoT.clear();
        azuriranje = odabraniIoT.size() == 1;

        for (Uredaji uredaj : uredaji) {
            boolean postoji = false;
            for (Izbornik izbornik : odabraniIoT) {
                if (izbornik.getVrijednost().equals(uredaj.getId().toString())) {
                    postoji = true;
                }
            }
            if (!postoji) {
                raspoloziviIoT.add(new Izbornik(uredaj.getNaziv(), uredaj.getId().toString()));
            }
        }
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime()) / 1000;

        zapisiDnevnik(razlika, vrijemePocetak, 1);

    }

    /**
     * Dodavanje IoT uređaja u bazu podataka. Zapisuje u dnevnik korisnikovu
     * akciju (status 2 - Dodavanje)
     */
    public void dodajIoTUredaj() {
        Date vrijemePocetak = new Date();
        Lokacija l = meteoIoTKlijent.dajLokaciju(noviAdresa);
        float lat = Float.parseFloat(l.getLatitude());
        float lng = Float.parseFloat(l.getLongitude());
        Uredaji uredaji = new Uredaji(Integer.parseInt(noviId), noviNaziv, lat, lng, 0, new Date(), new Date());
        uredajiFacade.create(uredaji);
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime()) / 1000;

        zapisiDnevnik(razlika, vrijemePocetak, 2);
    }

    /**
     * Preuzimanje IoT uređaja (uređaj se iz tablice raspoloživih uređaja
     * prebacuje u tablicu odabranih uređaja) Zapisuje u dnevnik korisnikovu
     * akciju (status 3 - Preuzimanje uređaja)
     */
    public void preuzmiIoTUredjaj() {
        Date vrijemePocetak = new Date();
        prognoze = true;
        for (Iterator<Izbornik> iterator = raspoloziviIoT.iterator(); iterator.hasNext();) {
            Izbornik element = iterator.next();
            //System.out.println("OBRADA: " + element.getVrijednost());
            if (popisRaspoloziviIoT.contains(element.getVrijednost())) {
                odabraniIoT.add(element);
                iterator.remove();
            }
        }
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime()) / 1000;

        zapisiDnevnik(razlika, vrijemePocetak, 3);
    }

    /**
     * Metoda za ažuriranje podataka o IoT uređaju. Metoda dodaje zapis u
     * tablicu promjena. Zapisuje u dnevnik korisnikovu akciju 
     * (status 4 - Ažuriranje uređaja)
     */
    public void azurirajIoTUredjaj() {
        Date vrijemePocetak = new Date();
        Uredaji uredaj = uredajiFacade.find(Integer.parseInt(azurirajId));
        if (uredaj.getNaziv() == null ? azurirajNaziv != null : !uredaj.getNaziv().equals(azurirajNaziv)) {
            uredaj.setNaziv(azurirajNaziv);
        }
        if (azurirajAdresa != null) {
            Lokacija l = meteoIoTKlijent.dajLokaciju(azurirajAdresa);
            if (uredaj.getLatitude() != Float.parseFloat(l.getLatitude())) {
                uredaj.setLatitude(Float.parseFloat(l.getLatitude()));
            }
            if (uredaj.getLongitude() != Float.parseFloat(l.getLongitude())) {
                uredaj.setLongitude(Float.parseFloat(l.getLongitude()));
            }
        }
        odabraniIoT.get(0).setLabela(uredaj.getNaziv());
        uredajiFacade.edit(uredaj);
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime()) / 1000;

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
    }

    /**
     * Prikaz podataka o odabranom IoT uređaju za ažuriranje. Zapisuje u dnevnik
     * korisnikovu akciju (status 5 - prikaz)
     */
    public void prikaziUredaj() {
        Date vrijemePocetak = new Date();
        String id = odabraniIoT.get(0).getVrijednost();
        System.out.println("--- ID: " + id);
        List<Uredaji> uredaji = uredajiFacade.findAll();
        for (Uredaji uredaj : uredaji) {
            if (uredaj.getId().toString().equals(id)) {
                azurirajId = uredaj.getId().toString();
                azurirajNaziv = uredaj.getNaziv();
                azurirajAdresa = meteoIoTKlijent.dajAdresu(uredaj.getLatitude(), uredaj.getLongitude());
            }
        }
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime()) / 1000;

        zapisiDnevnik(razlika, vrijemePocetak, 5);
    }

    /**
     * Vraćanje IoT uređaja u Raspoložive uređaje.
     */
    public void vratiIoTUredjaj() {
        azuriranje = odabraniIoT.size() == 1;
        for (Iterator<Izbornik> iterator = odabraniIoT.iterator(); iterator.hasNext();) {
            Izbornik next = iterator.next();
            if (popisOdabraniIoT.contains(next.getVrijednost())) {
                raspoloziviIoT.add(next);
                iterator.remove();
            }
        }
        prognoze = !odabraniIoT.isEmpty();
        if (!prognoze) {
            gumbPregledPrognoza = "Pregled prognoza";
            prikazPrognoze = false;
        }
    }

    /**
     * Prikazuje petodnevnu prognozu za odabrane uređaje.
     */
    public void prikaziPrognozu() {
        Date vrijemePocetak = new Date();
        prikazPrognoze = true;
        meteoPrognoze.clear();
        List<Uredaji> uredaji = uredajiFacade.findAll();
        for (Izbornik izbornik : odabraniIoT) {
            for (Uredaji uredaj : uredaji) {
                if (izbornik.getVrijednost().equals(uredaj.getId().toString())) {
                    String adresa = meteoIoTKlijent.dajAdresu(uredaj.getLatitude(), uredaj.getLongitude());
                    for (MeteoPrognoza meteoPrognoza : meteoIoTKlijent.dajMeteoPrognoze(uredaj.getId(), adresa)) {
                        meteoPrognoze.add(meteoPrognoza);
                    }
                }
            }
        }
        gumbPregledPrognoza = "Ažuriraj prognoze";
        Date vrijemeKraj = new Date();
        int razlika = (int) (vrijemeKraj.getTime() - vrijemePocetak.getTime()) / 1000;

        zapisiDnevnik(razlika, vrijemePocetak, 6);
    }

    /**
     * Zapisuje podatke u dnevnik. Statusi: 1 - Preuzmi raspoložive IoT uređaje
     * 2 - Dodaj IoT uređaj 3 - Preuzmi IoT Uređaj 4 - Ažuriraj IoT uređaj 5 -
     * Prikaži prognozu
     *
     * @param razlika Vrijeme obrade u sekundama
     * @param vrijemePocetak Vrijeme početka obrade
     * @param status Status.
     */
    private void zapisiDnevnik(int razlika, Date vrijemePocetak, int status) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
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

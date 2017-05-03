
package org.foi.nwtis.dhorvat3.ws.serveri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.foi.nwtis.dhorvat3.ws.serveri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DajSveMeteoPodatkeZaUredjaj_QNAME = new QName("http://serveri.ws.dhorvat3.nwtis.foi.org/", "dajSveMeteoPodatkeZaUredjaj");
    private final static QName _DajSveMeteoPodatkeZaUredjajResponse_QNAME = new QName("http://serveri.ws.dhorvat3.nwtis.foi.org/", "dajSveMeteoPodatkeZaUredjajResponse");
    private final static QName _DajSveUredjaje_QNAME = new QName("http://serveri.ws.dhorvat3.nwtis.foi.org/", "dajSveUredjaje");
    private final static QName _DajSveUredjajeResponse_QNAME = new QName("http://serveri.ws.dhorvat3.nwtis.foi.org/", "dajSveUredjajeResponse");
    private final static QName _DodajUredjaj_QNAME = new QName("http://serveri.ws.dhorvat3.nwtis.foi.org/", "dodajUredjaj");
    private final static QName _DodajUredjajResponse_QNAME = new QName("http://serveri.ws.dhorvat3.nwtis.foi.org/", "dodajUredjajResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.foi.nwtis.dhorvat3.ws.serveri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DajSveMeteoPodatkeZaUredjaj }
     * 
     */
    public DajSveMeteoPodatkeZaUredjaj createDajSveMeteoPodatkeZaUredjaj() {
        return new DajSveMeteoPodatkeZaUredjaj();
    }

    /**
     * Create an instance of {@link DajSveMeteoPodatkeZaUredjajResponse }
     * 
     */
    public DajSveMeteoPodatkeZaUredjajResponse createDajSveMeteoPodatkeZaUredjajResponse() {
        return new DajSveMeteoPodatkeZaUredjajResponse();
    }

    /**
     * Create an instance of {@link DajSveUredjaje }
     * 
     */
    public DajSveUredjaje createDajSveUredjaje() {
        return new DajSveUredjaje();
    }

    /**
     * Create an instance of {@link DajSveUredjajeResponse }
     * 
     */
    public DajSveUredjajeResponse createDajSveUredjajeResponse() {
        return new DajSveUredjajeResponse();
    }

    /**
     * Create an instance of {@link DodajUredjaj }
     * 
     */
    public DodajUredjaj createDodajUredjaj() {
        return new DodajUredjaj();
    }

    /**
     * Create an instance of {@link DodajUredjajResponse }
     * 
     */
    public DodajUredjajResponse createDodajUredjajResponse() {
        return new DodajUredjajResponse();
    }

    /**
     * Create an instance of {@link Uredjaj }
     * 
     */
    public Uredjaj createUredjaj() {
        return new Uredjaj();
    }

    /**
     * Create an instance of {@link Lokacija }
     * 
     */
    public Lokacija createLokacija() {
        return new Lokacija();
    }

    /**
     * Create an instance of {@link MeteoPodaci }
     * 
     */
    public MeteoPodaci createMeteoPodaci() {
        return new MeteoPodaci();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveMeteoPodatkeZaUredjaj }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dhorvat3.nwtis.foi.org/", name = "dajSveMeteoPodatkeZaUredjaj")
    public JAXBElement<DajSveMeteoPodatkeZaUredjaj> createDajSveMeteoPodatkeZaUredjaj(DajSveMeteoPodatkeZaUredjaj value) {
        return new JAXBElement<DajSveMeteoPodatkeZaUredjaj>(_DajSveMeteoPodatkeZaUredjaj_QNAME, DajSveMeteoPodatkeZaUredjaj.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveMeteoPodatkeZaUredjajResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dhorvat3.nwtis.foi.org/", name = "dajSveMeteoPodatkeZaUredjajResponse")
    public JAXBElement<DajSveMeteoPodatkeZaUredjajResponse> createDajSveMeteoPodatkeZaUredjajResponse(DajSveMeteoPodatkeZaUredjajResponse value) {
        return new JAXBElement<DajSveMeteoPodatkeZaUredjajResponse>(_DajSveMeteoPodatkeZaUredjajResponse_QNAME, DajSveMeteoPodatkeZaUredjajResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveUredjaje }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dhorvat3.nwtis.foi.org/", name = "dajSveUredjaje")
    public JAXBElement<DajSveUredjaje> createDajSveUredjaje(DajSveUredjaje value) {
        return new JAXBElement<DajSveUredjaje>(_DajSveUredjaje_QNAME, DajSveUredjaje.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveUredjajeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dhorvat3.nwtis.foi.org/", name = "dajSveUredjajeResponse")
    public JAXBElement<DajSveUredjajeResponse> createDajSveUredjajeResponse(DajSveUredjajeResponse value) {
        return new JAXBElement<DajSveUredjajeResponse>(_DajSveUredjajeResponse_QNAME, DajSveUredjajeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DodajUredjaj }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dhorvat3.nwtis.foi.org/", name = "dodajUredjaj")
    public JAXBElement<DodajUredjaj> createDodajUredjaj(DodajUredjaj value) {
        return new JAXBElement<DodajUredjaj>(_DodajUredjaj_QNAME, DodajUredjaj.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DodajUredjajResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dhorvat3.nwtis.foi.org/", name = "dodajUredjajResponse")
    public JAXBElement<DodajUredjajResponse> createDodajUredjajResponse(DodajUredjajResponse value) {
        return new JAXBElement<DodajUredjajResponse>(_DodajUredjajResponse_QNAME, DodajUredjajResponse.class, null, value);
    }

}

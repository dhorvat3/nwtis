/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.ejb.sb;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.xml.ws.WebServiceRef;
import org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS_Service;
import org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci;

/**
 *
 * @author Davorin Horvat
 */
@Singleton
@LocalBean
public class MeteoOsvjezivac {

    @Resource(mappedName = "jms/NWTiS_vjezba_12")
    private Queue nWTiS_vjezba_12;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @EJB
    private MeteoPrognosticar meteoPrognosticar;

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8084/dhorvat3_zadaca_3_1/GeoMeteoWS.wsdl")
    private GeoMeteoWS_Service service;

    
    @Schedule(hour = "*", minute = "*/2")  
    public void myTimer() {
        System.out.println("Timer event: " + new Date());
        List<String> adrese = meteoPrognosticar.dajAdrese();
        for (String adresa : adrese) {
            List<MeteoPodaci> meteoPodaci = dajSveMeteoPodatkeZaUredjaj(0, 0, 0);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private java.util.List<org.foi.nwtis.dhorvat3.ws.serveri.MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(int id, long from, long to) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveMeteoPodatkeZaUredjaj(id, from, to);
    }

    public void sendJMSMessageToNWTiS_vjezba_12(String messageData) {
        context.createProducer().send(nWTiS_vjezba_12, messageData);
    }

    private java.util.List<org.foi.nwtis.dhorvat3.ws.serveri.Uredjaj> dajSveUredjaje() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.foi.nwtis.dhorvat3.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveUredjaje();
    }
    
    
}

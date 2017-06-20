/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.dretve;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dhorvat3.socket.helper.MeteoHelper;
import org.foi.nwtis.dhorvat3.web.podaci.Lokacija;
import org.foi.nwtis.dhorvat3.web.podaci.MeteoPodaci;

/**
 *
 * @author Davorin Horvat
 */
public class MeteoDretva extends Thread {

    private boolean lock = false;
    private boolean stop = false;
    private boolean running = false;
    private int interval = 0;
    private final Object pauseLock = new Object();
    private String apiKey;
    private Statement statement;

    public MeteoDretva(int interval, String apiKey, Statement statement) throws IllegalArgumentException {
        if ("".equals(apiKey)) {
            throw new IllegalArgumentException();
        }

        this.statement = statement;
        this.interval = interval;
        this.apiKey = apiKey;
    }

    @Override
    public void interrupt() {
        try {
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(MeteoDretva.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (!stop) {
            synchronized (pauseLock) {
                if (isStop()) {
                    break;
                }
                if (isLock()) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MeteoDretva.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                    if (isStop()) {
                        break;
                    }
                }
            }
            System.out.println("--- KORAK METEO DRETVE");
            running = true;
            ArrayList<Lokacija> lokacije = null;
            //ArrayList<MeteoPodaci> meteoPodaci = new ArrayList<>();
            try {
                lokacije = MeteoHelper.dajSveLokacije(statement);
            } catch (SQLException ex) {
                Logger.getLogger(MeteoDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (lokacije != null) {
                for (Lokacija lokacija : lokacije) {
                    //System.out.println("- Lokacija: " + lokacija.getLatitude() + " " + lokacija.getLongitude());
                    //System.out.println("APIKEY: " + apiKey);
                    MeteoPodaci meteoPodaci = MeteoHelper.dajMeteoPodatke(lokacija.getLatitude(), lokacija.getLongitude(), apiKey);
                    //System.out.println("MeteoPodaci: " + meteoPodaci.getTemperatureUnit());
                    String query = "INSERT INTO meteo (id, temp, tempmin, tempmax, vlaga, tlak, preuzeto) VALUES (";
                    query += lokacija.getId() + ", ";
                    query += meteoPodaci.getTemperatureValue() + ", ";
                    query += meteoPodaci.getTemperatureMin() + ", ";
                    query += meteoPodaci.getTemperatureMax() + ", ";
                    query += meteoPodaci.getHumidityValue() + ", ";
                    query += meteoPodaci.getPressureValue() + ", ";
                    query += " now()";
                    query += ")";
                    System.out.println("- METEO SQL: " + query);
                    try {
                        statement.executeUpdate(query);
                    } catch (SQLException ex) {
                        Logger.getLogger(MeteoDretva.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            //ažuriraj meteo podatke
            running = false;
            try {
                sleep(interval * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MeteoDretva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean stop) {
        this.lock = stop;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public int stopThread() {
        if (isStop()) {
            return 0;
        }
        stop = true;
        resumeThread();
        return 1;
    }

    public int pauseThread() {
        if (isLock()) {
            return 0;
        }
        lock = true;
        return 1;
    }

    public int resumeThread() {
        if (!isLock()) {
            return 0;
        }
        synchronized (pauseLock) {
            lock = false;
            pauseLock.notifyAll();
        }
        return 1;
    }
}

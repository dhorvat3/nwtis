/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.dretve;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;

/**
 *
 * @author Davorin Horvat
 */
public class ObradaZahtjeva extends Thread {

    private Socket client = null;
    private InputStream inputStream;
    private OutputStream outputStream;
    private final ServletContext context;
    private MeteoDretva meteoDretva = null;
    private Statement statement = null;

    public ObradaZahtjeva(Socket client, ServletContext context, MeteoDretva meteoDretva) {
        this.client = client;
        this.context = context;
        this.meteoDretva = meteoDretva;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            statement = Helper.getStatement(context);
            String regexMain = "USER ([\\w]+); PASSWD ([\\w]+);\\s?(IoT|IoT_Master|PAUSE|START|STOP|STATUS)?;?\\s?([\\w]+)?;?";
            String odgovor = "";

            StringBuffer naredba = Helper.getInputStreamString(inputStream);

            System.out.println("Naredba: " + naredba);

            Pattern pattern = Pattern.compile(regexMain);
            Matcher matcher = pattern.matcher(naredba);
            statement = Helper.getStatement(context);
            if (matcher.matches()) {
                int id = Helper.checkLogin(matcher.group(1), matcher.group(2), statement);
                if (id > 0) {
                    if (matcher.group(3) == null) {
                        System.out.println("--- OBRADA --- samo korisnik: " + matcher.group(1) + " " + matcher.group(2));
                        Helper.log(id, 1, "Zahtjev - Provjera podataka", statement);
                        try {
                            odgovor = AdminObrada.autentikacija(matcher.group(1), matcher.group(2), context);
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (matcher.groupCount() < 4) {
                        System.out.println("--- OBRADA --- Nije unesen zadnji argument");

                    } else if (matcher.group(3).equals("IoT_Master")) {
                        System.out.println("--- OBRADA --- IoT_Master: " + matcher.group(1) + " " + matcher.group(3) + " " + matcher.group(4));

                    } else if (matcher.group(3).equals("IoT")) {
                        System.out.println("--- OBRADA --- IoT: " + matcher.group(1) + " " + matcher.group(3) + " " + matcher.group(4));

                    } else if (matcher.group(3).equals("PAUSE")) {
                        System.out.println("--- OBRADA --- ADmin Pause");
                        odgovor = AdminObrada.pauzirajServer(meteoDretva);
                        Helper.log(id, 1, "Zahtjev - Pauziraj server", statement);
                    } else if (matcher.group(3).equals("START")) {
                        System.out.println("--- OBRADA --- Admin Start");
                        odgovor = AdminObrada.pokreniServer(meteoDretva);
                        Helper.log(id, 1, "Zahtjev - Pokreni server", statement);
                    } else if (matcher.group(3).equals("STOP")) {
                        System.out.println("--- OBRADA --- Admin Stop");
                        //TODO Blokirati unos komadi kad je socket server ugašen
                        odgovor = AdminObrada.zaustaviServer(meteoDretva);
                        Helper.log(id, 1, "Zahtjev - Zaustavi server", statement);

                    } else if (matcher.group(3).equals("STATUS")) {
                        System.out.println("--- OBRADA --- Admin Status");
                        odgovor = AdminObrada.uRadu(meteoDretva);
                        Helper.log(id, 1, "Zahtjev - Status servera", statement);

                    } else {
                        System.out.println("--- OBRADA --- Nepoznata naredba!");

                    }
                }
            } else {
                System.out.println("--- OBRADA --- Nepoznato");
            }

            outputStream.write(odgovor.getBytes());
            outputStream.flush();

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}

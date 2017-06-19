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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;
import org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava;
import org.foi.nwtis.dhorvat3.web.slusaci.SlusacAplikacije;

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
        try {
            statement.close();
            client.close();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        }

        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            //statement = Helper.getStatement(SlusacAplikacije.getContext());
            String regexMain = "USER ([\\w]+); PASSWD ([\\w]+);\\s?(IoT_Master|PAUSE|START|STOP|STATUS)?;?\\s?([\\w]+)?;?";
            String odgovor = "";

            StringBuffer naredba = Helper.getInputStreamString(inputStream);

            System.out.println("Naredba: " + naredba);

            Pattern pattern = Pattern.compile(regexMain);
            Matcher matcher = pattern.matcher(naredba);
            statement = Helper.getStatement(SlusacAplikacije.getContext());
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
                        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
                        String masterUsername = konfig.dajPostavku("IoT_Master_username");
                        String masterPass = konfig.dajPostavku("IoT_Master_pass");

                        switch (matcher.group(4)) {
                            case "START": {
                                System.out.println("--- OBRADA --- IoT_Master START;");
                                try {
                                    odgovor = IoTMasterObrada.aktivirajGrupu(masterUsername, masterPass);
                                } catch (NeuspjesnaPrijava ex) {
                                    odgovor = "Prijava neuspješna!";
                                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Helper.log(id, 1, "Zahtjev - registriraj grupu", statement);
                                break;
                            }

                            case "STOP": {
                                System.out.println("--- OBRADA --- IoT_Master STOP;");
                                try {
                                    odgovor = IoTMasterObrada.blokirajGrupu(masterUsername, masterPass);
                                } catch (NeuspjesnaPrijava ex) {
                                    odgovor = "Prijava neuspješna!";
                                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Helper.log(id, 1, "Zahtjev - odjavi grupu", statement);
                                break;
                            }

                            case "WORK": {
                                System.out.println("--- OBRADA --- IoT_Master WORK;");
                                try {
                                    odgovor = IoTMasterObrada.aktivirajGrupu(masterUsername, masterPass);
                                } catch (NeuspjesnaPrijava ex) {
                                    odgovor = "Prijava neuspješna!";
                                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Helper.log(id, 1, "Zahtjev - aktiviraj grupu", statement);
                                break;
                            }

                            case "LOAD": {
                                System.out.println("--- OBRADA --- IoT_Master LOAD;");
                                try {
                                    odgovor = IoTMasterObrada.ucitajUredaje(masterUsername, masterPass);
                                } catch (NeuspjesnaPrijava ex) {
                                    odgovor = "Prijava neuspješna!";
                                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Helper.log(id, 1, "Zahtjev - učitaj uređaje", statement);
                                break;
                            }

                            case "CLEAR": {
                                System.out.println("--- OBRADA --- IoT_Master CLEAR;");
                                try {
                                    odgovor = IoTMasterObrada.brisiUredaje(masterUsername, masterPass);
                                } catch (NeuspjesnaPrijava ex) {
                                    odgovor = "Prijava neuspješna!";
                                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Helper.log(id, 1, "Zahtjev - briši uređaje", statement);
                                break;
                            }

                            case "STATUS": {
                                System.out.println("--- OBRADA --- IoT_Master STATUS;");
                                try {
                                    odgovor = IoTMasterObrada.statusGrupe(masterUsername, masterPass);
                                } catch (NeuspjesnaPrijava ex) {
                                    odgovor = "Prijava neuspješna!";
                                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Helper.log(id, 1, "Zahtjev - status grupe", statement);
                                break;
                            }

                            case "LIST": {
                                System.out.println("--- OBRADA --- IoT_Master LIST;");
                                try {
                                    odgovor = IoTMasterObrada.dajUredaje(masterUsername, masterPass);
                                } catch (NeuspjesnaPrijava ex) {
                                    odgovor = "Prijava neuspješna!";
                                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Helper.log(id, 1, "Zahtjev - dohvati uređaje", statement);
                                break;
                            }

                            default:
                                odgovor = "Nepoznata naredba";
                                break;
                        }

                        //} else if (matcher.group(3).equals("IoT")) {
                        //    System.out.println("--- OBRADA --- IoT: " + matcher.group(1) + " " + matcher.group(3) + ", " + matcher.group(4));
                    } else if (matcher.group(3).equals("PAUSE")) {
                        //System.out.println("--- OBRADA --- ADmin Pause");
                        odgovor = AdminObrada.pauzirajServer(meteoDretva);
                        Helper.log(id, 1, "Zahtjev - Pauziraj server", statement);
                    } else if (matcher.group(3).equals("START")) {
                        //System.out.println("--- OBRADA --- Admin Start");
                        odgovor = AdminObrada.pokreniServer(meteoDretva);
                        Helper.log(id, 1, "Zahtjev - Pokreni server", statement);
                    } else if (matcher.group(3).equals("STOP")) {
                        //System.out.println("--- OBRADA --- Admin Stop");
                        //TODO Blokirati unos komadi kad je socket server ugašen
                        odgovor = AdminObrada.zaustaviServer(meteoDretva);
                        Helper.log(id, 1, "Zahtjev - Zaustavi server", statement);

                    } else if (matcher.group(3).equals("STATUS")) {
                        //System.out.println("--- OBRADA --- Admin Status");
                        odgovor = AdminObrada.uRadu(meteoDretva);
                        Helper.log(id, 1, "Zahtjev - Status servera", statement);

                    } else {
                        System.out.println("--- OBRADA --- Nepoznata naredba!");
                        odgovor = "Nepoznata naredba";
                    }
                } else {
                    System.out.println("Neispravni podaci za prijavu");
                    odgovor = "Neispravni podaci za prijavu!";
                }
            } else {

                String regexIoT = "USER ([\\w]+);\\sPASSWD\\s([\\w]+);\\sIoT\\s([1-9]{1,6})\\s(ADD|WORK|WAIT|REMOVE|STATUS)\\s?(\\\"?[\\w\\s\\,]+\\\")?\\s?(\\\"?[A-Z][\\w\\s\\,]+\\\")?\\;?";
                Pattern patternIoT = Pattern.compile(regexIoT);
                Matcher matcherIoT = patternIoT.matcher(naredba);
                if (matcherIoT.matches()) {
                    int id = Helper.checkLogin(matcherIoT.group(1), matcherIoT.group(2), statement);
                    if (id > 0) {
                        //System.out.println("--- OBRADA --- IoT: " + matcherIoT.group(3) + ", " + matcherIoT.group(4) + ", " + matcherIoT.group(5) + ", " + matcherIoT.group(6));
                        if (null == matcherIoT.group(3)) {
                            System.out.println("--- OBRADA --- IoT nepoznata naredba");
                            odgovor = "Nepoznata naredba!";
                        } else {
                            Konfiguracija konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
                            String masterUsername = konfig.dajPostavku("IoT_Master_username");
                            String masterPass = konfig.dajPostavku("IoT_Master_pass");
                            switch (matcherIoT.group(4)) {
                                case "ADD":
                                    if(matcherIoT.group(6) == null)
                                        break;
                                    System.out.println("--- OBRADA --- IoT ADD: " + matcherIoT.group(3) + ", " + matcherIoT.group(5).replace("\"", "") + ", " + matcherIoT.group(6).replace("\"", ""));

                                    try {
                                        Helper.log(id, 1, "Zahtjev - Dodaj uredaj", statement);
                                        odgovor = IoTObrada.dodajUredaj(masterUsername, masterPass, Integer.parseInt(matcherIoT.group(3)), matcherIoT.group(5).replace("\"", ""), matcherIoT.group(6).replace("\"", ""));
                                    } catch (NeuspjesnaPrijava ex) {
                                        odgovor = "Prijava neuspješna";
                                        Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                                case "WORK":
                                    System.out.println("--- OBRADA --- IoT WORK" + matcherIoT.group(3));

                                    try {
                                        Helper.log(id, 1, "Zahtjev - Aktiviraj uredaj", statement);
                                        odgovor = IoTObrada.aktivirajUredaj(masterUsername, masterPass, Integer.parseInt(matcherIoT.group(3)));
                                    } catch (NeuspjesnaPrijava ex) {
                                        odgovor = "Prijava neuspješna";
                                        Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                                case "WAIT":
                                    Helper.log(id, 1, "Zahtjev - Blokiraj uredaj", statement);
                                    System.out.println("--- OBRADA --- IoT WAIT" + matcherIoT.group(4));

                                    try {
                                        odgovor = IoTObrada.blokirajUredaj(masterUsername, masterPass, Integer.parseInt(matcherIoT.group(3)));
                                    } catch (NeuspjesnaPrijava ex) {
                                        odgovor = "Prijava neuspješna";
                                        Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                                case "REMOVE":
                                    System.out.println("--- OBRADA --- IoT REMOVE" + matcherIoT.group(4));

                                    try {
                                        Helper.log(id, 1, "Zahtjev - Brisi uredaj", statement);
                                        odgovor = IoTObrada.brisiUredaj(masterUsername, masterPass, Integer.parseInt(matcherIoT.group(3)));
                                    } catch (NeuspjesnaPrijava ex) {
                                        odgovor = "Prijava neuspješna";
                                        Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                                case "STATUS":
                                    System.out.println("--- OBRADA --- IoT STATUS" + matcherIoT.group(3));

                                    try {
                                        Helper.log(id, 1, "Zahtjev - Status uredaja", statement);
                                        odgovor = IoTObrada.statusUredaja(masterUsername, masterPass, Integer.parseInt(matcherIoT.group(3)));
                                    } catch (NeuspjesnaPrijava ex) {
                                        odgovor = "Prijava neuspješna";
                                        Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    break;
                            }
                        }
                    } else {
                        odgovor = "Neipravni podaci za prijavu!";
                    }
                } else {

                    System.out.println("--- OBRADA --- Nepoznato");
                    odgovor = "Nepoznata naredba!";
                }
            }
            
            sendMail(naredba.toString(), new Date());

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
    
    
    public void sendMail(String naredba, Date pocetakObrade){
        Konfiguracija konfig = (Konfiguracija) context.getAttribute("Server_Konfig");
        
        String naslov = konfig.dajPostavku("mail.subject");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss.zzz");
        String vrijeme = dateFormat.format(pocetakObrade);
        
        String body = "Naredba: " + naredba;
        body += "\r\nPočetak obrade: " + vrijeme;
        
        String server = konfig.dajPostavku("mail.server");
        String port = konfig.dajPostavku("mail.port");
        String from = konfig.dajPostavku("mail.fromUsername");
        String to = konfig.dajPostavku("mail.toUsername");
        
        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", server);
            properties.put("mail.smtp.port", port);
            Session session = Session.getInstance(properties, null);
            
            InternetAddress fromAddress = new InternetAddress(from);
            InternetAddress toAddress = new InternetAddress(to);
            
            Message message = new MimeMessage(session);
            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(naslov);
            message.setText(body);
            
            Transport.send(message);
        } catch (AddressException ex) {
            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

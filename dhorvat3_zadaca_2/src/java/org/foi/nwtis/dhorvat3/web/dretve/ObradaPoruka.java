/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.dretve;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.Konfiguracija;
import org.foi.nwtis.dhorvat3.konfiguracije.bp.BP_Konfiguracija;

/**
 * Slušač aplikacije starta pozadinsku dretvu koja provjerava na poslužitelju
 * (adresa i IMAP port određena konfiguracijom) u pravilnom intervalu (određen
 * konfiguracijom u sek) ima li poruka u poštanskom sandučiću korisnika (adresa
 * i lozinka određeni konfiguracijom, npr. servis@nwtis.nastava.foi.hr, 123456).
 * Koristi se IMAP protokol. Poruke koje u predmetu (subject) imaju točno
 * traženi sadržaj (određen konfiguracijom, npr. NWTiS poruka) obrađuju se tako
 * da se ispituje sadržaj poruke. Dretva na kraju svakog ciklusa šalje email
 * poruku u text/plain formatu na adresu (određena konfiguracijom, npr.
 * admin@nwtis.nastava.foi.hr), uz predmet koji započinje statičkim dijelom
 * (određen konfiguracijom, npr. Statistika poruka) iza kojeg dolazi redni broj
 * poruke u formatu #.##0,
 *
 * @author Davorin Horvat
 */
public class ObradaPoruka extends Thread {

    private ServletContext servletContext = null;
    private boolean stop = false;

    private Connection connection;
    private Statement statement;

    @Override
    public void interrupt() {
        stop = true;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Za poruku koja ima ispravnu sintaksu slijedi provođenja postupka: ako se
     * radi o komandi ADD tada treba dodati zapis u tablicu UREDAJI u bazi
     * podataka, s time da ako već postoji, onda je pogreška. Ako se radi o
     * komandi TEMP tada se provjerava zapis u tablici UREDAJI za zadani broj
     * IoT i ako postoji, dodaje se zapis u tablicu TEMPERATURE. Ako ne postoji,
     * onda je pogreška. Ako se radi o komandi EVENT tada se provjerava zapis u
     * tablici UREDAJI za zadani broj IoT i ako postoji, dodaje se zapis u
     * tablicu DOGADAJI. Ako ne postoji, onda je pogreška.
     *
     * Obrađene poruke (određen konfiguracijom, npr. NWTiS poruka) prebacuju se
     * u posebnu mapu (određena konfiguracijom, npr. NWTiS_Poruke). Ostale
     * poruke prebacuju se u svoju mapu (određena konfiguracijom, npr.
     * NWTiS_OstalePoruke). Ako neka mapa ne postoji, dretva ju treba sama
     * kreirati.
     */
    @Override
    public void run() {
        Date pocetakObrade = new Date();
        BP_Konfiguracija bp_konfig = (BP_Konfiguracija) servletContext.getAttribute("BP_Konfig");
        String url = bp_konfig.getServerDatabase() + bp_konfig.getUserDatabase();
        String user = bp_konfig.getUserUsername();
        String password = bp_konfig.getUserPassword();
        try {
            Class.forName(bp_konfig.getDriverDatabase());
            System.out.println("url " + url);
            System.out.println("user " + user);
            System.out.println("pass: " + password);
            this.connection = DriverManager.getConnection(url, user, password);
            this.statement = this.connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }

        Konfiguracija konfig = (Konfiguracija) servletContext.getAttribute("Mail_Konfig");
        String server = konfig.dajPostavku("mail.server");
        String port = konfig.dajPostavku("mail.port");
        String korisnik = konfig.dajPostavku("mail.usernameThread");
        String lozinka = konfig.dajPostavku("mail.passwordThread");
        int trajanjeCiklusa = Integer.parseInt(konfig.dajPostavku("mail.timeSecThread"));
        String naslov = konfig.dajPostavku("mail.subject");
        String otherFolder = konfig.dajPostavku("mail.folderOther");
        String nwtisFolder = konfig.dajPostavku("mail.folderNWTiS");
        float trajanjeObrade = 0;
        int redniBrojCiklusa = 0;

        String regexAdd = "ADD IoT (\\d{1,6}) \"([a-zA-Z žćčđš]+)\"GPS: (\\d+\\.\\d+),(\\d+\\.\\d+);";
        String regexTemp = "TEMP IoT (\\d{1,6}) T: (\\d{4}.[0-1][0-9].[0-3][0-9]) ([0-2][0-9]:[0-6][0-9]:[0-6][0-9]) C: ((?:\\-|\\+)?\\d+\\.\\d+);";
        String regexEvent = "EVENT IoT (\\d{1,6}) T: (\\d{4}.[0-1][0-9].[0-3][0-9]) ([0-2][0-9]:[0-6][0-9]:[0-6][0-9]) F: (\\d+);";

        Pattern pTemp = Pattern.compile(regexTemp);
        Pattern pAdd = Pattern.compile(regexAdd);
        Pattern pEvent = Pattern.compile(regexEvent);

        while (!stop) {
            int brojAdd = 0;
            int brojTemp = 0;
            int brojEvent = 0;
            int brojGresaka = 0;
            int brojPoruka = 0;
            redniBrojCiklusa++;
            System.out.println("Ciklus dretve ObradaPoruka: " + redniBrojCiklusa);
            try {
                Properties properties = System.getProperties();
                properties.put("mail.smtp.host", server);
                properties.put("mail.smtp.port", port);
                Session session = Session.getInstance(properties, null);

                Store store = session.getStore("imap");
                store.connect(server, korisnik, lozinka);

                //Otvori INBOX
                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_WRITE);
                Message[] messages = folder.getMessages();
                brojPoruka = messages.length;

                //Otvori datoteku za ostale poruke
                Folder otherF = store.getFolder(otherFolder);
                if (!otherF.exists()) {
                    otherF.create(Folder.HOLDS_MESSAGES);
                }
                otherF.open(Folder.READ_WRITE);
                List<Message> otherMessages = new ArrayList<>();

                //Otvori datoteku za obrađene poruke
                Folder processedFolder = store.getFolder(nwtisFolder);
                if (!processedFolder.exists()) {
                    processedFolder.create(Folder.HOLDS_MESSAGES);
                }
                processedFolder.open(Folder.READ_WRITE);
                List<Message> processedMessages = new ArrayList<>();

                //Otvori datoteku za smeće
                Folder trashFolder = store.getFolder("Trash");
                if (!trashFolder.exists()) {
                    trashFolder.create(Folder.HOLDS_MESSAGES);
                }
                trashFolder.open(Folder.READ_WRITE);
                List<Message> trashMessages = new ArrayList<>();
                for (int i = 0; i < messages.length; i++) {
                    if (messages[i].getSubject().equals(naslov)) {
                        String content = messages[i].getContent().toString();

                        Matcher mAdd = pAdd.matcher(content);
                        Matcher mTemp = pTemp.matcher(content);
                        Matcher mEvent = pEvent.matcher(content);
                        if (mAdd.find()) {
                            if (postojiUredjaj(mAdd.group(1)) != 0) {
                                System.out.println("--- UREĐAJ --- Postoji uređaj! ID: " + mAdd.group(1));
                                brojGresaka += 1;
                            } else {

                                String query = "INSERT INTO uredaji (id, naziv, latitude, longitude, vrijeme_kreiranja) values (" + mAdd.group(1) + ", '" + mAdd.group(2) + "', " + mAdd.group(3) + ", " + mAdd.group(4) + ", NOW());";
                                System.out.println("SQL: " + query);
                                this.statement.executeUpdate(query);
                                System.out.println("--- UREĐAJ --- Uređaj dodan.");
                                brojAdd += 1;
                            }
                            processedMessages.add(messages[i]);
                        } else if (mTemp.find()) {
                            if (postojiUredjaj(mTemp.group(1)) != 0) {
                                String query = "INSERT INTO temperature (id, temp, vrijeme_mjerenja, vrijeme_kreiranja) VALUES (" + mTemp.group(1) + ", " + mTemp.group(4) + ", '" + mTemp.group(2) + " " + mTemp.group(3) + "', NOW())";
                                System.out.println("SQL: " + query);
                                this.statement.executeUpdate(query);
                                System.out.println("--- TEMPERATURA --- Dodan zapis temperature.");
                                brojTemp += 1;
                            } else {
                                System.out.println("--- TEMPERATURA --- Ne postji uređaj! ID: " + mAdd.group(1));
                                brojGresaka += 1;
                            }
                            processedMessages.add(messages[i]);
                        } else if (mEvent.find()) {
                            if (postojiUredjaj(mEvent.group(1)) != 0) {
                                if (postojiVrsta(mEvent.group(4))) {
                                    String query = "INSERT INTO dogadaji (id, vrsta, vrijeme_izvrsavanja, vrijeme_kreiranja) VALUES (" + mEvent.group(1) + ", " + mEvent.group(4) + ", '" + mEvent.group(2) + " " + mEvent.group(3) + "', NOW())";
                                    System.out.println("SQL: " + query);
                                    this.statement.executeUpdate(query);
                                    System.out.println("--- EVENT --- Dodan zapis događaja.");
                                    brojEvent += 1;
                                } else {
                                    System.out.println("--- EVENT --- Vrsta ne postoji. ID: " + mEvent.group(4));
                                    brojGresaka += 1;
                                }
                            } else {
                                System.out.println("--- EVENT --- Ne postoji uređaj! ID: " + mEvent.group(1));
                                brojGresaka += 1;
                            }
                            processedMessages.add(messages[i]);
                        } else {
                            trashMessages.add(messages[i]);
                        }

                    } else {
                        otherMessages.add(messages[i]);
                    }
                    messages[i].setFlag(Flags.Flag.DELETED, true);
                }
                if (!processedMessages.isEmpty()) {
                    Message[] m = processedMessages.toArray(new Message[processedMessages.size()]);
                    folder.copyMessages(m, processedFolder);
                }
                if (!otherMessages.isEmpty()) {
                    Message[] m = otherMessages.toArray(new Message[otherMessages.size()]);
                    folder.copyMessages(m, otherF);
                }
                if (!trashMessages.isEmpty()) {
                    Message[] m = trashMessages.toArray(new Message[trashMessages.size()]);
                    folder.copyMessages(m, trashFolder);
                }
                folder.close(true);
                processedFolder.close(false);
                otherF.close(false);
                trashFolder.close(false);
                store.close();

                Date krajObrade = new Date();
                trajanjeObrade = krajObrade.getTime() - (pocetakObrade.getTime() - 1);

                posaljiStatistiku(redniBrojCiklusa, pocetakObrade, krajObrade, (int) trajanjeObrade, brojPoruka, brojAdd, brojTemp, brojEvent, brojGresaka);

                if (trajanjeObrade < 0) {
                    trajanjeObrade = 0;
                }

                sleep(trajanjeCiklusa * 1000);
            } catch (InterruptedException | MessagingException | IOException | SQLException ex) {
                Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Provjerava postoji li uređaj za zadani ID
     *
     * @param id id koji treba provjeriti
     * @return ID pronađenog uređaja
     * @throws SQLException
     */
    private int postojiUredjaj(String id) throws SQLException {
        ResultSet rs;

        String query = "SELECT * FROM uredaji WHERE id = " + id;
        System.out.println("SQL: " + query);
        rs = this.statement.executeQuery(query);
        int index = 0;
        while (rs.next()) {
            index = rs.getInt("id");
        }
        System.out.println("ID: " + index);
        return index;
    }

    /**
     * Provjerava postoji li vrsta događaja u bazi podataka
     *
     * @param id
     * @return
     * @throws SQLException
     */
    private boolean postojiVrsta(String id) throws SQLException {
        ResultSet rs;
        String query = "SELECT * FROM dogadaji_vrste WHERE vrsta = " + id;
        rs = this.statement.executeQuery(query);
        return rs.next();
    }

    /**
     * Šalje mail s informacijama o obradi.
     *
     * @param redniBroj
     * @param pocetak
     * @param kraj
     * @param trajanje
     * @param brojPoruka
     * @param brojAdd
     * @param brojTemp
     * @param brojEvent
     * @param brojGresaka
     */
    private void posaljiStatistiku(int redniBroj, Date pocetak, Date kraj, int trajanje, int brojPoruka, int brojAdd, int brojTemp, int brojEvent, int brojGresaka) {
        //Build forma #.##0
        String rb = "";
        if (redniBroj / 1000 > 0) {
            rb = String.valueOf(redniBroj / 1000) + "." + String.valueOf(redniBroj % 1000);
        } else if (redniBroj / 100 > 0) {
            rb = " " + String.valueOf(redniBroj);
        } else if (redniBroj / 10 > 0) {
            rb = "  " + String.valueOf(redniBroj);
        } else {
            rb = "   " + String.valueOf(redniBroj);
        }
        //Make title 
        Konfiguracija konfig = (Konfiguracija) servletContext.getAttribute("Mail_Konfig");
        String naslov = konfig.dajPostavku("mail.subjectStatistics");
        naslov += rb;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss.zzz");
        String pocetakObrade = dateFormat.format(pocetak);
        String krajObrade = dateFormat.format(kraj);

        String text = "Obrada započela u: " + pocetakObrade;
        text += "\r\nObrada završila u: " + krajObrade;
        text += "\r\n\r\nTrajanje obrade u ms: " + trajanje;
        text += "\r\nBroj poruka: " + brojPoruka;
        text += "\r\nBroj dodanih IoT: " + brojAdd;
        text += "\r\nBroj mjerenih: " + brojTemp;
        text += "\r\nBroj izvršenih EVENT: " + brojEvent;
        text += "\r\nBroj pogrešaka: " + brojGresaka;

        String server = konfig.dajPostavku("mail.server");
        String port = konfig.dajPostavku("mail.port");
        String from = konfig.dajPostavku("mail.usernameThread");
        String to = konfig.dajPostavku("mail.usernameStatistics");
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
            message.setText(text);

            Transport.send(message);
        } catch (AddressException ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

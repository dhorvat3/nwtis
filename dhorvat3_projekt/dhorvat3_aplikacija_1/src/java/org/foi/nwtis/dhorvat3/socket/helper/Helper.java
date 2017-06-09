/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.helper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.bp.BP_Konfiguracija;

/**
 *
 * @author Davorin Horvat
 */
public final class Helper {

    public static StringBuffer getInputStreamString(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int znak = inputStream.read();
            if (znak == -1) {
                break;
            }
            stringBuffer.append((char) znak);
        }

        return stringBuffer;
    }

    public static Statement getStatement(ServletContext context) throws ClassNotFoundException, SQLException {
        BP_Konfiguracija bp_konfig = (BP_Konfiguracija) context.getAttribute("BP_Konfig");
        String url = bp_konfig.getServerDatabase() + bp_konfig.getUserDatabase();
        String user = bp_konfig.getUserUsername();
        String password = bp_konfig.getUserPassword();
        Class.forName(bp_konfig.getDriverDatabase());
        Connection connection = DriverManager.getConnection(url, user, password);

        return connection.createStatement();
    }
    
    public static int checkLogin(String userName, String pass, Statement statement) throws SQLException{
        ResultSet rs;
        String query = "SELECT * FROM korisnici WHERE korIme='" + userName + "' AND pass='" + pass + "'";
        System.out.println("- ADMIN - autentikacija SQL: " + query);
        rs = statement.executeQuery(query);
        int index = 0;
        while(rs.next()){
            index = rs.getInt("id");
        }
        System.out.println("- ADMIN - autentikacija ID: " + index);
        return index;
    }
    
    public static void log(int userId, int type, String description, Statement statement) throws SQLException{
        String query = "INSERT INTO dnevnik (tip, opis, id_korisnik) VALUES (";
        query += type + ", '";
        query += description + "', ";
        query += userId;
        query += ")";
        statement.executeUpdate(query);
    }
}

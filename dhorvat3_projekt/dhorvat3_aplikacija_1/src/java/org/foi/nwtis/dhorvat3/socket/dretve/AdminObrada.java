/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.dretve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;

/**
 *
 * @author Davorin Horvat
 */
public final class AdminObrada {
    
    public static int autentikacija(String userName, String pass, ServletContext context) throws ClassNotFoundException, SQLException{
        ResultSet rs;
        Statement statement = Helper.getStatement(context);
        
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
    
    
}

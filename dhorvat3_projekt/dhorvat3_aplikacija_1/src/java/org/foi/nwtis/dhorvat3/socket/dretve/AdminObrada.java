/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.socket.dretve;

import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;

/**
 *
 * @author Davorin Horvat
 */
public final class AdminObrada {

    protected static String autentikacija(String userName, String pass, ServletContext context) throws ClassNotFoundException, SQLException {
        Statement statement = Helper.getStatement(context);
        if (Helper.checkLogin(userName, pass, statement) < 1) {
            return "ERR 10;";
        }

        return "";
    }

    protected static String pokreniServer(MeteoDretva meteoDretva) {
        return (meteoDretva.resumeThread() == 1) ? "OK 10;" : "ERR 11;";
    }

    protected static String pauzirajServer(MeteoDretva meteoDretva) {
        return (meteoDretva.pauseThread() == 1) ? "OK 10;" : "ERR 10;";
    }

    protected static String zaustaviServer(MeteoDretva meteoDretva) {
        return (meteoDretva.stopThread() == 1) ? "OK 10;" : "ERR 12;";
    }

    protected static String uRadu(MeteoDretva meteoDretva) {
        if (meteoDretva.isStop()) {
            return "OK 15;";
        }
        if (meteoDretva.isRunning()) {
            return "OK 14;";
        }
        return "OK 13;";
    }
}

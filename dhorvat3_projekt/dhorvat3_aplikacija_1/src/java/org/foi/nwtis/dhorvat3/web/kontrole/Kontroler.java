/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.kontrole;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.dhorvat3.socket.helper.Helper;
import org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
public class Kontroler extends HttpServlet {

    private Statement statement = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext context = request.getServletContext();

        try {
            statement = Helper.getStatement(context);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        String putanja = request.getServletPath();
        String odrediste = null;

        switch (putanja) {
            case "/Kontroler":
                odrediste = "/index.xhtml";
                break;
            case "/PregledKorisnika":
                if (request.getSession().getAttribute("korisnik") == null) {
                    odrediste = "/prijava.xhtml";
                } else {
                    odrediste = "/pregledKorisnika.xhtml";
                }
                break;
            case "/PregledDnevnika":
                if (request.getSession().getAttribute("korisnik") == null) {
                    odrediste = "/prijava.xhtml";
                } else {
                    odrediste = "/pregledDnevnika.xhtml";
                }
                break;
            case "/PregledZahtjeva":
                if (request.getSession().getAttribute("korisnik") == null) {
                    odrediste = "/prijava.xhtml";
                } else {
                    odrediste = "/pregledZahtjeva.xhtml";
                }
                break;
            case "/ProvjeraKorisnika":
                try {
                    provjeriKorisnika(request);
                    odrediste = "/index.xhtml";
                } catch (SQLException ex) {
                    Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
                    odrediste = "/greska.xhtml";
                } catch (NeuspjesnaPrijava ex){
                    odrediste = "/greska.xhtml";
                }
                
                break;
            case "/Prijava":
                odrediste = "/prijava.xhtml";
                break;
            case "/Odjava":
                HttpSession session = request.getSession();
                session.invalidate();

                odrediste = "/index.xhtml";
                break;
        }

        if (odrediste != null) {
            odrediste = "/faces" + odrediste;
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(odrediste);
            dispatcher.forward(request, response);
        } else {
            throw new ServerException("Nepoznata putanja!");
        }
    }

    private void provjeriKorisnika(HttpServletRequest request) throws NeuspjesnaPrijava, SQLException {
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        if (username == null || pass == null || pass.length() == 0 || username.length() == 0) {
            throw new NeuspjesnaPrijava("Korisničko ime i lozinka moraju biti upisani.");
        } else {
            int id = Helper.checkLogin(username, pass, statement);
            if (id != 0) {
                HttpSession session = request.getSession();
                session.setAttribute("korisnik", Helper.getUser(id, statement, request));
            } else {
                throw new NeuspjesnaPrijava("Neispravno korisničko ime ili lozinka.");
            }

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dhorvat3.web.kontrole;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.dhorvat3.web.NeuspjesnaPrijava;
import org.foi.nwtis.dhorvat3.web.podaci.Korisnik;

/**
 *
 * @author Davorin Horvat
 */
public class Kontroler extends HttpServlet {

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
        
        
        String putanja = request.getServletPath();
        String odrediste = null;
        
        switch(putanja){
            case "/Kontroler":
                odrediste = "/index.xhtml";
                break;
            case "/PregledKorisnika":
                if(request.getSession().getAttribute("korisnik") == null){
                    odrediste = "/prijava.xhtml";
                } else {
                    odrediste = "/pregledKorisnika.xhtml";
                }
                break;
            case "/ProvjeraKorisnika":
                provjeriKorisnika(request);
                odrediste = "/index.xhtml";
                break;
            case "/Prijava":
                odrediste = "/prijava.xhtml";
                break;
        }
        
        if(odrediste != null){
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(odrediste);
            dispatcher.forward(request, response);
        } else {
            throw new ServerException("Nepoznata putanja!");
        }
    }
    
    private void provjeriKorisnika(HttpServletRequest request) throws NeuspjesnaPrijava{
        String ko = request.getParameter("ki");
        String lo = request.getParameter("lo");
        if(ko == null || lo == null || lo.length() == 0 || ko.length() == 0){
            throw new NeuspjesnaPrijava("Neuspješna prijava");
        } else  {
            HttpSession session = request.getSession();
            Korisnik k = new Korisnik(ko, "Davorin", "Horvat", request.getRemoteAddr(), request.getSession().getId(), 0);
            session.setAttribute("korisnik", k);
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

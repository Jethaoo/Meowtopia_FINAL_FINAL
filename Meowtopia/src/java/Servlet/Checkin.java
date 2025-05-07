/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import Model.*;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;

/**
 *
 * @author User
 */
public class Checkin extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            int checkinCoin = 0;
            if (email == null) {
                response.sendRedirect("login.jsp"); // Not logged in
                return;
            }
            AccountService as = new AccountService(em);
            Account account = em.find(Account.class, email);
            if (account.getLogincounter() > 0 && account.getLogincounter() <= 4) {
                checkinCoin = 5;
            } else if (account.getLogincounter() > 4 && account.getLogincounter() <= 6) {
                checkinCoin = 10;
            } else {
                checkinCoin = 15;
            }

            utx.begin();
            account.setCatcoin(account.getCatcoin() + checkinCoin);
            account.setCheckin("Claimed");

            as.updateCustomerDetails(account);
            utx.commit();
            HttpSession session = request.getSession();
            session.setAttribute("login", account);

            response.setContentType("application/json");
            response.getWriter().write("{\"success\":true, \"checkin\":\"" + account.getCheckin() + "\", \"balance\":" + account.getCatcoin() + "}");
        } catch (Exception e) {

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

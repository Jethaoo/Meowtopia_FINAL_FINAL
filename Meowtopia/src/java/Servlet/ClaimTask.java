/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Model.Account;
import Model.AccountService;
import Model.Progress;
import Model.ProgressPK;
import Model.ProgressService;
import Model.Task;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.io.IOException;

/**
 *
 * @author User
 */
public class ClaimTask extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String taskid = request.getParameter("taskid");
            String email = request.getParameter("email");
            if (email == null) {
                response.sendRedirect("login.jsp"); // Not logged in
                return;
            }

            ProgressPK pPK = new ProgressPK(email, taskid);
            Progress progress = em.find(Progress.class, pPK);
            Task task = em.find(Task.class, taskid);

            if (task.getActiontimes() <= (progress.getTaskcounter()) && progress.getTaskstatus().equals("NULL")) {
                AccountService as = new AccountService(em);
                Account account = em.find(Account.class, email);
                account.setCatcoin(account.getCatcoin() + task.getReward());
                ProgressService ps = new ProgressService(em);
                progress.setTaskstatus("Claimed");

                utx.begin();
                as.updateCustomerDetails(account);
                ps.updateProgress(progress);
                utx.commit();

                HttpSession session = request.getSession();
                session.setAttribute("login", account);

                response.setContentType("application/json");
                response.getWriter().write("{\"success\":true, \"reward\":" + task.getReward() + ", \"taskstatus\":\"" + progress.getTaskstatus() + "\", \"balance\":" + account.getCatcoin() + "}");

            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\":false, "
                        + "\"message\":\"Task haven't complete\", "
                        + "\"actiontimes\":" + task.getActiontimes() + ", "
                        + "\"taskcounter\":" + progress.getTaskcounter() + ", "
                        + "\"taskaction\":\"" + task.getActiontype() + "\"}");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
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

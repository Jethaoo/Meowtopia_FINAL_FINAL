/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Model.Account;
import Model.AccountService;
import Model.Progress;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class ClaimAllTasks extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String email = request.getParameter("email");
            boolean claimAll = false;

            if (email == null) {
                response.sendRedirect("login.jsp"); // Not logged in
                return;
            }

            ProgressService ps = new ProgressService(em);
            List<Progress> pList = ps.findAllProgressByEmailTaskstatus(email);
            Account account = em.find(Account.class, email);
            List<Progress> claimList = new ArrayList();
            for (int i = 0; i < pList.size(); i++) {
                Task task = em.find(Task.class, pList.get(i).getProgressPK().getTaskid());

                if (task.getActiontimes() <= (pList.get(i).getTaskcounter())) {
                    AccountService as = new AccountService(em);
                    account.setCatcoin(account.getCatcoin() + task.getReward());
                    pList.get(i).setTaskstatus("Claimed");

                    utx.begin();
                    as.updateCustomerDetails(account);
                    ps.updateProgress(pList.get(i));
                    utx.commit();
                    claimList.add(pList.get(i));
                    claimAll = true;
                }
            }

            if (claimAll) {
                StringBuilder json = new StringBuilder();
                json.append("{\"success\":true,");
                json.append("\"balance\":").append(account.getCatcoin()).append(",");
                json.append("\"tasks\":[");

                for (int i = 0; i < claimList.size(); i++) {
                    claimList.get(i);
                    String taskId = claimList.get(i).getProgressPK().getTaskid();
                    // Find the related task from Task table (you can use taskFacade.find(taskId))
                    Task task = em.find(Task.class, taskId);
                    json.append(String.format(
                            "{\"taskid\":\"%s\",\"reward\":\"%s\",\"taskstatus\":\"%s\"}",
                            task.getTaskid(),
                            task.getReward(),
                            claimList.get(i).getTaskstatus()
                    ));

                    if (i < claimList.size() - 1) {
                        json.append(",");
                    }
                }

                json.append("]}");
// 4. Send back to JavaScript
                HttpSession session = request.getSession();
                session.setAttribute("login", account);
                response.setContentType("application/json");
                response.getWriter().write(json.toString());
                claimAll = false;
            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\":false, \"message\":\"No completed task to claim\"}");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

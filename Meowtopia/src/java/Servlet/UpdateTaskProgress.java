/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Model.*;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
import java.io.IOException;
import java.util.List;

public class UpdateTaskProgress extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String na = "N/A";
            String action = request.getParameter("action");
            String id = request.getParameter("id");
            String email = request.getParameter("email");

            ProgressService ps = new ProgressService(em);
            List<Progress> pList = ps.findAllProgressByEmailTaskstatus(email);

            for (int i = 0; i < pList.size(); i++) {
                Progress progress = em.find(Progress.class, pList.get(i).getProgressPK());
                Task task = em.find(Task.class, progress.getProgressPK().getTaskid());
                System.out.println(task.getActiontype().equals(action));
                System.out.println(task.getSpecificitem().equals(na));
                if (task.getActiontype().equals(action) && id != null && task.getSpecificitem().equals(id)) {
                    progress.setTaskcounter(progress.getTaskcounter() + 1);
                    utx.begin();
                    ps.updateProgress(progress);
                    utx.commit();
                } else if (task.getActiontype().equals(action) && id != null && task.getSpecificitem().equals(na)) {
                    progress.setTaskcounter(progress.getTaskcounter() + 1);
                    utx.begin();
                    ps.updateProgress(progress);
                    utx.commit();
                } else if (task.getActiontype().equals("Pet") && id == null) {
                    progress.setTaskcounter(progress.getTaskcounter() + 1);
                    utx.begin();
                    ps.updateProgress(progress);
                    utx.commit();
                }
            }
// 4. Send back to JavaScript
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":true}");

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setContentType("application/json");
            System.out.println("END UPDATE PROGRESS TASK");
            response.getWriter().write("{\"success\":false, \"message\":\"Internal server error\"}");
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

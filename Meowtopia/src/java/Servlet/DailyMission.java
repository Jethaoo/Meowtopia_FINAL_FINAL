/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

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
import jakarta.transaction.UserTransaction;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DailyMission extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            if (email == null) {
                response.sendRedirect("login.jsp"); // Not logged in
                return;
            }

            ProgressService ps = new ProgressService(em);
            List<Progress> pList = ps.findAllProgressByEmail(email);
            
            if (pList.size() < 5) {
                List<Task> allTasks = em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
                for (int i = 0; i < 5; i++) {
                    
                    // Shuffle and pick one random task
                    Collections.shuffle(allTasks);

                    Progress progress = new Progress(email, allTasks.get(0).getTaskid(), "NULL", 0);
                    utx.begin();
                    ps.createProgress(progress);
                    utx.commit();
                    allTasks.remove(allTasks.get(0));
                }
            }

            //get all data again 
            pList = ps.findAllProgressByEmail(email);
            StringBuilder json = new StringBuilder();
            json.append("{\"success\":true,\"tasks\":[");

            for (int i = 0; i < 5; i++) {
                Progress p = pList.get(i);
                String taskId = p.getProgressPK().getTaskid();
                // Find the related task from Task table (you can use taskFacade.find(taskId))
                Task task = em.find(Task.class, taskId);
                json.append(String.format(
                        "{\"taskid\":\"%s\",\"tasktitle\":\"%s\",\"taskcounter\":%d,\"reward\":%d,\"actiontimes\":%d,\"taskstatus\":\"%s\"}",
                        task.getTaskid(),
                        task.getTasktitle().replace("\"", "\\\""),
                        p.getTaskcounter(),
                        task.getReward(),
                        task.getActiontimes(),
                        p.getTaskstatus() // Add the task status here
                ));

                if (i < pList.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]}");
// 4. Send back to JavaScript
            response.setContentType("application/json");
            response.getWriter().write(json.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Do something with the email, like fetch daily missions for that user
//        request.setAttribute("missions", getDailyMissions(email));
//        request.getRequestDispatcher("dailyMission.jsp").forward(request, response);
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

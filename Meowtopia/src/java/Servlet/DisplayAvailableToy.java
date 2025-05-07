/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DisplayAvailableToy extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            Petcat petcat = (Petcat) session.getAttribute("petcat");

            CattoysService cts = new CattoysService(em);
            List<Cattoys> cattoys = cts.findAllToyByPetId(petcat.getPetid());
            System.out.println(cattoys);
            List<AvailableToy> availableToys = new ArrayList<>();
            for (Cattoys ct : cattoys) {
                String toyId = ct.getCattoysPK().getToyid();
                Toys toys = em.find(Toys.class, toyId);

                if (toys != null) {
                    availableToys.add(new AvailableToy(toys.getToyid(), toys.getToyname(), toys.getHappyvalue(), toys.getEnergyused(), toys.getToypic()));
                }
            }

            session.setAttribute("availableToys", availableToys);

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");

            for (int i = 0; i < availableToys.size(); i++) {
                AvailableToy item = availableToys.get(i);
                jsonBuilder.append("{")
                        .append("\"toyid\":\"").append(item.getToyid()).append("\",")
                        .append("\"toyname\":\"").append(item.getToyname()).append("\",")
                        .append("\"happiness\":").append(item.getHappyvalue()).append(",")
                        .append("\"energyused\":").append(item.getEnergyused()).append(",")
                        .append("\"toypic\":\"data:image/png;base64,").append(item.getPicBase64())
                        .append("\"}");

                if (i < availableToys.size() - 1) {
                    jsonBuilder.append(",");
                }
            }

            jsonBuilder.append("]");
            PrintWriter out = response.getWriter();
            out.print(jsonBuilder.toString());
            out.flush();
            response.setContentType("application/json");

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

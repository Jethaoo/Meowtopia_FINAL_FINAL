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

public class DisplayAvailableFood extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            Petcat petcat = (Petcat) session.getAttribute("petcat");

            CatfoodService cfs = new CatfoodService(em);
            List<Catfood> catfood = cfs.findAllFoodByPetId(petcat.getPetid());
            System.out.println(catfood);
            List<AvailableFood> availableFoods = new ArrayList<>();
            for (Catfood cf : catfood) {
                String foodId = cf.getCatfoodPK().getFoodid();
                Food food = em.find(Food.class, foodId);

                if (food != null) {
                    availableFoods.add(new AvailableFood(food.getFoodid(), food.getFoodname(), food.getEnergy(), food.getFoodpic(), cf.getQty()));
                }
            }

            session.setAttribute("availableFoods", availableFoods);

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");

            for (int i = 0; i < availableFoods.size(); i++) {
                AvailableFood item = availableFoods.get(i);
                jsonBuilder.append("{")
                        .append("\"foodid\":\"").append(item.getFoodid()).append("\",")
                        .append("\"foodname\":\"").append(item.getFoodname()).append("\",")
                        .append("\"energy\":").append(item.getEnergy()).append(",")
                        .append("\"foodpic\":\"data:image/png;base64,").append(item.getPicBase64()).append("\",")
                        .append("\"qty\":").append(item.getQuantity())
                        .append("}");
                if (i < availableFoods.size() - 1) {
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

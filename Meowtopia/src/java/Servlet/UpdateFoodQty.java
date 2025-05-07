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
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UpdateFoodQty extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            String foodId = request.getParameter("foodId");
            Petcat petcat = (Petcat) session.getAttribute("petcat");
            String petId = petcat.getPetid();
            System.out.println("catid: " + petId);

            if (foodId != null) {

                CatfoodService cfs = new CatfoodService(em);
                Catfood food = cfs.findCatfoodByPK(petId, foodId);
                int currentQty = food.getQty();
                utx.begin();

                if (currentQty == 1) {
                    cfs.deleteCatfood(petId, foodId);
                    utx.commit();
                    out.print("{\"success\":true, \"removed\":true}");
                } else {
                    food.setQty(currentQty - 1);
                    em.merge(food);
                    utx.commit();
                    out.print("{\"success\":true, \"removed\":false}");
                }
                
                List<AvailableFood> newAvailableFoods = new ArrayList<>();
                List<Catfood> latestFood = cfs.findAllFoodByPetId(petId);
                for (Catfood cf : latestFood) {
                    String newFoodId = cf.getCatfoodPK().getFoodid();
                    Food newFood = em.find(Food.class, newFoodId);
                    if (newFood != null) {
                        newAvailableFoods.add(new AvailableFood(newFood.getFoodid(),newFood.getFoodname(), newFood.getEnergy(), newFood.getFoodpic(), cf.getQty()));
                    }
                }
                
                session.setAttribute("availableFoods", newAvailableFoods);

            } else {
                out.print("{\"success\":false, \"message\":\"Missing foodId\"}");
            }

        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            out.print("{\"success\":false, \"message\":\"Server error\"}");
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

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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class BuyItem extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String email = request.getParameter("email");
            String itemId = request.getParameter("itemId");
            String category = request.getParameter("category");
            String itemName = null;
            int price = Integer.parseInt(request.getParameter("price"));
            boolean recorded = false;

            // Replace with your DB logic to fetch user balance
            Account account = em.find(Account.class, email);
            int balance = account.getCatcoin();
            //Find PetId from email
            PetcatService petcatService = new PetcatService(em);
            Petcat petCat = petcatService.findPetcatByEmail(account.getEmail());

            if (balance >= price) {
                //UPDATE CATTOYS TABLE WITH ITEMID AND EMAIL
                CattoysService cts = new CattoysService(em);
                
                if (category.equals("Toys")) {

                    Cattoys ct = new Cattoys(petCat.getPetid(), itemId, true);
                    Toys ty = em.find(Toys.class, itemId);
                    utx.begin();
                    cts.createCattoys(ct);
                    utx.commit();
                    itemName = ty.getToyname();
                    recorded = true;
                } else if (category.equals("Food")) {
                    CatfoodService cfs = new CatfoodService(em);
                    Catfood cf = cfs.findCatfoodByPK(petCat.getPetid(), itemId);
                    Food fd = em.find(Food.class, itemId);
                    if (cf != null) {
                        ////Food already inside catfood table
                        utx.begin();
                        cf.setQty(cf.getQty() + 1);
                        cfs.updateFoodQuantity(cf);
                        utx.commit();
                    } else {
                        ///Food did not inside vatfood table
                        cf = new Catfood(petCat.getPetid(), itemId, 1);
                        utx.begin();
                        cfs.createCatfood(cf);
                        utx.commit();
                    }
                    itemName = fd.getFoodname();
                    recorded = true;
                }

                //Update account balance
                AccountService acs = new AccountService(em);
                utx.begin();
                account.setCatcoin(balance - price);
                acs.updateCustomerDetails(account);
                utx.commit();

                account = em.find(Account.class, email);

                if (recorded) {
                    String json = String.format(
                            "{\"success\":true,\"category\":\"%s\", \"balance\":%d, \"itemName\":\"%s\"}",
                            category, account.getCatcoin(), itemName);

                    List<Cattoys> updatedPurchasedToys = cts.findAllToyByPetId(petCat.getPetid());
                    session.setAttribute("purchasedToys", updatedPurchasedToys);
                    session.setAttribute("login", account);
                    response.setContentType("application/json");
                    response.getWriter().write(json);
                } else {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\":false}");
                }
            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"success\":false, \"message\":\"Not enough coins to buy this item.\"}");
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

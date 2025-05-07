/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import java.io.IOException;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class login extends HttpServlet {
    
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("signin-username");
            String password = request.getParameter("signin-password");
            HttpSession session = request.getSession();
            boolean anotherDay = false;
            if (isValidUser(username, password)) {
                Account customer = em.find(Account.class, username);
                Date currentDate = new Date(System.currentTimeMillis());
                Date recordDate = customer.getRecorddate();

                // Calculate difference in milliseconds
                long diffInMillis = currentDate.getTime() - recordDate.getTime();

                // Convert to days
                long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                
                if (Math.abs(diffInDays) > 0) {
                    anotherDay = true;
                    utx.begin();
                    ProgressService ps = new ProgressService(em);
                    ps.deleteAllProgressByEmail(username);
                    
                    customer.setRecorddate(currentDate);
                    if (customer.getLogincounter() != 7 && Math.abs(diffInDays) == 1) {
                        customer.setLogincounter(customer.getLogincounter() + 1);
                    } else {
                        customer.setLogincounter(1);
                    }
                    AccountService as = new AccountService(em);
                    customer.setCheckin("NULL");
                    as.updateCustomerDetails(customer);
                    utx.commit();
                    
                }

//              everytime log in get PetCat data
                PetcatService petcatService = new PetcatService(em);
                Petcat petCat = petcatService.findPetcatByEmail(customer.getEmail());
                Petcat petcat = em.find(Petcat.class, petCat.getPetid());
////////////////this is for get the purchased toys for the specific pet account
                CattoysService ctS = new CattoysService(em);
                List<Cattoys> ctList = ctS.findAllToyByPetId(petCat.getPetid());
                List<ToysFood> shopItems = new ArrayList<>();
// Fetch Toys
                List<Toys> toysList = em.createNamedQuery("Toys.findAll", Toys.class).getResultList();
                for (Toys toy : toysList) {
                    shopItems.add(new ToysFood(
                            toy.getToyid(),
                            toy.getToyname(),
                            toy.getPrice(),
                            toy.getHappyvalue(),
                            toy.getEnergyused(),
                            toy.getToypic(),
                            "Toys"
                    ));
                }

// Fetch Food
                List<Food> foodList = em.createNamedQuery("Food.findAll", Food.class).getResultList();
                for (Food food : foodList) {
                    shopItems.add(new ToysFood(
                            food.getFoodid(),
                            food.getFoodname(),
                            food.getPrice(),
                            null, // No happyValue for food
                            food.getEnergy(), // Map ENERGY as energyUsed
                            food.getFoodpic(),
                            "Food"
                    ));
                }
                
                CatfoodService cfs = new CatfoodService(em);
                List<Catfood> catfood = cfs.findAllFoodByPetId(petCat.getPetid());
                List<AvailableFood> availableFoods = new ArrayList<>();
                for (Catfood cf : catfood) {
                    String foodId = cf.getCatfoodPK().getFoodid();
                    Food food = em.find(Food.class, foodId);
                    
                    if (food != null) {
                        availableFoods.add(new AvailableFood(food.getFoodid(), food.getFoodname(), food.getEnergy(), food.getFoodpic(), cf.getQty()));
                    }
                }
                
                session.setAttribute("availableFoods", availableFoods);
// Store in session or request
                session.setAttribute("shopItems", shopItems);
                session.setAttribute("purchasedToys", ctList);
                session.setAttribute("currentCategory", "Toys");

// Now store in session or request
                session.setAttribute("food", foodList);
                session.setAttribute("toyList", toysList);
                session.setAttribute("petcat", petcat);
                session.setAttribute("firstTimeCheckIn",anotherDay);
                session.setAttribute("login", customer);
                session.setAttribute("userEmail", username);
                session.setAttribute("em", em);
                response.sendRedirect("View/index.jsp");
            } else {
                session.setAttribute("errorMsg", "Invalid email or password!");
                response.sendRedirect("View/login.jsp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    private boolean isValidUser(String username, String password) {
        List<Account> user = em.createNamedQuery("Account.findByEmailAndPw", Account.class)
                .setParameter("email", username)
                .setParameter("password", password)
                .getResultList();
        
        return !user.isEmpty();
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

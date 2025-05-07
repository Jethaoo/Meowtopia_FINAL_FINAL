/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Model.*;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class signup extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("signup-name");
            String email = request.getParameter("signup-email");
            String password = request.getParameter("signup-password");
            String confirmPassword = request.getParameter("signup-confirm-password");

            Account accountList = em.find(Account.class, email);

            HttpSession session = request.getSession();
            if (name == null || name.isEmpty()) {
                session.setAttribute("nameError", "Name is required");
            } else if (!isValidName(name)) {
                session.setAttribute("nameError", "Invalid name format");
            }

            if (accountList != null) {
                session.setAttribute("emailError", "Email already exists");
            }

            if (email == null || email.isEmpty()) {
                session.setAttribute("emailError", "Email is required");
            } else if (!isValidEmail(email)) {
                session.setAttribute("emailError", "Invalid email format");
            }

            if (password == null || password.isEmpty()) {
                session.setAttribute("passwordError", "Password is required");
            } else if (!isValidPassword(password)) {
                session.setAttribute("passwordError", "Password must have at least 8 characters, at least 1 uppercase letter, 1 lowercase letter, 1 symbol, and 1 number");
            }

            if (confirmPassword == null || confirmPassword.isEmpty()) {
                session.setAttribute("confirmPasswordError", "Please confirm password");
            } else if (!confirmPassword.equals(password)) {
                session.setAttribute("confirmPasswordError", "Passwords do not match");
            }

            if (session.getAttribute("nameError") != null || session.getAttribute("emailError") != null
                    || session.getAttribute("phoneError") != null || session.getAttribute("addressError") != null
                    || session.getAttribute("passwordError") != null
                    || session.getAttribute("confirmPasswordError") != null) {
                response.sendRedirect("View/signup.jsp");
            } else {
                AccountService as = new AccountService(em);
                Date currentDate = new Date(System.currentTimeMillis());
                Account account = new Account(email, name, password, 50, currentDate, 1);

                utx.begin();
                as.createAccount(account);
                utx.commit();

                Account newCustomer = em.find(Account.class, email);

                System.out.println(newCustomer.getRecorddate());
                PetcatService pcs = new PetcatService(em);
                String petId = pcs.generateUniquePetcatId();
                Petcat petcat = new Petcat(petId, newCustomer, 100, 100);

                utx.begin();
                pcs.createPetcat(petcat);
                utx.commit();

                // Add default food
                CatfoodService cfs = new CatfoodService(em);
                Catfood defaultFood = new Catfood(new CatfoodPK(petId, "F001"), 5); // F001 = default food ID, 5 = qty
                utx.begin();
                cfs.createCatfood(defaultFood);
                utx.commit();

                // Add default toy
                CattoysService cts = new CattoysService(em);
                Cattoys defaultToy = new Cattoys(new CattoysPK(petId, "T001"), true); // T001 = default toy ID, 1 = qty or level
                utx.begin();
                cts.createCattoys(defaultToy);
                utx.commit();

                HttpSession msgSession = request.getSession();
                msgSession.setAttribute("successMessage", "You have signed up successfully!");
                response.sendRedirect("View/login.jsp");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean isValidName(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
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

package Servlet;

import Model.Petcat;
import Model.PetcatService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import jakarta.annotation.Resource;
import java.io.IOException;

public class UpdatePetStatus extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            HttpSession session = request.getSession();
            String userEmail = (String) session.getAttribute("userEmail");
            
            if (userEmail == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"success\": false, \"message\": \"User not logged in\"}");
                return;
            }
            
            PetcatService pcs = new PetcatService(em);
            Petcat pet = pcs.findPetcatByEmail(userEmail);
            
            if (pet == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"success\": false, \"message\": \"Pet not found\"}");
                return;
            }
            
            String action = request.getParameter("action");
            int amount = Integer.parseInt(request.getParameter("amount"));
            
            utx.begin();
            
            switch(action) {
                case "increaseHappiness":
                    pet.setHappiness(Math.min(100, pet.getHappiness() + amount));
                    break;
                case "decreaseHappiness":
                    pet.setHappiness(Math.max(0, pet.getHappiness() - amount));
                    break;
                case "increaseEnergy":
                    pet.setHungriness(Math.min(100, pet.getHungriness() + amount));
                    break;
                case "decreaseEnergy":
                    pet.setHungriness(Math.max(0, pet.getHungriness() - amount));
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"success\": false, \"message\": \"Invalid action\"}");
                    return;
            }
            
            em.merge(pet);
            utx.commit();
            
            
            Petcat updatedPet = pcs.findPetcatByEmail(userEmail);
            String jsonResponse = String.format(
                "{\"success\": true, \"happiness\": %d, \"energy\": %d}",
                updatedPet.getHappiness(),
                updatedPet.getHungriness()
            );
            
            session.setAttribute("petcat", updatedPet);
            
            response.getWriter().write(jsonResponse);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Server error\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
} 
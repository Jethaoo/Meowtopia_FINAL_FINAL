/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Model.Post;
import Model.PostService;
import Model.PostlikedService;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

public class LikePost extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String postid = request.getParameter("postid");
            PostService ps = new PostService(em);
            PostlikedService pls = new PostlikedService(em);
            Post post = ps.getPostById(postid);

            boolean liked = false;

            HttpSession session = request.getSession();
            String userEmail = (String) session.getAttribute("userEmail");

            if (post != null && userEmail != null) {
                utx.begin();
                if (pls.hasUserLikedPost(userEmail, postid)) {
                    pls.unlikePost(userEmail, postid);
                    post.setTotallike(post.getTotallike() - 1);
                    liked = false;
                } else {
                    pls.likePost(userEmail, postid);
                    post.setTotallike(post.getTotallike() + 1);
                    liked = true;
                }
                ps.updatePost(post);
                utx.commit();

                String json = String.format(
                        "{\"success\":true,\"liked\":%b,\"totalLikes\":%d}",
                        liked,
                        post.getTotallike()
                );
                response.getWriter().write(json);
            } else {
                response.getWriter().write("{\"success\":false}");
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

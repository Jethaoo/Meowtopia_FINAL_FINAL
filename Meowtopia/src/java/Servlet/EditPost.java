/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Model.Post;
import Model.Account;
import Model.PostService;
import static Servlet.AddPost.toByteArray;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import jakarta.transaction.UserTransaction;
import java.io.InputStream;
import java.util.Date;

@MultipartConfig
public class EditPost extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account email = (Account) session.getAttribute("login");
        Date today = new Date();

        if (email == null) {
            response.sendRedirect("View/login.jsp");
            return;
        }

        try {
            String postId = request.getParameter("postId");
            String description = request.getParameter("desc");
            int totalLike = Integer.parseInt(request.getParameter("totalLike"));
            Part filePart = request.getPart("image");
            byte[] picBytes = null;

            PostService ps = new PostService(em);
            Post existingPost = ps.getPostById(postId);

            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream picStream = filePart.getInputStream()) {
                    picBytes = toByteArray(picStream);
                }
            } else {
                picBytes = existingPost.getImage();
            }

            Post userPost = new Post(postId, description, picBytes, 0, today, email);

            utx.begin();
            ps.updatePost(userPost);
            utx.commit();

            session.setAttribute("successMessage", "You have edited a post successfully!");
            response.sendRedirect("DisplayUserPosts");

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

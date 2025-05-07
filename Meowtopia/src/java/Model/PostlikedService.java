/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import Model.Post;
import Model.Postliked;
import Model.PostlikedPK;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class PostlikedService {

    @PersistenceContext
    private EntityManager em;

    public PostlikedService(EntityManager em) {
        this.em = em;
    }

    public boolean hasUserLikedPost(String email, String postId) {
        List<Postliked> likes = em.createNamedQuery("Postliked.findByEmailAndPostid", Postliked.class)
                .setParameter("email", email)
                .setParameter("postid", postId)
                .getResultList();
        return !likes.isEmpty();
    }

    public void likePost(String userEmail, String postId) {
        PostlikedPK likePK = new PostlikedPK(userEmail, postId);
        Postliked like = new Postliked(likePK);
        em.persist(like);
    }

    public void unlikePost(String userEmail, String postId) {
        PostlikedPK likePK = new PostlikedPK(userEmail, postId);
        Postliked like = em.find(Postliked.class, likePK);
        if (like != null) {
            em.remove(like);
        }
    }
}

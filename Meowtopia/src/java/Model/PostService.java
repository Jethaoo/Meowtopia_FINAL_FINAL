/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import Model.Post;
import java.util.List;

public class PostService {

    @PersistenceContext
    private EntityManager em;

    public PostService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void createPost(Post post) {
        em.persist(post);
    }

    public List<Post> getAllPosts() {
        return em.createNamedQuery("Post.findAll", Post.class).getResultList();
    }

    public List<Post> getAllPostsDesc() {
        return em.createNamedQuery("Post.findAllPostByDate", Post.class)
                .getResultList();
    }

    public Post getPostById(String postId) {
        return em.find(Post.class, postId);
    }

    public List<Post> getPostByEmail(String email) {
        Account account = em.find(Account.class, email);

        return em.createNamedQuery("Post.findByEmail", Post.class)
                .setParameter("email", account)
                .getResultList();
    }

    public List<Post> getPostByEmailDesc(String email) {
        Account account = em.find(Account.class, email);

        return em.createNamedQuery("Post.findByEmailPostByDate", Post.class)
                .setParameter("email", account)
                .getResultList();
    }

    public void updatePost(Post post) {
        em.merge(post);
    }

    public boolean deletePost(String postId) {
        Post post = getPostById(postId);
        if (post != null) {
            em.remove(post);
            return true;
        }
        return false;
    }

    public String generateUniquePostId() {
        String prefix = "P";
        int sequenceLength = 3;
        String postId = "";

        int maxSequence = findNumberSequence();

        while (true) {
            String numericPart = String.format("%0" + sequenceLength + "d", maxSequence + 1);
            postId = prefix + numericPart;

            Post existingPost = em.find(Post.class, postId);
            if (existingPost == null) {
                break;
            }

            maxSequence++;
        }

        return postId;
    }

    public int findNumberSequence() {
        List<Post> list = getAllPosts();
        int i = 0;
        for (Post p : list) {
            i++;
        }
        return i;
    }
}

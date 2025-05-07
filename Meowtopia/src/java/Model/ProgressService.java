/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class ProgressService {

    @PersistenceContext
    private EntityManager em;

    public ProgressService(EntityManager em) {
        this.em = em;
    }

    public boolean createProgress(Progress progress) {
        em.persist(progress);
        return true;
    }

    public void updateProgress(Progress progress) {
        em.merge(progress);
    }

    public boolean deleteProgress(Progress progress) {
        em.remove(progress);
        return true;
    }

    @Transactional
    public void deleteAllProgressByEmail(String email) {
        em.createQuery("DELETE FROM Progress p WHERE p.progressPK.email = :email")
                .setParameter("email", email)
                .executeUpdate();
    }

    public List<Progress> findAllProgressByEmail(String email) {
        return em.createQuery("SELECT p FROM Progress p WHERE p.progressPK.email = :email", Progress.class)
                .setParameter("email", email).getResultList();
    }

    public List<Progress> findAllProgressByEmailTaskstatus(String email) {
        return em.createQuery("SELECT p FROM Progress p WHERE p.taskstatus = :taskstatus AND p.progressPK.email = :email", Progress.class)
                .setParameter("taskstatus", "NULL") // Set taskstatus to "NULL" or any other value
                .setParameter("email", email) // Set email as the parameter
                .getResultList();
    }

    public Progress findProgressByPK(String email, String taskId) {
        ProgressPK pk = new ProgressPK(email, taskId);
        return em.find(Progress.class, pk);
    }

    @Transactional
    public void updateProgressDetails(Account account) {
        em.merge(account);
    }
}

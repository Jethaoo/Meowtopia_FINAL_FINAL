/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class CatfoodService {

    @PersistenceContext
    private EntityManager em;

    public CatfoodService(EntityManager em) {
        this.em = em;
    }

    public boolean createCatfood(Catfood catfood) {
        em.persist(catfood);
        return true;
    }

    public boolean buyFood(String userEmail, String foodId, int quantity) {
        CatfoodPK catfoodPK = new CatfoodPK(userEmail, foodId);
        Catfood catfood = new Catfood(catfoodPK, quantity);
        em.persist(catfood);
        return true;
    }

    public void updateFoodQuantity(Catfood catfood) {
        em.merge(catfood);
    }

    public List<Catfood> findAllFoodByPetId(String petId) {
        return em.createNamedQuery("Catfood.findByPetid", Catfood.class)
                .setParameter("petid", petId)
                .getResultList();
    }

    public Catfood findCatfoodByPK(String petId, String foodId) {
        CatfoodPK pk = new CatfoodPK(petId, foodId);
        return em.find(Catfood.class, pk);
    }

    public boolean deleteCatfood(String petId, String foodId) {
        Catfood catfood = findCatfoodByPK(petId, foodId);
        if (catfood != null) {
            em.remove(catfood);
            return true;
        }
        return false;
    }
}

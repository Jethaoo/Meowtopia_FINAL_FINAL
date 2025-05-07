/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import Model.Food;
import java.util.List;

public class FoodService {
    @PersistenceContext
    private EntityManager em;
    
    public FoodService() {

    }

    public FoodService(EntityManager em) {
        this.em = em;
    }
    
    public List<Food> getFoodAll() {
        return em.createNamedQuery("Food.findAll").getResultList();
    }
    
    public Food getFoodByFoodId(String foodId) {
        List<Food> food = em.createNamedQuery("Food.findByFoodid", Food.class).setParameter("foodid", foodId).getResultList();
        return food.isEmpty() ? null : food.get(0);
    }
}

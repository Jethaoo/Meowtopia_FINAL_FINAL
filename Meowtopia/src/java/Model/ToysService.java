/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import Model.Toys;
import jakarta.persistence.PersistenceContext;

public class ToysService {

    @PersistenceContext
    private EntityManager em;

    @Transactional

    public List<Toys> getAllToys() {
        return em.createNamedQuery("Toys.findAll", Toys.class).getResultList();
    }

    public Toys getToysById(String toyId) {
        return em.find(Toys.class, toyId);
    }

}


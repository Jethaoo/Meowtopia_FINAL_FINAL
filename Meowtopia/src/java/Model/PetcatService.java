/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import Model.Petcat;
import java.util.List;

public class PetcatService {
    
    @PersistenceContext
    private EntityManager em;
    
    public PetcatService() {

    }

    public PetcatService(EntityManager em) {
        this.em = em;
    }
    
    @Transactional
    public void createPetcat(Petcat petcat) {
        em.persist(petcat);
    }
    
    public List<Petcat> getPetcatAll() {
        return em.createNamedQuery("Petcat.findAll").getResultList();
    }

    
    public String generateUniquePetcatId() {
        String prefix = "C";
        int sequenceLength = 3;
        String petId = "";

        int maxSequence = findPetcatNumberSequence();

        while (true) {
            String numericPart = String.format("%0" + sequenceLength + "d", maxSequence + 1);
            petId = prefix + numericPart;

            Petcat existingCart = em.find(Petcat.class, petId);
            if (existingCart == null) {
                break; 
            }

            maxSequence++;
        }

        return petId;
    }
    
    public int findPetcatNumberSequence() {
        List<Petcat> list = getPetcatAll();
        int i = 0;
        for (Petcat p : list) {
            i++;
        }
        return i;
    }
    
    public Petcat findPetcatByEmail(String email) {
        List<Petcat> petcats = em.createQuery("SELECT p FROM Petcat p WHERE p.email.email = :email", Petcat.class)
                                .setParameter("email", email)
                                .getResultList();
        return petcats.isEmpty() ? null : petcats.get(0);
    }

    
}

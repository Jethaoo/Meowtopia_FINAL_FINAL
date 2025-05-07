package Model;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class CattoysService {

    @PersistenceContext
    private EntityManager em;

    public CattoysService(EntityManager em) {
        this.em = em;
    }

    public boolean createCattoys(Cattoys cattoys) {
        em.persist(cattoys);
        return true;
    }


    // Get all toys for a specific pet
    public List<Cattoys> findAllToyByPetId(String petId) {
        return em.createQuery("SELECT c FROM Cattoys c WHERE c.cattoysPK.petid = :petid", Cattoys.class)
                .setParameter("petid", petId).getResultList();
    }

    // Get a specific toy item
    public Cattoys findCattoyByPK(String petId, String toyId) {
        CattoysPK pk = new CattoysPK(petId, toyId);
        return em.find(Cattoys.class, pk);
    }

    
}

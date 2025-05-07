/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import Model.Account;
import jakarta.transaction.Transactional;
import java.util.List;

public class AccountService {

    @PersistenceContext
    private EntityManager em;

    public AccountService(EntityManager em) {
        this.em = em;
    }

    public boolean createAccount(Account account) {
        em.persist(account);
        return true;
    }

    public Account getAccountByEmail(String email) {
        return em.find(Account.class, email);
    }


    @Transactional
    public void updateCustomerDetails(Account account) {
        em.merge(account);
    }

    public boolean checkAccountEmailExistence(String email) {
        Account account = em.find(Account.class, email);
        return account != null; // Return true if customer with given ID exists, otherwise false
    }
}


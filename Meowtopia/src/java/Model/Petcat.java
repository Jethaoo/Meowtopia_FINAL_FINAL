/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "PETCAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Petcat.findAll", query = "SELECT p FROM Petcat p"),
    @NamedQuery(name = "Petcat.findByPetid", query = "SELECT p FROM Petcat p WHERE p.petid = :petid"),
    @NamedQuery(name = "Petcat.findByHappiness", query = "SELECT p FROM Petcat p WHERE p.happiness = :happiness"),
    @NamedQuery(name = "Petcat.findByHungriness", query = "SELECT p FROM Petcat p WHERE p.hungriness = :hungriness")})
public class Petcat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PETID")
    private String petid;
    @Basic(optional = false)
    @Column(name = "HAPPINESS")
    private int happiness;
    @Basic(optional = false)
    @Column(name = "HUNGRINESS")
    private int hungriness;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Account email;

    public Petcat() {
    }

    public Petcat(String petid) {
        this.petid = petid;
    }

    public Petcat(String petid, int happiness, int hungriness) {
        this.petid = petid;
        this.happiness = happiness;
        this.hungriness = hungriness;
    }

    public Petcat(String petid, Account email, int happiness, int hungriness) {
        this.petid = petid;
        this.happiness = happiness;
        this.hungriness = hungriness;
        this.email = email;
    }

    public String getPetid() {
        return petid;
    }

    public void setPetid(String petid) {
        this.petid = petid;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getHungriness() {
        return hungriness;
    }

    public void setHungriness(int hungriness) {
        this.hungriness = hungriness;
    }

    public Account getEmail() {
        return email;
    }

    public void setEmail(Account email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (petid != null ? petid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Petcat)) {
            return false;
        }
        Petcat other = (Petcat) object;
        if ((this.petid == null && other.petid != null) || (this.petid != null && !this.petid.equals(other.petid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Petcat[ petid=" + petid + " ]";
    }
    
}

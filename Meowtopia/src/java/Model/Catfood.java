/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "CATFOOD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catfood.findAll", query = "SELECT c FROM Catfood c"),
    @NamedQuery(name = "Catfood.findByPetid", query = "SELECT c FROM Catfood c WHERE c.catfoodPK.petid = :petid"),
    @NamedQuery(name = "Catfood.findByPetidAndFoodid", query = "SELECT c FROM Catfood c WHERE c.catfoodPK.petid = :petid AND c.catfoodPK.foodid = :foodid"),
    @NamedQuery(name = "Catfood.findByFoodid", query = "SELECT c FROM Catfood c WHERE c.catfoodPK.foodid = :foodid"),
    @NamedQuery(name = "Catfood.findByQty", query = "SELECT c FROM Catfood c WHERE c.qty = :qty")})
public class Catfood implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CatfoodPK catfoodPK;
    @Basic(optional = false)
    @Column(name = "QTY")
    private int qty;

    public Catfood() {
    }

    public Catfood(CatfoodPK catfoodPK) {
        this.catfoodPK = catfoodPK;
    }

    public Catfood(CatfoodPK catfoodPK, int qty) {
        this.catfoodPK = catfoodPK;
        this.qty = qty;
    }

    public Catfood(String petid, String foodid, int qty) {
        this.catfoodPK = new CatfoodPK(petid, foodid);
         this.qty = qty;
    }

    public CatfoodPK getCatfoodPK() {
        return catfoodPK;
    }

    public void setCatfoodPK(CatfoodPK catfoodPK) {
        this.catfoodPK = catfoodPK;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catfoodPK != null ? catfoodPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catfood)) {
            return false;
        }
        Catfood other = (Catfood) object;
        if ((this.catfoodPK == null && other.catfoodPK != null) || (this.catfoodPK != null && !this.catfoodPK.equals(other.catfoodPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Catfood[ catfoodPK=" + catfoodPK + " ]";
    }
    
}

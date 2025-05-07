/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author User
 */
@Embeddable
public class CatfoodPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PETID")
    private String petid;
    @Basic(optional = false)
    @Column(name = "FOODID")
    private String foodid;

    public CatfoodPK() {
    }

    public CatfoodPK(String petid, String foodid) {
        this.petid = petid;
        this.foodid = foodid;
    }

    public String getPetid() {
        return petid;
    }

    public void setPetid(String petid) {
        this.petid = petid;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (petid != null ? petid.hashCode() : 0);
        hash += (foodid != null ? foodid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatfoodPK)) {
            return false;
        }
        CatfoodPK other = (CatfoodPK) object;
        if ((this.petid == null && other.petid != null) || (this.petid != null && !this.petid.equals(other.petid))) {
            return false;
        }
        if ((this.foodid == null && other.foodid != null) || (this.foodid != null && !this.foodid.equals(other.foodid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CatfoodPK[ petid=" + petid + ", foodid=" + foodid + " ]";
    }
    
}

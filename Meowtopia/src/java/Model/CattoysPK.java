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
public class CattoysPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PETID")
    private String petid;
    @Basic(optional = false)
    @Column(name = "TOYID")
    private String toyid;

    public CattoysPK() {
    }

    public CattoysPK(String petid, String toyid) {
        this.petid = petid;
        this.toyid = toyid;
    }

    public String getPetid() {
        return petid;
    }

    public void setPetid(String petid) {
        this.petid = petid;
    }

    public String getToyid() {
        return toyid;
    }

    public void setToyid(String toyid) {
        this.toyid = toyid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (petid != null ? petid.hashCode() : 0);
        hash += (toyid != null ? toyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CattoysPK)) {
            return false;
        }
        CattoysPK other = (CattoysPK) object;
        if ((this.petid == null && other.petid != null) || (this.petid != null && !this.petid.equals(other.petid))) {
            return false;
        }
        if ((this.toyid == null && other.toyid != null) || (this.toyid != null && !this.toyid.equals(other.toyid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CattoysPK[ petid=" + petid + ", toyid=" + toyid + " ]";
    }
    
}

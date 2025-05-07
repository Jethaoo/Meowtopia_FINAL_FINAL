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
@Table(name = "CATTOYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cattoys.findAll", query = "SELECT c FROM Cattoys c"),
    @NamedQuery(name = "Cattoys.findByPetid", query = "SELECT c FROM Cattoys c WHERE c.cattoysPK.petid = :petid"),
    @NamedQuery(name = "Cattoys.findByToyid", query = "SELECT c FROM Cattoys c WHERE c.cattoysPK.toyid = :toyid"),
    @NamedQuery(name = "Cattoys.findByToystatus", query = "SELECT c FROM Cattoys c WHERE c.toystatus = :toystatus")})
public class Cattoys implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CattoysPK cattoysPK;
    @Basic(optional = false)
    @Column(name = "TOYSTATUS")
    private Boolean toystatus;

    public Cattoys() {
    }

    public Cattoys(CattoysPK cattoysPK) {
        this.cattoysPK = cattoysPK;
    }

    public Cattoys(CattoysPK cattoysPK, Boolean toystatus) {
        this.cattoysPK = cattoysPK;
        this.toystatus = toystatus;
    }

    public Cattoys(String petid, String toyid, Boolean toystatus) {
        this.cattoysPK = new CattoysPK(petid, toyid);
        this.toystatus = toystatus;
    }

    public CattoysPK getCattoysPK() {
        return cattoysPK;
    }

    public void setCattoysPK(CattoysPK cattoysPK) {
        this.cattoysPK = cattoysPK;
    }

    public Boolean getToystatus() {
        return toystatus;
    }

    public void setToystatus(Boolean toystatus) {
        this.toystatus = toystatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cattoysPK != null ? cattoysPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cattoys)) {
            return false;
        }
        Cattoys other = (Cattoys) object;
        if ((this.cattoysPK == null && other.cattoysPK != null) || (this.cattoysPK != null && !this.cattoysPK.equals(other.cattoysPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Cattoys[ cattoysPK=" + cattoysPK + " ]";
    }
    
}

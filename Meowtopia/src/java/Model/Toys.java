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
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.Base64;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "TOYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Toys.findAll", query = "SELECT t FROM Toys t"),
    @NamedQuery(name = "Toys.findByToyid", query = "SELECT t FROM Toys t WHERE t.toyid = :toyid"),
    @NamedQuery(name = "Toys.findByToyname", query = "SELECT t FROM Toys t WHERE t.toyname = :toyname"),
    @NamedQuery(name = "Toys.findByPrice", query = "SELECT t FROM Toys t WHERE t.price = :price"),
    @NamedQuery(name = "Toys.findByHappyvalue", query = "SELECT t FROM Toys t WHERE t.happyvalue = :happyvalue"),
    @NamedQuery(name = "Toys.findByEnergyused", query = "SELECT t FROM Toys t WHERE t.energyused = :energyused")})
public class Toys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TOYID")
    private String toyid;
    @Basic(optional = false)
    @Column(name = "TOYNAME")
    private String toyname;
    @Lob
    @Column(name = "TOYPIC")
    private byte[] toypic;
    @Basic(optional = false)
    @Column(name = "PRICE")
    private int price;
    @Basic(optional = false)
    @Column(name = "HAPPYVALUE")
    private int happyvalue;
    @Column(name = "ENERGYUSED")
    private Integer energyused;

    public Toys() {
    }

    public Toys(String toyid) {
        this.toyid = toyid;
    }

    public Toys(String toyid, String toyname, int price, int happyvalue) {
        this.toyid = toyid;
        this.toyname = toyname;
        this.price = price;
        this.happyvalue = happyvalue;
    }

    public String getToyid() {
        return toyid;
    }

    public void setToyid(String toyid) {
        this.toyid = toyid;
    }

    public String getToyname() {
        return toyname;
    }

    public void setToyname(String toyname) {
        this.toyname = toyname;
    }

    public byte[] getToypic() {
        return toypic;
    }

    public void setToypic(byte[] toypic) {
        this.toypic = toypic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHappyvalue() {
        return happyvalue;
    }

    public void setHappyvalue(int happyvalue) {
        this.happyvalue = happyvalue;
    }

    public Integer getEnergyused() {
        return energyused;
    }

    public void setEnergyused(Integer energyused) {
        this.energyused = energyused;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (toyid != null ? toyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Toys)) {
            return false;
        }
        Toys other = (Toys) object;
        if ((this.toyid == null && other.toyid != null) || (this.toyid != null && !this.toyid.equals(other.toyid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Toys[ toyid=" + toyid + " ]";
    }

    public String getPicBase64() {
        if (toypic != null && toypic.length > 0) {
            return Base64.getEncoder().encodeToString(toypic);
        } else {
            return "";
        }
    }
}

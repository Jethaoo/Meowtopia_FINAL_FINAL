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
@Table(name = "FOOD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f"),
    @NamedQuery(name = "Food.findByFoodid", query = "SELECT f FROM Food f WHERE f.foodid = :foodid"),
    @NamedQuery(name = "Food.findByFoodname", query = "SELECT f FROM Food f WHERE f.foodname = :foodname"),
    @NamedQuery(name = "Food.findByEnergy", query = "SELECT f FROM Food f WHERE f.energy = :energy"),
    @NamedQuery(name = "Food.findByPrice", query = "SELECT f FROM Food f WHERE f.price = :price")})
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "FOODID")
    private String foodid;
    @Basic(optional = false)
    @Column(name = "FOODNAME")
    private String foodname;
    @Basic(optional = false)
    @Column(name = "ENERGY")
    private int energy;
    @Lob
    @Column(name = "FOODPIC")
    private byte[] foodpic;
    @Basic(optional = false)
    @Column(name = "PRICE")
    private int price;

    public Food() {
    }

    public Food(String foodid) {
        this.foodid = foodid;
    }

    public Food(String foodid, String foodname, int energy, int price) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.energy = energy;
        this.price = price;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public byte[] getFoodpic() {
        return foodpic;
    }

    public void setFoodpic(byte[] foodpic) {
        this.foodpic = foodpic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (foodid != null ? foodid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.foodid == null && other.foodid != null) || (this.foodid != null && !this.foodid.equals(other.foodid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Food[ foodid=" + foodid + " ]";
    }

    public String getPicBase64() {
        if (foodpic != null && foodpic.length > 0) {
            return Base64.getEncoder().encodeToString(foodpic);
        } else {
            return "";
        }
    }
}

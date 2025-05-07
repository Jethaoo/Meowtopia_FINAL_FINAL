/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
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
@Table(name = "POSTLIKED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Postliked.findAll", query = "SELECT p FROM Postliked p"),
    @NamedQuery(name = "Postliked.findByEmail", query = "SELECT p FROM Postliked p WHERE p.postlikedPK.email = :email"),
    @NamedQuery(name = "Postliked.findByPostid", query = "SELECT p FROM Postliked p WHERE p.postlikedPK.postid = :postid"),
    @NamedQuery(name = "Postliked.findByEmailAndPostid", query = "SELECT p FROM Postliked p WHERE p.postlikedPK.email = :email AND p.postlikedPK.postid = :postid")
})
public class Postliked implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PostlikedPK postlikedPK;

    public Postliked() {
    }

    public Postliked(PostlikedPK postlikedPK) {
        this.postlikedPK = postlikedPK;
    }

    public Postliked(String email, String postid) {
        this.postlikedPK = new PostlikedPK(email, postid);
    }

    public PostlikedPK getPostlikedPK() {
        return postlikedPK;
    }

    public void setPostlikedPK(PostlikedPK postlikedPK) {
        this.postlikedPK = postlikedPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postlikedPK != null ? postlikedPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Postliked)) {
            return false;
        }
        Postliked other = (Postliked) object;
        if ((this.postlikedPK == null && other.postlikedPK != null) || (this.postlikedPK != null && !this.postlikedPK.equals(other.postlikedPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Postliked[ postlikedPK=" + postlikedPK + " ]";
    }
    
}

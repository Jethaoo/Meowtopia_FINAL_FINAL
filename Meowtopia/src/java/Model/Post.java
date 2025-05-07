/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Base64;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "POST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p"),
    @NamedQuery(name = "Post.findByPostid", query = "SELECT p FROM Post p WHERE p.postid = :postid"),
    @NamedQuery(name = "Post.findByEmail", query = "SELECT p FROM Post p WHERE p.email = :email"),
    @NamedQuery(name = "Post.findByDescription", query = "SELECT p FROM Post p WHERE p.description = :description"),
    @NamedQuery(name = "Post.findByTotallike", query = "SELECT p FROM Post p WHERE p.totallike = :totallike"),
    @NamedQuery(name = "Post.findByPostdate", query = "SELECT p FROM Post p WHERE p.postdate = :postdate"),
    @NamedQuery(name = "Post.findAllPostByDate", query = "SELECT p FROM Post p ORDER BY p.postdate DESC"),
    @NamedQuery(name = "Post.findByEmailPostByDate", query = "SELECT p FROM Post p WHERE p.email = :email ORDER BY p.postdate DESC")
})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "POSTID")
    private String postid;
    @Column(name = "DESCRIPTION")
    private String description;
    @Lob
    @Column(name = "IMAGE")
    private byte[] image;
    @Basic(optional = false)
    @Column(name = "TOTALLIKE")
    private int totallike;
    @Column(name = "POSTDATE")
    @Temporal(TemporalType.DATE)
    private Date postdate;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Account email;

    public Post() {
    }

    public Post(String postid, String description, byte[] image, int totallike, Date postdate, Account email) {
        this.postid = postid;
        this.description = description;
        this.image = image;
        this.totallike = totallike;
        this.postdate = postdate;
        this.email = email;
    }

    public Post(String postid) {
        this.postid = postid;
    }

    public Post(String postid, int totallike) {
        this.postid = postid;
        this.totallike = totallike;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getTotallike() {
        return totallike;
    }

    public void setTotallike(int totallike) {
        this.totallike = totallike;
    }

    public Date getPostdate() {
        return postdate;
    }

    public void setPostdate(Date postdate) {
        this.postdate = postdate;
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
        hash += (postid != null ? postid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.postid == null && other.postid != null) || (this.postid != null && !this.postid.equals(other.postid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Post[ postid=" + postid + " ]";
    }
    
    public String getPicBase64() {
        if (image != null && image.length > 0) {
            return Base64.getEncoder().encodeToString(image);
        } else {
            return "";
}
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "ACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByAccname", query = "SELECT a FROM Account a WHERE a.accname = :accname"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByCatcoin", query = "SELECT a FROM Account a WHERE a.catcoin = :catcoin"),
    @NamedQuery(name = "Account.findByRecorddate", query = "SELECT a FROM Account a WHERE a.recorddate = :recorddate"),
    @NamedQuery(name = "Account.findByLogincounter", query = "SELECT a FROM Account a WHERE a.logincounter = :logincounter"),
    @NamedQuery(name = "Account.findByCheckin", query = "SELECT a FROM Account a WHERE a.checkin = :checkin"),
    @NamedQuery(name = "Account.findByEmailAndPw", query = "SELECT a FROM Account a WHERE a.email = :email AND a.password = :password")
})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "ACCNAME")
    private String accname;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "CATCOIN")
    private int catcoin;
    @Column(name = "RECORDDATE")
    @Temporal(TemporalType.DATE)
    private Date recorddate;
    @Column(name = "LOGINCOUNTER")
    private int logincounter;
    @Column(name = "CHECKIN")
    private String checkin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "email")
    private List<Petcat> petcatList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "email")
    private List<Post> postList;

    public Account() {
    }

    public Account(String email) {
        this.email = email;
    }

    public Account(String email, int catcoin) {
        this.email = email;
        this.catcoin = catcoin;
    }

    public Account(String email, String accname, String password, int catcoin) {
        this.email = email;
        this.accname = accname;
        this.password = password;
        this.catcoin = catcoin;
    }

    public Account(String email, String accname, String password, int catcoin, Date recorddate, int logincounter) {
        this.email = email;
        this.accname = accname;
        this.password = password;
        this.catcoin = catcoin;
        this.recorddate = recorddate;
        this.logincounter = logincounter;
    }

    public Account(String email, Date recorddate, int logincounter) {
        this.email = email;
        this.recorddate = recorddate;
        this.logincounter = logincounter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccname() {
        return accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCatcoin() {
        return catcoin;
    }

    public void setCatcoin(int catcoin) {
        this.catcoin = catcoin;
    }

    public Date getRecorddate() {
        return recorddate;
    }

    public void setRecorddate(Date recorddate) {
        this.recorddate = recorddate;
    }

    public Integer getLogincounter() {
        return logincounter;
    }

    public void setLogincounter(int logincounter) {
        this.logincounter = logincounter;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    @XmlTransient
    public List<Petcat> getPetcatList() {
        return petcatList;
    }

    public void setPetcatList(List<Petcat> petcatList) {
        this.petcatList = petcatList;
    }

    @XmlTransient
    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object
    ) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Account[ email=" + email + " ]";
    }

}

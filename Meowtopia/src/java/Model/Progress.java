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
@Table(name = "PROGRESS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Progress.findAll", query = "SELECT p FROM Progress p"),
    @NamedQuery(name = "Progress.findByEmail", query = "SELECT p FROM Progress p WHERE p.progressPK.email = :email"),
    @NamedQuery(name = "Progress.findByTaskid", query = "SELECT p FROM Progress p WHERE p.progressPK.taskid = :taskid"),
    @NamedQuery(name = "Progress.findByTaskstatus", query = "SELECT p FROM Progress p WHERE p.taskstatus = :taskstatus"),
    @NamedQuery(name = "Progress.findByTaskcounter", query = "SELECT p FROM Progress p WHERE p.taskcounter = :taskcounter"),
    @NamedQuery(name = "Progress.deleteAllProgressByEmail", query = "DELETE FROM Progress p WHERE p.progressPK.email = :email"),
    @NamedQuery(name = "Progress.findByEmailTaskstatus", query = "SELECT p FROM Progress p WHERE p.taskstatus = :taskstatus AND p.progressPK.email = :email")})
public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProgressPK progressPK;
    @Basic(optional = false)
    @Column(name = "TASKSTATUS")
    private String taskstatus;
    @Column(name = "TASKCOUNTER")
    private Integer taskcounter;

    public Progress() {
    }

    public Progress(ProgressPK progressPK) {
        this.progressPK = progressPK;
    }

    public Progress(ProgressPK progressPK, String taskstatus) {
        this.progressPK = progressPK;
        this.taskstatus = taskstatus;
    }

    public Progress(String email, String taskid) {
        this.progressPK = new ProgressPK(email, taskid);
    }

    public Progress(String email, String taskid, String taskstatus, int taskcounter) {
        this.progressPK = new ProgressPK(email, taskid);
        this.taskstatus = taskstatus;
        this.taskcounter = taskcounter;
    }

    public ProgressPK getProgressPK() {
        return progressPK;
    }

    public void setProgressPK(ProgressPK progressPK) {
        this.progressPK = progressPK;
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }

    public Integer getTaskcounter() {
        return taskcounter;
    }

    public void setTaskcounter(Integer taskcounter) {
        this.taskcounter = taskcounter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (progressPK != null ? progressPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Progress)) {
            return false;
        }
        Progress other = (Progress) object;
        if ((this.progressPK == null && other.progressPK != null) || (this.progressPK != null && !this.progressPK.equals(other.progressPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Progress[ progressPK=" + progressPK + " ]";
    }

}

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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author User
 */
@Entity
@Table(name = "TASK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findByTaskid", query = "SELECT t FROM Task t WHERE t.taskid = :taskid"),
    @NamedQuery(name = "Task.findByTasktitle", query = "SELECT t FROM Task t WHERE t.tasktitle = :tasktitle"),
    @NamedQuery(name = "Task.findByReward", query = "SELECT t FROM Task t WHERE t.reward = :reward"),
    @NamedQuery(name = "Task.findByActiontype", query = "SELECT t FROM Task t WHERE t.actiontype = :actiontype"),
    @NamedQuery(name = "Task.findByActiontimes", query = "SELECT t FROM Task t WHERE t.actiontimes = :actiontimes"),
    @NamedQuery(name = "Task.findBySpecificitem", query = "SELECT t FROM Task t WHERE t.specificitem = :specificitem")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TASKID")
    private String taskid;
    @Basic(optional = false)
    @Column(name = "TASKTITLE")
    private String tasktitle;
    @Basic(optional = false)
    @Column(name = "REWARD")
    private int reward;
    @Column(name = "ACTIONTYPE")
    private String actiontype;
    @Column(name = "ACTIONTIMES")
    private Integer actiontimes;
    @Column(name = "SPECIFICITEM")
    private String specificitem;

    public Task() {
    }

    public Task(String taskid) {
        this.taskid = taskid;
    }

    public Task(String taskid, String tasktitle, int reward) {
        this.taskid = taskid;
        this.tasktitle = tasktitle;
        this.reward = reward;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTasktitle() {
        return tasktitle;
    }

    public void setTasktitle(String tasktitle) {
        this.tasktitle = tasktitle;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getActiontype() {
        return actiontype;
    }

    public void setActiontype(String actiontype) {
        this.actiontype = actiontype;
    }

    public Integer getActiontimes() {
        return actiontimes;
    }

    public void setActiontimes(Integer actiontimes) {
        this.actiontimes = actiontimes;
    }

    public String getSpecificitem() {
        return specificitem;
    }

    public void setSpecificitem(String specificitem) {
        this.specificitem = specificitem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskid != null ? taskid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.taskid == null && other.taskid != null) || (this.taskid != null && !this.taskid.equals(other.taskid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Task[ taskid=" + taskid + " ]";
    }
    
}

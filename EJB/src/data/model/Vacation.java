package data.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hervé on 17/10/2014.
 */
@Entity
public class Vacation implements Serializable{
    private int id;
    private Date begdate;
    private String begtime;
    private Date enddate;
    private String endtime;
    private String status;
    private Employee employee;
    private Employee manager;
    private Employee hr;
    private Set<Comment> comments = new HashSet<Comment>();

    public Vacation(){

    }
    @Id
    @SequenceGenerator(name = "vacationGenPk", sequenceName = "vacation_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacationGenPk")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "employeeId")
    public Employee getEmployee(){return employee;}

    public void setEmployee(Employee employee){this.employee = employee;}

    @ManyToOne
    @JoinColumn(name = "managerId")

    public Employee getManager(){return manager;}

    public void setManager(Employee manager){this.manager = manager;}

    @ManyToOne
    @JoinColumn(name = "hrId")
    public Employee getHr(){return hr;}

    public void setHr(Employee hr){this.hr = hr;}

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "vacation")
    @OrderBy("creadate")
    public Set<Comment> getComments(){return comments;}

    public void setComments(Set<Comment> comments) {this.comments = comments;}

    public void addComments(Comment comment){
        comments.add(comment);
    }
    public void removeComments(Comment comment){
        comments.remove(comment);
    }
    @Basic
    @Column(name = "begdate", nullable = false, insertable = true, updatable = true)
    public Date getBegdate() {
        return begdate;
    }

    public void setBegdate(Date begdate) {
        this.begdate = begdate;
    }

    @Basic
    @Column(name = "begtime", nullable = false, insertable = true, updatable = true, length = 20)
    public String getBegtime() {
        return begtime;
    }

    public void setBegtime(String begtime) {
        this.begtime = begtime;
    }

    @Basic
    @Column(name = "enddate", nullable = false, insertable = true, updatable = true)
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Basic
    @Column(name = "endtime", nullable = false, insertable = true, updatable = true, length = 20)
    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "status", nullable = false, insertable = true, updatable = true, length = 20)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vacation vacation = (Vacation) o;

        if (id != vacation.id) return false;
        if (begdate != null ? !begdate.equals(vacation.begdate) : vacation.begdate != null) return false;
        if (begtime != null ? !begtime.equals(vacation.begtime) : vacation.begtime != null) return false;
        if (enddate != null ? !enddate.equals(vacation.enddate) : vacation.enddate != null) return false;
        if (endtime != null ? !endtime.equals(vacation.endtime) : vacation.endtime != null) return false;
        if (status != null ? !status.equals(vacation.status) : vacation.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (begdate != null ? begdate.hashCode() : 0);
        result = 31 * result + (begtime != null ? begtime.hashCode() : 0);
        result = 31 * result + (enddate != null ? enddate.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}

package data.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Hervé on 17/10/2014.
 */
@Entity
public class Solde implements Serializable{
    private int id;
    private int number;
    private int year;
    private Employee employee;

    public Solde(){

    }

    @Id
    @SequenceGenerator(name = "soldeGenPk", sequenceName = "solde_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "soldeGenPk")
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number", nullable = false, insertable = true, updatable = true)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "year", nullable = false, insertable = true, updatable = true)
    public int getYear(){
        return year;
    }
    public void setYear(){
        this.year = year;
    }

    @ManyToOne
    @JoinColumn(name = "employeeId")
    public Employee getEmployee(){return employee;}

    public void setEmployee(Employee employee){this.employee = employee;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solde solde = (Solde) o;

        if (id != solde.id) return false;
        if (number != (solde.number)) return false;

        return true;
    }
}

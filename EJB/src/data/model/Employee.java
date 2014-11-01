package data.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hervé on 17/10/2014.
 */
@Entity
public class Employee {
    private int id;
    private String lastname;
    private String firstname;
    private Service service;
    private Employee manager ;
    private Set<Employee> associates ;

    public Employee(){

    }

    public Employee(String firstName, String lastName){
        this.firstname = firstName ;
        this.lastname = lastName ;
    }

    public Employee(Service service, Employee manager, String firstname, String lastname){
        this.service = service ;
        this.manager = manager ;
        this.firstname = firstname ;
        this.lastname = lastname ;
    }

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lastname", nullable = false, insertable = true, updatable = true, length = 30)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "firstname", nullable = false, insertable = true, updatable = true, length = 30)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceId")
    public Service getService(){
        return service;
    }

    public void setService(Service service){
        this.service = service;
    }

    @ManyToOne
    @Column(name = "managerId", nullable = true, insertable = true, updatable = true, length = 35)
    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @OneToMany
    @Column(name="id", nullable = true, insertable = true,updatable = true, length = 35)
    public Set<Employee> getAssociates() {
        return associates;
    }

    public void setAssociates(Set<Employee> associates) {
        this.associates = associates;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (firstname != null ? !firstname.equals(employee.firstname) : employee.firstname != null) return false;
        if (lastname != null ? !lastname.equals(employee.lastname) : employee.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        return result;
    }
}

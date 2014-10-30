package data.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hervé on 17/10/2014.
 */
@Entity
public class Service {
    private int id;
    private String name;
    private Set<Employee> employees;

    public Service(){

    }

    public Service(String name){
        this.name = name ;
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
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "service")
    public Set<Employee> getEmployees(){
        return employees;
    }

    public void setEmployees(Set<Employee> employees){
        this.employees = employees ;
    }

    public void addEmployee(Employee emp){
        this.employees.add(emp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (id != service.id) return false;
        if (name != null ? !name.equals(service.name) : service.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

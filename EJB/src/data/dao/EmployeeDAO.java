package data.dao;

import data.model.Employee;
import data.model.Service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Manfred on 29/10/2014.
 */

@Stateless
@LocalBean
public class EmployeeDAO {
    @PersistenceContext
    EntityManager entityManager ;

    public EmployeeDAO(){

    }

    public void create(Employee employee){
        try{
            entityManager.persist(employee);
        }catch(Exception e){
        }
    }

    public void remove(Employee employee){
        try{
            entityManager.remove(employee);
        }catch(Exception e){

        }
    }

    public void update(Employee employee){
        if(employee != null){
            entityManager.merge(employee);
        }
    }

    public List<Employee> getAll(){
        Query query = this.entityManager.createQuery("SELECT employee FROM Employee employee");
        List l = query.getResultList();
        return (List<Employee>) l;
    }

    public List<Employee> getEmployeesByService(Service service){
        String serviceName = service.getName();
        Query query = entityManager.createQuery("SELECT employee FROM Employee employee " +
                "WHERE employee.service.name = :serviceName");
        List l = query.getResultList();

        return (List<Employee>) l;
    }

    public List<Employee> getEmployeesByService(String serviceName){
        Query query = entityManager.createQuery("SELECT employee FROM Employee employee " +
                "WHERE employee.service.name = :serviceName");
        List l = query.getResultList();

        return (List<Employee>) l;
    }

    public Employee getManager(Employee employee){
        return employee.getManager();
    }


    public Employee find(int id){
        return entityManager.find(Employee.class, id);
    }

    public Employee find(String firstName, String lastName){
        Query query = entityManager.createQuery("SELECT employee FROM Employee employee " +
                "WHERE employee.firstname = :firstName AND employee.lastname = :lastName");

        return (Employee)query.getSingleResult();
    }
}

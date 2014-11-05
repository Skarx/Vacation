package service;

import data.model.Employee;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Manfred on 05/11/2014.
 */
@Remote
public interface ILogin {

    public List<Employee> getEmployees();
    public Employee getEmployee(String firstName, String lastName);
}

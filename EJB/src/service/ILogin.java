package service;

import data.model.Employee;
import data.model.Service;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Manfred on 05/11/2014.
 */
@Remote
public interface ILogin {

    public List<Service> getServices();
    public List<Employee> getEmployees();
    public Employee getEmployee(int id);
    public boolean isManager(int idEmployee);
    public boolean isHR(int idEmployee);
}

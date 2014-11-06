package service;

import data.dao.EmployeeDAO;
import data.model.Employee;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manfred on 05/11/2014.
 */
@Stateless
public class ServiceLogin implements ILogin {

    @EJB
    private EmployeeDAO employeeDAO ;

    public ServiceLogin(){
        this.employeeDAO = new EmployeeDAO();
    }

    @Override
    public List<Employee> getEmployees() {
        return this.employeeDAO.getAll();
    }

    @Override
    public Employee getEmployee(String firstName, String lastName) {
        //return this.employeeDAO.find(firstName, lastName);
        return new Employee();
    }
}

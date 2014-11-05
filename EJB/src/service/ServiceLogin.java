package service;

import data.dao.EmployeeDAO;
import data.model.Employee;

import javax.ejb.EJB;
import java.util.List;

/**
 * Created by Manfred on 05/11/2014.
 */
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
}

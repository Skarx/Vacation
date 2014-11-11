package service;

import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.model.Employee;
import data.model.Service;

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

    @EJB
    private ServiceDAO serviceDAO ;

    public ServiceLogin(){
        this.employeeDAO = new EmployeeDAO();
    }

    @Override
    public List<Service> getServices() {
        return this.serviceDAO.getAll() ;
    }

    @Override
    public List<Employee> getEmployees() {
        return this.employeeDAO.getAll();
    }

    @Override
    public Employee getEmployee(int id) {
        return this.employeeDAO.find(id) ;
    }

    @Override
    public boolean isManager(int idEmployee) {
        Employee employee = this.employeeDAO.find(idEmployee);
        if(employee.getAssociates().size() > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isHR(int idEmployee) {
        Employee employee = this.employeeDAO.find(idEmployee);
        if(employee.getService().getName().equals("RH")){
            return true;
        }else{
            return false;
        }
    }
}

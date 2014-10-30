import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.model.Employee;
import data.model.Service;

/**
 * Created by Manfred on 30/10/2014.
 */
public class test_dao {
    public static void main(String[] args){
        test_serviceDAO() ;
        test_employeeDAO() ;
        test_vacationDAO() ;
        test_commentDAO() ;
    }

    private static void test_serviceDAO(){
        // make the DAO
        ServiceDAO serviceDAO = new ServiceDAO();

        // Create a new service
        Service service = new Service("Coucou");
        serviceDAO.create(service);

        // Trying to create an existing service
        serviceDAO.create(service);

        // Remove an existing service
        serviceDAO.remove(service);

        // Remove a service that does not exist
        serviceDAO.remove(service);

        // Change a service name
        Service service2 = new Service("Prodction");
        serviceDAO.create(service2);
        service2.setName("Production");
        serviceDAO.update(service2);

        // Get a service by id
        serviceDAO.find(2);

        // Get a service by name
        serviceDAO.find("Production");
    }

    private static void test_employeeDAO() {
        // make the DAO
        EmployeeDAO employeeDAO = new EmployeeDAO() ;
        ServiceDAO serviceDAO = new ServiceDAO();

        // create new employee
        Employee employee = new Employee("Manfred", "CHEBLI");
        Employee employee2 = new Employee("Hervé", "CARRON");
        Employee employee3 = new Employee("Jean", "JACQUES");
        employeeDAO.create(employee);
        employeeDAO.create(employee2);
        employeeDAO.create(employee3);

        // create employee that already exists
        Employee e = new Employee("Manfred", "CHEBLI");
        employeeDAO.create(e);

        // remove employee
        employeeDAO.remove(employee2);

        // remove employee that does not exist
        employeeDAO.remove(employee2);

        // change employee names
        employee3.setFirstname("John");
        employee3.setLastname("JACK");
        employeeDAO.update(employee3);

        // change employee's service
        Service service1 = new Service("Coucou");
        Service service2 = new Service("Production");
        employee.setService(service1);
        service2.addEmployee(employee3);
        serviceDAO.create(service1);
        serviceDAO.create(service2);

        employee.setService(service2);
        employeeDAO.update(employee);

        // get an employee by id
        employeeDAO.find(1);

        // get an employee by name
        employeeDAO.find("John", "Jacques");
    }

    private static void test_vacationDAO(){
        // make DAO

        // create

        // create already exist

        // remove

        // remove does not exist

        // update

        // find by id

    }

    private static void test_commentDAO(){
        // make DAO

        // create

        // create already exist

        // remove

        // remove does not exist

        // update

        // find by id
    }
}

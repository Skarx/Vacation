import data.model.Employee;
import data.model.Service;
import data.model.Vacation;
import service.IEmployee;
import service.ILogin;
import service.IValidator;
import utils.ServicesLocator;
import utils.ServicesLocatorException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manfred on 06/11/2014.
 */
public class PlanningServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // recuperation du service
        IEmployee serviceAsk = null ;
        ILogin serviceLogin = null ;
        try {
            serviceAsk = (IEmployee) ServicesLocator.getInstance().getRemoteInterface("ServiceAsk");
            serviceLogin = (ILogin) ServicesLocator.getInstance().getRemoteInterface("ServiceLogin");
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }
        // recuperation de l'employee
        HttpSession session = request.getSession(true);
        Employee employee = employee = (Employee) session.getAttribute("currentSessionUser");

        String accessPoint = request.getParameter("accessPoint");
        List<Vacation> l = new ArrayList<Vacation>();

        if(accessPoint.equals("service")){
            // recuperation du planning d'un service
            String serviceName = request.getParameter("service");
            l = serviceAsk.getVacationsByService(serviceName, null, 0);
        }else if(accessPoint.equals("employee")){
            int employeeId = Integer.parseInt(request.getParameter("employee"));
            // recuperation de l'employee
            l = serviceAsk.getVacationsByEmployee(employeeId, null, 0);
        }else if(accessPoint.equals("associates")){
            // recuperation des conges des collaborateurs
            l = serviceAsk.getVacationsByAssociates(employee.getId(), null, 0);
        }else{
            // recuperation de tous les conges
            l = serviceAsk.getAllVacations();
        }

        // recuperation de la liste des employes
        List<Employee> employees = serviceLogin.getEmployees() ;
        request.setAttribute("employees", employees);
        // recuperation de la liste des services
        List<Service> services = serviceLogin.getServices() ;
        request.setAttribute("services", services);

        request.setAttribute("vacations", l);
        request.getRequestDispatcher("planning.jsp").forward(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // recuperation du service
        IEmployee serviceAsk = null ;
        ILogin serviceLogin = null ;
        try {
            serviceAsk = (IEmployee) ServicesLocator.getInstance().getRemoteInterface("ServiceAsk");
            serviceLogin = (ILogin) ServicesLocator.getInstance().getRemoteInterface("ServiceLogin");
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }
        // recuperation des conges
        List<Vacation> l = serviceAsk.getAllVacations();
        request.setAttribute("vacations", l);

        // recuperation de la liste des employes
        List<Employee> employees = serviceLogin.getEmployees() ;
        request.setAttribute("employees", employees);
        // recuperation de la liste des services
        List<Service> services = serviceLogin.getServices() ;
        request.setAttribute("services", services);


        request.getRequestDispatcher("planning.jsp").forward(request, response);
    }
}

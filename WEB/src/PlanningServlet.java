import data.model.Employee;
import data.model.Vacation;
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

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // recuperation de l'employee
        // recuperation de l'employe
        HttpSession session = request.getSession(true);
        Employee employee = employee = (Employee) session.getAttribute("currentSessionUser");

        // recuperation de tous les conges
        try {
            IValidator serviceValidate = (IValidator) ServicesLocator.getInstance().getRemoteInterface("ServiceValidate");
            List<Vacation> l = serviceValidate.getMyAssociatesPendingVacations(employee);
            System.out.println(l);
            request.setAttribute("vacations", l);
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("planning.jsp").forward(request, response);
    }
}

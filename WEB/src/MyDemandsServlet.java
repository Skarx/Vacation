import data.model.Employee;
import data.model.Service;
import data.model.Vacation;
import service.IEmployee;
import service.ILogin;
import service.IValidator;
import utils.ServicesLocator;
import utils.ServicesLocatorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Manfred on 11/11/2014.
 */
//TODO Handle NPE quand l'utilisateur n'a aucune demande
public class MyDemandsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recuperation des services
        IEmployee serviceAsk = null ;
        IValidator serviceValidate = null ;
        try {
            serviceAsk = (IEmployee) ServicesLocator.getInstance().getRemoteInterface("ServiceAsk");
            serviceValidate= (IValidator) ServicesLocator.getInstance().getRemoteInterface("ServiceValidate");
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }

        // recuperation de l'employee
        HttpSession session = request.getSession(true);
        Employee employee = (Employee) session.getAttribute("currentSessionUser");

        // recuperation du conges selectionne
        int vacationId = Integer.parseInt(request.getParameter("vacationId"));
        Vacation vacation = serviceValidate.getVacationById(vacationId);

        // recuperation du commentaire eventuel
        String comment = request.getParameter("comment");

        // changement de l'etat
        serviceAsk.cancelVacation(vacation, comment);

        // redirection sur la meme page
        response.sendRedirect("/Vacations/myDemands");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recuperation du service
        IEmployee serviceAsk = null ;
        try {
            serviceAsk = (IEmployee) ServicesLocator.getInstance().getRemoteInterface("ServiceAsk");
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }

        // recuperation de l'employee
        HttpSession session = request.getSession(true);
        Employee employee = employee = (Employee) session.getAttribute("currentSessionUser");

        // recuperation des conges pour l'employee connecte
        List<Vacation> l = serviceAsk.getVacationsByEmployee(employee.getId(), null, 0);
        request.setAttribute("vacations", l);

        request.getRequestDispatcher("myDemands.jsp").forward(request, response);
    }
}

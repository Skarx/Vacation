import data.model.Employee;
import data.model.Vacation;
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
public class ValidateHRServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Employee employee = (Employee) session.getAttribute("currentSessionUser");

        IValidator serviceValidate = null ;
        try {
            serviceValidate = (IValidator) ServicesLocator.getInstance().getRemoteInterface("ServiceValidate");
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }

        // recuperation du conges selectionne
        int vacationId = Integer.parseInt(request.getParameter("vacationId"));
        Vacation vacation = serviceValidate.getVacationById(vacationId);

        // recuperation du commentaire eventuel
        String comment = request.getParameter("comment");

        // recuperation de la reponse
        String validate = request.getParameter("validate");
        if(validate.equals("accept")){
            // modification du status du conges
            serviceValidate.validateVacation(vacation, employee, comment);
        }else if(validate.equals("refuse")){
            // modification du status du conges
            serviceValidate.refuseVacation(vacation, employee, comment);
        }

        // redirection sur la meme page
        response.sendRedirect("/Vacations/validateHR");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recuperation de l'employee
        HttpSession session = request.getSession(true);
        Employee employee = (Employee) session.getAttribute("currentSessionUser");

        IValidator serviceValidate = null ;
        try {
            serviceValidate = (IValidator) ServicesLocator.getInstance().getRemoteInterface("ServiceValidate");
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }

        // recuperation des conges dans le status VALITATEDMGR
        List<Vacation> validatedMgrVacations = serviceValidate.getVacationsValidatedMgr();

        request.setAttribute("vacations", validatedMgrVacations);
        request.getRequestDispatcher("validateHR.jsp").forward(request, response);
    }
}

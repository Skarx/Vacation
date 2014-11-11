import data.model.Employee;
import data.model.Vacation;
import service.IEmployee;
import service.IValidator;
import utils.ServicesLocator;
import utils.ServicesLocatorException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Manfred on 09/11/2014.
 */
public class ValidateMgrServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
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
        response.sendRedirect("/Vacations/validateMgr");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // recuperation de l'employee
        HttpSession session = request.getSession(true);
        Employee employee = (Employee) session.getAttribute("currentSessionUser");

        IValidator serviceValidate = null ;
        try {
            serviceValidate = (IValidator) ServicesLocator.getInstance().getRemoteInterface("ServiceValidate");
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }

        // recuperation des conges dans le status PENDING des collaborateurs
        List<Vacation> pendingVacations = serviceValidate.getMyAssociatesPendingVacations(employee);

        request.setAttribute("vacations", pendingVacations);
        request.getRequestDispatcher("validateMgr.jsp").forward(request, response);
    }
}
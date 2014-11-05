
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import service.*;
import utils.ServicesLocator;

/**
 * Created by Manfred on 04/11/2014.
 */
@WebServlet(name="NewVacationServlet", urlPatterns = "/newVacation")
public class NewVacationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recuperation des params de la requête
        String begDate_str = request.getParameter("begDate");
        String endDate_str = request.getParameter("endDate");
        String begTime = request.getParameter("begTime");
        String endTime = request.getParameter("endTime");
        String comment = request.getParameter("comment");

        // on retourne une erreur si les parametres ne sont pas tous presents

        Date begDate = null ;
        Date endDate = null ;

        // conversion des string en date
        try {
            begDate = convertStringToDate(begDate_str);
            endDate = convertStringToDate(endDate_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // recuperation de l'employe

        try {
            IEmployee serviceEmployee = (IEmployee)ServicesLocator.getInstance().getRemoteInterface("serviceAsk");
            //serviceEmployee.newVacation(begTime, endDate, begTime, endTime, comment, );

        }catch(Exception e){
            // TODO preparer les exceptions de newVacation
        }

        request.setAttribute("message", "Votre demande de congé a bien été créée.");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hello = "coucou" ;
        request.setAttribute("hello", hello);
        request.getRequestDispatcher("newVacation.jsp").forward(request, response);
    }

    private Date convertStringToDate(String date_str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(date_str);
        return date ;
    }
}


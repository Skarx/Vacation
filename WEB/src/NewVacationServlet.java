
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.model.DayTime;
import data.model.Employee;
import service.*;
import utils.ServicesLocator;
import utils.ServicesLocatorException;

/**
 * Created by Manfred on 04/11/2014.
 */
public class NewVacationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recuperation des params de la requête
        String begDate_str = request.getParameter("begDate");
        String endDate_str = request.getParameter("endDate");
        String begTime_str = request.getParameter("begTime");
        String endTime_str = request.getParameter("endTime");
        String comment = request.getParameter("comment");

        // on retourne une erreur si les parametres ne sont pas tous presents

        Date begDate = null ;
        Date endDate = null ;

        // conversion des string en Date
        try {
            begDate = convertStringToDate(begDate_str);
            endDate = convertStringToDate(endDate_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // conversion des string en DayTime
        DayTime begTime = null ;
        DayTime endTime = null ;
        try{
            begTime = convertStringToDayTime(begTime_str);
            endTime = convertStringToDayTime(endTime_str);
        }catch(BadDayTimeException ex){
            // TODO handle exception
        }


        // recuperation de l'employe
        HttpSession session = request.getSession(true);
        Employee employee = employee = (Employee) session.getAttribute("currentSessionUser"); ;

        try {
            IEmployee serviceEmployee = (IEmployee)ServicesLocator.getInstance().getRemoteInterface("ServiceAsk");
            Employee manager = employee.getManager() ;
            serviceEmployee.newVacation(begDate, endDate, begTime, endTime, comment, employee, manager);
        }catch(Exception e){
            // TODO preparer les exceptions de newVacation
        }

        request.setAttribute("message", "Votre demande de congé a bien été créée.");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Employee employee = employee = (Employee) session.getAttribute("currentSessionUser"); ;
        int solde = 0 ;
        try{
            // recuperation du solde des conges de l'employe
            IEmployee serviceAsk = (IEmployee)ServicesLocator.getInstance().getRemoteInterface("ServiceAsk");
            solde = serviceAsk.checkVacations(employee);
        } catch (ServicesLocatorException e) {
            e.printStackTrace();
        }
        request.setAttribute("solde", solde);
        request.getRequestDispatcher("newVacation.jsp").forward(request, response);
    }

    private Date convertStringToDate(String date_str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(date_str);
    }

    private DayTime convertStringToDayTime(String dayTime_str) throws BadDayTimeException{
        if(dayTime_str.equals(DayTime.MORNING.toString())){
            return DayTime.MORNING ;
        }else if(dayTime_str.equals(DayTime.AFTERNOON.toString())){
            return DayTime.AFTERNOON ;
        }else if(dayTime_str.equals(DayTime.ALL_DAY.toString())){
            return DayTime.ALL_DAY ;
        }else {
            throw new BadDayTimeException("Impossible de déterminer le moment de la journée choisi : " + dayTime_str);
        }
    }
}

class BadDayTimeException extends Exception{
    public BadDayTimeException(){
    }
    public BadDayTimeException(String message){
        super(message);
    }
}


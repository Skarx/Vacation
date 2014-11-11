
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

        // conversion des string en Date
        Date begDate = null ;
        Date endDate = null ;
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
            checkDayTime(begTime, endTime, begDate, endDate);
            begTime = convertStringToDayTime(begTime_str);
            endTime = convertStringToDayTime(endTime_str);
        }catch(BadDayTimeException ex){
            // TODO handle exception
        }
        // recuperation de l'employe
        HttpSession session = request.getSession(true);
        Employee employee = employee = (Employee) session.getAttribute("currentSessionUser");

        // creation de la demande de conges
        try {
            IEmployee serviceEmployee = (IEmployee)ServicesLocator.getInstance().getRemoteInterface("ServiceAsk");
            //TODO refaire avec les services
            //TODO refaire avec les services
            if(checkNumbersOfDay(begDate, endDate)> 0)
                throw new BadDateException();
            Employee manager = employee.getManager() ;
            serviceEmployee.newVacation(begDate, endDate, begTime, endTime, comment, employee, manager);
        }catch(Exception e){
            // TODO preparer les exceptions de newVacation
        }

        request.setAttribute("message", "Votre demande de congé a bien été créée.");
        response.sendRedirect("/Vacations/");
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
        }
        else {
            throw new BadDayTimeException("Impossible de déterminer le moment de la journée choisi : " + dayTime_str);
        }
 }
    private void checkDayTime(DayTime startTime, DayTime endTime, Date startDate, Date endDate) throws BadDayTimeException {
            if(startDate.equals(endDate) && startTime.equals(DayTime.AFTERNOON) && endTime.equals(DayTime.MORNING))
                throw new BadDayTimeException();
    }


    private int checkNumbersOfDay(Date begDate, Date endDate) throws BadDateException {
        if(begDate.after(endDate))
            throw new BadDateException();
        Calendar begCalendar = new GregorianCalendar();
        Calendar endCalendar = new GregorianCalendar();
        if(begCalendar.get(Calendar.YEAR)!= endCalendar.get(Calendar.YEAR))
            throw new BadDateException();
        begCalendar.setTime(begDate);
        endCalendar.setTime(endDate);
        // jours fériés
        Calendar jourDelAn = new GregorianCalendar();
        Calendar feteDuTravail = new GregorianCalendar();
        Calendar feteVictoire = new GregorianCalendar();
        Calendar feteNationale = new GregorianCalendar();
        Calendar armistice = new GregorianCalendar();
        // fêtes religieuses
        Calendar paque = new GregorianCalendar();
        Calendar ascension = new GregorianCalendar();
        Calendar pentecote = new GregorianCalendar();
        Calendar assomption = new GregorianCalendar();
        Calendar toussaint = new GregorianCalendar();
        Calendar noel = new GregorianCalendar();
        int nbDay=0;

        paque = paqueDay(begCalendar.get(Calendar.YEAR));
        //Initialisation des jours fériés
        jourDelAn.set(begCalendar.get(Calendar.YEAR), 1, 1);
        feteDuTravail.set(begCalendar.get(Calendar.YEAR), 5, 1);
        feteVictoire.set(begCalendar.get(Calendar.YEAR), 5, 8);
        feteNationale.set(begCalendar.get(Calendar.YEAR), 7, 14);
        armistice.set(begCalendar.get(Calendar.YEAR), 11, 11);
        paque.add(Calendar.DAY_OF_MONTH, 1);
        ascension.add(Calendar.DAY_OF_MONTH, 38);
        pentecote.add(Calendar.DAY_OF_MONTH, 49);
        assomption.set(begCalendar.get(Calendar.YEAR), 8, 15);
        toussaint.set(begCalendar.get(Calendar.YEAR), 11, 1);
        noel.set(begCalendar.get(Calendar.YEAR), 12, 25);
        long diff = Math.abs(endDate.getTime() - begDate.getTime());
        long numberOfDay = (long)diff/86400000;
        if(numberOfDay != 0){
            for(int i=0;i<numberOfDay;i++){
                if(begCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && begCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                        && !begCalendar.equals(noel)
                        && !begCalendar.equals(toussaint)
                        && !begCalendar.equals(assomption)
                        && !begCalendar.equals(pentecote)
                        && !begCalendar.equals(ascension)
                        && !begCalendar.equals(paque)
                        && !begCalendar.equals(armistice)
                        && !begCalendar.equals(feteNationale)
                        && !begCalendar.equals(feteVictoire)
                        && !begCalendar.equals(feteDuTravail)
                        && !begCalendar.equals(jourDelAn))
                    nbDay++;
                begCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return nbDay;
    }
    private Calendar paqueDay(int year){
        int a = year/19;
        int b = year/4;
        int c = year/7;
        int d = ((19*a) + 24)/30;
        int e = (((2*b) + (4*c) + (6*d) + 5)/7);
        int resultMars = 22+d+e;
        int resultAvril = d+e-9;
        Calendar paque = new GregorianCalendar();
        paque.set(year, 4, resultAvril);
        if(resultMars>31) {
            paque.set(year, 4, resultAvril);
            return paque;
        }
        else {
            paque.set(year, 5, resultMars);
            return paque;
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
class BadDateException extends Exception{
    public BadDateException(){
    }
    public BadDateException(String message){
        super(message);
    }
}

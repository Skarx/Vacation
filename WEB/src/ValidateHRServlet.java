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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        Date dimPaque;
        Calendar ascension = new GregorianCalendar();
        Calendar pentecote = new GregorianCalendar();
        Calendar assomption = new GregorianCalendar();
        Calendar toussaint = new GregorianCalendar();
        Calendar noel = new GregorianCalendar();
        int nbDay=0;
        int year = begCalendar.get(Calendar.YEAR);
        dimPaque = paqueDay(begCalendar.get(Calendar.YEAR));
        //Initialisation des jours fériés
        jourDelAn.set(year, 0, 1);
        jourDelAn.getTime();
        feteDuTravail.set(begCalendar.get(Calendar.YEAR), 5 - 1, 1);
        feteDuTravail.getTime();
        feteVictoire.set(begCalendar.get(Calendar.YEAR), 5 - 1, 8);
        feteVictoire.getTime();
        feteNationale.set(begCalendar.get(Calendar.YEAR), 7 - 1, 14);
        feteNationale.getTime();
        armistice.set(begCalendar.get(Calendar.YEAR), 11 - 1, 11);
        armistice.getTime();
        paque.setTime(dimPaque);
        paque.add(Calendar.DAY_OF_MONTH, 1);
        paque.getTime();
        ascension.setTime(ascension(dimPaque));
        ascension.getTime();
        pentecote.setTime(pentecote(dimPaque));
        pentecote.getTime();
        assomption.set(begCalendar.get(Calendar.YEAR), 8-1, 15);
        assomption.getTime();
        toussaint.set(begCalendar.get(Calendar.YEAR), 11 - 1, 1);
        toussaint.getTime();
        noel.set(begCalendar.get(Calendar.YEAR), 12-1, 25);
        noel.getTime();
        long diff = Math.abs(endDate.getTime() - begDate.getTime());
        long numberOfDay = (long)diff/86400000;
        if(numberOfDay != 0){
            for(int i=0;i<=numberOfDay;i++){
                if(begCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                        && begCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                        && !testCalendar(begCalendar, noel)
                        && !testCalendar(begCalendar,toussaint)
                        && !testCalendar(begCalendar, assomption)
                        && !testCalendar(begCalendar, pentecote)
                        && !testCalendar(begCalendar, ascension)
                        && !testCalendar(begCalendar, paque)
                        && !testCalendar(begCalendar, armistice)
                        && !testCalendar(begCalendar, feteNationale)
                        && !testCalendar(begCalendar, feteVictoire)
                        && !testCalendar(begCalendar, feteDuTravail)
                        && !testCalendar(begCalendar, jourDelAn))
                    nbDay++;
                begCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return nbDay;
    }
    private Date paqueDay(int year){
        int a = year%19;
        int b = year%4;
        int c = year%7;
        int d = ((19*a) + 24)%30;
        int e = (((2*b) + (4*c) + (6*d) + 5)%7);
        int resultMars = 22+d+e;
        int resultAvril = d+e-9;
        Calendar paque = new GregorianCalendar();
        if(resultMars>31) {
            paque.set(year, 4 - 1, resultAvril);
            return paque.getTime();
        }
        else {
            paque.set(year, 5 - 1, resultMars);
            return paque.getTime();
        }
    }
    private Date pentecote (Date paque){
        Calendar pentecote = new GregorianCalendar();
        pentecote.setTime(paque);
        pentecote.add(Calendar.DAY_OF_MONTH, 50);
        return pentecote.getTime();
    }
    private Date ascension (Date paque){
        Calendar ascension = new GregorianCalendar();
        ascension.setTime(paque);
        ascension.add(Calendar.DAY_OF_MONTH, 39);
        return ascension.getTime();
    }
    private boolean testCalendar(Calendar c1, Calendar c2){
        if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
            if(c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                if(c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH))
                    return true;
        return false;
    }
}

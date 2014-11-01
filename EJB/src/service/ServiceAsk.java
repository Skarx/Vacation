package service;

import data.dao.CommentDAO;
import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.dao.VacationDAO;
import data.model.*;

import javax.ejb.EJB;
import java.sql.Date;

/**
 * Created by Manfred on 01/11/2014.
 */
public class ServiceAsk implements IEmployee{

    @EJB
    private EmployeeDAO employeeDAO ;

    @EJB
    private ServiceDAO serviceDAO ;

    @EJB
    private VacationDAO vacationDAO ;

    @EJB
    private CommentDAO commentDAO ;

    public ServiceAsk(){
        this.employeeDAO = new EmployeeDAO() ;
        this.serviceDAO = new ServiceDAO() ;
        this.vacationDAO = new VacationDAO() ;
        this.commentDAO = new CommentDAO() ;
    }

    @Override
    public Vacation newVacation(Date begDate, Date endDate, DayTime begTime, DayTime endTime, Comment comment, Employee hr, Employee manager, Status status) {
        return null;
    }

    @Override
    public Vacation cancelVacation(Vacation vacation) {
        return null;
    }

    @Override
    public void makePlanning() {

    }

    @Override
    public void checkVacations() {

    }

    @Override
    public void getVacationsByEmployee() {

    }
}

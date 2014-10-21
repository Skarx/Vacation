package service;

import data.dao.CommentDAO;
import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.dao.VacationDAO;
import data.model.*;

import javax.ejb.EJB;
import java.sql.Date;
import java.util.List;

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
        Vacation nvVacation = new Vacation();
        nvVacation.setBegdate(begDate);
        nvVacation.setBegtime(begTime.toString());
        nvVacation.setEnddate(endDate);
        nvVacation.setBegtime(endTime.toString());
        nvVacation.addComments(comment);
        nvVacation.setHr(hr);
        nvVacation.setManager(manager);
        nvVacation.setStatus(status.toString());
        return vacationDAO.persist(nvVacation);
    }

    @Override
    public Vacation cancelVacation(Vacation vacation) {
        vacation.setStatus(Status.PENDINGCANCEL.toString());
        return vacationDAO.update(vacation);
    }

    @Override
    public void makePlanning(Employee employee) {
        List<Vacation> vacations = vacationDAO.findByManager(employeeDAO.getManager(employee));
    }


    @Override
    public void checkVacations() {

    }

    @Override
    public List<Vacation> getVacationsByEmployee(Employee emp) {
        return vacationDAO.findByEmployee(emp);
    }

    @Override
    public List<Vacation> getVacationByEmployeeAndYear(Employee emp, int year){
        return vacationDAO.findByEmployeeAndYear(emp, year);
    }
    @Override
    public List<Vacation> getVacationByEmployeeAndStatus(Employee employee, Status status){
        return vacationDAO.findByEmployeeAndStatus(employee, status);
    }
    @Override
    public List<Vacation> getVacationByManagerAndStatus(Employee manager, Status status){
        return vacationDAO.findByManagerAndStatus(manager, status);
    }
    @Override
    public List<Vacation> getVacationByHrAndStatus(Employee hr, Status status){
        return vacationDAO.findByHrAndStatus(hr, status);
    }

}

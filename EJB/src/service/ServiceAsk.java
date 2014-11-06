package service;

import data.dao.CommentDAO;
import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.dao.VacationDAO;
import data.model.*;

import javax.ejb.EJB;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    public Vacation newVacation(Date begDate, Date endDate, DayTime begTime, DayTime endTime, Comment comment,
                                Employee hr, Employee manager) {
        Vacation nvVacation = new Vacation();
        nvVacation.setBegdate(begDate);
        nvVacation.setBegtime(begTime.toString());
        nvVacation.setEnddate(endDate);
        nvVacation.setBegtime(endTime.toString());
        nvVacation.addComments(comment);
        nvVacation.setHr(hr);
        nvVacation.setManager(manager);
        nvVacation.setStatus(Status.PENDING.toString());
        return vacationDAO.persist(nvVacation);
    }

    @Override
    public void makePlanning(Employee employee) {
        //List<Vacation> vacations = vacationDAO.findByManager(employeeDAO.getManager(employee));
    }


    @Override
    public int checkVacations(Employee employee) {
        return employee.getNbVacation();
    }

    @Override
    public List<Vacation> getMyAssociatesPendingVacations(Employee employee) {
        List<Vacation> vacationList = null;
        /*for(Employee emp: employeeDAO.getEmployeesByService(employee.getService())){
            if(!emp.equals(employee))
                vacationList.addAll(vacationDAO.findByEmployee(emp));
        }*/
        return vacationList;
    }
    @Override
    public List<Vacation> getMyVacations(Employee employee){
        return vacationDAO.findByEmployee(employee);
    }
    @Override
    public Vacation cancelPendingVacation(Vacation vacation){
        vacation.setStatus(Status.CANCELLED.toString());
        return vacationDAO.update(vacation);
    }

    @Override
    public Vacation cancelVacation(Vacation vacation) {
        vacation.setStatus(Status.PENDINGCANCEL.toString());
        return vacationDAO.update(vacation);
    }

}

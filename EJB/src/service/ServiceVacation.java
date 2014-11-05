package service;

import data.dao.CommentDAO;
import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.dao.VacationDAO;
import data.model.*;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Hervé on 20/10/2014.
 */
@Stateless
@LocalBean
public class ServiceVacation {
    @EJB
    private CommentDAO commentDAO;
    @EJB
    private EmployeeDAO employeeDAO;
    @EJB
    private ServiceDAO serviceDAO;
    @EJB
    private VacationDAO vacationDAO;

    public ServiceVacation(){}

    public int newVacation(Date begDate, Date endDate, DayTime begTime, DayTime endTime, Set<Comment> comments, Employee hr, Employee manager, Status status ){
        Vacation nvVacation = new Vacation();
        nvVacation.setBegdate(begDate);
        nvVacation.setBegtime(begTime.toString());
        nvVacation.setEnddate(endDate);
        nvVacation.setBegtime(endTime.toString());
        nvVacation.setComments(comments);
        nvVacation.setHr(hr);
        nvVacation.setManager(manager);
        nvVacation.setStatus(status.toString());
        return vacationDAO.persist(nvVacation).getId();
    }
    public int newVacation(Date begDate, Date endDate, DayTime begTime, DayTime endTime, Comment comment, Employee hr, Employee manager, Status status ){
        Vacation nvVacation = new Vacation();
        nvVacation.setBegdate(begDate);
        nvVacation.setBegtime(begTime.toString());
        nvVacation.setEnddate(endDate);
        nvVacation.setBegtime(endTime.toString());
        nvVacation.addComments(comment);
        nvVacation.setHr(hr);
        nvVacation.setManager(manager);
        nvVacation.setStatus(status.toString());
        return vacationDAO.persist(nvVacation).getId();
    }
    public Vacation getVacation(int idVacation){
        return vacationDAO.findById(idVacation);
    }
    public void updateVacation(int idVacation, Date begDate, DayTime begTime, Date endDate, DayTime endTime, Set<Comment> comments, Employee hr, Employee manager, Status status ){
        Vacation vac = vacationDAO.findById(idVacation);
        vac.setEnddate(endDate);
        vac.setEndtime(endTime.toString());
        vac.setBegdate(begDate);
        vac.setBegtime(begTime.toString());
        vac.setComments(comments);
        vac.setHr(hr);
        vac.setManager(manager);
        vac.setStatus(status.toString());
        vacationDAO.update(vac);
    }
    public void deleteVacation(int idVacation){
        vacationDAO.remove(vacationDAO.findById(idVacation));
    }
    public List<Vacation> listOfVacationByManager(int idManager){
        return vacationDAO.findByManager(employeeDAO.find(idManager));
    }
    public List<Vacation> listOfVacationByHr(int idHr){
        return vacationDAO.findByHr(employeeDAO.find(idHr));
    }
    public List<Vacation> listOfVacationByEmployee(int idEmployee){
        return vacationDAO.findByEmployee(employeeDAO.find(idEmployee));
    }
    public List<Vacation> listOfVacationByManagerAndStatus(int idEmployee, Status status){
        return vacationDAO.findByEmployeeAndStatus(employeeDAO.find(idEmployee), status);
    }
    public List<Vacation> listOfVacationByHrAndStatus(int idHr, Status status){
        return vacationDAO.findByHrAndStatus(employeeDAO.find(idHr), status);
    }
    public List<Vacation> listOfVacationByEmployeeAndStatus(int idEmployee, Status status){
        return vacationDAO.findByEmployeeAndStatus(employeeDAO.find(idEmployee), status);
    }
}

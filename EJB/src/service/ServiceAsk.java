package service;

import data.dao.CommentDAO;
import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.dao.VacationDAO;
import data.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Manfred on 01/11/2014.
 */
@Stateless
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
    public Vacation newVacation(java.util.Date begDate, java.util.Date endDate, DayTime begTime, DayTime endTime, String comment,
                                Employee employee, Employee manager) {
        Vacation nvVacation = new Vacation();

        // Date de début et moment de la journée
        nvVacation.setBegdate(new java.sql.Date(begDate.getTime()));
        nvVacation.setBegtime(begTime.toString());

        // Date de fin et moment de la journée
        nvVacation.setEnddate(new java.sql.Date(endDate.getTime()));
        nvVacation.setEndtime(endTime.toString());

        // Demandeur et Validateur
        nvVacation.setEmployee(employee);
        nvVacation.setManager(manager);

        //Test si l'employé est Directeur, si oui, passage en validé MGR Sinon, Passage en attente
        if(manager == null)
            nvVacation.setStatus(Status.VALIDATEDMGR.toString());
        else
            nvVacation.setStatus(Status.PENDING.toString());

        System.out.println("Avant : " + nvVacation.getId());
        Vacation v = vacationDAO.persist(nvVacation);
        System.out.println("Après : " + nvVacation.getId());
        System.out.println("Après2: " + v.getId());

        if(!comment.equals("")){
            Comment comment_obj = new Comment();
            comment_obj.setComments(comment);
            comment_obj.setCreator(employee);
            comment_obj.setVacation(nvVacation);
            this.commentDAO.persist(comment_obj);
        }

        return nvVacation ;
    }


    @Override
    public void makePlanning(Employee employee) {
        List<Vacation> vacations = vacationDAO.findByManager(employeeDAO.getManager(employee));
    }


    @Override
    public int checkVacations(Employee employee) {
        return employee.getNbVacation();
    }

    @Override
    public List<Vacation> getMyVacations(Employee employee){
        return vacationDAO.findByEmployee(employee);
    }

    @Override
    public List<Vacation> getAllVacations() {
        return this.vacationDAO.findAll();
    }

    @Override
    public List<Vacation> getVacationsByService(String serviceName, String status, int year) {
        Service service = this.serviceDAO.find(serviceName);
        return this.vacationDAO.findByService(service);
    }

    @Override
    public List<Vacation> getVacationsByEmployee(int employeeId, String status, int year) {
        Employee employee = this.employeeDAO.find(employeeId);
        return this.vacationDAO.findByEmployee(employee);
    }

    @Override
    public List<Vacation> getVacationsByAssociates(int managerId, String status, int year) {
        Employee manager = this.employeeDAO.find(managerId);
        return this.vacationDAO.findByManager(manager);
    }

    @Override
    public Vacation cancelVacation(Vacation vacation, String comment) {
        if(vacation.getStatus().equals(Status.PENDING.toString()) || vacation.getEmployee().getManager() == null) {
            vacation.setStatus(Status.CANCELLED.toString());
            if(!comment.equals("")){
                Comment comment_obj = new Comment(comment, vacation.getEmployee(), vacation);
                vacation.addComments(comment_obj);
            }
            return vacationDAO.update(vacation);
        }
        vacation.setStatus(Status.PENDINGCANCEL.toString());
        return vacationDAO.update(vacation);
    }

    public String getComments(Vacation vacation){
        String comments = new String();
        for(Comment c : vacation.getComments()){
            comments += c.getComments() + "\n" ;
        }
        return comments.substring(0, comments.length()-1) ;
    }
}

package service;

import data.dao.*;
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

    @EJB
    private SoldeDAO soldeDAO;

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

        Vacation v = vacationDAO.persist(nvVacation);
        /*if(!comment.equals("")){
            Comment comment_obj = new Comment();
            comment_obj.setComments(comment);
            comment_obj.setCreator(employee);
            comment_obj.setVacation(v);
            commentDAO.persist(comment_obj);
        }*/

        if(!comment.equals("")){
            Comment comment_obj = new Comment(comment, employee, v);
            v.addComments(comment_obj);
        }
        v = vacationDAO.update(v);

        return v;
    }


    @Override
    public void makePlanning(Employee employee) {
        List<Vacation> vacations = vacationDAO.findByManager(employeeDAO.getManager(employee));
    }


    @Override
    public float checkSolde(Employee employee, int year) {
        return soldeDAO.findByYearAndEmployee(employee, year).getNumber();
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
            // Le conge n'a pas ete valide par le manager ou l'employe est le directeur
            vacation.setStatus(Status.CANCELLED.toString());
        }else{
            // Le conge a ete valide par un RH
            vacation.setStatus(Status.PENDINGCANCEL.toString());
        }
        // ajout du commentaire eventuel
        if(!comment.equals("")){
            Comment comment_obj = new Comment(comment, vacation.getEmployee(), vacation);
            vacation.addComments(comment_obj);
        }
        return vacationDAO.update(vacation);
    }

    public String getComments(Vacation vacation){
        List<Comment> comments_lst = this.commentDAO.findByVacation(vacation);
        String comments_str = new String();
        for(Comment c : comments_lst){
            comments_str += c.getComments() + "\n" ;
        }
        return comments_str.substring(0, comments_str.length()-1) ;
    }
}

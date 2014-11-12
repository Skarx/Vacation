package service;

import data.dao.*;
import data.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Manfred on 01/11/2014.
 */
@Stateless
public class ServiceValidate implements IValidator {

    @EJB
    private EmployeeDAO employeeDAO ;

    @EJB
    private VacationDAO vacationDAO ;

    @EJB
    private CommentDAO commentDAO ;

    @EJB
    private SoldeDAO soldeDAO;


    public ServiceValidate(){
        this.vacationDAO = new VacationDAO() ;
        this.commentDAO = new CommentDAO() ;
    }

    @Override
    public Vacation getVacationById(int id) {
        return this.vacationDAO.findById(id);
    }

    @Override
    public Vacation validateVacation(Vacation vacation, Employee validator, String comment) {
        if(vacation.getStatus().equals(Status.PENDING.toString())
                && vacation.getEmployee().getManager().equals(validator)){
            // en attente de validation par le manager
            vacation.setStatus(Status.VALIDATEDMGR.toString());
            if(!comment.equals("")){
                Comment comment_obj = new Comment(comment, validator, vacation);
                vacation.addComments(comment_obj);
            }
            vacation = vacationDAO.update(vacation);
        }else if(vacation.getStatus().equals(Status.VALIDATEDMGR.toString())
                && validator.getService().getName().equals("RH")){
            // en attente de validation par le service rh
            vacation.setStatus(Status.VALIDATEDHR.toString());
            vacation.setHr(validator);
            if(!comment.equals("")){
                Comment comment_obj = new Comment(comment, validator, vacation);
                vacation.addComments(comment_obj);
            }
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation refuseVacation(Vacation vacation, Employee validator, String comment) {
        if((vacation.getStatus().equals(Status.PENDING.toString()) && vacation.getEmployee().getManager().equals(validator))
                ||(vacation.getStatus().equals(Status.VALIDATEDMGR.toString()) && validator.getService().getName().equals("RH"))){
            vacation.setStatus(Status.REFUSED.toString());
            if(!comment.equals("")){
                Comment comment_obj = new Comment(comment, validator, vacation);
                vacation.addComments(comment_obj);
            }
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation validateCancelling(Vacation vacation, Employee validator, String comment) {
        // changement de l'état de la demande de congé en fonction du status
        if(vacation.getStatus().equals(Status.PENDINGCANCEL.toString())
                && vacation.getEmployee().getManager().equals(validator)) {
            vacation.setStatus(Status.CANCELLED.toString());
            if(!comment.equals("")){
                Comment comment_obj = new Comment(comment, validator, vacation);
                vacation.addComments(comment_obj);
            }
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation refuseCancelling(Vacation vacation, Employee validator, String comment) {
        // changement de l'état de la demande de congé en fonction du status
        if(vacation.getStatus().equals(Status.PENDINGCANCEL.toString())
                && vacation.getEmployee().getManager().equals(validator)) {
            vacation.setStatus(Status.VALIDATEDHR.toString());
            if(!comment.equals("")){
                Comment comment_obj = new Comment(comment, validator, vacation);
                vacation.addComments(comment_obj);
            }
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public List<Vacation> getMyAssociatesPendingVacations(Employee employee) {
        List<Vacation> vacations = new ArrayList<Vacation>();

        // pour chaque associe du manager, on incremente la liste des conges
        List<Employee> associates = this.employeeDAO.getAssociates(employee) ;
        for( Employee e : associates){
            vacations.addAll(vacationDAO.findPendingVacationsByEmployee(e));
        }

        return vacations ;
    }

    @Override
    public List<Vacation> getVacationsValidatedMgr() {
        List<Vacation> vacations = this.vacationDAO.findByStatus(Status.VALIDATEDMGR.toString());
        return vacations ;
    }

    @Override
    public List<Vacation> getVacationsForEmployee(Employee employee) {
        List<Vacation> vacations = new ArrayList<Vacation>() ;
        vacations.addAll(vacationDAO.findByEmployee(employee));
        return vacations ;
    }

    @Override
    public void updateVacationForEmployee() {

    }

    @Override
    public int checkSolde(Employee employee, int year) {
        return soldeDAO.findByYearAndEmployee(employee, year).getNumber();
    }

    @Override
    public int changeSolde(Employee employee, int year, int i) {
        Solde solde = soldeDAO.findByYearAndEmployee(employee, year);
        solde.setNumber(solde.getNumber()-i);
        soldeDAO.update(solde);
        return solde.getNumber();
    }

}
package service;

import data.dao.CommentDAO;
import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.dao.VacationDAO;
import data.model.Comment;
import data.model.Employee;
import data.model.Status;
import data.model.Vacation;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manfred on 01/11/2014.
 */
public class ServiceValidate implements IValidator {

    @EJB
    private VacationDAO vacationDAO ;

    @EJB
    private CommentDAO commentDAO ;

    public ServiceValidate(){
        this.vacationDAO = new VacationDAO() ;
        this.commentDAO = new CommentDAO() ;
    }

    @Override
    public Vacation validateVacation(Vacation vacation, Employee validator, Comment comment) {
        if(vacation.getStatus() == Status.PENDING.toString()
                && vacation.getEmployee().getManager() == validator){
            // en attente de validation par le manager
            vacation.setStatus(Status.VALIDATEDMGR.toString());
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }else if(vacation.getStatus() == Status.VALIDATEDMGR.toString()
                && validator.getService().getName() == "RH"){
            // en attente de validation par le service rh
            vacation.setStatus(Status.VALIDATEDHR.toString());
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation refuseVacation(Vacation vacation, Employee validator, Comment comment) {
        if((vacation.getStatus() == Status.PENDING.toString() && vacation.getEmployee().getManager() == validator)
                ||(vacation.getStatus() == Status.VALIDATEDMGR.toString() && validator.getService().getName() == "RH")){
            vacation.setStatus(Status.REFUSED.toString());
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation validateCancelling(Vacation vacation, Employee validator, Comment comment) {
        // changement de l'état de la demande de congé en fonction du status
        if(vacation.getStatus() == Status.PENDINGCANCEL.toString()
                && vacation.getEmployee().getManager() == validator) {
            vacation.setStatus(Status.CANCELLED.toString());
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation refuseCancelling(Vacation vacation, Employee validator, Comment comment) {
        // changement de l'état de la demande de congé en fonction du status
        if(vacation.getStatus() == Status.PENDINGCANCEL.toString()
                && vacation.getEmployee().getManager() == validator) {
            vacation.setStatus(Status.VALIDATEDHR.toString());
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public List<Vacation> getMyAssociatesPendingVacations(Employee employee) {
        List<Vacation> vacations = new ArrayList<Vacation>();

        // pour chaque associe du manager, on incremente la liste des conges
        for( Employee e : employee.getAssociates()){
            vacations.addAll(vacationDAO.findPendingVacationsByEmployee(e));
        }

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
}
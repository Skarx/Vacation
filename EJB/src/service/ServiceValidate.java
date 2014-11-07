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

    public ServiceValidate(){
        this.vacationDAO = new VacationDAO() ;
        this.commentDAO = new CommentDAO() ;
    }

    @Override
    public Vacation validateVacation(Vacation vacation, Employee validator, Comment comment) {
        if(vacation.getStatus().equals(Status.PENDING.toString())
                && vacation.getEmployee().getManager().equals(validator)){
            // en attente de validation par le manager
            vacation.setStatus(Status.VALIDATEDMGR.toString());
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }else if(vacation.getStatus().equals(Status.VALIDATEDMGR.toString())
                && validator.getService().getName().equals("RH")){
            // en attente de validation par le service rh
            vacation.setStatus(Status.VALIDATEDHR.toString());
            vacation.setHr(validator);
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation refuseVacation(Vacation vacation, Employee validator, Comment comment) {
        if((vacation.getStatus().equals(Status.PENDING.toString()) && vacation.getEmployee().getManager().equals(validator))
                ||(vacation.getStatus().equals(Status.VALIDATEDMGR.toString()) && validator.getService().getName().equals("RH"))){
            vacation.setStatus(Status.REFUSED.toString());
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation validateCancelling(Vacation vacation, Employee validator, Comment comment) {
        // changement de l'état de la demande de congé en fonction du status
        if(vacation.getStatus().equals(Status.PENDINGCANCEL.toString())
                && vacation.getEmployee().getManager().equals(validator)) {
            vacation.setStatus(Status.CANCELLED.toString());
            vacation.addComments(comment);
            vacation = vacationDAO.update(vacation);
        }
        return vacation;
    }

    @Override
    public Vacation refuseCancelling(Vacation vacation, Employee validator, Comment comment) {
        // changement de l'état de la demande de congé en fonction du status
        if(vacation.getStatus().equals(Status.PENDINGCANCEL.toString())
                && vacation.getEmployee().getManager().equals(validator)) {
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
        List<Employee> associates = this.employeeDAO.getAssociates(employee) ;
        for( Employee e : associates){
            vacations.addAll(vacationDAO.findAll());
            //vacations.addAll(vacationDAO.findPendingVacationsByEmployee(e));
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
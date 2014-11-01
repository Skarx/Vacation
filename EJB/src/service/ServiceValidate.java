package service;

import data.dao.CommentDAO;
import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.dao.VacationDAO;
import data.model.Employee;
import data.model.Vacation;

import javax.ejb.EJB;
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
    public void validateVacation(Vacation vacation) {

    }

    @Override
    public void refuseVacation(Vacation vacation) {

    }

    @Override
    public void validateCancelling(Vacation vacation) {

    }

    @Override
    public void refuseCancelling(Vacation vacation) {

    }

    @Override
    public List<Vacation> getMyAssociatesPendingVacations(Employee employee) {
        return null;
    }

    @Override
    public List<Vacation> getVacationsForEmployee(Employee employee) {
        return null;
    }

    @Override
    public void updateVacationForEmployee() {

    }
}

package service;

import data.model.Comment;
import data.model.Employee;
import data.model.Vacation;

import java.util.List;

/**
 * Created by Manfred on 01/11/2014.
 */
public interface IValidator {

    public Vacation validateVacation(Vacation vacation, Employee validator, Comment comment);

    public Vacation refuseVacation(Vacation vacation, Employee validator, Comment comment);

    public Vacation validateCancelling(Vacation vacation, Employee validator, Comment comment);

    public Vacation refuseCancelling(Vacation vacation, Employee validator, Comment comment);

    public List<Vacation> getMyAssociatesPendingVacations(Employee employee);

    public List<Vacation> getVacationsForEmployee(Employee employee);

    public void updateVacationForEmployee();
}

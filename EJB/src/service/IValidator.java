package service;

import data.model.Employee;
import data.model.Vacation;

import java.util.List;

/**
 * Created by Manfred on 01/11/2014.
 */
public interface IValidator {

    public void validateVacation(Vacation vacation);

    public void refuseVacation(Vacation vacation);

    public void validateCancelling(Vacation vacation);

    public void refuseCancelling(Vacation vacation);

    public List<Vacation> getMyAssociatesPendingVacations(Employee employee);

    public List<Vacation> getVacationsForEmployee(Employee employee);

    public void updateVacationForEmployee();
}

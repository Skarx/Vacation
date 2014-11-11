package service;

import data.model.Comment;
import data.model.Employee;
import data.model.Vacation;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Manfred on 01/11/2014.
 */
@Remote
public interface IValidator {

    public Vacation getVacationById(int id);

    public Vacation validateVacation(Vacation vacation, Employee validator, String comment);

    public Vacation refuseVacation(Vacation vacation, Employee validator, String comment);

    public Vacation validateCancelling(Vacation vacation, Employee validator, String comment);

    public Vacation refuseCancelling(Vacation vacation, Employee validator, String comment);

    public List<Vacation> getMyAssociatesPendingVacations(Employee employee);

    public List<Vacation> getVacationsValidatedMgr();

    public List<Vacation> getVacationsForEmployee(Employee employee);

    public void updateVacationForEmployee();
}

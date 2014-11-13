package service;

import data.model.*;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

/**
 * Created by Manfred on 01/11/2014.
 */
@Remote
public interface IEmployee {
    Vacation newVacation(java.util.Date begDate, java.util.Date endDate, DayTime begTime, DayTime endTime, String comment,
                         Employee employee, Employee manager);

    public void makePlanning(Employee manager) ;
    public float checkSolde(Employee employee, int year) ;
    public List<Vacation> getMyVacations(Employee employee) ;
    public List<Vacation> getAllVacations();
    public List<Vacation> getVacationsByService(String serviceName, String status, int year) ;
    public List<Vacation> getVacationsByEmployee(int employeeId, String status, int year) ;
    public List<Vacation> getVacationsByAssociates(int employeeId, String status, int year) ;
    public Vacation cancelVacation(Vacation vacation, String comment) ;
}

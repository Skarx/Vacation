package service;

import data.model.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by Manfred on 01/11/2014.
 */
public interface IEmployee {

    public Vacation newVacation(Date begDate, Date endDate, DayTime begTime, DayTime endTime,
                            Comment comment, Employee hr, Employee manager, Status status) ;
    public Vacation cancelVacation(Vacation vacation) ;
    public void makePlanning(Employee manager) ;
    public void checkVacations() ;
    public List<Vacation> getVacationsByEmployee(Employee emp) ;
    public List<Vacation> getVacationByEmployeeAndYear(Employee emp, int year) ;
    public List<Vacation> getVacationByEmployeeAndStatus(Employee emp, Status status) ;
    public List<Vacation> getVacationByManagerAndStatus(Employee manager, Status status) ;
    public List<Vacation> getVacationByHrAndStatus(Employee manager, Status status) ;

}

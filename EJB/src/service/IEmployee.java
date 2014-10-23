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
    public void makePlanning(Employee manager) ;
    public int checkVacations(Employee employee) ;
    public List<Vacation> getMyAssociatesPendingVacations(Employee employee) ;
    public List<Vacation> getMyVacations(Employee employee) ;
    public Vacation cancelPendingVacation(Vacation vacation) ;
    public Vacation cancelVacation(Vacation vacation) ;
}

package service;

import data.model.*;

import java.sql.Date;

/**
 * Created by Manfred on 01/11/2014.
 */
public interface IEmployee {

    public Vacation newVacation(Date begDate, Date endDate, DayTime begTime, DayTime endTime,
                            Comment comment, Employee hr, Employee manager, Status status) ;
    public Vacation cancelVacation(Vacation vacation) ;
    public void makePlanning() ;
    public void checkVacations() ;
    public void getVacationsByEmployee() ;

}

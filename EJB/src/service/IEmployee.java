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

    public Vacation newVacation(Date begDate, Date endDate, DayTime begTime, DayTime endTime,
                            String comment, Employee hr, Employee manager) ;
    public void makePlanning(Employee manager) ;
    public int checkVacations(Employee employee) ;
    public List<Vacation> getMyVacations(Employee employee) ;
    public Vacation cancelVacation(Vacation vacation) ;
}

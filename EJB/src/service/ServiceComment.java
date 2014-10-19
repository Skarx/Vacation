package service;
import data.dao.CommentDAO;
import data.dao.EmployeeDAO;
import data.dao.ServiceDAO;
import data.dao.VacationDAO;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import data.model.Comment;
import data.model.Employee;
import data.model.Vacation;

import java.security.Timestamp;

/**
 * Created by Hervé on 19/10/2014.
 */
@Stateless
@LocalBean
public class ServiceComment {
    @EJB
    private CommentDAO commentDAO;
    @EJB
    private EmployeeDAO employeeDAO;
    @EJB
    private ServiceDAO serviceDAO;
    @EJB
    private VacationDAO vacationDAO;
    // Default constructor
    public ServiceComment(){}
    // Create a new Comment

    public int newComment(String comment, Employee emp, Vacation vacation){
        Comment nvComment = new Comment();
        java.util.Date date= new java.util.Date();
        nvComment.setComments(comment);
        nvComment.setCreadate(new java.sql.Timestamp(date.getTime()));
        nvComment.setCreator(emp);
        nvComment.setVacation(vacation);
        nvComment = commentDAO.persist(nvComment);
        vacation.addComments(nvComment);
        vacationDAO.update(vacation);
        return nvComment.getId();
    }




}

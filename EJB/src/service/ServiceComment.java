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
import java.util.Date;
import java.util.List;

/**
 * Created by Hervé on 19/10/2014.

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
        return nvComment.getId();
    }
    public Comment getComment(int idComment){
        return commentDAO.findById(idComment);
    }
    public void updateComment(int idComment, String comment){
        Comment com = commentDAO.findById(idComment);
        Date date = new Date();
        com.setCreadate(new java.sql.Timestamp(date.getTime()));
        com.setComments(comment);
        commentDAO.update(com);
    }
    public void deleteComment(int idComment){
        commentDAO.remove(commentDAO.findById(idComment));
    }
    public List<Comment> listOfCommentsByVacation(int idVacation){
        return commentDAO.findByVacation(vacationDAO.findById(idVacation));
    }
    public List<Comment> listOfCommentByCreator(int idCreator){
        return commentDAO.findByCreator(employeeDAO.find(idCreator));
    }
}
*/
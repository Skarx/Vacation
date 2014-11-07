package data.dao;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import data.model.Employee;
import data.model.Vacation;
import data.model.Status;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Manfred on 27/10/2014.
 */

@Stateless
@LocalBean
public class VacationDAO {
    //-----------------------------------------------------------------------------
    /**
     * Référence vers le gestionnaire de persistance.
     */
    @PersistenceContext
    private EntityManager entityManager;
    //-----------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public VacationDAO()
    {
        // TODO Auto-generated constructor stub
    }
    //-----------------------------------------------------------------------------
    public Vacation findById(Integer id)
    {
        return entityManager.find(Vacation.class, id);
    }
    //----------------------------------------------------------------------------
    public List<Vacation> findAll()
    {
        Query query = entityManager.createQuery(
                "SELECT Vacation FROM Vacation Vacation "
                        + "ORDER BY Vacation.id DESC");
        List l = query.getResultList();

        return (List<Vacation>)l;
    }
    //-----------------------------------------------------------------------------
    public List<Vacation> findByManager(Employee manager){
        Query query = entityManager.createQuery("" +
                "SELECT Vacation FROM Vacation Vacation " +
                "WHERE Vacation.manager = :manager");
        return query.getResultList();
    }
    //-----------------------------------------------------------------------------
    public List<Vacation> findByHr(Employee hr){
        Query query = entityManager.createQuery("" +
                "SELECT Vacation FROM Vacation Vacation " +
                "WHERE Vacation.hr = :hr");
        return query.getResultList();
    }
    //-----------------------------------------------------------------------------
    public List<Vacation> findByEmployee(Employee employee){
        Query query = entityManager.createQuery("" +
                "SELECT Vacation FROM Vacation Vacation " +
                "WHERE Vacation.employee = :employee");
        return query.getResultList();
    }
    //-----------------------------------------------------------------------------
    public List<Vacation> findPendingVacationsByEmployee(Employee employee){
        String status = Status.PENDING.toString();
        Query query = entityManager.createQuery("SELECT Vacation FROM Vacation Vacation " +
                "WHERE Vacation.employee = :employee   AND  " +
                "Vacation.status like :status");
        query.setParameter("employee", employee);
        query.setParameter("status", status);
        return query.getResultList();
    }
    //-----------------------------------------------------------------------------
    public List<Vacation> findByEmployeeAndStatus(Employee employee, Status status){
        Query query = entityManager.createQuery("" +
                "SELECT Vacation FROM Vacation Vacation " +
                "WHERE Vacation.employee = :employee " +
                "AND Vacation.status IS " + status.toString());
        return query.getResultList();
    }
    //-----------------------------------------------------------------------------
    public List<Vacation> findByManagerAndStatus(Employee manager, Status status){
        Query query = entityManager.createQuery("" +
                "SELECT Vacation FROM Vacation Vacation " +
                "WHERE Vacation.manager = :manager "+ " " +
                "AND Vacations.status IS " + status.toString());
        return query.getResultList();
    }
    //-----------------------------------------------------------------------------
    public List<Vacation> findByHrAndStatus(Employee hr, Status status){
        Query query = entityManager.createQuery("" +
                "SELECT Vacation FROM Vacation Vacation " +
                "WHERE Vacation.hr = :hr "+ " " +
                "AND Vacations.status IS " + status.toString());
        return query.getResultList();
    }
    public List<Vacation> findByEmployeeAndYear(Employee emp, int year){
        Query query = entityManager.createQuery("" +
                "SELECT Vacation FROM Vacation Vacation " +
                "WHERE Vacation.employee = :emp IS " + " " +
                "AND YEAR(Vacations.enddate) IS " + year);
        return query.getResultList();
    }
    //-----------------------------------------------------------------------------
    public Vacation persist(Vacation vacation){
        entityManager.persist(entityManager.merge(vacation));
        return vacation;

    }
    //-----------------------------------------------------------------------------

    public Vacation update (Vacation vacation){
        return entityManager.merge(vacation);

    }
    //-----------------------------------------------------------------------------

    public void remove(Vacation Vacation){
        entityManager.remove(Vacation);
    }

}

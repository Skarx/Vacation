package data.dao;

import data.model.Employee;
import data.model.Service;
import data.model.Solde;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Hervé on 12/11/2014.
 */
@Stateless
@LocalBean
public class SoldeDAO {
    @PersistenceContext
    EntityManager entityManager ;

    public SoldeDAO(){

    }
    public Solde findById(Integer id)
    {
        return entityManager.find(Solde.class, id);
    }
    //----------------------------------------------------------------------------
    public List<Solde> findAll()
    {
        Query query = entityManager.createQuery(
                "SELECT Solde FROM Solde Solde "
                        + "ORDER BY Solde.id DESC");
        List l = query.getResultList();

        return (List<Solde>)l;
    }
    public void create(Solde solde){
        try{
            entityManager.persist(solde);
        }catch(Exception e){
        }
    }

    public void remove(Solde solde){
        try{
            entityManager.remove(solde);
        }catch(Exception e){

        }
    }

    public void update(Solde solde){
        if(solde != null){
            entityManager.merge(solde);
        }
    }


    public Solde findByYearAndEmployee(Employee employee, int year){
        Query query = entityManager.createQuery("SELECT Solde FROM Solde Solde where " +
                "Solde.year = :year AND Solde.employee = :employee");
        query.setParameter("employee", employee);
        query.setParameter("year", year);
        return (Solde)query.getSingleResult();

    }
}

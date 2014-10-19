package data.dao;

import data.model.Service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Manfred on 29/10/2014.
 */

@Stateless
@LocalBean
public class ServiceDAO {

    @PersistenceContext
    EntityManager entityManager ;

    public ServiceDAO(){

    }

    public void create(Service service){
        try{
            entityManager.persist(service);
        }catch(Exception e){
        }
    }

    public void remove(Service service){
        try{
            entityManager.remove(service);
        }catch(Exception e){

        }
    }

    public void update(Service service){
        if(service != null){
            entityManager.merge(service);
        }
    }

    public Service find(int id){
        return entityManager.find(Service.class, id);
    }

    public Service find(String serviceName){
        return entityManager.find(Service.class, serviceName);
    }

    public List<Service> getAll(){
        Query query = entityManager.createQuery("SELECT Service FROM Service Service ORDER BY Service.id DESC");
        List l = query.getResultList();

        return (List<Service>) l;
    }
}

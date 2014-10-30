package data.test;

import data.dao.ServiceDAO;
import data.model.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceDAOTest {

    private ServiceDAO serviceDAO = new ServiceDAO();
    private Service s1 ;
    private Service s2 ;
    private Service s3 ;

    @Before
    public void setUp() throws Exception {
        this.s1 = new Service("Production");
        this.s2 = new Service("RH");
        this.s3 = new Service("Support");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {
        serviceDAO.create(s1);
        serviceDAO.create(s1);
    }

    @Test
    public void testRemove() throws Exception {
        serviceDAO.remove(s2);
        serviceDAO.remove(s2);
    }

    @Test
    public void testUpdate() throws Exception {
        s2.setName("Coucou");
        serviceDAO.update(s2);
    }

    @Test
    public void testFind() throws Exception {

    }

    @Test
    public void testFind1() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

    }
}
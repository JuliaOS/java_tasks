package ru.stqa.pft.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.model.Users;

import java.util.List;

/**
 * Created by Julia on 5/30/2017.
 */
public class DbConnectionTest {
    private SessionFactory sessionFactory;

    @BeforeMethod
    protected void setUp() throws Exception {

        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @Test

    public void testDbConnection(){

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from Users" ).list();
        for ( Users user : (List<Users>) result ) {
            System.out.println( "User (" + user.getUsername() + ") : " + user.getEmail() );
        }
        session.getTransaction().commit();
        session.close();

    }
}

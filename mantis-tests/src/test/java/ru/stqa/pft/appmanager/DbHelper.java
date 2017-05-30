package ru.stqa.pft.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.model.Users;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Julia on 5/21/2017.
 */
public class DbHelper {
    private final SessionFactory sessionFactory;

    public DbHelper(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public ArrayList<Users> user(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Users> result = session.createQuery( "from Users").list();
        session.getTransaction().commit();
        session.close();
        return new ArrayList<Users>(result);
    }
}

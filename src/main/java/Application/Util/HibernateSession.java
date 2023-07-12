package Application.Util;

import Application.Model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateSession {
    private static SessionFactory sessionFactory;

    /*Создает SessionFactory и возвращает Session
     *Created SessionFactory and return Session*/

    @Bean
    public static Session getSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
                        .setProperty(Environment.DRIVER, "org.postgresql.Driver")
                        .setProperty(Environment.URL, "jdbc:postgresql://localhost:5432/TestProgectAxiomaticsREST")
                        .setProperty(Environment.USER, "postgres")
                        .setProperty(Environment.PASS, "123321")
                        .setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
                        .setProperty(Environment.SHOW_SQL, "false")
                        .setProperty(Environment.HBM2DDL_AUTO, "update");
                sessionFactory = configuration.buildSessionFactory();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory.openSession();
    }
}

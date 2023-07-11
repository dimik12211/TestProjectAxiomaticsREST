package Application.DAO;

import Application.Model.Person;
import Application.Util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class DAO_Person {

    private Session session = HibernateSession.getSessionFactory();

    public void save(Person person) {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void update(Person person){
        try {
            Transaction transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

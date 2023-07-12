package Application.DAO;

import Application.Model.Person;
import Application.Service.ServicePerson;
import Application.Util.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DAOPerson {

    Logger logger = LoggerFactory.getLogger(DAOPerson.class);

    private Session session = HibernateSession.getSessionFactory();

    public void save(Person person) {
        try {
            Transaction transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
            logger.info("Сохранение в БД успешно (DAO)");
        } catch (Exception e) {
            logger.error("Ошибка сохранения в БД (DAO): " + e.getMessage());
            throw e;
        }
    }

    public void update(Person person) {
        try {
            Transaction transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
            logger.info("Обновление в БД успешно (DAO)");
        } catch (Exception e) {
            logger.error("Ошибка обновления в БД (DAO): " + e.getMessage());
            throw e;
        }
    }
}

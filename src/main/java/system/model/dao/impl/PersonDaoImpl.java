package system.model.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import system.HibernateUtil;
import system.model.Person;
import system.model.dao.PersonDao;

/**
 * Created by vladimir on 02.03.2018.
 */
public class PersonDaoImpl implements PersonDao {
    @Override
    public Person getPersonByTelNumber(String telNumber) {
        Session session = HibernateUtil.getSession();
        Person person = null;
        try {
            person = (Person) session
                    .createQuery("from Person where telNumber = :telParam")
                    .setParameter("telParam", telNumber)
                    .getSingleResult();
        } catch (Exception e) {
            //ignore
        } finally {
            session.close();
        }
        return person;
    }

    @Override
    public Person getPersonById(long id) {
        Person person = null;
        Session session = HibernateUtil.getSession();
        try {
            person = (Person) session.createQuery("from Person where id = :idParam")
                    .setParameter("idParam", id)
                    .getSingleResult();
        } catch (Exception e) {
            //ignore
        } finally {
            session.close();
        }

        return person;
    }

    @Override
    public void createOrUpdate(Person person) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.saveOrUpdate(person);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(long id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createQuery("delete from Person where id =:id")
                    .setParameter("id", id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }
}

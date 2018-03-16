package system;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

/**
 * Created by vladimir on 02.03.2018.
 */
public class HibernateUtil {
    private static SessionFactory ourSessionFactory;

    public static Session getSession() throws HibernateException {
        if (ourSessionFactory==null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure();
                ourSessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return ourSessionFactory.openSession();
    }

    public static void close() throws IOException {
        if (ourSessionFactory!=null
                && ourSessionFactory.isOpen())
            ourSessionFactory.close();
    }
}

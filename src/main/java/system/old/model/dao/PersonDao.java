package system.old.model.dao;

import system.old.model.Person;

/**
 * Created by vladimir on 02.03.2018.
 */
public interface PersonDao {
    Person getPersonByTelNumber(String telNumber);

    Person getPersonById(long id);

    void createOrUpdate(Person person);

    void delete(long id);
}

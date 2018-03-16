package system.model.dao;

import system.model.Person;

/**
 * Created by vladimir on 02.03.2018.
 */
public interface PersonDao {
    Person getPersonByTelNumber(String telNumber);

    Person getPersonById(long id);

    void createOrUpdate(Person person);

    void delete(long id);
}

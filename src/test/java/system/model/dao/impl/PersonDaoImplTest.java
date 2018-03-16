package system.model.dao.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import system.model.Person;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by vladimir on 03.03.2018.
 */
class PersonDaoImplTest {
    private static PersonDaoImpl personDao;

    @BeforeAll
    static void init() {
        personDao = new PersonDaoImpl();
    }

    @Test
    @DisplayName("get person by number")
    void getPersonByTelNumber() {
        final Person person = personDao.getPersonByTelNumber("+7911");
        assertAll("properties",
                ()->assertNotNull(person),
                ()->assertTrue("+7911".equals(person.getTelNumber())));
    }

    @Test
    @DisplayName("create person an delete")
    void createOrUpdate() {
        Person person = new Person("Иван", "Иванов", "Иванович", "+7(911)111-11-11");
        personDao.createOrUpdate(person);
        final Person DBPerson = personDao.getPersonByTelNumber(person.getTelNumber());
        assertNotNull(DBPerson);

        personDao.delete(DBPerson.getId());
        person = personDao.getPersonById(DBPerson.getId());
        assertNull(person);
    }

}
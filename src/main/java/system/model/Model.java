package system.model;

import javafx.collections.ObservableList;
import system.model.dao.PersonDao;
import system.model.dao.impl.PersonDaoImpl;
import system.model.queue.ColumnPerson;
import system.model.queue.TableQueue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladimir on 17.02.2018.
 */
public class Model {
    private Map<String, Person> allPerson;
    private Person tmpPerson;
    private TableQueue tableQueue;
    private PersonDao personDao;


    public Person getByTelNumber(String telNumber) {
        if (telNumber!=null) {
            Person person = allPerson.get(telNumber);
            if (person==null) {
                person = personDao.getPersonByTelNumber(telNumber);
                if (person!=null)
                    allPerson.put(telNumber, person);
            }
            return person;
        }
        return null;
    }

    public Model() {
        this.tableQueue = new TableQueue();
        this.allPerson = new HashMap<>();
        this.personDao = new PersonDaoImpl();
    }

    public void addPerson(Person person) {
        personDao.createOrUpdate(person);
        allPerson.put(person.getTelNumber(), person);
    }

    public void setTmpPerson(Person tmpPerson) {
        this.tmpPerson = tmpPerson;
    }

    public Person getTmpPerson() {
        return tmpPerson;
    }

    public ObservableList<ColumnPerson> getTableQueue() {
        return this.tableQueue.getQueueTable();
    }

    public void setColumnPersonInQueue(ColumnPerson columnPerson) {
        tableQueue.setColumnPerson(columnPerson);
    }
}

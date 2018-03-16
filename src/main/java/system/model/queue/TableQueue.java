package system.model.queue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.model.Person;
import system.model.queue.listener.*;
import system.controller.*;
import java.util.Comparator;

/**
 * Created by vladimir on 21.02.2018.
 */
public class TableQueue implements QueueListener {
    private ObservableList<ColumnPerson> queueTable;
    private RegistrationPersonController controller;
    private int count = 0;

    public TableQueue() {
        this.queueTable = FXCollections.observableArrayList();
    }

    public ObservableList<ColumnPerson> getQueueTable() {
        return queueTable;
    }

    public void setColumnPerson(ColumnPerson columnPerson) {
        columnPerson.setId(getNewId());
        columnPerson.setListener(this);
        queueTable.add(columnPerson);
    }

    public int getNewId() {
        return count++;
    }

    public void deleteColumnPerson(ColumnPerson person) {
        queueTable.remove(person);
    }
}

package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.controller.to.QueueRow;

/**
 * Created by vladimir on 01.04.2018.
 */
public class Queue {
    private ObservableList<QueueRow> queueRows = FXCollections.observableArrayList();

    private static Queue instance;

    private Queue() {
    }

    public static Queue getInstance() {
        if (instance==null)
            instance = new Queue();
        return instance;
    }

    public ObservableList<QueueRow> getQueueRows() {
        return queueRows;
    }

    public void insertRows(QueueRow row) {
        queueRows.add(row);
    }
}

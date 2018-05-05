package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.util.CollectionUtils;
import system.controller.to.QueueRow;

import java.util.*;
import static system.util.ValidationUtil.*;
import static system.controller.page.helper.QueueHelper.*;

/**
 * Created by vladimir on 01.04.2018.
 */
public class Queue implements QueueListener {

    private ObservableList<QueueRow> queueRows = FXCollections.observableArrayList();

    private LinkedList<QueueRow> activeQueue = new LinkedList<>();

    private List<QueueRow> disactiveQueue = new ArrayList<>();

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
        if (!activeQueue.isEmpty()) rowEnable(false);
        activeQueue.add(row);
        refreshQueue();
    }

    private void refreshQueue() {
        queueRows.clear();
        rowEnable(true);
        queueRows.addAll(activeQueue);
        queueRows.addAll(disactiveQueue);
    }

    /**
    * Списываем первый билет из очереди
    * Если в это абонемент или сет с доступным кол-вом, то списываем и переносим в конец очерели
    * Иначе списываем и удаляем из очереди
     */
    public void reedeemTicket() {
        if (checkNotEmptyActiveQueue()) {
            QueueRow first = activeQueue.getFirst();


        }
    }

    public void toUp(QueueRow row) {
        final int index = activeQueue.indexOf(row);
        if (index >= 0 &&
                indexExists(activeQueue, index - 1)) {
            rowEnable(false);
            Collections.swap(activeQueue, index, index - 1);
        }
        refreshQueue();
    }

    public void toDown(QueueRow row) {
        final int index = activeQueue.indexOf(row);
        if (index >= 0 &&
                indexExists(activeQueue, index + 1)) {
            rowEnable(false);
            Collections.swap(activeQueue, index, index + 1);
        }
        refreshQueue();
    }

    public void shiftRowInActive(QueueRow row) {
        disactiveQueue.remove(row);
        row.disabledControlButton(false);
        insertRows(row);
        refreshQueue();
    }

    public void shiftRowInDisactiveQueue(QueueRow row) {
        activeQueue.remove(row);
        row.disabledControlButton(true);
        disactiveQueue.add(row);
        refreshQueue();
    }

    public void remove(QueueRow row) {
        if (row.isActive())
            activeQueue.remove(row);
        else
            disactiveQueue.remove(row);
        refreshQueue();
    }

    private void rowEnable(boolean flag) {
        if (!activeQueue.isEmpty()) {
            QueueRow first = activeQueue.getFirst();
            QueueRow last = activeQueue.getLast();
            first.setDisabledDown(false);
            last.setDisabledUp(false);
            first.setDisabledUp(flag);
            last.setDisabledDown(flag);
        }
    }

    public LinkedList<QueueRow> getActiveQueue() {
        return activeQueue;
    }
}

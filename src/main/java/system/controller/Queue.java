package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.controller.page.listener.StopWatchListener;
import system.controller.to.QueueRow;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static system.util.ValidationUtil.*;
import static system.controller.page.helper.QueueHelper.*;

/**
 * Created by vladimir on 01.04.2018.
 */

import static system.util.TicketUtil.*;

public class Queue implements QueueListener {

    private ObservableList<QueueRow> queueRows = FXCollections.observableArrayList();

    private ObservableList<QueueRow> activeRows = FXCollections.observableArrayList();

    private LinkedList<QueueRow> activeQueue = new LinkedList<>();

    private List<QueueRow> disactiveQueue = new ArrayList<>();

    private List<StopWatchListener> stopWatchListeners = new ArrayList<>();

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

    public ObservableList<QueueRow> getActiveRows() {
        return activeRows;
    }

    public void insertRows(QueueRow row) {
        if (!activeQueue.isEmpty()) rowEnable(false);
        activeQueue.add(row);
        refreshQueue();
    }

    private void refreshQueue() {
        queueRows.clear();
        activeRows.clear();
        rowEnable(true);
        queueRows.addAll(activeQueue);
        queueRows.addAll(disactiveQueue);
        activeRows.addAll(activeQueue);
        updateTime(0);
    }

    /**
    * Списываем первый билет из очереди
    * Если в это абонемент или сет с доступным кол-вом, то списываем и переносим в конец очерели
    * Иначе списываем и удаляем из очереди
     *
     * Списывание выполняется в две операции:
     * * startReedemTicket() - фиксируем запись в client_ticket_story с временем начала
     * * endReedeemTicket() - фиксируем время окончания в client_ticket_story, для сетов ставим метку "испольщования" и удаляем из очереди
     */
    public void endReedeemTicket() {
        if (checkNotEmptyActiveQueue()) {
            QueueRow first = activeQueue.pollFirst();
            if (first.endUseClientTicket()) {
                activeQueue.addLast(first);
            }
            refreshQueue();
        }
    }

    public void startReedemTicket() {
        if (checkNotEmptyActiveQueue()) {
            QueueRow first = activeQueue.getFirst();
            first.startUseClientTicket();
        }
    }


    public void toUp(QueueRow row) {
        final int index = activeQueue.indexOf(row);
        final int newIndex = index - 1;
        if (index >= 0 &&
                indexExists(activeQueue, index - 1) &&
                !activeQueue.get(newIndex).isBlock()) { //проверяем, что элемент сверху не заблокирован
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

    /*Переносим строку в активную очередь*/
    public void shiftRowInActive(QueueRow row) {
        disactiveQueue.remove(row);
        row.disabledControlButton(false);
        insertRows(row);
        refreshQueue();
    }

    /*Переносим строку в неактивную очередь*/
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

    private void rowActiveControl() {
        for (QueueRow row : activeQueue) {
            if (!row.isBlock())
                row.disabledControlButton(false);
        }
    }

    private void rowEnable(boolean flag) {
        if (!activeQueue.isEmpty()) {
            rowActiveControl();
            QueueRow first = activeQueue.getFirst();
            QueueRow last = activeQueue.getLast();


            if (!first.isBlock()) {
                first.setDisabledDown(false);
                first.setDisabledUp(flag);
            }
            if (first==last) {
                first.setDisabledUp(true);
                first.setDisabledDown(true);
            } else{
                last.setDisabledUp(false);
                last.setDisabledDown(flag);
            }
        }
    }

    public boolean checkActive() {
        return !activeQueue.isEmpty();
    }

    public LinkedList<QueueRow> getActiveQueue() {
        return activeQueue;
    }

    public List<QueueRow> getDisactiveQueue() {
        return disactiveQueue;
    }

    public void updateTime(long value) {
        long total = getTotalTime();
        total -= value;

        LocalTime localTime = LocalTime.ofSecondOfDay(value);
        String timef = localTime.format(DateTimeFormatter.ofPattern("mm:ss"));

        for (StopWatchListener listener : stopWatchListeners) {
            listener.setTotalTime(total);
            listener.setTime(timef);
        }
    }

    private long getTotalTime() {
//        for (QueueRow row : getActiveQueue())
//            total += row.getTickets().size() * TICKET_DURATION * 60;
        return getActiveQueue().size() * TICKET_DURATION * 60;
    }

    public void setStopWatchListener(StopWatchListener listener) {
        stopWatchListeners.add(listener);
    }

    public void removeStopWatchListener(StopWatchListener listener) {
        this.stopWatchListeners.remove(listener);
    }

    public void clearStopWatchListener() {
        this.stopWatchListeners.clear();
    }
}

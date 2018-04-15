package system.controller;

import system.controller.to.QueueRow;

/**
 * Created by vladimir on 14.04.2018.
 */
public interface QueueListener {

    void toUp(QueueRow row);

    void toDown(QueueRow row);

    void shiftRowInActive(QueueRow row);

    void shiftRowInDisactiveQueue(QueueRow row);

    void remove(QueueRow row);
}

package system.model.queue.listener;

import system.model.queue.ColumnPerson;

/**
 * Created by vladimir on 23.02.2018.
 */
public interface QueueListener {
    int getNewId();

    void deleteColumnPerson(ColumnPerson person);
}

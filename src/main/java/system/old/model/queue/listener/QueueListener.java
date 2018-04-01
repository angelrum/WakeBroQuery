package system.old.model.queue.listener;

import system.old.model.queue.ColumnPerson;

/**
 * Created by vladimir on 23.02.2018.
 */
public interface QueueListener {
    int getNewId();
    void deleteColumnPerson(ColumnPerson person);
}

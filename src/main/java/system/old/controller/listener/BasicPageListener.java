package system.old.controller.listener;

import system.old.model.Model;
import system.old.model.Person;

/**
 * Created by vladimir on 17.02.2018.
 */
public interface BasicPageListener {

    void setPerson(Person person);

    Model getModel();

    void refresh();

    void cancelEnable();

    void cancelDisable();
}

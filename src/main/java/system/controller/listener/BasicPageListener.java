package system.controller.listener;

import system.model.Model;
import system.model.Person;

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

package system.controller.page.listener;

import system.model.Client;

/**
 * Created by vladimir on 01.04.2018.
 */
public interface ControllerListener {
    void cancelDisable();

    void cancelEnable();

    void setClient(Client client);

    void refresh();
}

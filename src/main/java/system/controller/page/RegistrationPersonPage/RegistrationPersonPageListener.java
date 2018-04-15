package system.controller.page.RegistrationPersonPage;

import system.controller.page.listener.ControllerListener;
import system.model.Client;

/**
 * Created by vladimir on 14.04.2018.
 */
public class RegistrationPersonPageListener implements ControllerListener {

    private RegistrationPersonControllerInit controller;

    public RegistrationPersonPageListener(RegistrationPersonControllerInit controller) {
        this.controller = controller;
    }

    @Override
    public void cancelDisable() {

    }

    @Override
    public void cancelEnable() {

    }

    @Override
    public void setClient(Client client) {
        controller.setMessage("Клиент уже заведен в системе");
        controller.disabledButton();
    }

    @Override
    public void refresh() {
        controller.clear();
    }
}

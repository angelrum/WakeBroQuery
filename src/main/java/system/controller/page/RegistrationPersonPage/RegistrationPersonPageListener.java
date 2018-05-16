package system.controller.page.RegistrationPersonPage;

import javafx.stage.Stage;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.model.Client;

/**
 * Created by vladimir on 14.04.2018.
 */
public class RegistrationPersonPageListener implements Controller {

    private RegistrationPersonControllerInit controller;

    public RegistrationPersonPageListener(RegistrationPersonControllerInit controller) {
        this.controller = controller;
    }

    @Override
    public void setStage(Stage stage) {

    }

    @Override
    public void setListener(ActiveListener listener) {

    }

    @Override
    public void setActiveClient(Client client) {
        controller.validateTelNumber();
        controller.disabledButton();
    }

    @Override
    public void execute(Command command) {
        controller.clear();
    }
}

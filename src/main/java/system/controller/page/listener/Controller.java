package system.controller.page.listener;

import javafx.stage.Stage;
import system.model.Client;

/**
 * Created by vladimir on 06.05.2018.
 */
public interface Controller {

    void setStage(Stage stage);

    void setListener(ActiveListener listener);

    void setActiveClient(Client client);

    void execute(Command command);

}

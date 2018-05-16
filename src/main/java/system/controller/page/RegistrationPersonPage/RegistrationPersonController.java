package system.controller.page.RegistrationPersonPage;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import system.controller.page.helper.NumberTextField;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.model.Client;

import java.util.Objects;

/**
 * Created by vladimir on 14.04.2018.
 */
public class RegistrationPersonController implements Controller {

    @FXML
    protected NumberTextField telNumber;

    @FXML
    protected JFXTextField fname;

    @FXML
    protected JFXTextField sname;

    @FXML
    protected JFXTextField lname;

    @FXML
    protected JFXTextField city;

    @FXML
    protected Button ok;

    @FXML
    protected Button cancel;

    protected Stage dialogStage;

    private RegistrationPersonControllerInit controllerInit;

    private ActiveListener listener; //ссылка на BasicPageActive

    public void setListener(ActiveListener listener) {
        this.listener = listener;
        this.telNumber.setText(listener.getInsertNumber());
    }

    @FXML
    public void initialize() {
        controllerInit = new RegistrationPersonControllerInit(this);
        controllerInit.init();
    }

    @FXML
    public void clickOk() {
        controllerInit.clickOk();
    }

    @FXML
    public void clickCancel() {
        controllerInit.close();
    }

    protected void insertClientInBasicPage(Client client) {
        if (!Objects.isNull(listener)) listener.setClient(client);
    }

    @Override
    public void setStage(Stage stage) {
        this.dialogStage = stage;
    }

    @Override
    public void setActiveClient(Client client) {

    }

    @Override
    public void execute(Command command) {

    }
}

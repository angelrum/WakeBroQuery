package system.controller.page.RegistrationPersonPage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import system.controller.page.helper.NumberTextField;
import system.controller.page.listener.ControllerActiveListener;
import system.model.Client;

import java.util.Objects;

/**
 * Created by vladimir on 14.04.2018.
 */
public class RegistrationPersonController {

    @FXML
    protected NumberTextField telNumber;

    @FXML
    protected TextField fname;

    @FXML
    protected TextField sname;

    @FXML
    protected TextField lname;

    @FXML
    protected TextField city;

    @FXML
    protected Button ok;

    @FXML
    protected Button cancel;

    @FXML
    protected Label message;

    @FXML
    protected Label lFname;

    @FXML
    protected Label lSname;

    @FXML
    protected Label lLname;

    @FXML
    protected Label lTelNumber;

    @FXML
    protected Label lCity;

    protected Stage dialogStage;

    private RegistrationPersonControllerInit controllerInit;

    private ControllerActiveListener listener; //ссылка на BasicPageControllerActive

    public ControllerActiveListener getListener() {
        return listener;
    }

    public void setListener(ControllerActiveListener listener) {
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

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    protected void insertClientInBasicPage(Client client) {
        if (!Objects.isNull(listener)) listener.setClient(client);
    }
}

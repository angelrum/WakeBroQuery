package system.controller.page.BasicPage;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.controller.page.listener.ControllerActiveListener;
import system.controller.page.helper.ButtonSubmit;
import system.controller.page.helper.NumberTextField;
import system.controller.page.listener.ControllerListener;
import system.controller.to.QueueRow;
import system.model.Client;

/**
 * Created by vladimir on 01.04.2018.
 */
public class BasicPageController implements ControllerListener{
    @FXML
    protected NumberTextField telNumber;

    @FXML
    protected Text fname;

    @FXML
    protected Text lname;

    @FXML
    protected Text sname;

    @FXML
    protected Button cancel;

    @FXML
    protected ButtonSubmit submit;

    @FXML
    protected TableView<QueueRow> queue;

    @FXML
    protected TableColumn<QueueRow, Number> idColumn;

    @FXML
    protected TableColumn<QueueRow, VBox> lControl;

    @FXML
    protected TableColumn<QueueRow, Label> lStatusColumn;

    @FXML
    protected TableColumn<QueueRow, CheckBox> cStatusColumn;

    @FXML
    protected TableColumn<QueueRow, Button> deleteColumn;

    @FXML
    protected TableColumn<QueueRow, String> fioColumn;

    @FXML
    protected TableColumn<QueueRow, String> abnColumn;

    @FXML
    protected Button play;

    @FXML
    protected Button stop;

    @FXML
    protected Text timer;

    @FXML
    protected Text total;

    protected int clientId;

    private ControllerActiveListener controllerActive;
    
    @FXML
    public void initialize() {
        controllerActive = new BasicPageControllerActive(this);
        new BasicPageControllerInit(this).init();
        new StopWatchController(this).init();
    }

    @FXML
    public void clickCancel() {
        controllerActive.clickCancelButton();
    }

    @FXML
    public void clickSubmit() {
        controllerActive.clickSubmitButton();
    }

    public void refresh() {
        controllerActive.refresh();
    }

    public void clear() {controllerActive.clear();}

    public void cancelEnable() {
        cancel.setDisable(false);
    }

    public void cancelDisable() {
        cancel.setDisable(true);
    }

    @Override
    public void setClient(Client client) {
        controllerActive.setClient(client);
    }

    public int getClientId() {
        return clientId;
    }

    public ControllerActiveListener getControllerActive() {
        return controllerActive;
    }
}

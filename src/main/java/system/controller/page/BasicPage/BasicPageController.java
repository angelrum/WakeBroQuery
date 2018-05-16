package system.controller.page.BasicPage;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.controller.page.helper.ButtonSubmit;
import system.controller.page.helper.NumberTextField;
import system.controller.to.QueueRow;
import system.model.Client;

/**
 * Created by vladimir on 01.04.2018.
 */
public class BasicPageController implements Controller {
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

    protected Client client;

    private ActiveListener controllerActive;
    
    @FXML
    public void initialize() {
        controllerActive = new BasicPageActive(this);
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

    //public void clear() {controllerActive.clear();}

    public void cancelEnable() {
        cancel.setDisable(false);
    }

    public void cancelDisable() {
        cancel.setDisable(true);
    }

    public ActiveListener getControllerActive() {
        return controllerActive;
    }
    @Override
    public void setStage(Stage stage) {

    }

    @Override
    public void setListener(ActiveListener listener) {

    }

    @Override
    public void setActiveClient(Client client) {
        controllerActive.setClient(client);
    }

    @Override
    public void execute(Command command) {
        switch (command) {
            case CANCEL_DISABLE:
                cancelDisable();
                break;
            case CANCEL_ENABLE:
                cancelEnable();
                break;
            case REFRESH:
                refresh();
        }

    }
}

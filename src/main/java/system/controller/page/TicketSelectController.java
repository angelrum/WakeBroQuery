package system.controller.page;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import system.controller.to.TicketRow;

/**
 * Created by vladimir on 08.04.2018.
 */
public class TicketSelectController {

    @FXML
    protected TableView<TicketRow> tableView;

    @FXML
    protected TableColumn<TicketRow, String> type;

    @FXML
    protected TableColumn<TicketRow, String> name;

    @FXML
    protected TableColumn<TicketRow, CheckBox> equipment;

    @FXML
    protected TableColumn<TicketRow, Number> duration;

    @FXML
    protected TableColumn<TicketRow, String> start;

    @FXML
    protected TableColumn<TicketRow, String> end;

    @FXML
    protected TableColumn<TicketRow, String> month;

    @FXML
    public void clickOk() {

    }

    @FXML
    public void clickCancel() {

    }

    @FXML
    public void initialize() {
        new TicketSelectControllerInit(this).init();
    }

}

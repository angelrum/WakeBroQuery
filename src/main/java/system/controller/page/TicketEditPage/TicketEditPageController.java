package system.controller.page.TicketEditPage;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.model.Client;
import system.model.Ticket;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by vladimir on 02.05.2018.
 */
public class TicketEditPageController implements Controller {

    @FXML protected TableView<Ticket> ticketView;

    @FXML protected TableColumn<Ticket, String> passColumn;

    @FXML protected TableColumn<Ticket, String> nameColumn;

    @FXML protected TableColumn<Ticket, Boolean> enableColumn;

    @FXML protected TableColumn<Ticket, Boolean> equipmentColumn;

    @FXML protected TableColumn<Ticket, Integer> durationColumn;

    @FXML protected TableColumn<Ticket, LocalDate> startDateColumn;

    @FXML protected TableColumn<Ticket, LocalDate> endDateColumn;

    @FXML protected TableColumn<Ticket, LocalTime> startTimeColumn;

    @FXML protected TableColumn<Ticket, LocalTime> endTimeColumn;

    @FXML protected TableColumn<Ticket, Integer> monthColumn;

    @FXML protected TableColumn<Ticket, Integer> yearColumn;

    @FXML protected TableColumn<Ticket, Double> costColumn;

    @FXML protected TableColumn<Ticket, Double> weekcostColumn;

    @FXML protected TableColumn<Ticket, StackPane> delete;

    private TicketEditPageControllerInit controller;

    protected Stage stage;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        controller = new TicketEditPageControllerInit(this);
        controller.init();
    }

    @FXML
    public void clickOk() {
        controller.save();
    }

    @FXML
    public void clickCancel() {
        controller.cancel();
    }

    @FXML
    public void clickCreate() {
        controller.create();
    }

    @Override
    public void setListener(ActiveListener listener) {

    }

    @Override
    public void setActiveClient(Client client) {

    }

    @Override
    public void execute(Command command) {

    }
}

package system.controller.page.TicketEditPage;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import system.model.Pass;
import system.model.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by vladimir on 02.05.2018.
 */
public class TicketEditPageController {

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

    @FXML
    public void initialize() {
        System.out.println("TicketEditPageController init");
        new TicketEditPageControllerInit(this).init();
    }
}

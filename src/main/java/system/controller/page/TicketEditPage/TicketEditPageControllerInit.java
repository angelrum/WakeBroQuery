package system.controller.page.TicketEditPage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import system.controller.SpringContextUtil;
import system.controller.page.helper.DatePickerCell;
import system.controller.page.helper.DoubleConverter;
import system.controller.page.helper.TimeConverter;
import system.model.Pass;
import system.model.Ticket;
import system.service.TicketService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vladimir on 02.05.2018.
 */
public class TicketEditPageControllerInit {

    private TicketEditPageController controller;

    private TicketService service = SpringContextUtil.getInstance().getBean(TicketService.class);

    private Integer[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    public TicketEditPageControllerInit(TicketEditPageController controller) {
        this.controller = controller;
    }

    public void init() {
        controller.passColumn.setCellValueFactory(param -> {
            Ticket ticket = param.getValue();
            return new SimpleStringProperty(ticket.getPass().getName());
        });
        controller.nameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name"));
        controller.enableColumn.setCellValueFactory(param -> {
            Ticket ticket = param.getValue();
            SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(ticket.isEnable());
            booleanProperty.addListener((observable, oldValue, newValue) -> ticket.setEnable(newValue));
            return booleanProperty;
        });

        controller.equipmentColumn.setCellValueFactory(param -> {
            Ticket ticket = param.getValue();
            SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(ticket.isEquipment());
            booleanProperty.addListener((observable, oldValue, newValue) -> ticket.setEquipment(newValue));
            return booleanProperty;
        });

        controller.durationColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("duration"));
        controller.startDateColumn.setCellValueFactory(new PropertyValueFactory<Ticket, LocalDate>("startDate"));
        controller.endDateColumn.setCellValueFactory(new PropertyValueFactory<Ticket, LocalDate>("endDate"));
        controller.startTimeColumn.setCellValueFactory(cellData->new SimpleObjectProperty<LocalTime>(cellData.getValue().getStartTime()));
        controller.endTimeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, LocalTime>("endTime"));
        controller.monthColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("month"));
        controller.costColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Double>("cost"));
        controller.weekcostColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Double>("weekendcost"));
        initEdit();
    }

    public void initEdit() {
        ObservableList<Ticket> tickets = FXCollections.observableArrayList(service.getAll());
        ObservableList<String> passes = FXCollections.observableArrayList(Stream.of(Pass.values()).map(Pass::getName).collect(Collectors.toList()));

        controller.passColumn.setCellFactory(ComboBoxTableCell.forTableColumn(passes));
        controller.passColumn.setOnEditCommit(event -> {
            TablePosition<Ticket, String> pos = event.getTablePosition();
            Ticket ticket = pos.getTableView().getItems().get(pos.getRow());
            ticket.setPass(Pass.getPassByName(event.getNewValue()));
        });

        controller.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        controller.enableColumn.setCellFactory(param -> new CheckBoxTableCell<Ticket, Boolean>());
        controller.equipmentColumn.setCellFactory(param -> new CheckBoxTableCell<Ticket, Boolean>());
        controller.durationColumn.editableProperty().setValue(false);
        controller.startDateColumn.setCellFactory(param -> new DatePickerCell(tickets));
        controller.endDateColumn.setCellFactory(param -> new DatePickerCell(tickets));
        controller.startTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new TimeConverter()));
        controller.endTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new TimeConverter()));
        ObservableList<Integer> month = FXCollections.observableArrayList(months);
        controller.monthColumn.setCellFactory(ComboBoxTableCell.forTableColumn(month));
        controller.costColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleConverter()));
        controller.weekcostColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleConverter()));
        controller.ticketView.setItems(tickets);
    }
}

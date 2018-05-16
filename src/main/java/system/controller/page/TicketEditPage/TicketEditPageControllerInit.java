package system.controller.page.TicketEditPage;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import org.springframework.util.StringUtils;
import system.controller.SpringContextUtil;
import system.controller.page.helper.*;
import system.controller.page.helper.DateCell;
import system.model.Pass;
import system.model.Ticket;
import system.service.TicketService;
import system.view.FactoryPage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static system.util.ValidationUtil.*;

/**
 * Created by vladimir on 02.05.2018.
 */
public class TicketEditPageControllerInit {

    private TicketEditPageController controller;
    private TicketService service = SpringContextUtil.getInstance().getBean(TicketService.class);
    private Integer[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    private Set<Ticket> bufferUpd = new HashSet<>();

    private Set<Ticket> bufferDel = new HashSet<>();


    public TicketEditPageControllerInit(TicketEditPageController controller) {
        this.controller = controller;
    }

    public void init() {
        controller.passColumn.setCellValueFactory(param -> {
            Ticket ticket = param.getValue();
            return new SimpleStringProperty(ticket.getPass()!=null ? ticket.getPass().getName() : null);
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
        controller.startTimeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, LocalTime>("startTime"));
        controller.endTimeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, LocalTime>("endTime"));
        controller.monthColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("month"));
        controller.costColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Double>("cost"));
        controller.weekcostColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Double>("weekendcost"));
        controller.delete.setCellValueFactory(param -> new SimpleObjectProperty<StackPane>(getButtonDelete(param.getValue())));
        initEdit();
        initEvent();
    }

    private void initEdit() {
        ObservableList<Ticket> tickets = FXCollections.observableArrayList(service.getAll());
        ObservableList<String> passes = FXCollections.observableArrayList(Stream.of(Pass.values()).map(Pass::getName).collect(Collectors.toList()));
        ObservableList<Integer> month = FXCollections.observableArrayList(months);

        controller.passColumn.setCellFactory(param -> new ComboBoxCell<String>(tickets, bufferUpd, passes, "pass"));
        //controller.nameColumn.setCellFactory((TextFieldTableCell.forTableColumn()));
        controller.nameColumn.setCellFactory(param -> new TableCell<Ticket, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                JFXTextField field = new JFXTextField();
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if(isEditing()) {
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                } else {
                    field.setText(item);
                    setText(item);
                    setGraphic(field);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });
        controller.enableColumn.setCellFactory(param -> new CheckBoxCell(tickets, bufferUpd, null, "enable"));
        controller.equipmentColumn.setCellFactory(param -> new CheckBoxCell(tickets, bufferUpd, null, "equipment"));
        controller.durationColumn.editableProperty().setValue(false);
        controller.startDateColumn.setCellFactory(param -> new DateCell(tickets, bufferUpd, "startDate"));
        controller.endDateColumn.setCellFactory(param -> new DateCell(tickets, bufferUpd, "endDate"));
        controller.startTimeColumn.setCellFactory(param -> new TimePickerCell(tickets, bufferUpd, "startTime"));
        controller.endTimeColumn.setCellFactory(param -> new TimePickerCell(tickets, bufferUpd, "endTime"));
        controller.monthColumn.setCellFactory(param -> new ComboBoxCell<Integer>(tickets, bufferUpd, month, "month"));
        controller.costColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleConverter()));
        controller.weekcostColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleConverter()));
        controller.ticketView.setItems(tickets);
    }

    private void initEvent() {
        //controller.passColumn.setOnEditCommit(event -> getEditTicket(event).setPass(Pass.getPassByName(event.getNewValue())));
        controller.nameColumn.setOnEditCommit(event -> getEditTicket(event).setName(event.getNewValue()));
        //controller.startTimeColumn.setOnEditCommit(event -> getEditTicket(event).setStartTime(event.getNewValue()));
        //controller.endTimeColumn.setOnEditCommit(event -> getEditTicket(event).setEndTime(event.getNewValue()));
        //controller.monthColumn.setOnEditCommit(event -> getEditTicket(event).setMonth(event.getNewValue()));
        controller.costColumn.setOnEditCommit(event -> getEditTicket(event).setCost(event.getNewValue()));
        controller.weekcostColumn.setOnEditCommit(event -> getEditTicket(event).setWeekendcost(event.getNewValue()));
        controller.ticketView.setOnMouseClicked(event -> controller.ticketView.getSelectionModel().clearSelection());
    }

    private <T> Ticket getEditTicket(TableColumn.CellEditEvent<Ticket, T> event) {
        TablePosition<Ticket, T> pos = event.getTablePosition();
        Ticket ticket = pos.getTableView().getItems().get(pos.getRow());
        bufferUpd.add(ticket);
        return ticket;
    }

    public void save() {
        String err = getError();
        if (StringUtils.hasLength(err)) {
            FactoryPage.getInstance().showAlert(err, controller.stage);
        } else {
            try {
                saveTicket();
                controller.stage.close();
            } catch (IllegalArgumentException e) {
                FactoryPage.getInstance().showAlert("Ошибка при сохранение данных", controller.stage);
            }
        }
    }

    private StackPane getButtonDelete(Ticket ticket) {
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
        icon.setStyleClass("icon");
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(icon);
        icon.setOnMouseClicked(event -> {
            bufferDel.add(ticket);
            controller.ticketView.getItems().remove(ticket);
        });
        return pane;
    }

    private String getError() {
        bufferUpd.removeAll(bufferDel);
        StringBuilder err = new StringBuilder();
        ObservableList<Ticket> tickets = controller.ticketView.getItems();
        for (Ticket ticket : bufferUpd) {
            int pos = tickets.indexOf(ticket);
            err.append(checkTicket(ticket, pos));
        }
        return err.toString();
    }

    private void saveTicket() throws IllegalArgumentException {
        for (Ticket ticket : bufferUpd)
            service.save(ticket);
        for (Ticket ticket : bufferDel) {
            if (!ticket.isNew())
                service.delete(ticket.getId());
        }
    }

    public void create() {
        Ticket ticket = new Ticket();
        controller.ticketView.getItems().add(ticket);
    }

    public void cancel() {
        controller.stage.close();
    }
}

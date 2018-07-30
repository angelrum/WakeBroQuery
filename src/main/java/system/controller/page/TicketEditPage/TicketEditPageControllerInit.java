package system.controller.page.TicketEditPage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import system.controller.SpringContextUtil;
import system.controller.page.helper.TableCell.*;
import system.controller.page.helper.converter.DoubleConverter;
import system.model.Pass;
import system.model.Ticket;
import system.service.TicketService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static system.util.ValidationUtil.*;
import static system.controller.page.helper.FontAwesomeIconHelper.*;
import static system.controller.page.helper.TableHelper.*;

/**
 * Created by vladimir on 02.05.2018.
 */
public class TicketEditPageControllerInit {

    private TicketEditPageController controller;
    private TicketService service = SpringContextUtil.getInstance().getBean(TicketService.class);
    private Integer[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private Integer[] days = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};

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
        controller.dayColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("day"));
        controller.costColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Double>("cost"));
        controller.weekcostColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Double>("weekendcost"));
        controller.delete.setCellValueFactory(param -> new SimpleObjectProperty<StackPane>(getButtonDelete(param.getValue(), bufferDel, controller.ticketView)));
        initEdit();
        initEvent();
    }

    private void initEdit() {
        ObservableList<String> passes = FXCollections.observableArrayList(Stream.of(Pass.values()).map(Pass::getName).collect(Collectors.toList()));
        ObservableList<Integer> month = FXCollections.observableArrayList(months);
        ObservableList<Integer> day = FXCollections.observableArrayList(days);
        ObservableList<Ticket> tickets = FXCollections.observableArrayList(service.getAll());

        controller.passColumn.setCellFactory(param -> new ComboBoxCell<Ticket, String>(bufferUpd, passes, "pass"));
        //controller.nameColumn.setCellFactory((TextFieldTableCell.forTableColumn()));
        controller.nameColumn.setCellFactory(param -> new TextFieldCell<Ticket, String>(bufferUpd, null, "name"));
        controller.enableColumn.setCellFactory(param -> new CheckBoxCell<Ticket>(bufferUpd, null, "enable"));
        controller.equipmentColumn.setCellFactory(param -> new CheckBoxCell<Ticket>(bufferUpd, null, "equipment"));
        controller.durationColumn.editableProperty().setValue(false);
        controller.startDateColumn.setCellFactory(param -> new DatePickerCell<Ticket>(bufferUpd, "startDate"));
        controller.endDateColumn.setCellFactory(param -> new DatePickerCell<Ticket>(bufferUpd, "endDate"));
        controller.startTimeColumn.setCellFactory(param -> new TimePickerCell<Ticket>(bufferUpd, "startTime"));
        controller.endTimeColumn.setCellFactory(param -> new TimePickerCell<Ticket>(bufferUpd, "endTime"));
        controller.dayColumn.setCellFactory(param -> new ComboBoxCell<Ticket, Integer>(bufferUpd, day, "day"));
        controller.monthColumn.setCellFactory(param -> new ComboBoxCell<Ticket, Integer>(bufferUpd, month, "month"));
        controller.costColumn.setCellFactory(param -> new TextFieldCell<Ticket, Double>(bufferUpd, null, "cost", new DoubleConverter()));
        controller.weekcostColumn.setCellFactory(param -> new TextFieldCell<Ticket, Double>(bufferUpd, null, "weekendcost", new DoubleConverter()));
        controller.ticketView.setItems(tickets);
    }

    private void initEvent() {
        controller.nameColumn.setOnEditCommit(event -> getEditObject(event, bufferUpd).setName(event.getNewValue()));
        controller.costColumn.setOnEditCommit(event -> getEditObject(event, bufferUpd).setCost(event.getNewValue()));
        controller.weekcostColumn.setOnEditCommit(event -> getEditObject(event, bufferUpd).setWeekendcost(event.getNewValue()));
        controller.ticketView.setOnMouseClicked(event -> controller.ticketView.getSelectionModel().clearSelection());
    }

    //перенес в TableHelper
//    private <T> Ticket getEditTicket(TableColumn.CellEditEvent<Ticket, T> event) {
//        TablePosition<Ticket, T> pos = event.getTablePosition();
//        Ticket ticket = pos.getTableView().getItems().get(pos.getRow());
//        bufferUpd.add(ticket);
//        return ticket;
//    }

    public void save() {
        String err = getError();
        saveObjects(bufferUpd, bufferDel, service, controller.stage, err);
//        if (StringUtils.hasLength(err)) {
//            FactoryPage.getInstance().showAlert(err, controller.stage);
//        } else {
//            try {
//                saveObject(bufferUpd, bufferDel, service);
//                controller.stage.close();
//            } catch (IllegalArgumentException e) {
//                FactoryPage.getInstance().showAlert("Ошибка при сохранение данных", controller.stage);
//            }
//        }
    }

//    private StackPane getButtonDelete(Ticket ticket) {
//        FontAwesomeIconView icon = FontAwesomeIconHelper.getIconView(FontAwesomeIcon.TRASH_ALT);
//        icon.setOnMouseClicked(event -> {
//            bufferDel.add(ticket);
//            controller.ticketView.getItems().remove(ticket);
//        });
//        return FontAwesomeIconHelper.getPane(icon);
//    }

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

//    private void saveTicket() throws IllegalArgumentException {
//        for (Ticket ticket : bufferUpd)
//            service.save(ticket);
//        for (Ticket ticket : bufferDel) {
//            if (!ticket.isNew())
//                service.delete(ticket.getId());
//        }
//    }

    public void create() {
        createObject(controller.ticketView, new Ticket());
//        Ticket ticket = new Ticket();
//        controller.ticketView.getItems().add(ticket);
//        controller.ticketView.refresh();
    }

    public void cancel() {
        controller.stage.close();
    }
}

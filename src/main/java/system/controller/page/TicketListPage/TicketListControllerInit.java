package system.controller.page.TicketListPage;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import system.controller.SpringContextUtil;
import system.controller.page.helper.TableCell.CheckBoxCell;
import system.controller.page.helper.TableCell.SpinnerCreate;
import system.controller.page.helper.TicketTableHelper;
import system.controller.to.TicketRow;
import system.model.Ticket;
import system.service.TicketService;
import system.service.exception.NotFoundException;

import static system.util.TicketUtil.*;
import static system.controller.page.helper.TicketTableHelper.*;

/**
 * Created by vladimir on 08.04.2018.
 */
public class TicketListControllerInit {

    private boolean activeTime = true;

    private TicketListController controller;

    private TicketService service = SpringContextUtil.getInstance().getBean(TicketService.class);

    public TicketListControllerInit(TicketListController controller) {
        this.controller = controller;
    }

    public void init() {
        controller.type.setCellValueFactory(new PropertyValueFactory<TicketRow, String>("pass"));
        controller.name.setCellValueFactory(new PropertyValueFactory<TicketRow, String>("name"));
        controller.equipment.setCellValueFactory(param -> {
            Ticket ticket = param.getValue().getTicket();
            //booleanProperty.addListener((observable, oldValue, newValue) -> ticket.setEquipment(newValue));
            return new SimpleBooleanProperty(ticket.isEquipment());
        });
        controller.duration.setCellValueFactory(new PropertyValueFactory<TicketRow, Number>("duration"));
        controller.start.setCellValueFactory(new PropertyValueFactory<TicketRow, String>("start"));
        controller.end.setCellValueFactory(new PropertyValueFactory<TicketRow, String>("end"));
        controller.day.setCellValueFactory(new PropertyValueFactory<TicketRow, String>("day"));
        controller.month.setCellValueFactory(new PropertyValueFactory<TicketRow, String>("month"));
        controller.count.setCellValueFactory(cellData -> {
            SpinnerCreate.getInstance().setValue(cellData.getValue());
            return cellData.getValue().countProperty().asObject();
        });
        initEdit();
        initTable();
    }


    private void initEdit() {
        controller.equipment.setCellFactory(param -> new CheckBoxCell<TicketRow>("equipment", true));
        controller.count.setCellFactory(param -> SpinnerCreate.getInstance().getCell("count"));
    }
    private void initTable() {
        try {
            ObservableList<TicketRow> rows = getTicketRowList(getActiveByTime(service.getAllActive(), activeTime));
            controller.tableView.setItems(rows);
            controller.tableView.getSelectionModel().clearSelection();
        } catch (NotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setActiveTime(boolean activeTime) {
        this.activeTime = activeTime;
    }

}

package system.controller.page.ClientTicketPage;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import system.controller.SpringContextUtil;
import system.controller.page.helper.TableCell.CheckBoxCell;
import system.controller.page.helper.TableCell.SpinnerCreate;
import system.controller.to.ClientTicketRow;
import system.model.ClientTicket;
import system.model.Ticket;
import system.service.ClientTicketService;
import system.service.exception.NotFoundException;

import java.util.List;
import static system.controller.page.helper.TicketTableHelper.*;
import static system.util.TicketUtil.*;

/**
 * Created by vladimir on 05.04.2018.
 */
public class ClientTicketControllerInit {

    private ClientTicketController controller;

    private boolean activeList = true;

    private ClientTicketService service = SpringContextUtil.getInstance().getBean(ClientTicketService.class);

    public ClientTicketControllerInit(ClientTicketController controller) {
        this.controller = controller;
    }

    public void init() {
        tableRowInit();
        tableInit();

    }

    private void tableRowInit() {
        controller.type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        controller.equipment.setCellValueFactory(param -> {
            Ticket ticket = param.getValue().getTicket();
            return new SimpleBooleanProperty(ticket.isEquipment());
        });
        controller.equipment.setCellFactory(param -> new CheckBoxCell<ClientTicketRow>("equipment", true));
        controller.start.setCellValueFactory(cellData->cellData.getValue().startProperty());
        controller.end.setCellValueFactory(cellData->cellData.getValue().endProperty());
        controller.dateEnd.setCellValueFactory(cellData->cellData.getValue().dateEndProperty());
        controller.maxCount.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getMaxCount()));
        //Редактируем только кол-во
        controller.count.setCellFactory(param -> SpinnerCreate.getInstance().getCell("count"));
        controller.count.setCellValueFactory(cellData-> {
            SpinnerCreate.getInstance().setValue(cellData.getValue());
            return new SimpleIntegerProperty(cellData.getValue().getCount()).asObject();
        });
        controller.duration.setCellValueFactory(cellData->cellData.getValue().durationProperty());
    }

    private void tableInit() {
        try {
            List<ClientTicket> ticketList = getActiveByTimeForClient(service.getAllActiveTicket(controller.client.getId()), activeList);
            ticketList = removeTicketWhichInQueue(ticketList);
            controller.tickets.setItems(getClientTicketRowList(ticketList));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        controller.tickets
                .getSelectionModel().clearSelection(); //снимаем выбор по-умолчанию.
    }

    public void setActiveList(boolean activeList) {
        this.activeList = activeList;
    }
}

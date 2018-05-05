package system.controller.page.ClientTicketPage;

import system.controller.SpringContextUtil;
import system.controller.page.helper.TicketTableHelper;
import system.model.ClientTicket;
import system.model.Ticket;
import system.service.ClientTicketService;
import system.service.CustomerTicketService;
import system.service.exception.NotFoundException;

import java.util.Map;

/**
 * Created by vladimir on 05.04.2018.
 */
public class ClientTicketControllerInit {

    private ClientTicketController controller;

    //private CustomerTicketService service = SpringContextUtil.getInstance().getBean(CustomerTicketService.class);

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
        controller.equipment.setCellValueFactory(cellData -> cellData.getValue().getEquipment());
        controller.start.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        controller.end.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        controller.dateEnd.setCellValueFactory(cellData -> cellData.getValue().dateEndProperty());
        controller.maxCount.setCellValueFactory(cellData->cellData.getValue().maxCountProperty());
        controller.control.setCellValueFactory(cellData -> cellData.getValue().getControl());
        controller.count.setCellValueFactory(cellData -> cellData.getValue().getCount());
        controller.duration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
    }

    private void tableInit() {
        try {
//            Map<ClientTicket, Ticket> allActiveTicket = service.getAllActiveTicket(controller.clientId);
//            controller.tickets.setItems(TicketTableHelper.getClientTicketRowList(allActiveTicket));
            controller.tickets.setItems(TicketTableHelper.getClientTicketRowList(service.getAllActiveTicket(controller.clientId)));
        } catch (NotFoundException e) {
            //ignore
        }
        controller.tickets.getSelectionModel().clearSelection(); //снимаем выбор по-умолчанию.
    }
}

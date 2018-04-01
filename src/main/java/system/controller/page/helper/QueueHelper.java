package system.controller.page.helper;

import system.controller.Queue;
import system.controller.SpringContextUtil;
import system.controller.to.QueueRow;
import system.model.ClientTicket;
import system.model.Ticket;
import system.service.ClientService;

/**
 * Created by vladimir on 01.04.2018.
 */
public class QueueHelper {
    private static ClientService service = SpringContextUtil.getInstance().getBean(ClientService.class);

    public static QueueRow transformToQueueRow(ClientTicket clientTicket, Ticket ticket) {
        QueueRow row = new QueueRow(service.get(clientTicket.getClientId()), clientTicket, ticket);
        return row;
    }
}

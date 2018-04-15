package system.controller.page.helper;

import system.controller.Queue;
import system.controller.SpringContextUtil;
import system.controller.to.QueueRow;
import system.controller.to.ClientTicketRow;
import system.model.Client;
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
        row.setListener(Queue.getInstance());
        return row;
    }

    public static QueueRow transformToQueueRow(ClientTicketRow row) {
        Client client = service.get(row.getClientTicket().getClientId());
        QueueRow rowQueue = new QueueRow(client, row.getClientTicket(), row.getTicket(), row.getCount().intValue());
        rowQueue.setListener(Queue.getInstance());
        return rowQueue;
    }
}

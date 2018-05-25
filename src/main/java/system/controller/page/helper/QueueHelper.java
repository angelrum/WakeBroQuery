package system.controller.page.helper;

import org.springframework.util.CollectionUtils;
import system.controller.Queue;
import system.controller.SpringContextUtil;
import system.controller.to.QueueRow;
import system.controller.to.ClientTicketRow;
import system.model.Client;
import system.model.ClientTicket;
import system.model.Ticket;
import system.service.ClientService;

import java.util.Collections;

/**
 * Created by vladimir on 01.04.2018.
 */
public class QueueHelper {

    public static QueueRow transformToQueueRow(ClientTicketRow row) {
        int count = row.getCount();
        QueueRow queueRow = new QueueRow(row.getTickets().subList(0, count));
        queueRow.setListener(Queue.getInstance());
        return queueRow;
    }

    public static boolean checkNotEmptyActiveQueue() {
        Queue queue = Queue.getInstance();
        return !CollectionUtils.isEmpty(queue.getActiveQueue());
    }
}

package system.controller.page.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.controller.Queue;
import system.controller.to.ClientTicketRow;
import system.controller.to.QueueRow;
import system.controller.to.TicketRow;
import system.model.ClientTicket;
import system.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vladimir on 05.04.2018.
 *
 *
 */
public class TicketTableHelper {
    /*
    * Map<Integer, ClientTicketRow>, где:
    * @key - id билета;
    * @ClientTicketRow - обертка для билета пользователя
    * */

    public static ObservableList<ClientTicketRow> getClientTicketRowList(List<ClientTicket> list) {
        Map<Integer, ClientTicketRow> tmp = new HashMap<>();
        for (ClientTicket ticket : list) {
            ClientTicketRow row = tmp.computeIfAbsent(ticket.getTicket().getId(), key -> new ClientTicketRow());
            row.addValue(ticket);
        }
        return FXCollections.observableArrayList(tmp.values());
    }


    public static ObservableList<TicketRow> getTicketRowList(List<Ticket> list) {
        List<TicketRow> rowList = new ArrayList<>();
        list.forEach(ticket -> rowList.add(new TicketRow(ticket)));
        ObservableList<TicketRow> rows = FXCollections.observableArrayList(rowList);
        return rows;
    }

    public static List<ClientTicket> removeTicketWhichInQueue(List<ClientTicket> tickets) {
        removeByList(Queue.getInstance().getActiveQueue(), tickets); //Удаляем по активному списку
        removeByList(Queue.getInstance().getDisactiveQueue(), tickets); //Удаляем по не активному списку
        return tickets;
    }

    private static void removeByList(List<QueueRow> queueRows, List<ClientTicket> list) {
        queueRows.forEach(row ->
                row.getTickets().forEach(ct1 ->
                        list.removeIf(ct2 ->
                                ct2.getId().compareTo(ct1.getId())==0)));
    }
}

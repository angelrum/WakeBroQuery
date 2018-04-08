package system.controller.page.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.controller.to.ClientTicketRow;
import system.controller.to.TicketRow;
import system.model.ClientTicket;
import system.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vladimir on 05.04.2018.
 */
public class TicketTableHelper {

    public static ObservableList<ClientTicketRow> getClientTicketRowList(Map<ClientTicket, Ticket> map) {
        Map<Integer, ClientTicketRow> tmpMap = new HashMap<>();
        for (Map.Entry<ClientTicket, Ticket> entry : map.entrySet()) {
            ClientTicketRow row = tmpMap.computeIfAbsent(entry.getValue().getId(), integer -> new ClientTicketRow(entry.getValue(), entry.getKey()));
            row.setMaxCount(row.getCount().intValue() + 1);
        }
        return FXCollections.observableArrayList(tmpMap.values());
    }

    public static ObservableList<TicketRow> getTicketRowList(List<Ticket> list) {
        List<TicketRow> rowList = new ArrayList<>();
        list.forEach(ticket -> rowList.add(new TicketRow(ticket)));
        ObservableList<TicketRow> rows = FXCollections.observableArrayList(rowList);
        return rows;
    }
}

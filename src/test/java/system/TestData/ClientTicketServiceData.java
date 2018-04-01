package system.TestData;

import system.model.ClientTicket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vladimir on 30.03.2018.
 */
public class ClientTicketServiceData {
    private static List<ClientTicket> list = new ArrayList<>();

    public final static ClientTicket NEW_TICKET = new ClientTicket(0, 0, 4);

    public final static ClientTicket DELETE_TICKET = new ClientTicket(0, 0, 0);



    public static void init() {
        ClientTicket ticket1 = new ClientTicket(0, 0, 1);
        ClientTicket ticket3 = new ClientTicket(0, 0, 2, LocalDate.of(2017, 12, 3), LocalDate.of(2017, 12, 15));

        ticket1.setId(0);
        DELETE_TICKET.setId(1);
        ticket1.setId(2);

        list.add(ticket1);
        list.add(DELETE_TICKET);
        list.add(ticket3);
    }

    public static void clear() {
        list.clear();
    }

    public static List<ClientTicket> getList() {
        return list;
    }

    public static List<ClientTicket> getActiveTickect() {
        return list.stream()
                .filter(ClientTicket::isActive)
                .collect(Collectors.toList());
    }

    public static List<ClientTicket> withoutDeleteTicket() {
        list.remove(1);
        return list;
    }
}

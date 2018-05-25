package system.util;

import system.model.ClientTicket;
import system.model.Ticket;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vladimir on 19.03.2018.
 */
public class TicketUtil {
    public static final Integer TICKET_DURATION = 5;

    public static List<Ticket> getActiveByTime(List<Ticket> tickets, boolean active) {
        if (active) {
            LocalTime now = LocalTime.now();
            return tickets.stream().filter(ticket -> ticket.getStartTime().isBefore(now) && ticket.getEndTime().isAfter(now)).collect(Collectors.toList());
        }
        return tickets;
    }

    public static List<ClientTicket> getActiveByTimeForClient(List<ClientTicket> tickets, boolean active) {
        if (active) {
            LocalTime now = LocalTime.now();
            return tickets.stream()
                    .filter(clientTicket ->
                            clientTicket.getTicket().getStartTime().isBefore(now) && clientTicket.getTicket().getEndTime().isAfter(now))
                    .collect(Collectors.toList());
        }
        return tickets;
    }
}

package system.TestData;

import system.model.Pass;
import system.model.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vladimir on 29.03.2018.
 */
public class TicketServiceData {
    private static final Map<Integer, Ticket> TICKETS = new HashMap<>();

    private static final Ticket UPDATE_TICKET = new Ticket(1, Pass.ABN_PASS, "Абонемент(Утренний)Update", true, LocalTime.of(10, 0), LocalTime.of(12, 0), LocalDate.now().getYear(), 3, 100, 200);
    public static final Ticket NEW_TICKET = new Ticket(1, Pass.ABN_PASS, "Абонемент(Утренний)newTicket", true, LocalTime.of(10, 0), LocalTime.of(12, 0), LocalDate.now().getYear(), 3, 100, 200);


    public static void init() {
        TICKETS.clear();
        Ticket ticket1 = new Ticket(1, Pass.ABN_PASS, "Абонемент(Утренний)", true, LocalTime.of(9, 0), LocalTime.of(12, 0), LocalDate.now().getYear(), 1, 100, 200);
        Ticket ticket2 = new Ticket(1,"Сет(Утренний)", true, LocalTime.of(9, 0), LocalTime.of(12, 0), LocalDate.now().getYear(),100, 200);
        ticket1.setId(0);
        ticket2.setId(1);
        TICKETS.put(ticket1.getId(), ticket1);
        TICKETS.put(ticket2.getId(), ticket2);
    }

    public static Collection<Ticket> getAll() {
        return TICKETS.values();
    }

    public static Ticket getUpdateTicket () {
        UPDATE_TICKET.setId(0);
        TICKETS.put(UPDATE_TICKET.getId(), UPDATE_TICKET);
        return UPDATE_TICKET;
    }

    public static Ticket getThrowTicket() {
        UPDATE_TICKET.setId(10);
        return UPDATE_TICKET;
    }

    public static Collection<Ticket> getAllWithNewTicket() {
        NEW_TICKET.setId(2);
        TICKETS.put(NEW_TICKET.getId(), NEW_TICKET);
        return TICKETS.values();
    }

    public static Collection<Ticket> getWithoutDeleteTicket() {
        TICKETS.remove(0);
        return getAll();
    }

}

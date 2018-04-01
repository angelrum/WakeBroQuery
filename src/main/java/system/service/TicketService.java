package system.service;

import system.model.Ticket;

import java.util.List;

/**
 * Created by vladimir on 20.03.2018.
 */
public interface TicketService {
    Ticket save(Ticket ticket);

    void delete(int id);

    List<Ticket> getAll();

    Ticket get(int ticketId);
}

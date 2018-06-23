package system.service;

import system.model.Ticket;

import java.util.List;

/**
 * Created by vladimir on 20.03.2018.
 */
public interface TicketService extends AbstractService<Ticket> {

    List<Ticket> getAllActive();

    Ticket get(int ticketId);
}

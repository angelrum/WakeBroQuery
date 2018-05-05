package system.service;

import system.model.ClientTicket;
import system.model.Ticket;
import system.service.exception.NotFoundException;

import java.util.List;

/**
 * Created by vladimir on 19.03.2018.
 */
public interface ClientTicketService {

    List<ClientTicket> getAllTicket(int clientId) throws NotFoundException;

    List<ClientTicket> getAllActiveTicket(int clientId) throws NotFoundException;

    ClientTicket save(int clientId, Ticket ticket);

    ClientTicket save(ClientTicket clientTicket);

    void delete(int clientId, int ticketId);
}
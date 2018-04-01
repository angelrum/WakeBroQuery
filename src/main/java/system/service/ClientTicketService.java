package system.service;

import system.model.Client;
import system.model.ClientTicket;
import system.service.exception.NotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Created by vladimir on 19.03.2018.
 */
public interface ClientTicketService {

    List<ClientTicket> getAllTicket(int clientId) throws NotFoundException;

    List<ClientTicket> getAllActiveTicket(int clientId) throws NotFoundException;

    ClientTicket save(int createId, int clientId, int ticketId);

    void deleteTicket(int clientId, int ticketId);
}
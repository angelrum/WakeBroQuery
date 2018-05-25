package system.service;

import system.model.ClientTicket;
import system.model.Ticket;

import java.util.Map;

/**
 * Created by vladimir on 01.04.2018.
 */
@Deprecated
public interface CustomerTicketService {

    Map<ClientTicket, Ticket> getAll(int clientId);

    Map<ClientTicket, Ticket> getAllActiveTicket(int clientId);
}

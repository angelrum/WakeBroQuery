package system.repository;

import system.model.ClientTicket;

import java.util.List;

/**
 * Created by vladimir on 18.03.2018.
 */
public interface ClientTicketRepository {

    ClientTicket save(ClientTicket clientTicket);

    boolean delete(int clientId, int id);

    List<ClientTicket> getAll(int userId);
}

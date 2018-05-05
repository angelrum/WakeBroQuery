package system.repository;

import system.model.ClientTicket;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by vladimir on 18.03.2018.
 */
public interface ClientTicketRepository {

    ClientTicket save(ClientTicket clientTicket, int clientId, int userId);

    ClientTicket update(ClientTicket clientTicket);

    boolean delete(int clientId, int id);

    List<ClientTicket> getAll(int clientId);

    List<ClientTicket> getActive(int clientId, LocalDate date);
}

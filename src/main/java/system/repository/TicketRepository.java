package system.repository;

import system.model.Ticket;

import java.util.List;

/**
 * Created by vladimir on 18.03.2018.
 */
public interface TicketRepository {

    Ticket save(Ticket ticket);

    boolean delete(int id);

    List<Ticket> getAll();

    Ticket get(int id);
}

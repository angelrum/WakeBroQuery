package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.model.Ticket;
import system.repository.TicketRepository;
import system.service.TicketService;

import java.util.List;
import java.util.stream.Collectors;

import static system.util.ValidationUtil.*;

/**
 * Created by vladimir on 27.03.2018.
 */
@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository repository;

    @Autowired
    public TicketServiceImpl(TicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ticket save(Ticket ticket) throws NullPointerException {
        checkNotNull(ticket);
        return checkNotNull(repository.save(ticket));
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public List<Ticket> getAll() {
        return checkNullOrEmptyList(repository.getAll());
    }

    public List<Ticket> getAllActive() {
        List<Ticket> list = checkNullOrEmptyList(repository.getAll());
        return list.stream()
                .filter(ticket -> ticket.isEnable())
                .collect(Collectors.toList());
    }

    public Ticket get(int ticketId) {
        return checkNotNull(repository.get(ticketId));
    }
}

package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.controller.AuthorizedUser;
import system.model.ClientTicket;
import system.model.Ticket;
import system.repository.ClientTicketRepository;
import system.service.ClientTicketService;
import system.service.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static system.util.ValidationUtil.*;

/**
 * Created by vladimir on 27.03.2018.
 *
 * Создан для взаимодействия с билетами клиента.
 *
 */

@Service
public class ClientTicketServiceImpl implements ClientTicketService {

    private ClientTicketRepository repository;

    @Autowired
    public ClientTicketServiceImpl(ClientTicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ClientTicket> getAllTicket(int clientId) throws NotFoundException {
        return checkNullOrEmptyList(repository.getAll(clientId));
    }

    @Override
    public List<ClientTicket> getAllActiveTicket(int clientId) throws NotFoundException {
        LocalDate date = LocalDate.now();
        return checkNullOrEmptyList(repository.getActive(clientId, date));
    }

    @Override
    public ClientTicket save(int clientId, Ticket ticket) {
        LocalDate start;
        LocalDate end;
        if (Objects.nonNull(ticket.getStartDate())
                && Objects.nonNull(ticket.getEndDate())) {
            start = ticket.getStartDate();
            end = ticket.getEndDate();
        } else if (ticket.getMonth() > 0) {
            start = LocalDate.now();
            end = LocalDate.now().plusMonths(ticket.getMonth());
        } else
            start = end = LocalDate.now();
        ClientTicket clientTicket = new ClientTicket(ticket, start, end);
        return repository.save(clientTicket, clientId, AuthorizedUser.id());
    }

    @Override
    public ClientTicket save(ClientTicket clientTicket) {
        return repository.update(clientTicket);
    }

    @Override
    public void delete(int clientId, int ticketId) {
        checkNotFoundWithId(repository.delete(clientId, ticketId), clientId);
    }
}

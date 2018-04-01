package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.model.ClientTicket;
import system.repository.ClientTicketRepository;
import system.service.ClientTicketService;
import system.service.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static system.util.ValidationUtil.*;

/**
 * Created by vladimir on 27.03.2018.
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
        return getAllTicket(clientId).stream()
                .filter(ClientTicket::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public ClientTicket save(int createId, int clientId, int ticketId) {
        ClientTicket ticket = new ClientTicket(createId, clientId, ticketId);
        return repository.save(ticket);
    }

    @Override
    public void deleteTicket(int clientId, int ticketId) {
        checkNotFoundWithId(repository.delete(clientId, ticketId), clientId);
    }
}

package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.model.ClientTicket;
import system.model.Ticket;
import system.service.ClientService;
import system.service.ClientTicketService;
import system.service.CustomerTicketService;
import system.service.TicketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static system.util.ValidationUtil.*;

/**
 * Created by vladimir on 01.04.2018.
 * Сервис для совместной выгрузки данных по клиентам и их билетам, и работе с ними.
 * Необходим на стадии рабоыт без БД
 */
@Deprecated
@Service
public class CustomerTicketServiceImpl implements CustomerTicketService {

    private ClientService clientService;

    private ClientTicketService clientTicketService;

    private TicketService ticketService;

    @Autowired
    public CustomerTicketServiceImpl(ClientService clientService, ClientTicketService clientTicketService, TicketService ticketService) {
        this.clientService = clientService;
        this.clientTicketService = clientTicketService;
        this.ticketService = ticketService;
    }

    public Map<ClientTicket, Ticket> getAll(int clientId) {
        List<ClientTicket> clientTickets = checkNullOrEmptyList(clientTicketService.getAllTicket(clientId));
        return getTicketList(clientTickets);
    }

    public Map<ClientTicket, Ticket> getAllActiveTicket(int clientId) {
        List<ClientTicket> clientTickets = checkNullOrEmptyList(clientTicketService.getAllActiveTicket(clientId));
        return getTicketList(clientTickets);
    }

    private Ticket getTicket(int ticketId) {
        return ticketService.get(ticketId);
    }

    private Map<ClientTicket, Ticket> getTicketList(List<ClientTicket> clientTickets) {
        Map<ClientTicket, Ticket> map = new HashMap<>();
        clientTickets.forEach(clientTicket -> map.put(clientTicket, getTicket(clientTicket.getTicket().getId())));
        return map;
    }
}

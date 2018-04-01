package system.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.model.ClientTicket;
import system.service.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static system.TestData.ClientTicketServiceData.*;

/**
 * Created by vladimir on 30.03.2018.
 */
class ClientTicketServiceImplTest {

    private ConfigurableApplicationContext ctxApp;

    private ClientTicketService service;

    @BeforeEach
    void setUp() {
        ctxApp = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        service = ctxApp.getBean(ClientTicketService.class);
        init();
    }

    @AfterEach
    void tearDown() {
        ctxApp.close();
        clear();
    }

    @Test
    void getAllTicket() {
        List<ClientTicket> actualy = service.getAllTicket(0);
        assertIterableEquals(getList(), actualy);
    }

    @Test
    void getAllActiveTicket() {
        List<ClientTicket> actually = service.getAllActiveTicket(0);
        assertIterableEquals(getActiveTickect(), actually);
    }

    @Test
    void saveClientTicket() {
        ClientTicket ticket = service.save(NEW_TICKET.getCreateId(), NEW_TICKET.getClientId(), NEW_TICKET.getTicketId());
        NEW_TICKET.setId(ticket.getId());
        assertEquals(NEW_TICKET, ticket);
    }

    @Test
    void deleteTicket() {
        service.deleteTicket(DELETE_TICKET.getClientId(), DELETE_TICKET.getTicketId());
        List<ClientTicket> actual = withoutDeleteTicket();
        List<ClientTicket> expected = service.getAllTicket(0);
        assertIterableEquals(expected, actual);
    }

    @Test
    void deleteThrows() {
        assertThrows(NotFoundException.class, ()->service.deleteTicket(10, 0));
    }

    @Test
    void deleteTicketWithException() {
        assertThrows(NotFoundException.class, ()->service.deleteTicket(10, 0));
    }

}
package system.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.model.Ticket;
import system.service.exception.NotFoundException;

import static system.TestData.TicketServiceData.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by vladimir on 29.03.2018.
 */
class TicketServiceImplTest {

    private TicketService service;
    private ConfigurableApplicationContext ctxApp;

    @BeforeEach
    void setUp() {
        ctxApp = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        service = ctxApp.getBean(TicketService.class);
        init();
    }

    @AfterEach
    void afterEach() {
        ctxApp.close();
    }

    @Test
    void save() {
        Ticket ticket = service.save(NEW_TICKET);
        NEW_TICKET.setId(ticket.getId());
        assertEquals(NEW_TICKET, ticket);
        assertIterableEquals(getAllWithNewTicket(), service.getAll());
    }

    @Test
    void update() {
        service.save(getUpdateTicket());
        assertIterableEquals(getAll(), service.getAll());
    }

    @Test
    void delete() {
        service.delete(0);
        assertIterableEquals(getWithoutDeleteTicket(), service.getAll());
    }

    @Test
    void updateNotFound() {
        assertThrows(NullPointerException.class, ()->service.save(getThrowTicket()));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, ()->service.delete(10));
    }

    @Test
    void getAllTest() {
        assertIterableEquals(getAll(), service.getAll());
    }

}
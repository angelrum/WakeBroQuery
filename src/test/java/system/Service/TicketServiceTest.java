package system.Service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import system.model.Ticket;
import system.service.TicketService;
import system.service.exception.NotFoundException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static system.TestData.ServiceUtil.assertMatchIgnorFields;
import static system.TestData.TicketData.*;

/**
 * Created by vladimir on 01.05.2018.
 */
public class TicketServiceTest extends ServiceTest {
    @Autowired
    public TicketService service;

    @Test
    public void get() {
        Ticket ticket = service.get(ABN_PASS.getId());
        assertThat(ticket).isEqualToIgnoringGivenFields(ABN_PASS, "id", "create");
    }

    @Test
    public void save() {
        Ticket ticket = service.save(NEW_TICKET);
        assertThat(ticket).isEqualToIgnoringGivenFields(NEW_TICKET, "id", "create");
    }

    @Test
    public void update() {
        Ticket ticket = service.get(UPDATE_PASS.getId());
        ticket.setName(UPDATE_PASS.getName());
        service.save(UPDATE_PASS);
        assertThat(service.get(UPDATE_PASS.getId())).isEqualToIgnoringGivenFields(UPDATE_PASS, "id", "create", "user");
    }

    @Test
    public void getUser() {
        Ticket ticket = service.get(ABN_PASS.getId());
        assertThat(ticket.getUser()).isEqualTo(ABN_PASS.getUser());
    }

    @Test
    public void getAll() {
        assertMatchIgnorFields(service.getAll(), Arrays.asList(ABN_PASS, TICKET_PASS2, TICKET_PASS), "id", "create", "user");
    }

    @Test
    public void getAllActive() {
        assertMatchIgnorFields(service.getAllActive(), Arrays.asList(ABN_PASS, TICKET_PASS), "id", "create", "user");
    }

    @Test
    public void delete() {
        service.delete(ABN_PASS.getId());
        assertMatchIgnorFields(service.getAll(), Arrays.asList(TICKET_PASS2, TICKET_PASS), "id", "create", "user");
    }

    @Test(expected = NotFoundException.class)
    public void deleteException() {
        service.delete(1);
    }

    @Test(expected = NullPointerException.class)
    public void getException() {
        service.get(1);
    }

}

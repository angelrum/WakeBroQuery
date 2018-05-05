package system.Service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import system.model.Client;
import system.service.ClientService;
import system.service.exception.NotFoundException;

import static system.TestData.ClientServiceData.*;


/**
 * Created by vladimir on 30.04.2018.
 */
public class ClientServiceTest extends ServiceTest {

    @Autowired
    private ClientService service;

    @Test
    public void get() {
        Client client = service.getByNumber(CLIENT1.getTelnumber());
        assertMatch(client, CLIENT1);
    }

    @Test
    public void save() {
        Client client = service.save(SAVE);
        assertMatch(client, SAVE);
    }

    @Test
    public void delete() {
        service.delete(CLIENT1.getId());
        assertMatch(service.getAll(), CLIENT2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(1);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(), CLIENT1, CLIENT2);
    }

    @Test
    public void update() {
        Client client = service.get(UPDATE.getId());
        client.setFirstname(UPDATE.getFirstname());
        assertMatch(client, UPDATE);
    }

    @Test
    public void getTicket() {
        Client client = service.get(CLIENT1.getId());
        Assertions.assertThat(client.getTickets().size()).isEqualTo(1);
    }

    @Test
    public void getTicketEmpty() {
        Client client = service.get(CLIENT2.getId());
        Assertions.assertThat(client.getTickets()).isEmpty();
    }
}

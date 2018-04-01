package system.repository.mock;

import org.springframework.stereotype.Repository;
import system.model.ClientTicket;
import system.repository.ClientTicketRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vladimir on 18.03.2018.
 */

@Repository
public class InMemoryClientTicketRepository implements ClientTicketRepository {

    //Map<ClientId, Map<id, ClientTicket>
    private Map<Integer, Map<Integer, ClientTicket>> tickets = new HashMap<>();

    private int id = 0;

    @PostConstruct
    public void init() {
        List<ClientTicket> list = Arrays.asList(
                new ClientTicket(0, 0, 1),
                new ClientTicket(0, 0, 0),
                new ClientTicket(0, 0, 2, LocalDate.of(2017, 12, 3), LocalDate.of(2017, 12, 15))
        );
        list.forEach(this::save);
    }

    @Override
    public ClientTicket save(ClientTicket clientTicket) {
        if (clientTicket.isNew())
            clientTicket.setId(id++);
        Map<Integer, ClientTicket> map = tickets.computeIfAbsent(clientTicket.getClientId(), HashMap::new);
        map.put(clientTicket.getId(), clientTicket);
        return clientTicket;
    }

    @Override
    public boolean delete(int clientId, int id) {
        ClientTicket ticket = null;
        Map<Integer, ClientTicket> map = tickets.get(clientId);
        if (map!=null) {
            ticket= map.remove(id);
            if (map.isEmpty())
                tickets.remove(clientId);
        }
        return ticket!=null;
    }

    @Override
    public List<ClientTicket> getAll(int userId) {
        if (tickets.containsKey(userId))
            return tickets.get(userId)
                    .values()
                    .stream()
                    .collect(Collectors.toList());
        return null;
    }
}

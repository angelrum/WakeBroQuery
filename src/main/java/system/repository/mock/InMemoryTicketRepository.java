package system.repository.mock;

import org.springframework.stereotype.Repository;
import system.model.Pass;
import system.model.Ticket;
import system.repository.TicketRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vladimir on 18.03.2018.
 */

@Repository
public class InMemoryTicketRepository implements TicketRepository {

    private Map<Integer, Ticket> tickets = new HashMap<>();
    private int id = 0;

    @PostConstruct
    public void init() {
        List<Ticket> list = Arrays.asList(
                new Ticket(1, Pass.ABN_PASS, "Абонемент(Утренний)", true, LocalTime.of(9, 0), LocalTime.of(12, 0), LocalDate.now().getYear(), 1, 100, 200),
                new Ticket(1,"Сет(Утренний)", true, LocalTime.of(9, 0), LocalTime.of(12, 0), LocalDate.now().getYear(),100, 200)
        );
        list.forEach(this::save);
    }

    @Override
    public Ticket save(Ticket ticket) {
        if (ticket.isNew()){
            ticket.setId(id++);
            tickets.put(ticket.getId(), ticket);
            return ticket;
        }
        return tickets.computeIfPresent(ticket.getId(), (id, oldValue) -> ticket);
    }

    @Override
    public boolean delete(int id) {
        return tickets.remove(id)!=null;
    }

    @Override
    public List<Ticket> getAll() {
        return tickets.values()
                .stream()
                .collect(Collectors.toList());
    }

    public Ticket get(int id) {
        return tickets.get(id);
    }
}

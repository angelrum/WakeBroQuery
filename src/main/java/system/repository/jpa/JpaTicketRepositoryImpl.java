package system.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import system.model.Ticket;
import system.model.User;
import system.repository.TicketRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by vladimir on 29.04.2018.
 */

@Repository
@Transactional(readOnly = true)
public class JpaTicketRepositoryImpl implements TicketRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Ticket save(Ticket ticket, int userId) {
        ticket.setUser(em.getReference(User.class, userId));
        if (ticket.isNew()) {
            em.persist(ticket);
            return ticket;
        }
        return em.merge(ticket);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Ticket.DELETE)
                .setParameter("id", id)
                .executeUpdate()!=0;
    }

    @Override
    public List<Ticket> getAll() {
        return em.createNamedQuery(Ticket.GET_ALL, Ticket.class)
                .getResultList();
    }

    @Override
    public Ticket get(int id) {
        return em.find(Ticket.class, id);
    }

    @Override
    public List<Ticket> getAllActive() {
        return em.createNamedQuery(Ticket.GET_ALL_ACTIVE, Ticket.class)
                .setParameter("date", LocalDate.now())
                .getResultList();
    }
}

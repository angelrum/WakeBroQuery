package system.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import system.model.Client;
import system.model.ClientTicket;
import system.model.User;
import system.repository.ClientTicketRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by vladimir on 28.04.2018.
 *
 * Считаем, что изменить можно только параметр @active, поэтому ссылки при изменение не перечитываем
 */
@Repository
@Transactional(readOnly = true)
public class JpaClientTicketRepositoryImpl implements ClientTicketRepository {

    @PersistenceContext
    public EntityManager em;

    @Override
    @Transactional
    public ClientTicket save(ClientTicket clientTicket, int clientId, int userId) {
        if (clientTicket.isNew()) {
            clientTicket.setClient(em.getReference(Client.class, clientId));
            clientTicket.setUser(em.getReference(User.class, userId));
            em.persist(clientTicket);
            return clientTicket;
        }
        return null;
    }

    @Override
    @Transactional
    public ClientTicket update(ClientTicket clientTicket) {
        return em.merge(clientTicket);
    }

    @Override
    public boolean delete(int clientId, int id) {
        return em.createNamedQuery(ClientTicket.DELETE)
                .setParameter("id", id)
                .setParameter("clientId", clientId)
                .executeUpdate()!=0;
    }

    @Override
    public List<ClientTicket> getAll(int clientId) {
        return em.createNamedQuery(ClientTicket.GET_ALL, ClientTicket.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }

    public List<ClientTicket> getActive(int clientId, LocalDate date) {
        return em.createNamedQuery(ClientTicket.GET_ACTIVE, ClientTicket.class)
                .setParameter("clientId", clientId)
                .setParameter("date", date)
                .getResultList();
    }
}

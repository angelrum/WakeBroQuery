package system.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import system.model.Client;
import system.model.User;
import system.repository.ClientRepository;
import system.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by vladimir on 29.04.2018.
 */
@Repository
@Transactional(readOnly = true)
public class JpaClientRepositoryImpl implements ClientRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Client save(Client client, int userId) {
        User user = em.getReference(User.class, userId);
        client.setUser(user);
        if (client.isNew()) {
            em.persist(client);
            return client;
        }
        return em.merge(client);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Client.DELETE)
                .setParameter("id", id)
                .executeUpdate()!=0;
    }

    @Override
    public Client get(int id) {
        return em.find(Client.class, id);
    }

    @Override
    public Client getByNumber(String number) {
        List<Client> clients = em.createNamedQuery(Client.GET_BY_NUMBER, Client.class)
                .setParameter("telnumber", number)
                .getResultList();
        return DataAccessUtils.singleResult(clients);
    }

    @Override
    public List<Client> getAll() {
        return em.createNamedQuery(Client.GET_ALL, Client.class)
                .getResultList();
    }
}

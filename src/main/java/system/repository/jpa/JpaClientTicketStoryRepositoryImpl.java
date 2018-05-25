package system.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import system.model.ClientTicket;
import system.model.ClientTicketStory;
import system.model.User;
import system.repository.ClientTicketStoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by vladimir on 22.05.2018.
 */
@Repository
@Transactional(readOnly = true)
public class JpaClientTicketStoryRepositoryImpl implements ClientTicketStoryRepository {

    @PersistenceContext
    public EntityManager em;

    @Override
    @Transactional
    public ClientTicketStory save(ClientTicketStory story, int userId) {
        if (story.isNew()) {
            story.setClientTicket(em.getReference(ClientTicket.class, story.getClientTicket().getId()));
            story.setUser(em.getReference(User.class, userId));
            em.persist(story);
            return story;
        }
        return em.merge(story);
    }

    @Override
    public List<ClientTicketStory> getAll(int clientId) {
        return em.createNamedQuery(ClientTicketStory.GET_ALL, ClientTicketStory.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }

    @Override
    @Transactional
    public boolean delete(int storyId) {
        return em.createNamedQuery(ClientTicketStory.DELETE)
                .setParameter("storyId", storyId)
                .executeUpdate()!=0;
    }

    @Override
    public ClientTicketStory getActive(int clientId) {
        List<ClientTicketStory> list = em.createNamedQuery(ClientTicketStory.GET_ACTIVE, ClientTicketStory.class)
                .setParameter("clientId", clientId)
                .getResultList();
        return DataAccessUtils.singleResult(list);
    }
}

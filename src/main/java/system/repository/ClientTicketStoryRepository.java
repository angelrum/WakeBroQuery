package system.repository;

import system.model.ClientTicket;
import system.model.ClientTicketStory;

import java.util.List;

/**
 * Created by vladimir on 22.05.2018.
 */
public interface ClientTicketStoryRepository {

    ClientTicketStory save(ClientTicketStory story, int userId);

    List<ClientTicketStory> getAll(int clientId);

    ClientTicketStory getActive(int clientId);

    boolean delete(int storyId);
}

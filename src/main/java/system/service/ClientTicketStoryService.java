package system.service;

import system.model.ClientTicket;
import system.model.ClientTicketStory;

import java.util.List;

/**
 * Created by vladimir on 22.05.2018.
 */
public interface ClientTicketStoryService {

    ClientTicketStory save(ClientTicket clientTicket);

    void closeStory(int clientId);

    ClientTicketStory getActiveStory(int clientId);

    void delete(int storyId);

    List<ClientTicketStory> getAll(int clientId);
}

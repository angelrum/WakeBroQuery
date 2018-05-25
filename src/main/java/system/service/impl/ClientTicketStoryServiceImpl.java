package system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.controller.AuthorizedUser;
import system.model.ClientTicket;
import system.model.ClientTicketStory;
import system.repository.ClientTicketStoryRepository;
import system.service.ClientTicketStoryService;

import java.time.LocalTime;
import java.util.List;

import static system.util.ValidationUtil.*;
/**
 * Created by vladimir on 22.05.2018.
 */
@Service
public class ClientTicketStoryServiceImpl implements ClientTicketStoryService {

    private ClientTicketStoryRepository repository;

    @Autowired
    public ClientTicketStoryServiceImpl(ClientTicketStoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientTicketStory save(ClientTicket clientTicket) {
        ClientTicketStory story = new ClientTicketStory(clientTicket);
        ClientTicketStory st = repository.save(story, AuthorizedUser.id());
        return st;
    }

    @Override
    public ClientTicketStory getActiveStory(int clientId) {
        return checkNotFoundWithId(repository.getActive(clientId), clientId);
    }

    @Override
    public void delete(int storyId) {
        checkNotFoundWithId(repository.delete(storyId), storyId);
    }

    @Override
    public void closeStory(int clientId) {
        ClientTicketStory story = checkNotFoundWithId(repository.getActive(clientId), clientId);
        story.setTimeEnd(LocalTime.now());
        repository.save(story, AuthorizedUser.id());
    }

    @Override
    public List<ClientTicketStory> getAll(int clientId) {
        return checkNullOrEmptyList(repository.getAll(clientId));
    }
}

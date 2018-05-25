package system.Service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import system.model.ClientTicketStory;
import system.service.ClientTicketStoryService;

/**
 * Created by vladimir on 23.05.2018.
 */
import java.util.Arrays;
import java.util.List;

import static system.TestData.ClientTicketData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static system.TestData.ClientTicketStoryData.*;
import static system.TestData.ServiceUtil.assertMatchIgnorFields;


public class ClientTicketStoryTest extends ServiceTest {

    @Autowired
    public ClientTicketStoryService service;

    @Test
    public void createStory() {
        ClientTicketStory story = service.save(CLIENT1_TICKET);
        assertThat(story).isEqualToIgnoringGivenFields(STORY1, "user", "timeStart", "clientTicket");
    }

    @Test
    public void getActiveStory() {
        ClientTicketStory story = service.getActiveStory(CLIENT_ID);
        assertThat(story).isEqualToIgnoringGivenFields(ACTIVE_STORY, "user", "clientTicket");
    }

    @Test
    public void getAll() {
        List<ClientTicketStory> stories = service.getAll(CLIENT_ID);
        assertMatchIgnorFields(stories, Arrays.asList(ACTIVE_STORY, NONACTIVE_STORY), "user", "clientTicket");
    }

    @Test
    public void delete() {
        service.delete(ACTIVE_STORY.getId());
        assertMatchIgnorFields(service.getAll(CLIENT_ID), Arrays.asList(NONACTIVE_STORY), "user", "clientTicket");
    }
}

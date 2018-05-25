package system.TestData;

import system.model.ClientTicketStory;

/**
 * Created by vladimir on 23.05.2018.
 */
import java.time.LocalTime;

import static system.TestData.ClientTicketData.*;

public class ClientTicketStoryData {
    public static ClientTicketStory STORY1 = new ClientTicketStory(CLIENT1_TICKET);
    public static ClientTicketStory ACTIVE_STORY = new ClientTicketStory(CLIENT1_TICKET);
    public static ClientTicketStory NONACTIVE_STORY = new ClientTicketStory(CLIENT1_TICKET);
    public static int CLIENT_ID = 100002;

    static {
        STORY1.setId(100010);
        ACTIVE_STORY.setId(100008);
        ACTIVE_STORY.setTimeStart(LocalTime.of(12, 50));
        NONACTIVE_STORY.setTimeStart(LocalTime.of(12, 50));
        NONACTIVE_STORY.setTimeEnd(LocalTime.of(12, 55));
        NONACTIVE_STORY.setId(100009);
    }
}

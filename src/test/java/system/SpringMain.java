package system;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.controller.AuthorizedUser;
import system.model.*;
import system.service.ClientTicketService;
import system.service.TicketService;
import system.service.UserService;

/**
 * Created by vladimir on 28.03.2018.
 */
public class SpringMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        TicketService service = appCtx.getBean(TicketService.class);
        Ticket ticket = service.get(100005);

        ClientTicketService clientTicketService = appCtx.getBean(ClientTicketService.class);
        clientTicketService.save(100002, ticket);
    }
}

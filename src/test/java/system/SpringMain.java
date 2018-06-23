package system;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.controller.AuthorizedUser;
import system.model.*;
import system.service.ClientTicketService;
import system.service.RegisterService;
import system.service.TicketService;
import system.service.UserService;

/**
 * Created by vladimir on 28.03.2018.
 */
public class SpringMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
//        TicketService service = appCtx.getBean(TicketService.class);
//        Ticket ticket = service.get(100005);
//
//        ClientTicketService clientTicketService = appCtx.getBean(ClientTicketService.class);
//        clientTicketService.save(100002, ticket);
        RegisterService service = appCtx.getBean(RegisterService.class);
        int id = service.save(new Register()).getId();
        System.out.println("New id = " + id);
        Register register = service.get(id);
        System.out.println(register.toString());
        service.delete(id);
        try {
            service.get(id);
        } catch (Exception e) {
            System.out.println("Запись по id =" + id + " отсутствует");
        }
    }
}

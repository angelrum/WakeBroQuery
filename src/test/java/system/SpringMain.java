package system;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.service.UserService;

/**
 * Created by vladimir on 28.03.2018.
 */
public class SpringMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        UserService service = appCtx.getBean(UserService.class);
        service.getAll().forEach(System.out::print);
    }
}

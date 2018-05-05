package system.controller;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vladimir on 01.04.2018.
 */
public class SpringContextUtil {
    private ConfigurableApplicationContext appCtx;

    private static SpringContextUtil instance;

    private SpringContextUtil() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
    }

    public static SpringContextUtil getInstance() {
        if (instance==null)
            instance = new SpringContextUtil();
        return instance;
    }

    public <T> T getBean(Class<T> clazz) {
        return appCtx.getBean(clazz);
    }
}

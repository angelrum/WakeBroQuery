package system.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import system.service.ClientService;

/**
 * Created by vladimir on 31.03.2018.
 */
class ClientServiceImplTest {

    private ClientService service;

    private ConfigurableApplicationContext ctxApp;

    @BeforeEach
    void setUp() {
        ctxApp = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        service = ctxApp.getBean(ClientService.class);
    }

    @AfterEach
    void tearDown() {
        ctxApp.close();
    }

    @Test
    void save() {

    }

    @Test
    void delete() {

    }

    @Test
    void getByNumber() {

    }

    @Test
    void getAll() {

    }

}
package system.view;

import system.MainApp;

import java.net.URL;

/**
 * Created by vladimir on 18.02.2018.
 */
public enum PageEnum {

    BASIC_PAGE ("Программа WakeBro Queue", MainApp.class.getResource("page/BasicPage.fxml")),
    REGISTRATION_PERSON_PAGE ("Регистрация клиента", MainApp.class.getResource("page/RegistrationPerson.fxml")),
    LOGIN_PAGE ("Регистрация", null),
    CLIENT_TICKET_LIST ("Добавить в очередь", MainApp.class.getResource("page/ClientTicketList.fxml")),
    TICKET_LIST ("Билеты пользователя", MainApp.class.getResource("page/TicketSelect.fxml")),
    TICKET_EDIT ("Билеты", MainApp.class.getResource("page/TicketEditPage.fxml"));

    private final String name;
    private final URL url;

    PageEnum(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }
}

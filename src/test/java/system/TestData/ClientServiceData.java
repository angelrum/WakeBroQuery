package system.TestData;

import system.model.Client;

import java.util.Arrays;

import static system.TestData.ServiceUtil.*;

/**
 * Created by vladimir on 30.04.2018.
 */
public class ClientServiceData {
    public static final Client CLIENT1 = new Client(100002, null, "Иван", "Иванов", "Иванович", "+7(911)111-11-11", "Анапа", UserServiceData.ADMIN);
    public static final Client CLIENT2 = new Client(100003, null,"Петр", "Антонов", "Андреевич", "+7(911)111-11-13", "Анапа", UserServiceData.ADMIN);
    public static final Client SAVE = new Client("Антон", "Антонов", "Иванович", "+7(911)111-11-12", "Анапа", UserServiceData.ADMIN);
    public static final Client UPDATE = new Client(100002, null, "Олег", "Иванов", "Иванович", "+7(911)111-11-11", "Анапа", UserServiceData.ADMIN);


    public static void assertMatch(Client actual, Client expected) {
        assertMatchIgnorFields(actual, expected, "id", "create", "tickets");
    }

    public static void assertMatch(Iterable<Client> actual, Client...expected) {
        assertMatchIgnorFields(actual, Arrays.asList(expected), "id", "create", "tickets");
    }

}

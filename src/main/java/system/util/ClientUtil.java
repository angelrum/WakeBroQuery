package system.util;

import system.controller.AuthorizedUser;
import system.model.Client;

/**
 * Created by vladimir on 14.04.2018.
 */
public class ClientUtil {
    public static Client createClient(String telnumber, String fname, String lname, String sname, String city) {
        Client client = new Client(fname, lname, sname, telnumber, city, AuthorizedUser.id());
        return client;
    }
}

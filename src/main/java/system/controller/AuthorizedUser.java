package system.controller;

/**
 * Created by vladimir on 31.03.2018.
 */
public class AuthorizedUser {

    private static int id = 100000;

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }

    public static int id() {
        return id;
    }
}

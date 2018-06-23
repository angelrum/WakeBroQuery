package system.model;

/**
 * Created by vladimir on 17.03.2018.
 */
public enum Role {
    USER ("Пользователь"),
    ADMIN ("Администратор");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Role getRoleByName(String name) {
        for (Role p : Role.values())
            if (name.equals(p.getName()))
                return p;
        return null;
    }
}

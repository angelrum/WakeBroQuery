package system.model;

/**
 * Created by vladimir on 17.03.2018.
 */
public enum Pass {
    ABN_PASS ("Абонемент"),
    SECOND_PASS ("Сет");

    private final String name;

    Pass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Pass getPassByName(String name) {
        for (Pass p : Pass.values())
            if (name.equals(p.getName()))
                return p;
        return null;
    }

}

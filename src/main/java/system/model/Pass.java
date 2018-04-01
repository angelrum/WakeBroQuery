package system.model;

/**
 * Created by vladimir on 17.03.2018.
 */
public enum Pass {
    ABN_PASS ("Абонемент"),
    SECOND_PASS ("Сет");

    private String name;

    Pass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

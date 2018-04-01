package system.model;

import java.time.LocalDateTime;

/**
 * Created by vladimir on 17.03.2018.
 * Идентификато, дата создания и данные пользователя/клиента
 *
 */
public abstract class AbstractNamedEntity extends AbstractDateEntity {

    private String firstname;

    private String lastname;

    private String secondname;

    public AbstractNamedEntity(String firstname, String lastname, String secondname) {
        this(null, firstname, lastname, secondname, LocalDateTime.now());
    }

    public AbstractNamedEntity(Integer id, String firstname, String lastname, String secondname, LocalDateTime creation) {
        super(id, creation);
        this.firstname = firstname;
        this.lastname = lastname;
        this.secondname = secondname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSecondname() {
        return secondname;
    }
}

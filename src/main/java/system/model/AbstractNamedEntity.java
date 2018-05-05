package system.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Created by vladimir on 17.03.2018.
 * Идентификато, дата создания и данные пользователя/клиента
 *
 */
@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractDateEntity {

    @Column(name = "firstname")
    @NotBlank
    private String firstname;

    @Column(name = "lastname")
    @NotBlank
    private String lastname;

    @Column(name = "secondname")
    @NotBlank
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

    public AbstractNamedEntity() {
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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }
}

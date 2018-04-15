package system.model;

import java.time.LocalDateTime;

/**
 * Created by vladimir on 16.03.2018.
 *
 * Информация о клиенте
 * @id является идентификатором клиента
 */
public class Client extends AbstractNamedEntity {

    private String telnumber;

    private String city;

    private int userId;

    public Client(String firstname, String lastname, String secondname, String telnumber, String city, int userId) {
        super(firstname, lastname, secondname);
        this.telnumber  = telnumber;
        this.city       = city;
        this.userId     = userId;
    }

    public Client(Integer id, LocalDateTime creation, String firstname, String lastname, String secondname, String telnumber, String city) {
        super(id, firstname, lastname, secondname, creation);
        this.telnumber  = telnumber;
        this.city       = city;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public String getCity() {
        return city;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

package system.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by vladimir on 16.03.2018.
 *
 * Информация о клиенте
 * @id является идентификатором клиента
 */
@NamedQueries({
        @NamedQuery(name = Client.DELETE, query = "DELETE FROM Client c WHERE c.id=:id"),
        @NamedQuery(name = Client.GET_ALL, query = "SELECT c FROM Client c INNER JOIN FETCH c.user LEFT JOIN FETCH c.tickets ORDER BY c.create"),
        @NamedQuery(name = Client.GET_BY_NUMBER, query = "SELECT distinct c FROM Client c INNER JOIN FETCH c.user LEFT JOIN FETCH c.tickets WHERE c.telnumber=:telnumber")
})

@Entity
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"firstname", "lastname", "secondname", "telnumber"}, name = "client_unique_index")})
public class Client extends AbstractNamedEntity {
    public final static String DELETE = "Client.delete";
    public final static String GET_ALL = "Client.all";
    public final static String GET_BY_NUMBER = "Client.by_number";

    @Column(name = "telnumber")
    @NotBlank
    private String telnumber;

    @Column(name = "city")
    @NotBlank
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private List<ClientTicket> tickets;

    public Client(String firstname, String lastname, String secondname, String telnumber, String city, User user) {
        super(firstname, lastname, secondname);
        this.telnumber  = telnumber;
        this.city       = city;
        this.user       = user;
    }

    public Client(Integer id, LocalDateTime creation, String firstname, String lastname, String secondname, String telnumber, String city, User user) {
        super(id, firstname, lastname, secondname, creation);
        this.telnumber  = telnumber;
        this.city       = city;
        this.user       = user;
    }

    public Client() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<ClientTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<ClientTicket> tickets) {
        this.tickets = tickets;
    }
}

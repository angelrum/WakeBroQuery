package system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by vladimir on 18.03.2018.
 *  Данные из класса AbstractDateEntity
 *  @param:
 *      @id         - идентификатор записи
 *      @datecreate     - дата создания записи
 *  Данные текущего класса:
 *  @userId     - ид. пользователя, который создал запись
 *  @clientId   - ид. пользователя, которому билет принадлежит
 *  @ticketid   - ид. билета
 *  @start/end  - дата начала и дата окончания действия билета, фиксируется сразу же при присвоение
 *
 * @Active
 * Билет является активным:
 * - если текущий день между днями использования;
 * - параметр active равен true
 *
 * @Inheritance стратегия соединения таблиц (https://habr.com/post/337488/)
 */
@NamedQueries({
        @NamedQuery(name = ClientTicket.DELETE,     query = "DELETE FROM ClientTicket ct WHERE ct.id=:id AND ct.client.id=:clientId"),
        @NamedQuery(name = ClientTicket.GET_ALL,    query = "SELECT ct FROM ClientTicket ct WHERE ct.client.id=:clientId order by ct.start, ct.active"),
        @NamedQuery(name = ClientTicket.GET_ACTIVE, query = "SELECT ct FROM ClientTicket ct WHERE ct.client.id=:clientId " +
                                                            "AND ct.active=true AND ct.start<=:date AND ct.end>=:date order by ct.start")
})
@Entity
@Table(name = "client_ticket")
@Inheritance(strategy = InheritanceType.JOINED)
public class ClientTicket extends AbstractDateEntity {
    public static final String DELETE = "ClientTicket.delete";
    public static final String GET_ALL = "ClientTicket.get_all";
    public static final String GET_ACTIVE = "ClientTicket.get_all_active";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "date_start")
    @NotNull
    private LocalDate start;

    @Column(name = "date_end")
    private LocalDate end;

    @Column(name = "active")
    @NotNull
    private boolean active = true;

    public ClientTicket(User user, Client client, Ticket ticket, LocalDate start, LocalDate end) {
        this.user = user;
        this.client = client;
        this.ticket = ticket;
        this.start = start;
        this.end = end;
    }

    public ClientTicket(User user, Client client, Ticket ticket) {
        this(user, client, ticket, null, null);
    }

    public ClientTicket(Ticket ticket, LocalDate start, LocalDate end) {
        this(null, null, ticket, start, end);
    }

    public ClientTicket() {
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public boolean isStartSet() {
        return start!=null;
    }

    public void reeded() {
        this.active = false;
        if (start==null) start = LocalDate.now();
        if (end==null) end = LocalDate.now();
    }

    public boolean isActive() {
        if (start==null || end==null) return true;
        LocalDate date = LocalDate.now();
        return date.compareTo(start) >= 0
                && date.compareTo(end) <= 0
                && active;
    }

    public User getUser() {
        return user;
    }

    public Client getClient() {
        return client;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

package system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by vladimir on 27.04.2018.
 */
@Entity
@Table(name = "client_ticket_story",
        uniqueConstraints = @UniqueConstraint(columnNames = {"client_ticket_id", "date", "time_start"}, name = "story_ticket_idx"))
public class ClientTicketStory extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_ticket_id")
    private ClientTicket clientTicket;

    @Column
    @NotNull
    private LocalDate date;

    @Column(name = "time_start")
    @NotNull
    private LocalTime timeStart;

    @Column(name = "time_end")
    private LocalTime timeEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ClientTicketStory() {
    }

    public ClientTicketStory(ClientTicket clientTicket, User user) {
        this.clientTicket = clientTicket;
        this.user         = user;
        this.date         = LocalDate.now();
        this.timeStart    = LocalTime.now();
    }

    public ClientTicketStory(ClientTicket clientTicket, @NotNull LocalDate date, @NotNull LocalTime timeStart, LocalTime timeEnd, User user) {
        this.clientTicket = clientTicket;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.user = user;
    }

    public ClientTicket getClientTicket() {
        return clientTicket;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public User getUser() {
        return user;
    }

    public void setClientTicket(ClientTicket clientTicket) {
        this.clientTicket = clientTicket;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

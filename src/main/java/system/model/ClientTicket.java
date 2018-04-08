package system.model;

import java.time.LocalDate;

/**
 * Created by vladimir on 18.03.2018.
 *  Данные из класса AbstractDateIdEntity
 *  @param:
 *      @id         - идентификатор записи
 *      @create     - дата создания записи
 *      @createid   - ид. создателя записи
 *  Данные текущего класса:
 *  @userId     - ид. пользователя, которому билет принадлежит
 *  @ticketid   - ид. билета
 *  @start/end  - дата начала и дата окончания действия билета, фиксируется сразу же при присвоение
 *
 */
public class ClientTicket extends AbstractDateIdEntity {

    private Integer clientId;

    private Integer ticketId;

    private LocalDate start;

    private LocalDate end;

    public ClientTicket(Integer createId, Integer clientId, Integer ticketId, LocalDate start, LocalDate end) {
        super(createId);
        this.clientId = clientId;
        this.ticketId = ticketId;
        this.start = start;
        this.end = end;
    }

    public ClientTicket(Integer createId, Integer clientId, Integer ticketId) {
        this(createId, clientId, ticketId, null, null);
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

    public boolean isActive() {
        if (start==null || end==null) return true;

        LocalDate date = LocalDate.now();
        return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Integer getTicketId() {
        return ticketId;
    }
}

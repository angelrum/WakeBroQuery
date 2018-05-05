package system.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static system.util.TicketUtil.*;

/**
 * Created by vladimir on 17.03.2018.
 * @param:
 *      @pass                тип пропуска сет/абонемент
 *      @equipment           включено ли снаряжение
 *      @datestart/dateend   относиться к интервалу действия абонемента, как временной период (например летний сезон с 05.2018 до 09.2018)
 *      @month               задаем длительность абонемента в месяцах
 *      @timestart/@timeend  относиться к временному интервалу действия билета, например утренний, с 09:00 до 12:00
 *      @duration            длительность сета, по умолчанию 5 минут
 *      @year                год действия абонемента или сета
 *      @cost/weekendcost    стоимость/стоимость в выходные(для сетов)
 *      @userId              идентификатор создателя записи
 * from AbstractDateEntity
 *      @create              дата добавления записи
 *      @id                  ид. записи
 *
 * Логика:
 * 1. Есть три поля, описывающие длительность действия билета: @start, @end и @month.
 *    1.1 Если все поля равны @datestart/@dateend = @month == null, значит абонемент длительностью в один день;
 *    1.2 Если заполнены поля @datestart и @dateend значит билет действует ограниченный период дат;
 *    1.3 Если поля @datestart/dateend пусты, но заполнено поле @month значит билет действует указанное кол-во месяцев
 *                                                                                           с момента добавления
 */
@Entity
@Table(name = "ticket",
        uniqueConstraints = @UniqueConstraint(columnNames = {"pass", "name", "year"}, name = "ticket_unique_index"))
@NamedQueries({
        @NamedQuery(name = Ticket.DELETE, query = "DELETE FROM Ticket t WHERE t.id=:id"),
        @NamedQuery(name = Ticket.GET_ALL, query = "SELECT t FROM Ticket t ORDER BY t.pass, t.name, t.equipment"),
        @NamedQuery(name = Ticket.GET_ALL_ACTIVE, query = "SELECT t FROM Ticket t WHERE t.enable = true ORDER BY t.pass, t.name, t.equipment")
})
public class Ticket extends AbstractDateEntity {
    public static final String DELETE = "Ticket.delete";
    public static final String GET_ALL = "Ticket.get_all";
    public static final String GET_ALL_ACTIVE = "Ticket.get_all_active";

    @Column(name = "pass")
    @Enumerated(EnumType.STRING)
    @NotBlank
    private Pass pass;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "enable")
    @NotNull
    private boolean enable = true;

    @Column(name = "equipment")
    @NotNull
    private boolean equipment = false;

    @Column
    @Positive
    private Integer duration;

    @Column(name = "start_time")
    private LocalTime startTime = LocalTime.of(0, 0);

    @Column(name = "end_time")
    private LocalTime endTime = LocalTime.of(23, 59);

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "year")
    @Min(2018)
    @Max(2099)
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "cost")
    @Min(0)
    private Double cost;

    @Column(name = "weekendcost")
    @Min(0)
    private Double weekendcost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Ticket(Pass pass, String name, boolean equipment, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, Integer year, Integer month, Double cost, Double weekendcost, User user) {
        this.pass = pass;
        this.name = name;
        this.equipment = equipment;
        this.duration = TICKET_DURATION;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.year = year;
        this.month = month;
        this.cost = cost;
        this.weekendcost = weekendcost;
        this.user = user;
    }

    public Ticket() {
    }

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEquipment() {
        return equipment;
    }

    public void setEquipment(boolean equipment) {
        this.equipment = equipment;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getCost() {
        return cost;
    }

    public Double getWeekendcost() {
        return weekendcost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setWeekendcost(Double weekendcost) {
        this.weekendcost = weekendcost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

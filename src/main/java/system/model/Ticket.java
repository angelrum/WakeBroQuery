package system.model;

import java.time.LocalTime;

import static system.util.TicketUtil.*;

/**
 * Created by vladimir on 17.03.2018.
 * @param:
 *      @pass                тип пропуска сет/абонемент
 *      @equipment           включено ли снаряжение
 *      @start/end           относиться к интервалу действия абонемента, например утренний с 9-12
 *      @duration            длительность сета, по умолчанию 5 минут
 *      @year                год действия абонемента или сета
 *      @cost/weekendcost    стоимость/стоимость в выходные(для сетов)
 *      @month               длительность абонемента в днях/месяцах
 * from AbstractDateIdEntity
 *      @createid            идентификатор создателя записи
 *      @create              дата добавления записи
 *      @id                  ид. записи
 *
 * Логика:
 * 1. Есть три поля, описывающие длительность действия билета: @start, @end и @month.
 *    1.1 Если все поля равны null, значит абонемент длительностью в один день;
 *    1.2 Если заполнены поля @start и @end значит билет действует ограниченный период дат;
 *    1.3 Если поля @start и @end пусты, но заполнено поле @month значит билет действует указанное кол-во месяцев
 *                                                                                           с момента добавления
 */
public class Ticket extends AbstractDateIdEntity {

    private Pass pass;

    private String name;

    private boolean enable = true;

    private boolean equipment;

    private Integer duration;

    private LocalTime start;

    private LocalTime end;

    private Integer year;

    private Integer month;

    private Integer cost;

    private Integer weekendcost;

    public Ticket(Integer createId, Pass pass, String name, boolean equipment, LocalTime start, LocalTime end, Integer year, Integer month, Integer cost, Integer weekendcost) {
        super(createId);
        this.pass = pass;
        this.name = name;
        this.equipment = equipment;
        this.duration = TICKET_DURATION;
        this.start = start;
        this.end = end;
        this.year = year;
        this.month = month;
        this.cost = cost;
        this.weekendcost = weekendcost;
    }

    public Ticket(Integer createId, String name, boolean equipment, LocalTime start, LocalTime end, Integer year, Integer cost, Integer weekendcost) {
        this(createId, Pass.SECOND_PASS, name, equipment, start, end, year, null, cost, weekendcost);
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

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getWeekendcost() {
        return weekendcost;
    }

    public void setWeekendcost(Integer weekendcost) {
        this.weekendcost = weekendcost;
    }

    @Override
    public boolean equals(Object o) {
        Ticket ticket = (Ticket) o;

        if (enable != ticket.enable) return false;
        if (equipment != ticket.equipment) return false;
        if (pass != ticket.pass) return false;
        if (name != null ? !name.equals(ticket.name) : ticket.name != null) return false;
        if (duration != null ? !duration.equals(ticket.duration) : ticket.duration != null) return false;
        if (start != null ? !start.equals(ticket.start) : ticket.start != null) return false;
        if (end != null ? !end.equals(ticket.end) : ticket.end != null) return false;
        if (year != null ? !year.equals(ticket.year) : ticket.year != null) return false;
        if (month != null ? !month.equals(ticket.month) : ticket.month != null) return false;
        if (cost != null ? !cost.equals(ticket.cost) : ticket.cost != null) return false;
        return weekendcost != null ? weekendcost.equals(ticket.weekendcost) : ticket.weekendcost == null;
    }

    @Override
    public int hashCode() {
        int result = pass != null ? pass.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (enable ? 1 : 0);
        result = 31 * result + (equipment ? 1 : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (weekendcost != null ? weekendcost.hashCode() : 0);
        return result;
    }
}

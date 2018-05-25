package system.controller.to;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.*;
import javafx.scene.control.*;
import system.model.Client;
import system.model.ClientTicket;
import system.model.Pass;
import system.model.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by vladimir on 05.04.2018.
 */
public class ClientTicketRow {

    private SimpleStringProperty    type = new SimpleStringProperty();

    private CheckBox                equipment = new CheckBox();

    private SimpleStringProperty    start = new SimpleStringProperty();

    private SimpleStringProperty    end = new SimpleStringProperty();

    private SimpleStringProperty    dateEnd = new SimpleStringProperty();

    private SimpleStringProperty    duration = new SimpleStringProperty();

    private Integer                 count = new Integer(1);

    private Ticket ticket;

    private List<ClientTicket> tickets = new ArrayList<>();

    public ClientTicketRow(ClientTicket clientTicket) {
        tickets.add(clientTicket);
        setTicket(clientTicket.getTicket());
    }

    public ClientTicketRow() {
    }

    public void addValue(ClientTicket clientTicket) {
        tickets.add(clientTicket);
        if (Objects.isNull(ticket))
            setTicket(clientTicket.getTicket());
        count = ticket.getPass()!= Pass.ABN_PASS ? tickets.size() : count;
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
        init();
    }

    private void init() {
        type.setValue(ticket.getPass().getName());
        equipment.setSelected(ticket.isEquipment());
        equipment.setDisable(true);
        equipment.getStyleClass().add("column");
        start.setValue(ticket.getStartTime().toString());
        end.setValue(ticket.getEndTime().toString());
        dateEnd.setValue(tickets.get(0).getEnd()!=null ? tickets.get(0).getEnd().toString() : null);
        duration.setValue(String.valueOf(ticket.getDuration()));
    }

    public ObservableObjectValue<CheckBox> getEquipment() {
        return new SimpleObjectProperty<>(equipment);
    }

    public ObservableStringValue typeProperty() {
        return type;
    }

    public ObservableStringValue startProperty() {
        return start;
    }

    public ObservableStringValue endProperty() {
        return end;
    }

    public ObservableStringValue dateEndProperty() {
        return dateEnd;
    }

    public ObservableStringValue durationProperty() {
        return duration;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getMaxCount() {

        return tickets.size();
    }

    public Integer getCount() {
        return count;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public List<ClientTicket> getTickets() {
        return tickets;
    }

    public Client getClient() {
        if (!tickets.isEmpty())
            return tickets.get(0).getClient();
        return null;
    }
}

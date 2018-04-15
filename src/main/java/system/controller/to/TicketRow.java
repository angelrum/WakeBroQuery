package system.controller.to;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import system.model.Ticket;

/**
 * Created by vladimir on 08.04.2018.
 *
 * Класс является оберткой над классом Ticket для вывода данных в окне TicketSelect
 */
public class TicketRow {

    private SimpleStringProperty pass = new SimpleStringProperty();

    private SimpleStringProperty name = new SimpleStringProperty();

    private SimpleObjectProperty<CheckBox> equipment = new SimpleObjectProperty<>();

    private SimpleIntegerProperty duration = new SimpleIntegerProperty();

    private SimpleStringProperty start = new SimpleStringProperty();

    private SimpleStringProperty end = new SimpleStringProperty();

    private SimpleStringProperty month = new SimpleStringProperty();

    private Ticket ticket;

    public TicketRow(Ticket ticket) {
        this.ticket = ticket;
        init();
    }

    private void init() {
        pass.setValue(ticket.getPass().getName());
        name.setValue(ticket.getName());
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(ticket.isEquipment());
        checkBox.setDisable(true);
        equipment.setValue(checkBox);
        duration.setValue(ticket.getDuration());
        start.setValue(ticket.getStart().toString());
        end.setValue(ticket.getEnd().toString());
        month.setValue(ticket.getMonth()==null ? null : ticket.getMonth().toString());
    }


    public SimpleStringProperty passProperty() {
        return pass;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleObjectProperty<CheckBox> equipmentProperty() {
        return equipment;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public SimpleIntegerProperty durationProperty() {
        return duration;
    }


    public SimpleStringProperty startProperty() {
        return start;
    }


    public SimpleStringProperty endProperty() {
        return end;
    }

    public SimpleStringProperty monthProperty() {
        return month;
    }
}

package system.controller.to;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import system.model.ClientTicket;
import system.model.Pass;
import system.model.Ticket;

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

    private VBox                    control = new VBox();

        private Button                  up = new Button();

        private Button                  down = new Button();

    private SimpleIntegerProperty   count = new SimpleIntegerProperty(0);

    private SimpleIntegerProperty   maxCount = new SimpleIntegerProperty(1);

    private int id;

    private Ticket ticket;

    private ClientTicket clientTicket;

    public ClientTicketRow(ClientTicket clientTicket) {
        this.clientTicket = clientTicket;
        this.ticket = clientTicket.getTicket();
        init();
    }

    private void init() {
        id = ticket.getId();
        type.setValue(ticket.getPass().getName());
        equipment.setSelected(ticket.isEquipment());
        equipment.setDisable(true);
        equipment.getStyleClass().add("column");
        start.setValue(null);
        end.setValue(null);
        dateEnd.setValue(clientTicket.getEnd()!=null?clientTicket.getEnd().toString():null);
        duration.setValue(String.valueOf(ticket.getDuration()));

        initButton();
    }

    private void initButton() {
        up.getStyleClass().add("button-up");
        up.setDisable(true);
        down.getStyleClass().add("button-down");
        control.getChildren().addAll(up, down);
        control.getStyleClass().add("box");
        initButtonAction();
    }

    private void initButtonAction() {
        up.setOnAction(event -> {
            count.setValue(count.getValue() + 1);
            up.setDisable(maxCount.isEqualTo(count).getValue());
            down.setDisable(false);
        });
        down.setOnAction(event -> {
            count.setValue(count.getValue().intValue() - 1);
            down.setDisable(count.getValue().intValue()==1);
            up.setDisable(false);
        });
        if (Pass.ABN_PASS
                .equals(ticket.getPass()))
            control.setDisable(true);

    }

    private void checkButton() {
        control.setDisable(maxCount.getValue()==1);
    }

    public ObservableObjectValue<CheckBox> getEquipment() {
        return new SimpleObjectProperty<>(equipment);
    }

    public ObservableObjectValue<VBox> getControl() {
        return new SimpleObjectProperty<>(control);
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

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public int getId() {
        return id;
    }

    public void setCount(int count) {
        this.count.setValue(count);
    }

    public void setMaxCount(int count) {
        maxCount.setValue(count);
        setCount(count);
        checkButton();
    }

    public SimpleIntegerProperty getCount() {
        return this.count;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public ClientTicket getClientTicket() {
        return clientTicket;
    }

    public SimpleIntegerProperty maxCountProperty() {
        return maxCount;
    }
}

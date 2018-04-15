package system.controller.to;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import system.controller.QueueListener;
import system.model.Client;
import system.model.ClientTicket;
import system.model.Ticket;

/**
 * Created by vladimir on 01.04.2018.
 *
 * Класс является элементом очереди
 */
public class QueueRow {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();

    private SimpleObjectProperty<VBox> control = new SimpleObjectProperty<>(new VBox());

        private Button up = new Button();

        private Button down = new Button();

    private ObservableValue<Label> status;

    private ObservableValue<CheckBox> cstatus;

    private ObservableValue<Button> delete;

    private Client client;

    private StringProperty passName = new SimpleStringProperty();;

    private ClientTicket clientTicket;

    private Ticket ticket;

    private QueueListener listener;

    private boolean active = true;

    private int count = 1;

    public QueueRow(Client client, ClientTicket clientTicket, Ticket ticket) {
        this.client         = client;
        this.clientTicket   = clientTicket;
        this.ticket         = ticket;
        init();
    }

    public QueueRow(Client client, ClientTicket clientTicket, Ticket ticket, int count) {
        this.client = client;
        this.clientTicket = clientTicket;
        this.ticket = ticket;
        this.count = count;
        init();
    }

    //основной блок инициализации
    private void init() {
        initDeleteButton();
        initStatusButton();
        initControlButton();
        initPassName();
    }

    private void initPassName() {
        switch (ticket.getPass()) {
            case SECOND_PASS:
                passName.setValue(
                        String.format("%s (%s)",
                        ticket.getPass().getName(),
                        count));
                break;
            case ABN_PASS:
                passName.setValue(ticket.getPass().getName());
                break;
        }
    }

    private void initDeleteButton() {
        Button button = new Button();
        button.getStyleClass().add("delete-person");
        button.setOnMouseClicked(event -> {
            listener.remove(this);
        });
        delete = new SimpleObjectProperty<>(button);
    }

    private void initStatusButton() {
        Label status = new Label();
        CheckBox cstatus = new CheckBox();
        status.setLabelFor(cstatus);
        status.getStyleClass().add("lstatus-person");
        status.getStyleClass().add("lstatus-person-active");
        cstatus.getStyleClass().add("cstatus-person");
        cstatus.setSelected(true);

        status.setOnMouseClicked(event -> {
            if (cstatus.isSelected()) {
                cstatus.setSelected(false);
                status.getStyleClass().remove("lstatus-person-active");
                status.getStyleClass().add("lstatus-person-disactive");
                listener.shiftRowInDisactiveQueue(this);
                active = false;
            }
            else {
                cstatus.setSelected(true);
                status.getStyleClass().remove("lstatus-person-disactive");
                status.getStyleClass().add("lstatus-person-active");
                listener.shiftRowInActive(this);
                active = true;
            }
        });

        this.status = new SimpleObjectProperty<>(status);
        this.cstatus = new SimpleObjectProperty<>(cstatus);
    }

    private void initControlButton() {
        up.getStyleClass().add("button-up");
        up.setOnAction(event -> listener.toUp(this));
        down.getStyleClass().add("button-down");
        down.setOnAction(event -> listener.toDown(this));
        control.getValue().getChildren().addAll(up, down);
        control.getValue().getStyleClass().add("box");
    }

    public StringProperty getClientName() {
        StringProperty name = new SimpleStringProperty();
        name.setValue(String.format("%s %s %s", client.getFirstname(), client.getLastname(), client.getSecondname()));
        return name;
    }

    public void setDisabledUp(boolean flag) {
        up.setDisable(flag);
    }

    public void setDisabledDown(boolean flag) {
        down.setDisable(flag);
    }

    public void disabledControlButton(boolean flag) {
        setDisabledUp(flag);
        setDisabledDown(flag);
    }

    public boolean isActive() {
        return active;
    }

    public StringProperty getPassName() {
        return passName;
    }

    public ObservableValue<VBox> boxButtonProperty() {
        return control;
    }

    public ObservableValue<Label> statusProperty() {
        return status;
    }

    public ObservableValue<CheckBox> cstatusProperty() {
        return cstatus;
    }

    public ObservableValue<Button> deleteProperty() {
        return delete;
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setListener(QueueListener listener) {
        this.listener = listener;
    }
}

package system.controller.to;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import system.model.Client;
import system.model.ClientTicket;
import system.model.Ticket;

/**
 * Created by vladimir on 01.04.2018.
 */
public class QueueRow {

    private VBox boxButton;

    private Label status;

    private CheckBox cstatus;

    private Button delete;

    private Client client;

    private ClientTicket clientTicket;

    private Ticket ticket;

    public QueueRow(Client client, ClientTicket clientTicket, Ticket ticket) {
        this.client         = client;
        this.clientTicket   = clientTicket;
        this.ticket         = ticket;
        init();
    }

    private void init() {
        initDeleteButton();
        initStatusButton();
    }

    private void initDeleteButton() {
        delete = new Button();
        delete.getStyleClass().add("delete-person");
        delete.setOnMouseClicked(event -> {
            //listener.deleteColumnPerson(getThisPerson());
        });
    }

    private void initStatusButton() {
        status = new Label();
        cstatus = new CheckBox();
        this.status.setLabelFor(cstatus);
        status.getStyleClass().add("lstatus-person");
        status.getStyleClass().add("lstatus-person-active");
        cstatus.getStyleClass().add("cstatus-person");
        cstatus.setSelected(true);

        status.setOnMouseClicked(event -> {
            if (cstatus.isSelected()) {
                cstatus.setSelected(false);
                status.getStyleClass().remove("lstatus-person-active");
                status.getStyleClass().add("lstatus-person-disactive");
                //setDisactiveId();
            }
            else {
                cstatus.setSelected(true);
                status.getStyleClass().remove("lstatus-person-disactive");
                status.getStyleClass().add("lstatus-person-active");
                //setActiveId();
            }
        });
    }

    public StringProperty getClientName() {
        StringProperty name = new SimpleStringProperty();
        name.setValue(String.format("%s %s %s", client.getFirstname(), client.getLastname(), client.getSecondname()));
        return name;
    }

    public StringProperty getPassName() {
        StringProperty name = new SimpleStringProperty();
        name.setValue(ticket.getPass().getName());
        return name;
    }

    public ObservableValue<VBox> getBoxButton() {
        ObservableValue<VBox> value = new SimpleObjectProperty<>(boxButton);
        return value;
    }

    public ObservableValue<Label> getStatus() {
        ObservableValue<Label> value = new SimpleObjectProperty<>(status);
        return value;
    }

    public ObservableValue<CheckBox> getCstatus() {
        ObservableValue<CheckBox> value = new SimpleObjectProperty<>(cstatus);
        return value;
    }

    public ObservableValue<Button> getDelete() {
        ObservableValue<Button> value = new SimpleObjectProperty<>(delete);
        return value;
    }

    public ObservableValue<Label> getId() {
        ObservableValue<Label> value = new SimpleObjectProperty<Label>();
        value.getValue().setText(String.valueOf(1));
        return value;
    }
}

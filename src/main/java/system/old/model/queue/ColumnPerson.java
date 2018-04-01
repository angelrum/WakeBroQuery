package system.old.model.queue;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import system.old.model.Person;
import system.old.model.queue.listener.QueueListener;

/**
 * Created by vladimir on 18.02.2018.
 */
public class ColumnPerson {

    private int id;

    private VBox boxButton;

    private Label status;

    private CheckBox cstatus;

    private Button delete;

    private Person person;

    private StringProperty fio;

    private StringProperty abn;

    private QueueListener listener;

    public void setListener(QueueListener listener) {
        this.listener = listener;
    }

    public ColumnPerson(int id, Person person, SetOrAbn setOrAbn) {
        this(person, setOrAbn);
        this.id = id;
    }

    public ColumnPerson(Person person, SetOrAbn setOrAbn) {
        delete = new Button();
        delete.getStyleClass().add("delete-person");
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                listener.deleteColumnPerson(getThisPerson());
            }
        });
        this.person = person;
        this.abn = new SimpleStringProperty(setOrAbn.toString());
        initStatus();
        initBoxButton();
        init();
    }

    public ColumnPerson getThisPerson() {
        return this;
    }

    private void init() {
        StringProperty fio = new SimpleStringProperty();
        fio.setValue(person.getFIO());
        this.fio = fio;
    }

    private void initBoxButton() {
        boxButton = new VBox();
        boxButton.getStyleClass().add("box-button");
        Button up = new Button();
        Button down = new Button();
        up.getStyleClass().add("button-up");
        down.getStyleClass().add("button-down");
        boxButton.getChildren().addAll(up, down);
    }

    private void initStatus() {
        this.status = new Label();
        this.cstatus = new CheckBox();
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
                setDisactiveId();
            }
            else {
                cstatus.setSelected(true);
                status.getStyleClass().remove("lstatus-person-disactive");
                status.getStyleClass().add("lstatus-person-active");
                setActiveId();
            }
        });
    }

    public String getFio() {
        return fio.get();
    }

    public Button getDelete() {
        return delete;
    }

    public String getAbn() {
        return abn.get();
    }

    public StringProperty abnProperty() {
        return abn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Label getStatus() {
        return status;
    }

    public CheckBox getCstatus() {
        return cstatus;
    }

    private void setActiveId() {
        id = listener.getNewId();
    }

    private void setDisactiveId() {
        this.id = id + 1000;
    }

    public VBox getBoxButton() {
        return boxButton;
    }
}

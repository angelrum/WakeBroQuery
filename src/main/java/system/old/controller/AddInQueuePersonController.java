package system.old.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import system.old.model.Model;
import system.old.model.queue.ColumnPerson;
import system.old.model.queue.SetOrAbn;

/**
 * Created by vladimir on 24.02.2018.
 */
public class AddInQueuePersonController {

    private Model model;

    private ColumnPerson tmpColumnPerson;

    private boolean clickOk;

    private Stage dialogStage;

    @FXML
    private Label fio;

    @FXML
    private Label telNumber;

    @FXML
    private RadioButton set;

    @FXML
    private RadioButton abonement;

    @FXML
    private ChoiceBox<Integer> countSet;

    @FXML
    public void initialize() {
        initRadioButton();
        initChoiceBox();
        clickOk = false;

    }

    private void initRadioButton() {
        EventHandler<ActionEvent> eventEventHandler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (set.isSelected())
                    countSet.setDisable(false);
                else
                    countSet.setDisable(true);
            }
        };
        ToggleGroup group = new ToggleGroup();
        set.setToggleGroup(group);
        abonement.setToggleGroup(group);
        set.setSelected(true);

        abonement.setOnAction(eventEventHandler);
        set.setOnAction(eventEventHandler);
    }

    private void initChoiceBox() {
        ObservableList<Integer> collections = FXCollections.observableArrayList();
        for (int i = 1; i < 10; i++)
            collections.add(i);
        countSet.setItems(collections);
        countSet.setValue(1);
    }

    public void setModel(Model model) {
        this.model = model;
        this.fio.setText(model.getTmpPerson().getFIO());
        this.telNumber.setText(model.getTmpPerson().getTelNumber());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void clickedOk() {
        clickOk = true;
        SetOrAbn setOrAbn;
        if (set.isSelected()) {
            setOrAbn = new SetOrAbn(countSet.getValue());
        } else {
            setOrAbn = new SetOrAbn();
        }
        tmpColumnPerson = new ColumnPerson(model.getTmpPerson(), setOrAbn);
        this.dialogStage.close();
    }

    @FXML
    public void clickedCancel() {
        clickOk = false;
        this.dialogStage.close();
    }

    public boolean isClickOk() {
        return clickOk;
    }

    public ColumnPerson getTmpColumnPerson() {
        return tmpColumnPerson;
    }
}

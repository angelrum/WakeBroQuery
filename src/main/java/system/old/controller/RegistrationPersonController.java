package system.old.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import system.controller.page.helper.NumberTextField;
import system.old.model.Model;
import system.old.model.Person;

/**
 * Created by vladimir on 18.02.2018.
 */
public class RegistrationPersonController {

    private Model model;

    private Person person;

    @FXML
    private NumberTextField telNumber;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField sname;

    @FXML
    private TextField city;

    @FXML
    private Button ok;

    @FXML
    private Button cancel;

    @FXML
    private Label message;

    @FXML
    private Label lFname;

    @FXML
    private Label lSname;

    @FXML
    private Label lLname;

    @FXML
    private Label lTelNumber;

    @FXML
    private Label lCity;

    private Stage dialogStage;

    private boolean okClicked = false;


    @FXML
    public void initialize() {

    }

    public void refresh() {
        Person person = model.getTmpPerson();
        if (person!=null) {
            this.telNumber.setText(person.getTelNumber());
        }
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    public void handleOk() {
        String fname = this.fname.getText();
        String lname = this.lname.getText();
        String sname = this.sname.getText();
        String telNumber = this.telNumber.getText();
        String city = this.city.getText();
        if (fname.equals("")
                || lname.equals("")
                || sname.equals("")
                || telNumber.equals("")
                || city.equals("")) {
            lFname.setTextFill((fname.equals("")? Color.web("#e40a0a") : Color.web("#000000")));
            lSname.setTextFill((sname.equals("")? Color.web("#e40a0a") : Color.web("#000000")));
            lLname.setTextFill((lname.equals("")? Color.web("#e40a0a") : Color.web("#000000")));
            lTelNumber.setTextFill((telNumber.equals("")? Color.web("#e40a0a") : Color.web("#000000")));
            lCity.setTextFill((city.equals("")? Color.web("#e40a0a") : Color.web("#000000")));
            message.setVisible(true);
        } else {
            person = new Person(fname, lname, sname, telNumber, city);
            okClicked = true;
            this.model.addPerson(person);
            this.message.setVisible(false);
            this.dialogStage.close();
        }
    }

    @FXML
    public void handleCancel() {
        okClicked = false;
        this.dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Person getPerson() {
        return person;
    }
}

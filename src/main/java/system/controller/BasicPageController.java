package system.controller;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import system.MainApp;
import system.controller.halper.ButtonSubmit;
import system.controller.halper.ButtonSubmitEnum;
import system.controller.halper.NumberTextField;
import system.controller.listener.BasicPageListener;
import system.model.queue.ColumnPerson;
import system.model.Model;
import system.model.Person;
import system.view.FactoryPage;
import system.view.PageEnum;

import java.util.Comparator;

/**
 * Created by vladimir on 14.02.2018.
 */
public class BasicPageController implements BasicPageListener {

    private Model model;

    private RegistrationPersonController registrationPersonController;

    private AddInQueuePersonController addInQueuePersonController;

    @FXML
    private NumberTextField telNumber;

    @FXML
    private Text fname;

    @FXML
    private Text lname;

    @FXML
    private Text sname;

    @FXML
    private Button cancel;

    @FXML
    private ButtonSubmit submit;

    @FXML
    private TableView<ColumnPerson> queueTable;

    @FXML
    private TableColumn<ColumnPerson, Label> idColumn;

    @FXML
    private TableColumn<ColumnPerson, VBox> lControl;

    @FXML
    private TableColumn<ColumnPerson, Label> lStatusColumn;

    @FXML
    private TableColumn<ColumnPerson, CheckBox> cStatusColumn;

    @FXML
    private TableColumn<ColumnPerson, Button> deleteColumn;

    @FXML
    private TableColumn<ColumnPerson, String> fioColumn;

    @FXML
    private TableColumn<ColumnPerson, String> abnColumn;

    @FXML
    public void initialize() {
        this.telNumber.setListener(this);
        this.cancel.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("image/cancel.png"))));
        cancelDisable();
        refresh();
        initTable();
    }

    private void initTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<ColumnPerson, Label>("id"));
        lControl.setCellValueFactory(new PropertyValueFactory<ColumnPerson, VBox>("boxButton"));
        lStatusColumn.setCellValueFactory(new PropertyValueFactory<ColumnPerson, Label>("status"));
        cStatusColumn.setCellValueFactory(new PropertyValueFactory<ColumnPerson, CheckBox>("cstatus"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<ColumnPerson, Button>("delete"));
        fioColumn.setCellValueFactory(new PropertyValueFactory<ColumnPerson, String>("fio"));
        abnColumn.setCellValueFactory(new PropertyValueFactory<ColumnPerson, String>("abn"));

        queueTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                sortQueueTable();
            }
        });
    }

    private void sortQueueTable() {
        FXCollections.sort(model.getTableQueue(), new Comparator<ColumnPerson>() {
            public int compare(ColumnPerson o1, ColumnPerson o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
    }

    public void refresh() {
        this.fname.setVisible(false);
        this.lname.setVisible(false);
        this.sname.setVisible(false);
        this.submit.setText("Зарегистрировать", ButtonSubmitEnum.REGISTRATION);
    }

    public void cancelEnable() {
        this.cancel.setDisable(false);
    }

    public void cancelDisable() {
        this.cancel.setDisable(true);
    }

    public void setModel(Model model) {
        this.model = model;
        this.queueTable.setItems(model.getTableQueue());
    }

    public Model getModel() {
        return model;
    }

    public void setPerson(Person person) {
        this.telNumber.setText(person.getTelNumber());
        this.fname.setText(person.getFname());
        this.lname.setText(person.getLname());
        this.sname.setText(person.getSname());
        this.fname.setVisible(true);
        this.lname.setVisible(true);
        this.sname.setVisible(true);
        this.submit.setText("Добавить в очередь", ButtonSubmitEnum.ADD_IN_QUEUE);
        this.model.setTmpPerson(person);
    }

    @FXML
    public void putCancel() {
        this.telNumber.clear();
        cancelDisable();
        refresh();
    }

    @FXML
    public void handleSubmit() {
        FactoryPage factoryPage = FactoryPage.getInstance();
        this.model.setTmpPerson(getPerson());

        if (this.submit.getSubmitEnum()==ButtonSubmitEnum.REGISTRATION) {
            factoryPage.showPage(PageEnum.REGISTRATION_PERSON_PAGE);
            if (registrationPersonController!=null
                    && registrationPersonController.isOkClicked()) {
                setPerson(registrationPersonController.getPerson());
            }
        } else if (this.submit.getSubmitEnum()==ButtonSubmitEnum.ADD_IN_QUEUE){
            factoryPage.showPage(PageEnum.ADD_IN_QUEUE);
            if (addInQueuePersonController.isClickOk()) {
                ColumnPerson columnPerson = addInQueuePersonController.getTmpColumnPerson();
                model.setColumnPersonInQueue(columnPerson);
            }
            sortQueueTable();
        }
    }

    private Person getPerson() {
        return new Person(this.fname.getText(),
                        this.lname.getText(),
                        this.sname.getText(),
                        this.telNumber.getText());
    }

    public void setRegistrationPersonController(RegistrationPersonController registrationPersonController) {
        this.registrationPersonController = registrationPersonController;
    }

    public void setAddInQueuePersonController(AddInQueuePersonController addInQueuePersonController) {
        this.addInQueuePersonController = addInQueuePersonController;
    }
}

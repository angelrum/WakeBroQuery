package system.controller.page.UserEditPage;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import system.controller.SpringContextUtil;
import system.controller.page.helper.TableCell.CheckBoxCell;
import system.controller.page.helper.TableCell.ComboBoxCell;
import system.controller.page.helper.TableCell.TextFieldCell;
import system.controller.page.helper.converter.TelnumberConverter;
import system.model.Role;
import system.model.User;
import system.service.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static system.controller.page.helper.FontAwesomeIconHelper.*;
import static system.controller.page.helper.TableHelper.*;
import static system.util.ValidationUtil.*;

public class UserEditPageInit {

    private UserService service = SpringContextUtil.getInstance().getBean(UserService.class);

    private Set<User> delete = new HashSet<>();
    private Set<User> update = new HashSet<>();

    private UserEditPage controller;

    UserEditPageInit(UserEditPage controller) {
        this.controller = controller;
    }

    public void init() {
        controller.role.setCellValueFactory(param -> {
            User user = param.getValue();
            return new SimpleStringProperty(user.getRole()!=null ? user.getRole().getName() : null);
        });
        controller.login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        controller.password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        controller.firstname.setCellValueFactory(new PropertyValueFactory<User, String>("firstname"));
        controller.lastname.setCellValueFactory(new PropertyValueFactory<User, String>("lastname"));
        controller.secondname.setCellValueFactory(new PropertyValueFactory<User, String>("secondname"));
        controller.email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        controller.telnumber.setCellValueFactory(new PropertyValueFactory<User, String>("telnumber"));
        controller.enable.setCellValueFactory(param -> {
            User user = param.getValue();
            SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(user.isEnabled());
            booleanProperty.addListener((observable, oldValue, newValue) -> user.setEnabled(newValue));
            return booleanProperty;
        });
        controller.delete.setCellValueFactory(param -> new SimpleObjectProperty(getButtonDelete(param.getValue(), delete, controller.tableView)));
        initEdit();
        initEvent();
    }

    private void initEdit() {
        ObservableList<String> roles = FXCollections.observableArrayList(Stream.of(Role.values()).map(Role::getName).collect(Collectors.toList()));
        ObservableList<User> users = FXCollections.observableArrayList(service.getAll());
        controller.role.setCellFactory(param -> new ComboBoxCell<User, String>(update, roles, "role"));
        controller.login.setCellFactory(param -> new TextFieldCell<User, String>(update, null, "login"));
        controller.password.setCellFactory(param -> new TextFieldCell<User, String>(update, null, "password"));
        controller.firstname.setCellFactory(param -> new TextFieldCell<User, String>(update, null, "firstname"));
        controller.lastname.setCellFactory(param -> new TextFieldCell<User, String>(update, null, "lastname"));
        controller.secondname.setCellFactory(param -> new TextFieldCell<User, String>(update, null, "secondname"));
        controller.email.setCellFactory(param -> new TextFieldCell<User, String>(update, null, "email"));
        controller.enable.setCellFactory(param -> new CheckBoxCell<User>(update, null, "enabled"));
        controller.telnumber.setCellFactory(param -> new TextFieldCell<User, String>(update, null, "telnumber", new TelnumberConverter()));
        controller.tableView.setItems(users);
    }

    private void initEvent() {
        controller.login.setOnEditCommit(event -> getEditObject(event, update).setLogin(event.getNewValue()));
        controller.password.setOnEditCommit(event -> getEditObject(event, update).setPassword(event.getNewValue()));
        controller.firstname.setOnEditCommit(event -> getEditObject(event, update).setFirstname(event.getNewValue()));
        controller.lastname.setOnEditCommit(event -> getEditObject(event, update).setLastname(event.getNewValue()));
        controller.secondname.setOnEditCommit(event -> getEditObject(event, update).setSecondname(event.getNewValue()));
        controller.email.setOnEditCommit(event -> getEditObject(event, update).setEmail(event.getNewValue()));
        controller.telnumber.setOnEditCommit(event -> getEditObject(event, update).setTelnumber(event.getNewValue()));
        controller.tableView.setOnMouseClicked(event -> controller.tableView.getSelectionModel().clearSelection());
    }

    public void save() {
        String err = getError();
        saveObjects(update, delete, service, controller.stage, err);
    }

    private String getError() {
        update.removeAll(delete);
        StringBuilder err = new StringBuilder();
        ObservableList<User> users = controller.tableView.getItems();
        for (User user : update) {
            int pos = users.indexOf(user);
            err.append(checkUser(user, pos));
        }
        return err.toString();
    }

    public void cancel() {
        controller.stage.close();
    }

    public void create() {
        createObject(controller.tableView, new User());
    }
}

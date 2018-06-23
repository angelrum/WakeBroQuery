package system.controller.page.UserEditPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.model.Client;
import system.model.User;

public class UserEditPage implements Controller {
    @FXML protected TableView<User> tableView;
    @FXML protected TableColumn<User, String> role;
    @FXML protected TableColumn<User, String> login;
    @FXML protected TableColumn<User, String> password;
    @FXML protected TableColumn<User, String> firstname;
    @FXML protected TableColumn<User, String> secondname;
    @FXML protected TableColumn<User, String> lastname;
    @FXML protected TableColumn<User, String> email;
    @FXML protected TableColumn<User, String> telnumber;
    @FXML protected TableColumn<User, Boolean> enable;
    @FXML protected TableColumn<User, StackPane> delete;

    protected Stage stage;

    private UserEditPageInit userEditPageInit;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        userEditPageInit = new UserEditPageInit(this);
        userEditPageInit.init();
    }

    @Override
    public void setListener(ActiveListener listener) {

    }

    @Override
    public void setActiveClient(Client client) {

    }

    @Override
    public void execute(Command command) {

    }

    public void clickOk(ActionEvent actionEvent) {
        userEditPageInit.save();
    }

    public void clickCancel(ActionEvent actionEvent) {
        userEditPageInit.cancel();
    }

    public void clickCreate(ActionEvent actionEvent) {
        userEditPageInit.create();
    }
}

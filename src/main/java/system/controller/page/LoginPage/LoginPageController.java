package system.controller.page.LoginPage;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import system.controller.AuthorizedUser;
import system.controller.SpringContextUtil;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.model.Client;
import system.model.User;
import system.service.UserService;
import system.service.exception.NotFoundException;
import system.view.FactoryPage;
import system.view.PageEnum;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static system.util.Helper.*;

public class LoginPageController implements Controller {

    @FXML protected VBox vbox;

    @FXML protected JFXSpinner firstSpinner;

    @FXML protected JFXSpinner secondSpinner;

    @FXML protected JFXTextField login;

    @FXML protected JFXPasswordField password;

    @FXML protected Text warning;

    @FXML protected Text time;

    private Clock clock = new Clock();

    private Stage stage;

    //Service block
    private UserService service;
    //End service block

    private void init() {
        service = SpringContextUtil.getInstance().getBean(UserService.class);
    }

    @FXML public void initialize() {
        this.clock.start();
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode()==KeyCode.ENTER) {
            String login = this.login.getText();
            try {
                User user = service.getByLogin(login);
                if (user.getPassword()
                        .equals(password.getText())) {
                    AuthorizedUser.openSession(user.getId());
                    stage.close();
                    FactoryPage.getInstance().showPage(PageEnum.BASIC_PAGE);
                    clock.interrupt();
                } else
                    opacityPropertyWithAnimation(warning, 100);
            } catch (NotFoundException e) {
                opacityPropertyWithAnimation(warning, 100);
            }
        } else if(warning.opacityProperty()
                            .getValue()!=0)
            opacityPropertyWithAnimation(warning, 100);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setListener(ActiveListener listener) {

    }

    @Override
    public void setActiveClient(Client client) {

    }

    @Override
    public void execute(Command command) {
        switch (command) {
            case START:
                Thread thread = new Thread(new SpringContext());
                thread.start();
                break;
        }
    }

    private class SpringContext implements Runnable {
        @Override
        public void run() {
            SpringContextUtil.getInstance();
            firstSpinner.setVisible(false);
            secondSpinner.setVisible(false);
            opacityPropertyWithAnimation(vbox, 1000);
            init();
        }
    }

    private class Clock extends Thread {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    LocalTime clock = LocalTime.now();
                    time.setText(clock.format(formatter));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}

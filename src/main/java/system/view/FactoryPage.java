package system.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import system.MainApp;
import system.controller.page.BasicPage.BasicPageController;
import system.controller.page.ClientTicketPage.ClientTicketController;
import system.controller.page.RegistrationPersonPage.RegistrationPersonController;
import system.controller.page.TicketListPage.TicketListController;
import system.controller.page.listener.ControllerActiveListener;

import java.io.IOException;
import java.net.URL;

/**
 * Created by vladimir on 18.02.2018.
 */
public class FactoryPage {
    private Stage basicStage;
    private final URL startPage = MainApp.class.getResource("page/BasicPage.fxml");
    private final URL registrationPage = MainApp.class.getResource("page/RegistrationPerson.fxml");
    private final URL clientTicketList = MainApp.class.getResource("page/ClientTicketList.fxml");
    private final URL ticketList = MainApp.class.getResource("page/TicketSelect.fxml");


    private ControllerActiveListener basicController;

    private static FactoryPage ourInstance = new FactoryPage();

    public static FactoryPage getInstance() {
        return ourInstance;
    }

    private FactoryPage() {
    }

    public void setBasicStage(Stage basicStage) {
        this.basicStage = basicStage;
    }

    public void showPage(PageEnum pageEnum) {
        switch (pageEnum) {
            case BASIC_PAGE:
                showBasicPage();
                break;
            case LOGIN_PAGE:
                break;
            case REGISTRATION_PERSON_PAGE:
                showRegistrationPage();
                break;
            case CLIENT_TICKET_LIST:
                showClientTicketList();
                break;
            case TICKET_LIST:
                showTicketList();
                break;
        }
    }

    private void showBasicPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(startPage);
            Scene scene = new Scene(loader.load());
            basicController = ((BasicPageController)loader.getController()).getControllerActive();
            //basicPageController.setModel(this.model);
            basicStage.setTitle("Программа WakeBro Queue");
            basicStage.setScene(scene);
            basicStage.show();
            //ScenicView.show(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRegistrationPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(registrationPage);
            GridPane pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Регистрация клиента");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(pane));

            RegistrationPersonController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setListener(basicController);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showClientTicketList() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(clientTicketList);
            BorderPane pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить в очередь");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(pane));

            ClientTicketController controller =loader.getController();
            controller.setClientId(basicController.getClientId());
            controller.setDialogStage(dialogStage);
            controller.check();
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTicketList() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ticketList);
            BorderPane pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Выберите билет");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(pane));

            TicketListController controller = loader.getController();
            controller.setClientId(basicController.getClientId());
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String text, Stage dialogStage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Ошибка!");
        alert.setContentText(text);
        alert.showAndWait();
    }
}

package system.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import system.MainApp;
import system.controller.*;
import system.model.Model;

import java.io.IOException;
import java.net.URL;

/**
 * Created by vladimir on 18.02.2018.
 */
public class FactoryPage {
    private Stage basicStage;
    private Model model;
    private final URL startPage = MainApp.class.getResource("BasicPage.fxml");
    private final URL registrationPage = MainApp.class.getResource("RegistrationPerson.fxml");
    private final URL addInQueuePage = MainApp.class.getResource("AddPersonInQueue.fxml");
    private BasicPageController basicPageController;

    private static FactoryPage ourInstance = new FactoryPage();

    public static FactoryPage getInstance() {
        return ourInstance;
    }

    private FactoryPage() {
    }

    public void setBasicStage(Stage basicStage) {
        this.basicStage = basicStage;
    }

    public void setModel(Model model) {
        this.model = model;
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
            case ADD_IN_QUEUE:
                showAddInQueue();
                break;
        }
    }

    private void showBasicPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(startPage);
            Scene scene = new Scene(loader.load());
            basicPageController = loader.getController();
            basicPageController.setModel(this.model);
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
            dialogStage.setTitle("Регистрация");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(pane));

            RegistrationPersonController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setModel(model);
            controller.refresh();

            basicPageController.setRegistrationPersonController(controller);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAddInQueue() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(addInQueuePage);
            GridPane pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить в очередь");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(pane));

            AddInQueuePersonController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setModel(model);

            basicPageController.setAddInQueuePersonController(controller);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

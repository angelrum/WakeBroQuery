package system.view;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import system.MainApp;
import system.controller.AuthorizedUser;
import system.controller.Queue;
import system.controller.page.BasicPage.BasicPageController;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.controller.page.listener.ActiveListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimir on 18.02.2018.
 */
public class FactoryPage {
    private final static URL START = MainApp.class.getResource("page/BasicPage.fxml");
    private ThreadGroup threadGroup = new ThreadGroup("basic_thread_group");

    private ActiveListener basic;

    private Stage basicStage;

    private List<Stage> stages = new ArrayList<>();

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
                createLoginPage(pageEnum);
                break;
            case SCREEN_PAGE:
                createScreenPage(pageEnum);
                break;
            default:createPage(pageEnum);
        }
    }

    private void createPage(PageEnum page) {
        try {
            FXMLLoader loader = new FXMLLoader(page.getUrl());
            BorderPane pane = loader.load();
            pane.setTop(getMenuBar());

            Stage stage = new Stage();
            stage.setTitle(page.getName());
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует другие окна приложения
            stage.setScene(new Scene(pane));

            Controller controller = loader.getController();
            controller.setStage(stage);
            controller.setListener(basic);
            controller.setActiveClient(basic.getClient());
            controller.execute(null);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createScreenPage(PageEnum page) {
        try {
            FXMLLoader loader = new FXMLLoader(page.getUrl());
            SplitPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle(page.getName());
            stage.initModality(Modality.NONE);
            stage.setScene(new Scene(pane));
            stage.setOnCloseRequest(event -> stages.remove(stage));
            Controller controller = loader.getController();
            controller.setStage(stage);
            stages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createLoginPage(PageEnum page) {
        try {
            FXMLLoader loader = new FXMLLoader(page.getUrl());
            AnchorPane pane = loader.load();
            Controller controller = loader.getController();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(pane));
            controller.setStage(stage);
            stage.show();
            controller.execute(Command.START);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showBasicPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(START);
            BorderPane pane = loader.load();
            pane.setTop(getMenuBar());

            Scene scene = new Scene(pane);
            basic = ((BasicPageController)loader.getController()).getControllerActive();
            basicStage.setTitle("Программа WakeBro Queue");
            basicStage.setScene(scene);
            basicStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String text, Stage dialogStage) {
        JFXAlert alert = new JFXAlert(dialogStage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Ошибка!"));
        layout.setBody(new Label(text));
        JFXButton close = new JFXButton("Закрыть");
        close.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(close);
        alert.setContent(layout);
        alert.show();
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.initOwner(dialogStage);
//        alert.setTitle("Ошибка!");
//        alert.setContentText(text);
//        alert.showAndWait();
    }

    private MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu control = new Menu("Администрирование");
        MenuItem controlItem = new MenuItem("Управление билетами");
        controlItem.setOnAction(event -> FactoryPage.getInstance().showPage(PageEnum.TICKET_EDIT));

        MenuItem item1 = new MenuItem("Управление клиентами");
        item1.setOnAction(event -> FactoryPage.getInstance().showPage(PageEnum.CLIENT_EDIT));

        MenuItem item2 = new MenuItem("Управление пользователями");
        item2.setOnAction(event -> FactoryPage.getInstance().showPage(PageEnum.USER_EDIT));

        control.getItems().addAll(controlItem, item1, item2);
        menuBar.getMenus().addAll(control);

        final String os = System.getProperty ("os.name");
        if (os != null && os.startsWith ("Mac"))
            menuBar.useSystemMenuBarProperty ().set (true);
        return menuBar;
    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }

    public boolean checkActiveThread() {
        return threadGroup.activeCount() > 0;
    }

    public Thread getThreadByName(String name) {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        if (threadGroup.enumerate(threads) > 0) {
            for (Thread t : threads)
                if (name.equals(t.getName()))
                    return t;
        }
        return null;
    }

    public void close() {
        AuthorizedUser.closeSession();
        basicStage.close();
        Queue.getInstance().clearStopWatchListener();
        for (Stage stage : stages)
            stage.close();
        //createLoginPage(PageEnum.LOGIN_PAGE);
        //threadGroup.interrupt();
    }
}

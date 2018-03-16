package system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import system.HibernateUtil;
import system.controller.BasicPageController;
import system.model.Model;
import system.view.FactoryPage;
import system.view.PageEnum;


/**
 * Created by vladimir on 17.02.2018.
 */
public class MainApp extends Application {
    private Model model = new Model();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FactoryPage factoryPage = FactoryPage.getInstance();
        factoryPage.setBasicStage(primaryStage);
        factoryPage.setModel(this.model);
        factoryPage.showPage(PageEnum.BASIC_PAGE);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        HibernateUtil.close();
    }
}

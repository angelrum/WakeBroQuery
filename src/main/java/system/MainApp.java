package system;

import javafx.application.Application;
import javafx.stage.Stage;
import system.old.model.Model;
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

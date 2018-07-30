package system;

import javafx.application.Application;
import javafx.stage.Stage;
import system.util.Helper;
import system.view.FactoryPage;
import system.view.PageEnum;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;


/**
 * Created by vladimir on 17.02.2018.
 */
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FactoryPage factoryPage = FactoryPage.getInstance();
        factoryPage.setBasicStage(primaryStage);
        factoryPage.showPage(PageEnum.LOGIN_PAGE);
        //factoryPage.showPage(PageEnum.BASIC_PAGE);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        propertyClose();
        FactoryPage.getInstance().close();
        FactoryPage.getInstance().getThreadGroup().interrupt();
    }

    private void propertyClose() {
        try {
            Path path = Paths.get(MainApp.class.getResource("db/hsqldb.properties").getFile());
            Properties properties = Helper.getProperties(Files.newInputStream(path));
            if (Objects.nonNull(properties)
                    && properties.getProperty("database.init").equals("true")) {
                properties.setProperty("database.init", "false");
                OutputStream outputStream = Files.newOutputStream(path);
                properties.store(outputStream, null);
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

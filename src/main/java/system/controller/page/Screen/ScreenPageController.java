package system.controller.page.Screen;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.controller.page.listener.StopWatchListener;
import system.controller.to.QueueRow;
import system.model.Client;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScreenPageController implements Controller, StopWatchListener {
    @FXML protected TableView<QueueRow> queue;
    @FXML protected TableColumn<QueueRow, TextField> fioColumn;
    @FXML protected TableColumn <QueueRow, TextField> abnColumn;
    @FXML protected Text timer;
    @FXML protected Text total;

    private final String TOTAL_FORMAT = "Длительность очереди %s";

    private ScreenPageControllerInit controller;

    @FXML public void initialize() {
        this.controller = new ScreenPageControllerInit(this);
        this.controller.init();
    }

    @Override
    public void setStage(Stage stage) {

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

    @Override
    public void setTime(String time) {
        timer.setText(time);
    }

    @Override
    public void setTotalTime(long value) {
        if (value==0)
            total.setText("");
        else {
            LocalTime time = LocalTime.ofSecondOfDay(value);
            total.setText(String.format(TOTAL_FORMAT, time.format(DateTimeFormatter.ofPattern("mm:ss"))));
        }
    }
}

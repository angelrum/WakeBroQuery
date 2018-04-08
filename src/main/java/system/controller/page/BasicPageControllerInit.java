package system.controller.page;

import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import system.MainApp;
import system.controller.Queue;
import system.controller.to.QueueRow;

/**
 * Created by vladimir on 01.04.2018.
 */
public class BasicPageControllerInit {
    private BasicPageController controller;

    public BasicPageControllerInit(BasicPageController controller) {
        this.controller = controller;
    }

    public void init() {
        fieldInit();
        queueInit();
        controller.cancelDisable();
        controller.refresh();
    }

    private void fieldInit() {
        controller.telNumber.setListener(controller);
        controller.cancel.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("image/cancel.png"))));
    }

    private void queueInit() {
        controller.fioColumn.setCellValueFactory(cellData -> cellData.getValue().getClientName());
        controller.abnColumn.setCellValueFactory(cellData -> cellData.getValue().getPassName());
        controller.lControl.setCellValueFactory(cellData -> cellData.getValue().boxButtonProperty());
        controller.lStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        controller.cStatusColumn.setCellValueFactory(cellData -> cellData.getValue().cstatusProperty());
        controller.deleteColumn.setCellValueFactory(cellData -> cellData.getValue().deleteProperty());
        controller.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        controller.queue.setItems(Queue.getInstance().getQueueRows());
    }
}
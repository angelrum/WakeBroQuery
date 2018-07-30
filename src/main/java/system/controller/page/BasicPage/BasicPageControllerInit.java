package system.controller.page.BasicPage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import system.MainApp;
import system.controller.Queue;

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
        //initPlay();
        controller.cancelDisable();
        controller.refresh();
    }

    private void fieldInit() {
        controller.telnumber.setListener(controller);
        controller.cancel.setGraphic(new ImageView(new Image(MainApp.class.getResourceAsStream("image/cancel.png"))));
    }

    private void queueInit() {
        controller.fioColumn.setCellValueFactory(cellData -> cellData.getValue().getClientName());
        controller.abnColumn.setCellValueFactory(cellData -> cellData.getValue().getPassName());
        controller.lControl.setCellValueFactory(cellData -> cellData.getValue().boxButtonProperty());
        controller.lStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        controller.cStatusColumn.setCellValueFactory(cellData -> cellData.getValue().cstatusProperty());
        controller.deleteColumn.setCellValueFactory(cellData -> cellData.getValue().deleteProperty());
        controller.queue.setItems(Queue.getInstance().getQueueRows());
    }
}

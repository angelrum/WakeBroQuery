package system.controller.page.Screen;

import system.controller.Queue;

public class ScreenPageControllerInit {
    private ScreenPageController controller;

    public ScreenPageControllerInit(ScreenPageController controller) {
        this.controller = controller;
    }

    protected void init() {
        initTable();
        initTime();
    }

    private void initTable() {
        controller.fioColumn.setCellValueFactory(cellData->cellData.getValue().getClientName());
        controller.abnColumn.setCellValueFactory(cellData->cellData.getValue().getPassName());
        controller.queue.setItems(Queue.getInstance().getActiveRows());
    }

    private void initTime() {
        Queue.getInstance().setStopWatchListener(controller);
    }
}

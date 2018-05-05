package system.controller.page.ClientTicketPage;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.util.CollectionUtils;
import system.controller.Queue;
import system.controller.page.helper.QueueHelper;
import system.controller.to.QueueRow;
import system.controller.to.ClientTicketRow;
import system.view.FactoryPage;
import system.view.PageEnum;

/**
 * Created by vladimir on 05.04.2018.
 */
public class ClientTicketController {

    @FXML
    protected TableView<ClientTicketRow> tickets;

    @FXML
    protected TableColumn<ClientTicketRow, String> type;

    @FXML
    protected TableColumn<ClientTicketRow, CheckBox> equipment;

    @FXML
    protected TableColumn<ClientTicketRow, String> start;

    @FXML
    protected TableColumn<ClientTicketRow, String> end;

    @FXML
    protected TableColumn<ClientTicketRow, String> dateEnd;

    @FXML
    protected TableColumn<ClientTicketRow, Number> maxCount;

    @FXML
    protected TableColumn<ClientTicketRow, VBox> control;

    @FXML
    protected TableColumn<ClientTicketRow, Number> count;

    @FXML
    protected TableColumn<ClientTicketRow, String> duration;

    protected int clientId;

    private Stage dialogStage;

    private ClientTicketControllerInit controllerInit;

    public void setClientId(int id) {
        controllerInit = new ClientTicketControllerInit(this);
        clientId = id;
        controllerInit.init();
    }

    @FXML
    public void clickOk() {
        ClientTicketRow row = tickets
                .focusModelProperty()
                .get()
                .getFocusedItem();
        if (row==null)
            FactoryPage.getInstance().showAlert("Билет не выбран!", dialogStage);
        else {
            QueueRow queueRow = QueueHelper.transformToQueueRow(row);
            Queue.getInstance().insertRows(queueRow);
            close();
        }
    }

    @FXML
    public void clickCancel() {
        close();
    }

    @FXML
    public void clickAddTicket() {
        dialogStage.toBack();
        FactoryPage.getInstance().showPage(PageEnum.TICKET_LIST);
        dialogStage.toFront();
        controllerInit.init();
    }

    /*
    * Проверяем наличие активных билетов, если их нет,
    * то переходим сразу же к выбору билета из списка
    * */
    public void check() {
        if (CollectionUtils.isEmpty(tickets.getItems()))
            clickAddTicket();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void close() {
        dialogStage.close();
    }
}

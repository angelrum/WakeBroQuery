package system.controller.page.ClientTicketPage;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.util.CollectionUtils;
import system.controller.Queue;
import system.controller.page.helper.QueueHelper;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.controller.to.QueueRow;
import system.controller.to.ClientTicketRow;
import system.model.Client;
import system.view.FactoryPage;
import system.view.PageEnum;

/**
 * Created by vladimir on 05.04.2018.
 */
public class ClientTicketController implements Controller {

    @FXML protected TableView<ClientTicketRow> tickets;

    @FXML protected TableColumn<ClientTicketRow, String> type;

    @FXML protected TableColumn<ClientTicketRow, Boolean> equipment;

    @FXML protected TableColumn<ClientTicketRow, String> start;

    @FXML protected TableColumn<ClientTicketRow, String> end;

    @FXML protected TableColumn<ClientTicketRow, String> dateEnd;

    @FXML protected TableColumn<ClientTicketRow, Number> maxCount;

    @FXML protected TableColumn<ClientTicketRow, Integer> count;

    @FXML protected TableColumn<ClientTicketRow, String> duration;

    @FXML protected JFXCheckBox activeList;

    protected Client client;

    private Stage dialogStage;

    private ClientTicketControllerInit controller;

    @FXML public void clickOk() {
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

    @FXML public void clickCancel() {
        close();
    }

    @FXML public void clickAddTicket() {
        dialogStage.toBack();
        FactoryPage.getInstance().showPage(PageEnum.TICKET_LIST);
        refresh();
        dialogStage.toFront();
    }

    @FXML public void clickActiveTimeCheck() {
        controller.setActiveList(activeList.isSelected());
        refresh();
    }

    private void refresh() {
        controller.init();
        tickets.refresh();
    }

    /*
    * Проверяем наличие активных билетов, если их нет,
    * то переходим сразу же к выбору билета из списка
    * */
    @Override
    public void execute(Command command) {
        if (CollectionUtils.isEmpty(tickets.getItems()))
            clickAddTicket();
    }

    private void close() {
        dialogStage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.dialogStage = stage;
    }

    @Override
    public void setListener(ActiveListener listener) {

    }

    @Override
    public void setActiveClient(Client client) {
        controller = new ClientTicketControllerInit(this);
        this.client = client;
        refresh();
    }
}

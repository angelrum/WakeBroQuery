package system.controller.page.TicketListPage;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import system.controller.AuthorizedUser;
import system.controller.SpringContextUtil;
import system.controller.to.TicketRow;
import system.service.ClientTicketService;
import system.view.FactoryPage;

import java.util.Objects;

/**
 * Created by vladimir on 08.04.2018.
 */
public class TicketListController {

    @FXML
    protected TableView<TicketRow> tableView;

    @FXML
    protected TableColumn<TicketRow, String> type;

    @FXML
    protected TableColumn<TicketRow, String> name;

    @FXML
    protected TableColumn<TicketRow, CheckBox> equipment;

    @FXML
    protected TableColumn<TicketRow, Number> duration;

    @FXML
    protected TableColumn<TicketRow, String> start;

    @FXML
    protected TableColumn<TicketRow, String> end;

    @FXML
    protected TableColumn<TicketRow, String> month;

    private ClientTicketService service = SpringContextUtil.getInstance().getBean(ClientTicketService.class);

    private int clientId;

    private Stage dialogStage;

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void clickOk() {
        if (clientId > 0) {
            TicketRow row = tableView.getFocusModel().getFocusedItem();
            if (Objects.isNull(row))
                FactoryPage.getInstance().showAlert("Выберите билет!", dialogStage);
            else {
                service.save(clientId, row.getTicket());
                close();
            }
        }
    }

    @FXML
    public void clickCancel() {
        close();
    }

    @FXML
    public void initialize() {
        new TicketListControllerInit(this).init();
    }

    private void close() {
        dialogStage.close();
    }

}

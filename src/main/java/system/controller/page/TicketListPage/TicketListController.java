package system.controller.page.TicketListPage;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import system.controller.AuthorizedUser;
import system.controller.SpringContextUtil;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.controller.to.TicketRow;
import system.model.Client;
import system.service.ClientTicketService;
import system.view.FactoryPage;

import java.util.Objects;

/**
 * Created by vladimir on 08.04.2018.
 */
public class TicketListController implements Controller {

    @FXML protected TableView<TicketRow> tableView;

    @FXML protected TableColumn<TicketRow, String> type;

    @FXML protected TableColumn<TicketRow, String> name;

    @FXML protected TableColumn<TicketRow, Boolean> equipment;

    @FXML protected TableColumn<TicketRow, Number> duration;

    @FXML protected TableColumn<TicketRow, String> start;

    @FXML protected TableColumn<TicketRow, String> end;

    @FXML protected TableColumn<TicketRow, String> day;

    @FXML protected TableColumn<TicketRow, String> month;

    @FXML protected TableColumn<TicketRow, Integer> count;

    @FXML protected JFXCheckBox activeList;

    private ClientTicketService service = SpringContextUtil.getInstance().getBean(ClientTicketService.class);

    private TicketListControllerInit controller = new TicketListControllerInit(this);

    private Client client;

    private Stage dialogStage;

    @FXML public void clickOk() {
        if (Objects.nonNull(client)) {
            TicketRow row = tableView.getFocusModel().getFocusedItem();
            if (Objects.isNull(row))
                FactoryPage.getInstance().showAlert("Выберите билет!", dialogStage);
            else {
                for (int i = 0; i < row.getCount(); i++)
                    service.save(client.getId(), row.getTicket());
                close();
            }
        }
    }

    @FXML public void clickCancel() {
        close();
    }

    @FXML public void initialize() {
        controller.init();
        tableView.refresh();
    }

    @FXML public void clickActiveTimeCheck() {
        controller.setActiveTime(activeList.isSelected());
        initialize();
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
        this.client = client;
    }

    @Override
    public void execute(Command command) {

    }
}

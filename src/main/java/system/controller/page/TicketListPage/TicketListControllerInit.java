package system.controller.page.TicketListPage;

import javafx.collections.ObservableList;
import system.controller.SpringContextUtil;
import system.controller.page.helper.TicketTableHelper;
import system.controller.to.TicketRow;
import system.model.Ticket;
import system.service.TicketService;
import system.service.exception.NotFoundException;

import java.util.List;

/**
 * Created by vladimir on 08.04.2018.
 */
public class TicketListControllerInit {

    private TicketListController controller;

    private TicketService service = SpringContextUtil.getInstance().getBean(TicketService.class);

    public TicketListControllerInit(TicketListController controller) {
        this.controller = controller;
    }

    public void init() {
        controller.type.setCellValueFactory(cellData->cellData.getValue().passProperty());
        controller.name.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        controller.equipment.setCellValueFactory(cellData->cellData.getValue().equipmentProperty());
        controller.duration.setCellValueFactory(cellData->cellData.getValue().durationProperty());
        controller.start.setCellValueFactory(cellData->cellData.getValue().startProperty());
        controller.end.setCellValueFactory(cellData->cellData.getValue().endProperty());
        controller.month.setCellValueFactory(cellData->cellData.getValue().monthProperty());
        initTable();
    }

    private void initTable() {
        try {
            List<Ticket> list = service.getAllActive();
            ObservableList<TicketRow> rows = TicketTableHelper.getTicketRowList(list);
            controller.tableView.setItems(rows);
            controller.tableView.getSelectionModel().clearSelection();
        } catch (NotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}

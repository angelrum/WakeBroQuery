package system.controller.page;

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
public class TicketSelectControllerInit {

    private TicketSelectController controller;

    private TicketService service = SpringContextUtil.getInstance().getBean(TicketService.class);

    public TicketSelectControllerInit(TicketSelectController controller) {
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
        } catch (NotFoundException | NullPointerException e) {
            //controller.tableView.setItems(FXCollections.emptyObservableList());
        }
    }
}

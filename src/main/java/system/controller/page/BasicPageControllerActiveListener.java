package system.controller.page;

import system.controller.Queue;
import system.controller.SpringContextUtil;
import system.controller.page.helper.QueueHelper;
import system.controller.page.listener.ControllerActiveListener;
import system.controller.page.helper.ButtonSubmitEnum;
import system.model.Client;
import system.model.ClientTicket;
import system.model.Ticket;
import system.service.CustomerTicketService;

import java.util.Map;

/**
 * Created by vladimir on 01.04.2018.
 */
public class BasicPageControllerActiveListener implements ControllerActiveListener {

    private BasicPageController controller;

    private CustomerTicketService service = SpringContextUtil.getInstance().getBean(CustomerTicketService.class);

    public BasicPageControllerActiveListener(BasicPageController controller) {
        this.controller = controller;
    }

    public void refresh() {
        controller.clientId = 0;
        controller.fname.setVisible(false);
        controller.lname.setVisible(false);
        controller.sname.setVisible(false);
        controller.telNumber.setListener(controller);
        controller.submit.setText("Зарегистрировать", ButtonSubmitEnum.REGISTRATION);
    }

    public void clickCancelButton() {
        controller.telNumber.clear();
        controller.cancelDisable();
        refresh();
    }

    //Добавляем пользователя в очередь
    public void clickSubmitButton() {
        try {
            Map<ClientTicket, Ticket> allActiveTicket = service.getAllActiveTicket(controller.clientId);
            allActiveTicket.forEach((clientTicket, ticket) -> {
                Queue.getInstance().insertRows(QueueHelper.transformToQueueRow(clientTicket, ticket));
            });
        } catch (Exception e) {
            //ignore
        } finally {
            refresh();
        }
    }

    public void setClient(Client client) {
        controller.clientId = client.getId();
        controller.fname.setText(client.getFirstname());
        controller.lname.setText(client.getLastname());
        controller.sname.setText(client.getSecondname());
        controller.fname.setVisible(true);
        controller.fname.setVisible(true);
        controller.lname.setVisible(true);
        controller.sname.setVisible(true);
        controller.submit.setText("Добавить в очередь", ButtonSubmitEnum.ADD_IN_QUEUE);
    }

}

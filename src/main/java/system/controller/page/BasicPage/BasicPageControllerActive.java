package system.controller.page.BasicPage;

import system.controller.page.listener.ControllerActiveListener;
import system.controller.page.helper.ButtonSubmitEnum;
import system.model.Client;
import system.view.FactoryPage;
import system.view.PageEnum;

/**
 * Created by vladimir on 01.04.2018.
 */
public class BasicPageControllerActive implements ControllerActiveListener {

    private BasicPageController controller;

    public BasicPageControllerActive(BasicPageController controller) {
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

    public void clear() {
        refresh();
        controller.telNumber.clear();
    }

    public void clickCancelButton() {
        controller.telNumber.clear();
        controller.cancelDisable();
        refresh();
    }

    //Добавляем пользователя в очередь
    public void clickSubmitButton() {
        switch (controller.submit.getSubmitEnum()) {
            case ADD_IN_QUEUE:
                FactoryPage.getInstance().showPage(PageEnum.CLIENT_TICKET_LIST);
                clear();
                break;
            case REGISTRATION:
                FactoryPage.getInstance().showPage(PageEnum.REGISTRATION_PERSON_PAGE);
                break;
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

    @Override
    public int getClientId() {
        return controller.clientId;
    }

    @Override
    public String getInsertNumber() {
        return controller.telNumber.getText();
    }
}

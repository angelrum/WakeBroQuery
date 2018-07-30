package system.controller.page.BasicPage;

import system.controller.Queue;
import system.controller.page.listener.ActiveListener;
import system.controller.page.helper.ButtonSubmitEnum;
import system.controller.page.listener.StopWatchListener;
import system.model.Client;
import system.view.FactoryPage;
import system.view.PageEnum;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by vladimir on 01.04.2018.
 */
public class BasicPageActive implements ActiveListener, StopWatchListener {

    private final String TOTAL_FORMAT = "Длительность очереди %s";

    private BasicPageController controller;

    public BasicPageActive(BasicPageController controller) {
        this.controller = controller;
        Queue.getInstance().setStopWatchListener(this); //для определения длительности очереди
    }

    public void refresh() {
        controller.client = null;
        controller.fname.setVisible(false);
        controller.lname.setVisible(false);
        controller.sname.setVisible(false);
        controller.telnumber.setListener(controller);
        controller.submit.setText("Зарегистрировать", ButtonSubmitEnum.REGISTRATION);
    }

    public void clear() {
        refresh();
        controller.telnumber.clear();
    }

    public void clickCancelButton() {
        controller.telnumber.clear();
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
        controller.client = client;
        controller.telnumber.setText(client.getTelnumber());
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
    public Client getClient() {
        return controller.client;
    }

    @Override
    public String getInsertNumber() {
        return controller.telnumber.getText();
    }

    @Override
    public void setTotalTime(long value) {
        if (value==0)
            controller.total.setText("");
        else {
            LocalTime time = LocalTime.ofSecondOfDay(value);
            controller.total.setText(String.format(TOTAL_FORMAT, time.format(DateTimeFormatter.ofPattern("mm:ss"))));
        }
    }

    @Override
    public void setTime(String time) {
        controller.timer.setText(time);
    }
}

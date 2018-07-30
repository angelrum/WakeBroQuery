package system.controller.page.listener;

import system.model.Client;

/**
 * Created by vladimir on 01.04.2018.
 */
public interface ActiveListener {

    void refresh();

    void clear();

    void clickCancelButton();

    void clickSubmitButton();

    void setClient(Client client);

    Client getClient();

    String getInsertNumber();

    //void setTotalValue(long value);
}

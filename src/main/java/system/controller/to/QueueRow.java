package system.controller.to;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import system.controller.QueueListener;
import system.controller.SpringContextUtil;
import system.model.*;
import system.service.ClientTicketService;
import system.service.ClientTicketStoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static system.controller.page.helper.FontAwesomeIconHelper.*;

/**
 * Created by vladimir on 01.04.2018.
 *
 * Класс является элементом очереди
 */
public class QueueRow {
    //Service block
    private ClientTicketStoryService ctsService = SpringContextUtil.getInstance().getBean(ClientTicketStoryService.class);

    private ClientTicketService ctService = SpringContextUtil.getInstance().getBean(ClientTicketService.class);

    private SimpleObjectProperty<VBox> control = new SimpleObjectProperty<>(new VBox());

    private StackPane up;

    private StackPane down;

    private ObservableValue<StackPane> status;

    private ObservableValue<CheckBox> cstatus;

    private ObservableValue<StackPane> delete;

    private String pass = new String();

    private List<ClientTicket> tickets = new ArrayList<>();

    private ClientTicket tmpClTicket;

    private QueueListener listener;

    private boolean active = true;

    private boolean block = false;

    private final static String PLAY_STYLE = "status-play";

    private final static String PAUSE_STYLE = "status-pause";

    public QueueRow(List<ClientTicket> list) {
        tickets.addAll(list);
        init();
    }

    //основной блок инициализации
    private void init() {
        initDeleteButton();
        initStatusButton();
        initControlButton();
        initPassName();
    }

    private void initPassName() {
        Ticket ticket = getTicket();
        if (Objects.isNull(ticket)) return;

        switch (ticket.getPass()) {
            case SECOND_PASS:
                pass = String.format("%s (%s)", ticket.getPass().getName(), getCount());
                break;
            case ABN_PASS:
                pass = ticket.getPass().getName();
                break;
        }
    }

    private Client getClient() {
        if (!tickets.isEmpty())
            return tickets.get(0).getClient();
        return null;
    }

    private Ticket getTicket() {
        if (!tickets.isEmpty())
            return tickets.get(0).getTicket();
        return null;
    }

    private void initDeleteButton() {
        FontAwesomeIconView icon = getIconView(FontAwesomeIcon.TIMES);
        icon.setOnMouseClicked(event -> listener.remove(this));
        icon.getStyleClass().add("delete");
        delete = new SimpleObjectProperty<>(getPane(icon));


//        Button button = new Button();
//        button.getStyleClass().add("delete-person");
//        button.setOnMouseClicked(event -> {
//            listener.remove(this);
//        });
//        delete = new SimpleObjectProperty<>(button);
    }

    private void initStatusButton() {
        CheckBox cstatus = new CheckBox();
        cstatus.setSelected(true);

        FontAwesomeIconView icon = getIconView(FontAwesomeIcon.PAUSE);
        icon.getStyleClass().add(PAUSE_STYLE);

        icon.setOnMouseClicked(event -> {
            if (cstatus.isSelected()) {
                cstatus.setSelected(false);
                icon.setIcon(FontAwesomeIcon.PLAY);
                icon.getStyleClass().remove(PAUSE_STYLE);
                icon.getStyleClass().addAll(PLAY_STYLE);
                listener.shiftRowInDisactiveQueue(this);
                active = false;
            }
            else {
                icon.setIcon(FontAwesomeIcon.PAUSE);
                icon.getStyleClass().remove(PLAY_STYLE);
                icon.getStyleClass().addAll(PAUSE_STYLE);
                cstatus.setSelected(true);
                listener.shiftRowInActive(this);
                active = true;
            }
        });
        this.status = new SimpleObjectProperty<>(getPane(icon));
        this.cstatus = new SimpleObjectProperty<>(cstatus);
    }

    private void initControlButton() {
        FontAwesomeIconView iconUp = getIconView(FontAwesomeIcon.CHEVRON_UP);
        FontAwesomeIconView iconDown = getIconView(FontAwesomeIcon.CHEVRON_DOWN);
        iconUp.setOnMouseClicked(event -> listener.toUp(this));
        iconDown.setOnMouseClicked(event -> listener.toDown(this));
        up = getPane(iconUp);
        up.getStyleClass().add("button-up");
        down = getPane(iconDown);
        down.getStyleClass().add("button-down");
        control.getValue().getChildren().addAll(up, down);
        control.getValue().getStyleClass().add("box");
    }

    public boolean startUseClientTicket() {
        if (Objects.isNull(tmpClTicket)) {
            tmpClTicket = getTmpClient();
            if (Objects.isNull(tmpClTicket))
                return false;
            ctsService.save(tmpClTicket);
        }
        disactive();
        return true;
    }

    /*
    * Списываем билет и проверяем, что элемент очереди еще содержит билеты
    * */
    public boolean endUseClientTicket() {
        if (Objects.nonNull(tmpClTicket)) {
            ctsService.closeStory(tmpClTicket.getClient().getId());
            reedemClTicket();
            active();
            return checkQueue();
        }
        return false;
    }

    private void reedemClTicket() {
        if (tmpClTicket.getTicket().getPass()!=Pass.ABN_PASS) {
            tickets.remove(tmpClTicket);
            tmpClTicket.reeded();
            ctService.save(tmpClTicket);
            initPassName();
        }
        tmpClTicket = null;
    }

    private ClientTicket getTmpClient() {
        if (!tickets.isEmpty())
            return tickets.get(0);
        return null;
    }

    //Проверяем наличие активных билетов
    public boolean checkQueue() {
        return !tickets.isEmpty();
    }

    private void setActiveStatus(boolean flag) {
        block = flag;
        disabledControlButton(flag);
        status.getValue().setDisable(flag);
        delete.getValue().setDisable(flag);
    }
    private void active() {
        setActiveStatus(false);
    }

    private void disactive() {
        setActiveStatus(true);
    }

    public boolean isBlock() {
        return block;
    }

    public SimpleObjectProperty<TextField> getClientName() {
        return getTextProperty(String.format("%s %s %s", getClient().getLastname(), getClient().getFirstname(), getClient().getSecondname()));
    }

    public Integer getCount() {
        return tickets.size();
    }

    public void setDisabledUp(boolean flag) {
        up.setDisable(flag);
    }

    public void setDisabledDown(boolean flag) {
        down.setDisable(flag);
    }

    public void disabledControlButton(boolean flag) {
        setDisabledUp(flag);
        setDisabledDown(flag);
    }

    public boolean isActive() {
        return active;
    }

    public SimpleObjectProperty<TextField> getPassName() {
        return getTextProperty(pass);
    }

    private SimpleObjectProperty<TextField> getTextProperty(String text) {
        TextField field = new TextField(text);
        field.setEditable(false);
        field.getStyleClass().add("text-field");
        return new SimpleObjectProperty<>(field);
    }

    public ObservableValue<VBox> boxButtonProperty() {
        return control;
    }

    public ObservableValue<StackPane> statusProperty() {
        return status;
    }

    public ObservableValue<CheckBox> cstatusProperty() {
        return cstatus;
    }

    public ObservableValue<StackPane> deleteProperty() {
        return delete;
    }

    public void setListener(QueueListener listener) {
        this.listener = listener;
    }

    public List<ClientTicket> getTickets() {
        return tickets;
    }
}

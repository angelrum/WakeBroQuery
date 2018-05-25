package system.controller.page.helper.TableCell;

import system.controller.to.ClientTicketRow;
import system.controller.to.TicketRow;
import system.model.Pass;
import system.model.Ticket;

import java.util.Objects;

/**
 * Created by vladimir on 22.05.2018.
 * Создан для ребактирования таблицы
 *
 * Использование:
 * 1. через setCellFactory вызывается метод getCell;
 * 2. через setCellValueFactory вызывается метод инициализации setValue.
 *
 */
public class SpinnerCreate<S> {
    private static SpinnerCreate instance;
    private final Integer MAX_VALUE = 100;

    private SpinnerCell<S> spinnerCell;

    public static SpinnerCreate getInstance() {
        if (instance==null)
            instance = new SpinnerCreate();
        return instance;
    }

    public SpinnerCell<S> getCell(String field) {
        spinnerCell = new SpinnerCell<S>(field);
        return spinnerCell;
    }

    public void setValue(S s) {
        spinnerCell.initialize(getMaxCount(s));
        checkActive(s);
    }

    private Integer getMaxCount(S s) {
        if (s.getClass().isAssignableFrom(ClientTicketRow.class)) {
            ClientTicketRow row = (ClientTicketRow) s;
            return row.getMaxCount();
        }
        else
            return MAX_VALUE;
    }

    private void checkActive(S s) {
        Ticket ticket = null;
        if (s.getClass()
                .isAssignableFrom(ClientTicketRow.class)) {
            ClientTicketRow row = (ClientTicketRow) s;
            ticket = row.getTicket();
        } else if (s.getClass()
                .isAssignableFrom(TicketRow.class)) {
            TicketRow row = (TicketRow) s;
            ticket = row.getTicket();
        }
        if (Objects.nonNull(ticket))
            spinnerCell.setDisactive(ticket.getPass()==Pass.ABN_PASS);
    }
}

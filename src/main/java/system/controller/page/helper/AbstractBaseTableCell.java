package system.controller.page.helper;

import javafx.collections.ObservableList;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import system.model.Pass;
import system.model.Ticket;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by vladimir on 13.05.2018.
 */
public abstract class AbstractBaseTableCell <T> extends TableCell<Ticket, T> {
    protected ObservableList<Ticket> tickets;
    protected Set<Ticket> upd;
    private String field;

    public AbstractBaseTableCell(ObservableList<Ticket> tickets, Set<Ticket> upd, ObservableList<T> values, String field) {
        super();
        this.tickets = tickets;
        this.upd = upd;
        this.field = field;
        createControlClass(values);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if(isEditing()) {
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            insertValue(item);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    protected void setValue(Ticket ticket, T t) {
        try {
            Class clazz = ticket.getClass();
            Field field = clazz.getDeclaredField(this.field);
            field.setAccessible(true);
            if (field.getType()
                    .isAssignableFrom(Pass.class)) {
                field.set(ticket, Pass.getPassByName(t.toString()));
            } else
                field.set(ticket, t);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    abstract void createControlClass(ObservableList<T> values);

    abstract void insertValue(T item);
}

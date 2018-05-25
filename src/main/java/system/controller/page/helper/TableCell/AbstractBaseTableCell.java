package system.controller.page.helper.TableCell;

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
public abstract class AbstractBaseTableCell <S, T> extends TableCell<S, T> {
    protected Set<S> upd;
    private String field;

    public AbstractBaseTableCell(Set<S> upd, ObservableList<T> values, String field) {
        super();
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

    protected void setValue(S s, T t) {
        try {
            Class clazz = s.getClass();
            Field field = clazz.getDeclaredField(this.field);
            field.setAccessible(true);
            if (field.getType()
                    .isAssignableFrom(Pass.class)) {
                field.set(s, Pass.getPassByName(t.toString()));
            } else
                field.set(s, t);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void addUpdateItem(S s) {
        if (upd!=null) upd.add(s);
    }

    abstract void createControlClass(ObservableList<T> values);

    abstract void insertValue(T item);
}

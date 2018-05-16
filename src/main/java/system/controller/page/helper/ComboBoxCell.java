package system.controller.page.helper;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import system.model.Ticket;

import java.util.Objects;
import java.util.Set;

/**
 * Created by vladimir on 13.05.2018.
 */
public class ComboBoxCell <T> extends AbstractBaseTableCell<T> {
    private JFXComboBox<T> comboBox;

    public ComboBoxCell(ObservableList<Ticket> tickets, Set<Ticket> upd, ObservableList<T> values, String field) {
        super(tickets, upd, values, field);
    }

    @Override
    protected void createControlClass(ObservableList<T> values) {
        comboBox = new JFXComboBox<T>(values);
        comboBox.setOnAction(event -> {
            T t = comboBox.getValue();
            int index = getIndex();
            Ticket ticket = tickets.get(index);
            setValue(ticket, t);
            upd.add(ticket);
            setText(t==null? null : String.valueOf(t));
            commitEdit(t);
        });
    }

    @Override
    void insertValue(T item) {
        if (Objects.nonNull(item)) {
            comboBox.setValue(item);
            setText(String.valueOf(item));
        }
        setGraphic(this.comboBox);
    }
}

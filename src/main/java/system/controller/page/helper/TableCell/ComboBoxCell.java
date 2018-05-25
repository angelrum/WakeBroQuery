package system.controller.page.helper.TableCell;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import system.controller.page.helper.TableCell.AbstractBaseTableCell;
import system.model.Ticket;

import java.util.Objects;
import java.util.Set;

/**
 * Created by vladimir on 13.05.2018.
 */
public class ComboBoxCell <S, T> extends AbstractBaseTableCell<S, T> {
    private JFXComboBox<T> comboBox;

    public ComboBoxCell(Set<S> upd, ObservableList<T> values, String field) {
        super(upd, values, field);
    }

    @Override
    protected void createControlClass(ObservableList<T> values) {
        comboBox = new JFXComboBox<T>(values);
        comboBox.setOnAction(event -> {
            T t = comboBox.getValue();
            int index = getIndex();
            S s = getTableView().getItems().get(index);
            setValue(s, t);
            addUpdateItem(s);
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

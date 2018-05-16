package system.controller.page.helper;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.ObservableList;
import system.model.Ticket;

import java.util.Set;

/**
 * Created by vladimir on 13.05.2018.
 */
public class CheckBoxCell extends AbstractBaseTableCell<Boolean> {
    private JFXCheckBox checkBox;

    public CheckBoxCell(ObservableList<Ticket> observableList, Set upd, ObservableList<Boolean> values, String field) {
        super(observableList, upd, values, field);
    }

    @Override
    void createControlClass(ObservableList<Boolean> values) {
        this.checkBox = new JFXCheckBox();
        this.checkBox.setOnAction(event -> {
            Boolean t = checkBox.isSelected();
            int index = getIndex();
            Ticket ticket = tickets.get(index);
            setValue(ticket, t);
            upd.add(ticket);
            commitEdit(t);
        });
    }

    @Override
    void insertValue(Boolean item) {
        this.checkBox.setSelected(item);
        setGraphic(this.checkBox);
    }
}

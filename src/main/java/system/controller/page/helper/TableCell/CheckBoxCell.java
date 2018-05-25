package system.controller.page.helper.TableCell;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.ObservableList;
import system.controller.page.helper.TableCell.AbstractBaseTableCell;
import system.model.Ticket;

import java.util.Set;

/**
 * Created by vladimir on 13.05.2018.
 */
public class CheckBoxCell<S> extends AbstractBaseTableCell<S, Boolean> {
    private JFXCheckBox checkBox;

    public CheckBoxCell(Set<S> upd, ObservableList<Boolean> values, String field) {
        super(upd, values, field);
    }

    public CheckBoxCell (String field, boolean disactive) {
        super(null, null, field);
        setDisactive(disactive);
    }



    @Override
    void createControlClass(ObservableList<Boolean> values) {
        this.checkBox = new JFXCheckBox();
        this.checkBox.setOnAction(event -> {
            Boolean t = checkBox.isSelected();
            int index = getIndex();
            S s = getTableView().getItems()
                    .get(index);
            setValue(s, t);
            addUpdateItem(s);
            commitEdit(t);
        });
    }

    public void setDisactive(boolean disactive) {
        checkBox.setDisable(disactive);
    }

    @Override
    void insertValue(Boolean item) {
        this.checkBox.setSelected(item);
        setGraphic(this.checkBox);
    }
}

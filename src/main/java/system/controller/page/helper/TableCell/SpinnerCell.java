package system.controller.page.helper.TableCell;

import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import system.controller.to.TicketRow;

/**
 * Created by vladimir on 20.05.2018.
 *
 */
public class SpinnerCell <S> extends AbstractBaseTableCell <S, Integer> {
    private Spinner<Integer> spinner;
    private static Integer MAX_VALUE = 10;

    public SpinnerCell(String field) {
        super(null, null, field);
    }

    public SpinnerCell(String field, int max_value) {
        this(field);
        MAX_VALUE = max_value;
    }

    public void initialize(Integer max) {
        SpinnerValueFactory.IntegerSpinnerValueFactory factory = (SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory();
        factory.setMax(max);
    }

    @Override
    void createControlClass(ObservableList<Integer> values) {
        spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, MAX_VALUE));
        spinner.setOnMouseClicked(event -> {
            Integer value = spinner.getValue();
            S s = getTableView()
                    .getItems()
                    .get(getIndex());
            setValue(s, value);
            addUpdateItem(s);
            commitEdit(value);
        });
    }

    @Override
    protected void setValue(S s, Integer integer) {
        if (s.getClass()
                .isAssignableFrom(TicketRow.class)) {
            TicketRow row = (TicketRow) s;              //TicketRow присвоение выполняется через обертку
            row.countProperty().setValue(integer);
        } else
            super.setValue(s, integer);
    }

    @Override
    void insertValue(Integer item) {
        spinner.getValueFactory().setValue(item);
        setGraphic(spinner);
    }

    void setDisactive(boolean disactive) {
        spinner.setDisable(disactive);
    }

}

package system.controller.page.helper.TableCell;

import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.StringConverter;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

/**
 * Created by vladimir on 16.05.2018.
 */
public class TimePickerCell <S> extends AbstractBaseTableCell<S, LocalTime> {

    private JFXTimePicker timePicker;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public TimePickerCell(Set<S> upd, String field) {
        super(upd, null, field);
    }

    @Override
    void createControlClass(ObservableList<LocalTime> values) {
        timePicker = new JFXTimePicker();
        timePicker.setIs24HourView(true);
        timePicker.setEditable(true);
        timePicker.setConverter(new StringConverter<LocalTime>() {

            @Override
            public String toString(LocalTime date) {
                if (Objects.nonNull(date))
                    return formatter.format(date);
                return "";
            }

            @Override
            public LocalTime fromString(String string) {
                if (StringUtils.hasLength(string))
                    return LocalTime.parse(string, formatter);
                return null;
            }
        });

        timePicker.setOnAction(event -> {
            LocalTime time = timePicker.getValue();
            int index = getIndex();
            S s = getTableView().getItems()
                    .get(index);
            setValue(s, time);
            addUpdateItem(s);
            setText(time==null ? null : time.toString());
            insertValue(time);
            commitEdit(time);
        });
        setAlignment(Pos.CENTER);
    }

    @Override
    void insertValue(LocalTime item) {
        if (Objects.nonNull(item)) {
            timePicker.setValue(item);
            setText(item.toString());
        }
        setGraphic(this.timePicker);
    }
}

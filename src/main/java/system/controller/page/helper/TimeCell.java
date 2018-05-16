package system.controller.page.helper;

import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.util.StringConverter;
import org.springframework.util.StringUtils;
import system.model.Ticket;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

/**
 * Created by vladimir on 16.05.2018.
 */
public class TimeCell extends AbstractBaseTableCell<LocalTime> {

    private JFXTimePicker timePicker;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public TimeCell(ObservableList<Ticket> tickets, Set<Ticket> upd, String field) {
        super(tickets, upd, null, field);
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
            Ticket ticket = tickets.get(index);
            setValue(ticket, time);
            upd.add(ticket); //добавляем к списку редактируемых
//            setText(time==null ? null : time.toString());
//            commitEdit(time);
            insertValue(time);
        });
        setAlignment(Pos.CENTER);
    }

    @Override
    void insertValue(LocalTime item) {
        if (Objects.nonNull(item)) {
            timePicker.setValue(item);
            setText(item.format(formatter));
        }
        setGraphic(this.timePicker);
    }
}

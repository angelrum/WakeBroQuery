package system.controller.page.helper;

import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.util.StringConverter;
import org.springframework.util.StringUtils;
import system.model.Ticket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

/**
 * Created by vladimir on 16.05.2018.
 */
public class DateCell extends AbstractBaseTableCell<LocalDate> {
    private JFXDatePicker datePicker;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public DateCell(ObservableList<Ticket> tickets, Set<Ticket> upd, String field) {
        super(tickets, upd, null, field);
    }

    @Override
    void createControlClass(ObservableList<LocalDate> values) {
        datePicker = new JFXDatePicker();
        datePicker.setEditable(true);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (Objects.nonNull(date))
                    return formatter.format(date);
                return "";
            }
            @Override
            public LocalDate fromString(String string) {
                if (StringUtils.hasLength(string))
                    return LocalDate.parse(string, formatter);
                return null;
            }
        });
        datePicker.setOnAction(event -> {
            LocalDate date = datePicker.getValue();
            int index = getIndex();
            Ticket ticket = tickets.get(index);
            setValue(ticket, date);
            upd.add(ticket); //добавляем к списку редактируемых
            //setText(date==null ? null : date.toString());
            insertValue(date);
            commitEdit(date);

        });
        setAlignment(Pos.CENTER);

    }

    @Override
    void insertValue(LocalDate item) {
        if (Objects.nonNull(item)) {
            datePicker.setValue(item);
            setText(item.format(formatter));
        }
        setGraphic(this.datePicker);
    }
}

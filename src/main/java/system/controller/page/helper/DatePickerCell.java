package system.controller.page.helper;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;
import org.springframework.util.StringUtils;
import system.model.Ticket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Created by vladimir on 04.05.2018.
 */
public class DatePickerCell extends TableCell<Ticket, LocalDate> {
    private DatePicker datePicker;
    private ObservableList<Ticket> ticketData;

    public DatePickerCell(ObservableList<Ticket> tickets) {
        super();
        this.ticketData = tickets;
        if (datePicker == null)
            createDatePicker();
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        Platform.runLater(()->datePicker.requestFocus());
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if(isEditing()) {
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            datePicker.setValue(item);
            setText(item==null ? null : item.toString());
            setGraphic(this.datePicker);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker();
        datePicker.setEditable(true);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
            setText(date==null ? null : date.toString());
            commitEdit(date);
            if (Objects.nonNull(ticketData))
                ticketData.get(index).setStartDate(date);
        });
        setAlignment(Pos.CENTER);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }
}

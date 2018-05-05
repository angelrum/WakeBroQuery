package system.controller.page.helper;

import javafx.util.StringConverter;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Created by vladimir on 05.05.2018.
 */
public class TimeConverter extends StringConverter<LocalTime> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public String toString(LocalTime time) {
        if (Objects.isNull(time))
            return "";
        return time.format(formatter);
    }

    @Override
    public LocalTime fromString(String string) {
        if (StringUtils.hasLength(string)) {
            if (string.matches("[0-9]{2}[:]?[0-9]{2}")) {
                String time = string.replaceAll(":", "");
                int hour = Integer.parseInt(time.substring(0, 2));
                int minute = Integer.parseInt(time.substring(2, 4));
                if (hour >= 0 && hour <= 23
                        && minute >= 0 && minute <= 59)
                    return LocalTime.of(hour, minute);
            }
        }
        return null;
    }
}

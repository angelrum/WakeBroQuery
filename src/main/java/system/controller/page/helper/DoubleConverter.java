package system.controller.page.helper;

import javafx.util.converter.DoubleStringConverter;

/**
 * Created by vladimir on 05.05.2018.
 */
public class DoubleConverter extends DoubleStringConverter {
    @Override
    public Double fromString(String value) {
        value = value.replaceAll(",", ".");
        if (value.matches("\\d+(\\.\\d+)?"))
            return super.fromString(value);
        return null;
    }
}

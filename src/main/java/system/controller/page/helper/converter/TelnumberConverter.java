package system.controller.page.helper.converter;

import javafx.util.StringConverter;

public class TelnumberConverter extends StringConverter<String> {

    private final String formatNumber = "+_(___)___-__-__";

    @Override
    public String toString(String object) {
        return object;
    }

    @Override
    public String fromString(String string) {
        String format = String.valueOf(formatNumber);
        if ("".equals(string)) return string;

        string = string.replaceAll("[\\+|\\)|\\(|\\-]", "");
        for (String t : string.split(""))
            format = format.replaceFirst("_", t);
        return format;
    }
}

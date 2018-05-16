package system.controller.page.helper;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import org.springframework.util.StringUtils;
import system.controller.SpringContextUtil;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.service.ClientService;
import system.service.exception.NotFoundException;
import system.util.ValidationUtil;

import java.util.NoSuchElementException;

/**
 * Created by vladimir on 17.02.2018.
 */
public class NumberTextField extends JFXTextField {

    private final String formatNumber = "+_(___)___-__-__";

    private final String equalsLine = "+(___)___-__-__";

    private Controller listener;

    private ClientService service = SpringContextUtil.getInstance().getBean(ClientService.class);

    public void setListener(Controller listener) {
        this.listener = listener;
    }

    public NumberTextField() {
        textProperty().addListener((observable, oldValue, newValue) -> initButton(newValue));
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (StringUtils.isEmpty(text)) {
            super.replaceText(start, end, text);
            parseNumberToFormat();
        } else if (text.matches("[0-9]*") && check()) {
            super.replaceText(start, end, text);
            parseNumberToFormat();
        }
        //initButton();
        setPositionCaret(text, start);
    }

    private void initButton(String value) {
        if (listener!=null) {
            if (value.length() > 0) //if (getText().length() > 0)
                listener.execute(Command.CANCEL_ENABLE);
            else
                listener.execute(Command.CANCEL_DISABLE);
            searchPerson(value); //searchPerson(getText());
        }
    }

    private void setPositionCaret(String text, int start) {
        if (StringUtils.isEmpty(text))
            positionCaret(start);
        else
            positionCaret(getText().lastIndexOf(text) + 1);
    }

    private void parseNumberToFormat() {
        String format = String.valueOf(formatNumber);
        String text = getText();
        if (equalsLine.equals(text)) {
            setText("");
        } else {
            text = text.replaceAll("[\\+|\\)|\\(|\\-]", "");
            for (String t : text.split(""))
                format = format.replaceFirst("_", t);
            setText(format);
        }
    }

    private boolean check() {
        String line = getText().replaceAll("_", "");
        return line.length() < formatNumber.length();
    }

    private void searchPerson(String number) {
        if (ValidationUtil.checkTelNumber(number)) {
            try {
                listener.setActiveClient(service.getByNumber(number));
            } catch (NotFoundException | NoSuchElementException e) {
                listener.execute(Command.REFRESH);
            }
        } else
            listener.execute(Command.REFRESH);
    }
}

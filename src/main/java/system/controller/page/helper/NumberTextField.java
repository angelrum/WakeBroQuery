package system.controller.page.helper;

import javafx.scene.control.TextField;
import system.controller.SpringContextUtil;
import system.controller.page.listener.ControllerListener;
import system.service.ClientService;
import system.service.exception.NotFoundException;

import java.util.NoSuchElementException;

/**
 * Created by vladimir on 17.02.2018.
 */
public class NumberTextField extends TextField {

    private ControllerListener listener;

    private ClientService service = SpringContextUtil.getInstance().getBean(ClientService.class);

    private final String formatNumber = "+_(___)___-__-__";

    public void setListener(ControllerListener listener) {
        this.listener = listener;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.equals("")) {
            super.replaceText(start, end, text);
            setTelnumberInFormat();
        } else if (text.matches("[0-9]*")
                && check()) {
            super.replaceText(start, end, text);
            setTelnumberInFormat();
        }
        setButtonCancel();
        setPositionCaret(text, start);
    }

    private void setButtonCancel() {
        if (listener!=null) {
            if (getText().length() > 0) listener.cancelEnable();
            else listener.cancelDisable();
            searchPerson(getText());                                //Куда перенести?
        }
    }

    private void setPositionCaret(String text, int start) {
        if (text.equals(""))
            positionCaret(start);
        else
            positionCaret(getText().lastIndexOf(text)+1);
    }

    private void setTelnumberInFormat() {
        String format = new String(formatNumber);
        String text = getText();
        if ("+(___)___-__-__".equals(text)) {
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
        return line.length()<formatNumber.length();
    }

    private void searchPerson(String number) {
        try {
            listener.setClient(service.getByNumber(number));
        } catch (NotFoundException | NoSuchElementException e) {
            listener.refresh();
        }
//        Person person = listener.getModel().getByTelNumber(telNumber);
//        if (person!=null) {
//            listener.setPerson(person);
//        } else
//            listener.refresh();
    }
}

package system.controller.page.RegistrationPersonPage;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import org.springframework.util.StringUtils;
import system.controller.SpringContextUtil;
import system.model.Client;
import system.service.ClientService;
import system.util.ValidationUtil;

import static system.util.ClientUtil.*;

/**
 * Created by vladimir on 14.04.2018.
 */
public class RegistrationPersonControllerInit {

    private RegistrationPersonController controller;

    private NumberValidator validator = new NumberValidator();

    private RegistrationPersonPageListener listener = new RegistrationPersonPageListener(this);

    private ClientService service = SpringContextUtil.getInstance().getBean(ClientService.class);

    public RegistrationPersonControllerInit(RegistrationPersonController controller) {
        this.controller = controller;
    }

    public void init() {
        initValidator();
        controller.telNumber.setListener(listener);
        addKeyPressedListener(controller.telNumber);
        addTextListener(controller.lname);
        addTextListener(controller.fname);
        addTextListener(controller.sname);
        addTextListener(controller.city);
        disabledButton();
    }

    private void initValidator() {
        validator.setMessage("Введенный номер уже присвоен!");
        validator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.WARNING));
        controller.telNumber.getValidators().add(validator);
    }

    private void addTextListener(JFXTextField field) {
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                field.validate();
        });
    }

    private void addKeyPressedListener(JFXTextField field) {
        field.setOnKeyReleased(event -> {
            field.validate();
            validator.setError(false);
        });
    }

    public void clickOk() {
        String telnumber = controller.telNumber.getText();
        String fname = controller.fname.getText();
        String lname = controller.lname.getText();
        String sname = controller.sname.getText();
        String city = controller.city.getText();
        if (check(fname, lname, sname, city)) {
            try {
                Client client = service.save(createClient(telnumber, fname, lname, sname, city));
                controller.insertClientInBasicPage(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
            close();
        }
    }

    private boolean check(String fname, String lname, String sname, String city) {
        if (StringUtils.isEmpty(fname) ||
                StringUtils.isEmpty(lname) ||
                StringUtils.isEmpty(sname) ||
                StringUtils.isEmpty(city)) {
            if (StringUtils.isEmpty(fname)) controller.fname.validate();
            if (StringUtils.isEmpty(lname)) controller.lname.validate();
            if (StringUtils.isEmpty(sname)) controller.sname.validate();
            if (StringUtils.isEmpty(city)) controller.city.validate();
            return false;
        }
        return true;
    }

    public void validateTelNumber() {
        validator.setError(true);
    }

    protected void clear() {
        String telnumber = controller.telNumber.getText();
        if (ValidationUtil.checkTelNumber(telnumber))
            enabledButton();
        else
            disabledButton();

    }

    protected void disabledButton() {
        controller.ok.setDisable(true);
    }

    protected void enabledButton() {
        controller.ok.setDisable(false);
    }

    public void close() {
        controller.dialogStage.close();
    }

    private class NumberValidator extends ValidatorBase {
        private boolean error = false;

        @Override
        protected void eval() {
            hasErrors.set(error);
        }

        public void setError(boolean error) {
            this.error = error;
        }
    }
}

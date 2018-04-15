package system.controller.page.RegistrationPersonPage;

import javafx.scene.paint.Color;
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

    private RegistrationPersonPageListener listener = new RegistrationPersonPageListener(this);

    private ClientService service = SpringContextUtil.getInstance().getBean(ClientService.class);

    public RegistrationPersonControllerInit(RegistrationPersonController controller) {
        this.controller = controller;
    }

    public void init() {
        controller.telNumber.setListener(listener);
        disabledButton();
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
            controller.lFname.setTextFill((StringUtils.isEmpty(fname)? Color.web("#e40a0a") : Color.web("#000000")));
            controller.lSname.setTextFill((StringUtils.isEmpty(sname)? Color.web("#e40a0a") : Color.web("#000000")));
            controller.lLname.setTextFill((StringUtils.isEmpty(lname)? Color.web("#e40a0a") : Color.web("#000000")));
            controller.lCity.setTextFill((StringUtils.isEmpty(city)? Color.web("#e40a0a") : Color.web("#000000")));
            setMessage("Введены не все данные");
            return false;
        }
        return true;
    }

    protected void setMessage(String text) {
        controller.message.setText(text);
        controller.message.setVisible(true);
    }

    protected void clear() {
        controller.message.setVisible(false);
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
}

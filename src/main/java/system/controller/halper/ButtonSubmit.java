package system.controller.halper;

import javafx.scene.control.Button;

/**
 * Created by vladimir on 18.02.2018.
 */
public class ButtonSubmit extends Button {
    private ButtonSubmitEnum submitEnum;

    public void setText(String text, ButtonSubmitEnum buttonSubmitEnum) {
        setText(text);
        this.submitEnum = buttonSubmitEnum;
    }

    public ButtonSubmitEnum getSubmitEnum() {
        return submitEnum;
    }
}

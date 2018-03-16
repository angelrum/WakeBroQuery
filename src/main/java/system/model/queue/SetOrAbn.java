package system.model.queue;

/**
 * Created by vladimir on 24.02.2018.
 */
public class SetOrAbn {
    private SetOrAbnEnum setOrAbn;
    private int count;

    public SetOrAbn(SetOrAbnEnum setOrAbn, int count) {
        this.setOrAbn = setOrAbn;
        this.count = count;
    }

    public SetOrAbn(int count) {
        this.setOrAbn = SetOrAbnEnum.SET;
        this.count = count;
    }

    public SetOrAbn() {
        this.setOrAbn = SetOrAbnEnum.ABN;
        this.count = 0;
    }

    @Override
    public String toString() {
        if (setOrAbn == SetOrAbnEnum.ABN)
            return "Абонемент";
        else {
            if (count > 1 && count < 5)
                return String.format("%s сета", count);
            else if (count > 4)
                return String.format("%s сетов", count);
            else
                return String.format("%s сет", count);
        }
    }
}

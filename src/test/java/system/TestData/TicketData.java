package system.TestData;

import system.model.Pass;
import system.model.Ticket;

import java.time.LocalTime;

/**
 * Created by vladimir on 30.04.2018.
 */
public class TicketData {
    public static Ticket ABN_PASS = new Ticket(Pass.ABN_PASS, "Абонемент на месяц", true, null, null, null, null, 2018, 1, new Double(100), new Double(110), UserServiceData.ADMIN);
    public static Ticket TICKET_PASS = new Ticket(Pass.SECOND_PASS, "Сет (дневной)", true, null, null, null, null, 2018, 0, new Double(100), new Double(110), UserServiceData.ADMIN);
    public static Ticket TICKET_PASS2 = new Ticket(Pass.SECOND_PASS, "Сет (вечерний)", true, null, null, null, null, 2018, 0, new Double(100), new Double(110), UserServiceData.ADMIN);
    public static Ticket UPDATE_PASS = new Ticket(Pass.ABN_PASS, "Абонемент на день", true, null, null, null, null, 2018, 1, new Double(100), new Double(110), UserServiceData.ADMIN);
    public static Ticket NEW_TICKET = new Ticket(Pass.SECOND_PASS, "Сет (утренний)", true, LocalTime.of(8, 0), LocalTime.of(12, 0), null, null, 2018, 0, new Double(100), new Double(110), UserServiceData.ADMIN);

    static {
        ABN_PASS.setId(100004);
        TICKET_PASS.setId(100005);
        UPDATE_PASS.setId(ABN_PASS.getId());
        TICKET_PASS2.setEnable(false);
    }
}

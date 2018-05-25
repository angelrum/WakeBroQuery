package system.TestData;

import system.model.ClientTicket;

import java.time.LocalDate;

import static system.TestData.ClientServiceData.*;
import static system.TestData.UserServiceData.*;
import static system.TestData.TicketData.*;

/**
 * Created by vladimir on 30.04.2018.
 */
public class ClientTicketData {
    public static ClientTicket CLIENT1_TICKET = new ClientTicket(ADMIN, CLIENT1, ABN_PASS, LocalDate.now(), LocalDate.now());

    static {
        CLIENT1_TICKET.setId(100007);
    }


}

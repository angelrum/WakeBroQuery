package system.controller;

import system.model.Register;
import system.service.RegisterService;

import java.time.LocalDateTime;

/**
 * Created by vladimir on 31.03.2018.
 */
public class AuthorizedUser {

    private static int id = 100000; //default value

    private static int registerId;

    private static RegisterService registerService;

    public static void openSession(int id) {
        AuthorizedUser.id = id;
        if (registerService==null)
            registerService = SpringContextUtil.getInstance().getBean(RegisterService.class);
        registerId = registerService.save(new Register()).getId();
    }

    public static void closeSession() {
        if (registerId!=0) {
            Register register = registerService.get(registerId);
            register.setCloseshift(LocalDateTime.now());
            registerService.save(register);
        }
    }

    public static int id() {
        return id;
    }
}

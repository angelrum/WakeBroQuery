package system.service;

import system.model.Register;

import java.util.List;

public interface RegisterService extends AbstractService<Register> {

    Register get(int id);

    List<Register> getAll(int userId);
}

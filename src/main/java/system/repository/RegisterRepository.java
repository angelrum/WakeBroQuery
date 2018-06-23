package system.repository;

import system.model.Register;

import java.util.List;

public interface RegisterRepository {

    Register save(Register register, int userId);

    boolean delete (int id);

    List<Register> getAll();

    List<Register> getAll(int userId);

    Register get(int id);
}

package system.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import system.model.Register;
import system.model.User;
import system.repository.RegisterRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRegisterRepository implements RegisterRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Register save(Register register, int userId) {
        register.setUser(em.getReference(User.class, userId));
        if (register.isNew()) {
            em.persist(register);
            return register;
        }
        return em.merge(register);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Register.DELETE)
                .setParameter("id", id)
                .executeUpdate()!=0;
    }

    @Override
    public List<Register> getAll(int userId) {
        return em.createNamedQuery(Register.ALL_BY_USER, Register.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Register> getAll() {
        return em.createNamedQuery(Register.ALL, Register.class)
                .getResultList();
    }

    @Override
    public Register get(int id) {
        return em.find(Register.class, id);
    }
}

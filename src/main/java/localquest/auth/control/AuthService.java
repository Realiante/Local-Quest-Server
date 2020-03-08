package localquest.auth.control;


import localquest.auth.entity.SessionEntity;
import localquest.auth.entity.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
public class AuthService {
    @PersistenceContext
    private EntityManager em;

    public Optional<UserEntity> checkToken(String token){
        return em.createQuery("SELECT s FROM SessionEntity s WHERE s.token = :token", SessionEntity.class)
                .setParameter("token", token)
                .getResultStream()
                .map(SessionEntity::getUser)
                .findFirst();
    }
}

package localquest.auth.boundary;

import localquest.auth.entity.SessionEntity;
import localquest.auth.entity.dto.LoginInfo;
import localquest.auth.entity.UserEntity;
import localquest.auth.entity.dto.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/login")
@Stateless
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @PersistenceContext
    private EntityManager em;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserInfo user) {
        Optional<UserEntity> userEntity = em.createQuery("SELECT u FROM UserEntity u WHERE u.nickname = :nickname", UserEntity.class)
                .setParameter("nickname", user.getLoginName())
                .getResultStream()
                .findFirst();

        if (userEntity.isEmpty()
                || !userEntity.get().getPassword().equals(user.getPassword())) {
            log.info("user " + user.getLoginName() + " user " +user.getLoginName() + " Unauthorized");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setUser(userEntity.get());
        em.persist(sessionEntity);

        var result = new LoginInfo();
        result.setToken(sessionEntity.getToken());
        log.info("user " + user.getLoginName() + " logged in");
        return Response.ok(result).build();
    }


}

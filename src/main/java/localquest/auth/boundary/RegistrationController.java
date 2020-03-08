package localquest.auth.boundary;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/registration")
@Stateless
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    @PersistenceContext
    private EntityManager em;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserInfo user){
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname(user.getLoginName());
        userEntity.setPassword(user.getPassword());
        userEntity.setFullName("debug");
        em.persist(userEntity);
        return Response.status(201).build();
    }
}

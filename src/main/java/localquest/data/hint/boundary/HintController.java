package localquest.data.hint.boundary;


import localquest.auth.boundary.LoginController;
import localquest.auth.control.AuthService;
import localquest.auth.entity.UserEntity;
import localquest.data.hint.control.HintService;
import localquest.data.hint.entity.TextHintEntity;
import localquest.data.hint.entity.dto.TextHintDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/hints")
@Stateless
public class HintController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @PersistenceContext
    private EntityManager em;

    @Inject
    private AuthService authService;

    @Inject
    private HintService hintService;

    @GET
    @Path("/single/{op}/{code}/{pass}")
    public Response getSingleHint(@HeaderParam("token") String token,
                                  @PathParam("op") String operation,
                                  @PathParam("code") String code,
                                  @PathParam("pass") String pass) {

        //ToDO CLEAN UP THIS MESS PLS!!!!!!!!
        //todo FIX!!!
        Optional<TextHintEntity> textHintEntity = em.createQuery("SELECT h FROM TextHintEntity h WHERE h.code = :code", TextHintEntity.class)
                .setParameter("code", code)
                .getResultStream()
                .findFirst();
        Optional<UserEntity> user = authService.checkToken(token);
        boolean tokenCorrect = user.isPresent();
        boolean hintEntityExists = textHintEntity.isPresent();
        log.info("Get single hint "+ operation +"/"+code+"/"+pass);
        if(hintEntityExists) {
            if (operation.equals("find") && tokenCorrect) {
                user.get().getFoundHints().add(textHintEntity.get());
                if (textHintEntity.get().getPassword().equals("notNeeded")) {
                    user.get().getUnlockedHints().add(textHintEntity.get());
                }
                return Response.status(Response.Status.OK).build();
            } else if (operation.equals("unlock") && tokenCorrect) {
                if (pass.length() > 0) {
                    if (textHintEntity.get().getPassword().equals(pass)) {
                        user.get().getUnlockedHints().add(textHintEntity.get());
                        return Response.status(Response.Status.OK).build();
                    } else {
                        return Response.status(Response.Status.FORBIDDEN).build();
                    }
                } else {
                    return Response.status(Response.Status.FORBIDDEN).build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHints(@HeaderParam("token") String token) {
        return authService.checkToken(token)
                .map(this::findHints)
                .map(l -> Response.ok(l).build())
                .orElseGet(() -> Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private List<TextHintDTO> findHints(UserEntity userEntity) {
        return userEntity.getFoundHints().stream()
                .map(hint -> convert(hint, userEntity))
                .collect(Collectors.toList());

    }

    private TextHintDTO convert(TextHintEntity textHintEntity, UserEntity userEntity) {
        var hint = new TextHintDTO();
        hint.setId(textHintEntity.getId());
        hint.setHeader(textHintEntity.getHeader());
        boolean locked = !userEntity.getUnlockedHints().contains(textHintEntity);
        hint.setLocked(locked);
        hint.setCode(textHintEntity.getCode());
        if (locked) {
            hint.setBody("Locked");
        } else {
            hint.setBody(textHintEntity.getBody());
        }
        return hint;
    }
}

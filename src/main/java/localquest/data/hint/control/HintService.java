package localquest.data.hint.control;

import localquest.auth.entity.UserEntity;
import localquest.data.hint.entity.TextHintEntity;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class HintService {
    public List<TextHintEntity> findHints(UserEntity user){
        return Collections.emptyList();
    }
}

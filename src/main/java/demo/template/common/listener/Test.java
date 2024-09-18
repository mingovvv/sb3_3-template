package demo.template.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Slf4j
public class Test <S extends Session> extends SpringSessionBackedSessionRegistry<S> {

    public Test(FindByIndexNameSessionRepository<S> sessionRepository) {
        super(sessionRepository);
    }

}

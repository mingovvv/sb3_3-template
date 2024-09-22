package demo.template.common.listener;

import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

// for redis
@Slf4j
@Component
public class SessionEventListener implements HttpSessionListener {

    @EventListener
    public void handleSessionCreated(SessionCreatedEvent event) {
        log.info("Session created: " + event.getSession().getId());
    }

    @EventListener
    public void handleSessionDeleted(SessionDeletedEvent event) {
        log.info("Session deleted: " + event.getSession().getId());
    }

    @EventListener
    public void handleSessionExpired(SessionExpiredEvent event) {
        log.info("Session expired: " + event.getSession().getId());
    }

}

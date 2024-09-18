package demo.template.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;

@Slf4j
@Configuration
public class SessionEventListener {

    @EventListener
    public void handleSessionCreated(SessionCreatedEvent event) {
        // 세션 정보를 가져와서 히스토리 테이블에 기록
//        saveSessionToHistory(event.getSessionId(), event);
        log.info("Session created: " + event.getSession().getId());
    }

    @EventListener
    public void handleSessionDeleted(SessionDeletedEvent event) {
        // 필요한 경우 삭제 이벤트도 히스토리 테이블에 기록 가능
//        saveSessionToHistory(event.getSessionId(), event);
        log.info("Session deleted: " + event.getSession().getId());
    }

    @EventListener
    public void handleSessionExpired(SessionExpiredEvent event) {
        // 만료된 세션도 히스토리 테이블에 기록
//        saveSessionToHistory(event.getSessionId(), event);
        log.info("Session expired: " + event.getSession().getId());
    }

//    private void saveSessionToHistory(String sessionId, Object event) {
//        String sql = "INSERT INTO SPRING_SESSION_HIST (SESSION_ID, EVENT_TYPE, EVENT_TIME) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, sessionId, event.getClass().getSimpleName(), System.currentTimeMillis());
//    }

}

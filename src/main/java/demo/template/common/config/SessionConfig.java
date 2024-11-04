package demo.template.common.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.session.SaveMode;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Configuration
@EnableJdbcHttpSession
public class SessionConfig {

    @Bean
    public SessionCustomizer sessionCustomizer() {
        return new SessionCustomizer();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
//        return new HeaderHttpSessionIdResolver("X-Session-Id");
        return new CustomSessionIdResolver();
    }

    private static class CustomSessionIdResolver extends HeaderHttpSessionIdResolver {
        public CustomSessionIdResolver() {
            super("X-Session-Id");
        }

        @Override
        public List<String> resolveSessionIds(HttpServletRequest request) {
            List<String> sessionIds = super.resolveSessionIds(request);

            if (sessionIds.isEmpty() || !isValidSession(sessionIds.get(0))) {
                throw new ResponseStatusException(UNAUTHORIZED, "Invalid session ID.");
            }
            return sessionIds;
        }

        private boolean isValidSession(String sessionId) {
            // DB에 세션 ID가 있는지 확인
            // JdbcIndexedSessionRepository 또는 다른 세션 조회 로직을 사용하여 세션 유효성을 확인
            // 예: sessionRepository.findById(sessionId) != null
            return true; // 이 부분에 유효성 확인 로직을 추가하세요.
        }
    }

    static class SessionCustomizer implements SessionRepositoryCustomizer<JdbcIndexedSessionRepository> {

        @Override
        public void customize(JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {
            jdbcIndexedSessionRepository.setTableName("cde_QB_SPRING_SESSION");
            jdbcIndexedSessionRepository.setSaveMode(SaveMode.ON_SET_ATTRIBUTE);
//            jdbcIndexedSessionRepository.save(jdbcIndexedSessionRepository.createSession());
            jdbcIndexedSessionRepository.setDefaultMaxInactiveInterval(Duration.ofMinutes(10));
        }
    }

    static class CustomSessionRepository extends JdbcIndexedSessionRepository {

        public CustomSessionRepository(JdbcOperations jdbcOperations, TransactionOperations transactionOperations) {
            super(jdbcOperations, transactionOperations);
        }

    }

}

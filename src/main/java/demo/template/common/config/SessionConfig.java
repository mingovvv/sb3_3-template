package demo.template.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.session.SaveMode;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.support.TransactionOperations;

import java.time.Duration;

@Slf4j
@Configuration
@EnableJdbcHttpSession
public class SessionConfig {

    @Bean
    public SessionCustomizer sessionCustomizer() {
        return new SessionCustomizer();
    }

    static class SessionCustomizer implements SessionRepositoryCustomizer<JdbcIndexedSessionRepository> {

        @Override
        public void customize(JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {
            jdbcIndexedSessionRepository.setTableName("CDE_QB_SPRING_SESSION");
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

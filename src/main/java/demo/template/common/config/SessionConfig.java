package demo.template.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

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

        }
    }

}

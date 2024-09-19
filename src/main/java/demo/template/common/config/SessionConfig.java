package demo.template.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.config.SessionRepositoryCustomizer;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableJdbcHttpSession
public class SessionConfig {

    @Bean
    public SessionCustomizer sessionCustomizer() {
        return new SessionCustomizer();
    }

    class SessionCustomizer implements SessionRepositoryCustomizer<JdbcIndexedSessionRepository> {

        @Override
        public void customize(JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {

            jdbcIndexedSessionRepository.setIndexResolver(session -> {
                Map<String, String> indexes = new HashMap<>();
//            SecurityContext context = session.getAttribute("SPRING_SECURITY_CONTEXT");
//
//            if (context != null) {
//                Authentication authentication = context.getAuthentication();
//                if (authentication != null && authentication.isAuthenticated()) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                log.info("authentication: {}", authentication);
                indexes.put(jdbcIndexedSessionRepository.PRINCIPAL_NAME_INDEX_NAME, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//                }
//            }
                return indexes;
            });

        }
    }

}

package demo.template.common.config;

import demo.template.common.utils.MDCUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AuditorAwareCustomizer implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        if (Objects.nonNull(MDCUtil.getValue(MDCUtil.REQUEST_USER_ID))) {
            return Optional.of(MDCUtil.getValue(MDCUtil.REQUEST_USER_ID));
        } else {
            return Optional.empty();
        }

    }

}

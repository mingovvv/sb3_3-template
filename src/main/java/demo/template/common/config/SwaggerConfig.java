package demo.template.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"local","dev"})
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "{template} API Specification",
                version = "v1",
                description = "{template} API 명세서입니다."
        )
)
public class SwaggerConfig {

}

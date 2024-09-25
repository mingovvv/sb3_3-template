package demo.template.sb3_3template.http.config;

import demo.template.common.http.RestClientBuilder;
import demo.template.sb3_3template.http.InferencePipelineApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RestClientHttpApiConfig {

    /**
     * 'InferencePipelineApi' Service API config
     *
     * @param url url
     * @param connectTimeout connectTimeout
     * @param readTimeout readTimeout
     *
     * @return AuccChatbotService
     */
    @Bean
    public InferencePipelineApi accuChatbotService(@Value("${api.inference-pipeline.url}") String url,
                                                   @Value("${api.inference-pipeline.connection-timeout}") Duration connectTimeout,
                                                   @Value("${api.inference-pipeline.read-timeout}") Duration readTimeout) {

        return new RestClientBuilder()
                .url(url)
                .connectionTimeout(connectTimeout)
                .readTimeout(readTimeout)
                .build(InferencePipelineApi.class);
    }

}

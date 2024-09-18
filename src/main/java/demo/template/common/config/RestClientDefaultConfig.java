package demo.template.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
public class RestClientDefaultConfig {

    /**
     * RestClient Common Builder
     *
     * @return RestClient
     */
    public static RestClient.Builder defaultRestClientBuilder() {

        return RestClient.builder()
                .requestInterceptor((request, body, execution) -> {
                    log.info("API call ==> url : {}, method : {}", request.getURI(), request.getMethod());
                    log.info("API call ==> headers : {}", request.getHeaders());
                    log.info("API call ==> body : {}", new String(body, StandardCharsets.UTF_8));

                    // API call
                    ClientHttpResponse response = execution.execute(request, body);

                    return new BufferingResponseWrapper(response);
                })
                .defaultStatusHandler(
                        HttpStatusCode::is2xxSuccessful,
                        (request, response) -> {
                            log.info("API call <== statusCode : {}", response.getStatusCode());
                            log.info("API call <== headers : {}", response.getHeaders());
                            log.info("API call <== body : {}", IOUtils.toString(response.getBody(), StandardCharsets.UTF_8));
                        }
                )
                .defaultStatusHandler(
                        HttpStatusCode::isError,
                        (request, response) -> {
                            log.info("API call <== statusCode : {}", response.getStatusCode());
                            log.info("API call <== headers : {}", response.getHeaders());
                            log.info("API call <== body : {}", IOUtils.toString(response.getBody(), StandardCharsets.UTF_8));
//                            throw ApiErrorException.of(response.getStatusCode().value(), IOUtils.toString(response.getBody(), StandardCharsets.UTF_8));
                        })
                .messageConverters(converters ->
                        converters.add(new MappingJackson2HttpMessageConverter(httpObjectMapper()))
                );
    }

    /**
     * ObjectMapper for HTTP
     *
     * @return ObjectMapper
     */
    public static ObjectMapper httpObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .modules(new ParameterNamesModule(), new Jdk8Module(), new JavaTimeModule())
                .locale(Locale.getDefault())
                .timeZone(TimeZone.getDefault())
                // 시간 관련 객체(LocalDateTime, java.util.Date)를 직렬화 할 때 timestamp 숫자값이 아닌 포맷팅 문자열로 한다.
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // 모르는 property 를 역직렬화 할 때 오류없이 무시하게 한다.
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 모르는 ENUM 값을 역직렬화 할 때 null로 취급하게 한다.
                .featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .build();
    }

    /**
     * Buffer Class for Response Body
     */
    static class BufferingResponseWrapper implements ClientHttpResponse {

        private byte[] body;
        private final ClientHttpResponse response;

        public BufferingResponseWrapper(ClientHttpResponse response) {
            this.response = response;
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return response.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public void close() {
            response.close();
        }

        @Override
        public InputStream getBody() throws IOException {
            if (body == null) {
                body = StreamUtils.copyToByteArray(response.getBody());
            }
            return new ByteArrayInputStream(body);
        }

        @Override
        public HttpHeaders getHeaders() {
            return response.getHeaders();
        }

    }

}

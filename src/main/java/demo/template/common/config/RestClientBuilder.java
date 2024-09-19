package demo.template.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RestClientBuilder {
    // test
    public static final Duration DEFAULT_CONNECTION_TIMEOUT_DURATION = Duration.ofMillis(1000L);
    public static final Duration DEFAULT_READ_TIMEOUT_DURATION = Duration.ofMillis(5000L);

    private String url;
    private Duration connectionTimeout = DEFAULT_CONNECTION_TIMEOUT_DURATION;
    private Duration readTimeout = DEFAULT_READ_TIMEOUT_DURATION;
    private final Map<String, String> headers = new HashMap<>();

    public RestClientBuilder url(String url) {
        this.url = url;
        return this;
    }

    public RestClientBuilder connectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public RestClientBuilder readTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public RestClientBuilder headers(String... keyValuePairs) {

        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Key-value pairs should be provided in pairs.");
        }

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i];
            String value = keyValuePairs[i + 1];
            headers.put(key, value);
        }

        return this;
    }

    public <T> T build(Class<T> clientClass) {

        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(connectionTimeout)
                .withReadTimeout(readTimeout);

        RestClient restClient = RestClientDefaultConfig.defaultRestClientBuilder()
                .baseUrl(url)
                .requestFactory(ClientHttpRequestFactories.get(clientHttpRequestFactorySettings))
                .build();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            restClient = restClient.mutate().defaultHeader(entry.getKey(), entry.getValue()).build();
        }

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(clientClass);
    }

}

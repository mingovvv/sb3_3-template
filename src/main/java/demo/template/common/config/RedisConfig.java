package demo.template.common.config;

import demo.template.common.utils.FernetUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Profile({"dev", "prod"})
@Configuration
public class RedisConfig {

    @Value("${hash-key}")
    private String hashKey;

    @Value("${redis.service-name}")
    private String serviceName;

    @Value("${redis.redis-password}")
    private String redisPassword;

    @Value("${redis.sentinel-password}")
    private String sentinelPassword;

    @Value("${redis.sentinel-host-node1}")
    private String sentinelHostNode1;

    @Value("${redis.sentinel-host-node2}")
    private String sentinelHostNode2;

    @Value("${redis.sentinel-host-node3}")
    private String sentinelHostNode3;

    @Value("${redis.sentinel-port}")
    private String sentinelPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Sentinel 설정 (마스터 이름과 Sentinel 노드 정보 설정)
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(FernetUtil.decrypt(hashKey, serviceName))
                .sentinel(FernetUtil.decrypt(hashKey, sentinelHostNode1), Integer.parseInt(FernetUtil.decrypt(hashKey, sentinelPort)))
                .sentinel(FernetUtil.decrypt(hashKey, sentinelHostNode2), Integer.parseInt(FernetUtil.decrypt(hashKey, sentinelPort)))
                .sentinel(FernetUtil.decrypt(hashKey, sentinelHostNode3), Integer.parseInt(FernetUtil.decrypt(hashKey, sentinelPort)));
//                .master("mymaster")
//                .sentinel("localhost", 26379)
//                .sentinel("localhost", 26379)
//                .sentinel("localhost", 26379);

        sentinelConfig.setSentinelPassword(FernetUtil.decrypt(hashKey, sentinelPassword));
        sentinelConfig.setPassword(FernetUtil.decrypt(hashKey, redisPassword));
//        sentinelConfig.setSentinelPassword("");
//        sentinelConfig.setPassword("");

        return new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

}

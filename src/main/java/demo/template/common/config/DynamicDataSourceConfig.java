package demo.template.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import demo.template.common.utils.FernetUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;

@Configuration
public class DynamicDataSourceConfig {

    @Value("${hash-key}")
    private String hashKey;

    @Value("${key.mariadb-ip}")
    private String mariadbIpKey;

    @Value("${key.mariadb-user}")
    private String mariadbUserKey;

    @Value("${key.mariadb-password}")
    private String mariadbPasswordKey;

    @Value("${key.mariadb-port}")
    private String mariadbPortKey;

    @Value("${key.mariadb-db}")
    private String mariadbDbKey;


    private final RedisTemplate<String, String> redisTemplate;

    public DynamicDataSourceConfig(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    @Profile({"dev", "prod"})
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Profile({"dev", "prod"})
    public DataSource dataSource() {
        HikariConfig config = hikariConfig();

        // Redis에서 MariaDB 연결 정보 조회
        String mariadbIp = redisTemplate.opsForValue().get(FernetUtil.decrypt(hashKey, mariadbIpKey));
        String mariadbUser = redisTemplate.opsForValue().get(FernetUtil.decrypt(hashKey, mariadbUserKey));
        String mariadbPassword = redisTemplate.opsForValue().get(FernetUtil.decrypt(hashKey, mariadbPasswordKey));
        String mariadbPort = redisTemplate.opsForValue().get(FernetUtil.decrypt(hashKey, mariadbPortKey));
        String mariadbDb = redisTemplate.opsForValue().get(FernetUtil.decrypt(hashKey, mariadbDbKey));

        // MariaDB 연결 정보 설정
        String jdbcUrl = String.format("jdbc:mariadb://%s:%s/%s", mariadbIp, mariadbPort, mariadbDb);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(mariadbUser);
        config.setPassword(mariadbPassword);
        config.setDriverClassName("org.mariadb.jdbc.Driver");

        return new HikariDataSource(config);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("====================================");
        };
    }

}

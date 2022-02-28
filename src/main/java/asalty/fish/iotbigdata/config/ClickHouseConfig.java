package asalty.fish.iotbigdata.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/2/28 14:05
 */
@Configuration
@Slf4j
public class ClickHouseConfig {
    @Value("${spring.clickhouse.driver-class-name}")
    String driverClassName;

    @Value("${spring.clickhouse.url}")
    String url;

    @Value("${spring.clickhouse.port}")
    String port;

    @Value("${spring.clickhouse.database}")
    String database;

    @Value("${spring.clickhouse.username}")
    String username;

    @Value("${spring.clickhouse.password}")
    String password;

    private String fullUrl() {
        return url + ":" + port + "/" + database;
    }

    @Bean
    public Statement clickHouseStatement() throws Exception {
        Class.forName(driverClassName);
        log.info("clickhouse driver class name: {}", driverClassName);
        log.info("clickhouse url: {}", fullUrl());
        Connection conn = DriverManager.getConnection(fullUrl(), username, password);
        log.info("clickhouse connection init");
        return conn.createStatement();
    }
}

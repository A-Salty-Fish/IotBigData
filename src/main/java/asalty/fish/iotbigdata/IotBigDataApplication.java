package asalty.fish.iotbigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableElasticsearchRepositories
public class IotBigDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotBigDataApplication.class, args);
    }
}

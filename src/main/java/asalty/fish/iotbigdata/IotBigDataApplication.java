package asalty.fish.iotbigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class IotBigDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotBigDataApplication.class, args);
        System.out.println(0);
    }

}

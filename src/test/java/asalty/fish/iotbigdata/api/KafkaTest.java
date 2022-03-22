package asalty.fish.iotbigdata.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/22 19:06
 */
@SpringBootTest
public class KafkaTest {


    @Resource
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void test() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        kafkaTemplate.send("test", "hello");
        System.out.println("test");
        TimeUnit.SECONDS.sleep(2);
    }
}

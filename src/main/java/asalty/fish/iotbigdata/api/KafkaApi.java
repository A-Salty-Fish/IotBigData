package asalty.fish.iotbigdata.api;

import asalty.fish.iotbigdata.config.KafkaClientConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.PostConstruct;
import java.lang.annotation.Inherited;
import java.time.Duration;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/18 16:42
 */

public interface KafkaApi {

    String topic = "default_topic";

    String groupId = "default_group";

    KafkaClientConfig kafkaClientConfig = null;

    KafkaConsumer<String, String> consumer = null;

    KafkaConsumer<String, String> getKafkaConsumer();

    Object handleMessage(String message);

    @PostConstruct
    default void listen() {
        while (true) {
            try {
                ConsumerRecords<String, String> records = getKafkaConsumer().poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    handleMessage(record.value());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

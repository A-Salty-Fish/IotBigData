package asalty.fish.iotbigdata.api;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;

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

    KafkaConsumer<String, String> kafkaConsumer();

    Object handleMessage(String message);

    default void listen() {
        while (true) {
            try {
                ConsumerRecords<String, String> records = kafkaConsumer().poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    handleMessage(record.value());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

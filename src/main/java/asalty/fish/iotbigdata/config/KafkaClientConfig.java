package asalty.fish.iotbigdata.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Collections;
import java.util.Properties;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/22 18:17
 */
@Configuration
public class KafkaClientConfig {

//    @KafkaListener(topics = "test", groupId = "test")
//    public void listen(String content) {
//        System.out.println("accept:" + content);
//    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public KafkaConsumer<String, String> getConsumer(String topic, String groupId) {
        //配置信息
        Properties props = new Properties();
        //kafka服务器地址
        props.put("bootstrap.servers", bootstrapServers);
        //必须指定消费者组
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        //设置数据key和value的序列化处理类
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        //创建消息者实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅topic的消息
        consumer.subscribe(Collections.singleton(topic));
        return consumer;
    }
}

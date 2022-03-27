package asalty.fish.iotbigdata.api.insert;

import asalty.fish.iotbigdata.config.KafkaClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/22 19:22
 */
@Service
@Slf4j
public class KafkaInsert{

    @KafkaListener(topics = "test",groupId = "test")
    public void insert(String message){
        log.info("接收到的消息为：{}",message);
    }

}

package asalty.fish.iotbigdata.demo.service;

import asalty.fish.iotbigdata.demo.entity.IotEntity2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import util.CompressionRateTestUtil;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/27 16:38
 */
@SpringBootTest
public class IotEntity2ServiceTest {

    @Resource
    IotEntity2Service iotEntity2Service;

    @Test
    public void testRead() {
        System.out.println();
        System.out.println(iotEntity2Service.maxValue1());
    }

    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i < 10; i++) {
            IotEntity2 iotEntity2 = CompressionRateTestUtil.random(IotEntity2.class);
            iotEntity2.setValue1((long) new Random().nextInt(Integer.MAX_VALUE));
            iotEntity2Service.create(iotEntity2);
        }

        TimeUnit.SECONDS.sleep(5);
    }
}

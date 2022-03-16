package asalty.fish.iotbigdata.proxy;

import asalty.fish.iotbigdata.IotBigDataApplicationTests;
import asalty.fish.iotbigdata.demo.entity.TestCreateTable;
import asalty.fish.iotbigdata.demo.service.TestCreateTableService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 17:09
 */
@SpringBootTest
public class InsertProxyTest {

    @Resource
    TestCreateTableService testCreateTableService;

    @Test
    public void testSingleInsert() throws Exception {
        for (int i = 0; i < 10000; i++) {
            testCreateTableService.create(IotBigDataApplicationTests.getTestTimeEntity());
        }
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void testBatchInsert() throws Exception {
        for (int i = 0; i < 100; i++) {
            List<TestCreateTable> testTimeEntityList = new ArrayList<>();
            for (int j = 0; j < 10000; j++) {
                testTimeEntityList.add(IotBigDataApplicationTests.getTestTimeEntity());
            }
            testCreateTableService.batchCreate(testTimeEntityList);
            TimeUnit.MILLISECONDS.sleep(400);
        }
        TimeUnit.SECONDS.sleep(60);
    }
}

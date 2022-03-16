package asalty.fish.iotbigdata.proxy;

import asalty.fish.iotbigdata.IotBigDataApplicationTests;
import asalty.fish.iotbigdata.demo.entity.TestCreateTable;
import asalty.fish.iotbigdata.demo.service.TestCreateTableService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        testCreateTableService.create(IotBigDataApplicationTests.getTestTimeEntity());
    }

    @Test
    public void testBatchInsert() throws Exception {
        List<TestCreateTable> testTimeEntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testTimeEntityList.add(IotBigDataApplicationTests.getTestTimeEntity());
        }
        testCreateTableService.batchCreate(testTimeEntityList);
    }
}

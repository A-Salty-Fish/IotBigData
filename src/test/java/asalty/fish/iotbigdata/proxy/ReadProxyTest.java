package asalty.fish.iotbigdata.proxy;

import asalty.fish.iotbigdata.demo.service.TestCreateTableService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/17 15:43
 */
@SpringBootTest
public class ReadProxyTest {

    @Resource
    private TestCreateTableService testCreateTableService;

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            testCreateTableService.read("1");
        }
    }

}

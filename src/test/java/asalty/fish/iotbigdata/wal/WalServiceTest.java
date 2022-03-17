package asalty.fish.iotbigdata.wal;

import asalty.fish.iotbigdata.IotBigDataApplicationTests;
import asalty.fish.iotbigdata.demo.entity.TestCreateTable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/11 14:36
 */
@SpringBootTest
public class WalServiceTest {
    @Resource
    private WalService walService;

    @Test
    public void testFlushAll() throws Exception {
        ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10000; i++) {
            queue.add(IotBigDataApplicationTests.getTestTimeEntity());
        }
        walService.flush(TestCreateTable.class, queue);
        TimeUnit.SECONDS.sleep(20);
    }
}

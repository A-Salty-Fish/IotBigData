package asalty.fish.iotbigdata;

import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.entity.TestCreateTable;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class IotBigDataApplicationTests {

    @Resource
    TestCreateTableDao testCreateTableDao;

    @Test
    void contextLoads() {
        System.out.println(new Gson().toJson(testCreateTableDao.findAllByWatchID(541755838L)));
    }

    public TestCreateTable getTestTimeEntity() {
        TestCreateTable h = new TestCreateTable();
        Random random = new Random();
        h.setId((long) random.nextInt(1000000000));
        h.setGoodEvent("goodEvent" + random.nextInt(100000));
        h.setJavaEnable(random.nextBoolean());
        h.setTitle("title" + random.nextInt(100000));
        h.setWatchID((long) random.nextInt(1000000000));
        h.setUserAgentMajor(random.nextInt(100000));
        h.setTestUserDefinedColumn("testUserDefinedColumn" + random.nextInt(100000));
        h.setCreateDay(LocalDate.now());
        h.setCreateTime(LocalDateTime.now());
        return h;
    }

    @Test
    void testCreateTable() {
//        testCreateTableDao.create(getTestTimeEntity());
        for (int i = 0; i < 100; i++) {
            testCreateTableDao.create(getTestTimeEntity());
        }
    }

    @Test
    void testThreadCreateTable() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                testCreateTable();
                latch.countDown();
            }).start();
        }
        latch.await();
    }

    @Test
    void testStatistics() {
//        System.out.println(Long.parseLong("8344639403183861893"));
        System.out.println(testCreateTableDao.maxWatchID());
        System.out.println(testCreateTableDao.avgWatchID());
    }
}

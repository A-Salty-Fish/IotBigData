package asalty.fish.iotbigdata;

import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.entity.TestCreateTable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class IotBigDataApplicationTests {

    @Resource
    TestCreateTableDao testCreateTableDao;

    @Test
    void contextLoads() {
        System.out.println(testCreateTableDao.findAllByWatchID(0L).size());
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
        for (int i = 0; i < 100; i++) {
            testCreateTableDao.create(getTestTimeEntity());
        }
    }

    @Test
    void testStatistics() {
//        System.out.println(Long.parseLong("8344639403183861893"));
        System.out.println(testCreateTableDao.maxWatchID());
        System.out.println(testCreateTableDao.avgWatchID());
    }
}

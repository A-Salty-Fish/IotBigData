package asalty.fish.iotbigdata;

import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.dao.TestESTableDao;
import asalty.fish.iotbigdata.dao.TestMysqlTableDao;
import asalty.fish.iotbigdata.entity.TestCreateTable;
import asalty.fish.iotbigdata.entity.TestESTable;
import asalty.fish.iotbigdata.entity.TestMysqlTable;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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

    @Resource
    TestMysqlTableDao testMysqlTableDao;

    public TestMysqlTable getTestMysqlTable() {
        TestMysqlTable h = new TestMysqlTable();
        Random random = new Random();
//        h.setId((long) random.nextInt(1000000000));
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

    public TestMysqlTable getInsertTestMysqlTable() {
        TestMysqlTable h = new TestMysqlTable();
        h.setCreateDay(testMysqlTable.getCreateDay());
        h.setCreateTime(testMysqlTable.getCreateTime());
        h.setGoodEvent(testMysqlTable.getGoodEvent());
        h.setJavaEnable(testMysqlTable.getJavaEnable());
        h.setTitle(testMysqlTable.getTitle());
        h.setWatchID(testMysqlTable.getWatchID());
        h.setUserAgentMajor(testMysqlTable.getUserAgentMajor());
        h.setTestUserDefinedColumn(testMysqlTable.getTestUserDefinedColumn());
        return h;
    }

    TestMysqlTable testMysqlTable = getTestMysqlTable();

    @Test
    void testMysql() {
        for (int i =0 ;i<100;i++) {
//            testMysqlTable.setId(null);
            testMysqlTableDao.save(getInsertTestMysqlTable());
        }
    }

    @Test
    void testThreadCreateMysqlTable() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    testMysql();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
    }

    @Test
    public void testBatchCreateTestTable() throws Exception {
        List<TestCreateTable> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(getTestTimeEntity());
        }
        testCreateTableDao.batchCreate(list);
    }

    @Test
    public void getMaxByStartsWith() {
        System.out.println(testCreateTableDao.maxWatchIDByStartsWithGoodEvent("goodEvent5"));
    }

    @Test
    public void testNativeFunction() throws Exception {
//        System.out.println(testCreateTableDao.maxWatchIDByBit(705236002L, 20L));
        System.out.println(testMysqlTableDao.maxWatchIDByBit(173984299L, 20L));
    }

    public List<TestMysqlTable> getInsertTestMysqlTables(int size) {
        List<TestMysqlTable> testMysqlTables = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            TestMysqlTable h = new TestMysqlTable();
            h.setCreateDay(testMysqlTable.getCreateDay());
            h.setCreateTime(testMysqlTable.getCreateTime());
            h.setGoodEvent(testMysqlTable.getGoodEvent());
            h.setJavaEnable(testMysqlTable.getJavaEnable());
            h.setTitle(testMysqlTable.getTitle());
            h.setWatchID(testMysqlTable.getWatchID());
            h.setUserAgentMajor(testMysqlTable.getUserAgentMajor());
            h.setTestUserDefinedColumn(testMysqlTable.getTestUserDefinedColumn());
            testMysqlTables.add(h);
        }
        return testMysqlTables;
    }

    @Test
    public void testNativeFunction2() throws Exception {
        int remain = 10000000 - 646393;
        AtomicInteger count = new AtomicInteger(remain / 100);
        CountDownLatch latch = new CountDownLatch(12);
        for (int i = 0; i < 12;i++) {
            new Thread(() -> {
                try {
                    while (count.getAndDecrement() > 0) {
                        testMysqlTableDao.saveAll(getInsertTestMysqlTables(100));
                    }
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread " + i).start();
        }
        latch.await();
    }

    @Test
    public void testAvgBetween() {
//        System.out.println(testMysqlTableDao.avgWatchIDBetweenLeftAndRight(1, 10000000));
//        System.out.println(testCreateTableDao.avgWatchIDBetweenLeftAndRight(1, 10000000));
    }

    @Resource
    TestESTableDao testESTableDao;

    public TestESTable getTestESTable() {
        TestESTable h = new TestESTable();
        h.setCreateDay(testMysqlTable.getCreateDay().toEpochDay());
        h.setCreateTime(testMysqlTable.getCreateTime().toEpochSecond(ZoneOffset.UTC));
        h.setGoodEvent(testMysqlTable.getGoodEvent());
        h.setJavaEnable(testMysqlTable.getJavaEnable());
        h.setTitle(testMysqlTable.getTitle());
        h.setWatchID(testMysqlTable.getWatchID());
        h.setUserAgentMajor(testMysqlTable.getUserAgentMajor());
        h.setTestUserDefinedColumn(testMysqlTable.getTestUserDefinedColumn());
        return h;
    }

    @Test
    public void testSave() {
//        testESTableDao.deleteAll();
        testESTableDao.saveAll(Arrays.asList(getTestESTable()));
        System.out.println(new Gson().toJson(testESTableDao.findAll()));
    }

    @Resource
    RedisTemplate<String, String> redisTemplate;

    ThreadLocal<Gson> gson = ThreadLocal.withInitial(Gson::new);

    @Test
    public void testRedisSave() {
        TestESTable h = getTestESTable();
        redisTemplate.opsForValue().set("test", gson.get().toJson(h));
        TestESTable x = gson.get().fromJson(redisTemplate.opsForValue().get("test"), TestESTable.class);
        System.out.println(gson.get().toJson(x));
    }
}

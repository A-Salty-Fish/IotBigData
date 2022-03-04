package asalty.fish.iotbigdata.benchmark;

import asalty.fish.clickhousejpa.CRUDStatementHandler.handler.InsertStatementHandler;
import asalty.fish.clickhousejpa.jdbc.ClickHouseJdbcConfig;
import asalty.fish.iotbigdata.IotBigDataApplication;
import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.dao.TestMysqlTableDao;
import asalty.fish.iotbigdata.entity.TestCreateTable;
import asalty.fish.iotbigdata.entity.TestMysqlTable;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/4 11:29
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BatchInsertBenchMark {

    private ConfigurableApplicationContext context;

    TestCreateTableDao testCreateTableDao;

    List<TestCreateTable> testCreateTables;

    TestCreateTable testCreateTable;

    TestMysqlTableDao testMysqlTableDao;

    int BATCH_SIZE = 10;

    @Setup(Level.Trial)
    public void init() throws Exception {
        context = SpringApplication.run(IotBigDataApplication.class);
        testCreateTableDao = context.getBean(TestCreateTableDao.class);
        testMysqlTableDao = context.getBean(TestMysqlTableDao.class);
        testMysqlTable = getTestMysqlTable();
        testCreateTable = getTestCreateEntity();
        testCreateTables = getTestCreateEntitys(BATCH_SIZE);
    }

    public TestCreateTable getTestCreateEntity() {
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

    public List<TestCreateTable> getTestCreateEntitys(int size) {
        List<TestCreateTable> testCreateTables = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            testCreateTables.add(getTestCreateEntity());
        }
        return testCreateTables;
    }

    public TestMysqlTable getTestMysqlTable() {
        TestMysqlTable h = new TestMysqlTable();
        Random random = new Random();
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

    TestMysqlTable testMysqlTable;

    //  克隆一个测试实体列表
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

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(BatchInsertBenchMark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(5))
                .threads(12)
                .warmupForks(0)
                .warmupIterations(0)
                .forks(1)
                .build();
        new Runner(options).run();
    }

//    @Benchmark
//    public void testMysqlBatchInsertBy10() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(10));
//    }
//
//    @Benchmark
//    public void testClickHouseBatchInsertBy10() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(10));
//    }
//
//    @Benchmark
//    public void testMysqlBatchInsertBy50() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(50));
//    }
//
//    @Benchmark
//    public void testClickHouseBatchInsertBy50() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(50));
//    }
//
//    @Benchmark
//    public void testMysqlBatchInsertBy100() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(100));
//    }
//
//    @Benchmark
//    public void testClickHouseBatchInsertBy100() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(100));
//    }

    @Benchmark
    public void testMysqlBatchInsertBy200() {
        testMysqlTableDao.saveAll(getInsertTestMysqlTables(200));
    }

    @Benchmark
    public void testClickHouseBatchInsertBy200() {
        testCreateTableDao.batchCreate(getTestCreateEntitys(200));
    }

    @TearDown
    public void tearDown() {
        context.close();
    }
}

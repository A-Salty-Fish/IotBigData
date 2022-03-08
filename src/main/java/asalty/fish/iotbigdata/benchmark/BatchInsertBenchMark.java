package asalty.fish.iotbigdata.benchmark;

import asalty.fish.clickhousejpa.CRUDStatementHandler.handler.InsertStatementHandler;
import asalty.fish.clickhousejpa.jdbc.ClickHouseJdbcConfig;
import asalty.fish.iotbigdata.IotBigDataApplication;
import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.dao.TestESTableDao;
import asalty.fish.iotbigdata.dao.TestMysqlTableDao;
import asalty.fish.iotbigdata.entity.TestCreateTable;
import asalty.fish.iotbigdata.entity.TestESTable;
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
import java.time.ZoneOffset;
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

    TestESTableDao testESTableDao;

    int BATCH_SIZE = 10;

    @Setup(Level.Trial)
    public void init() throws Exception {
        context = SpringApplication.run(IotBigDataApplication.class);
        testCreateTableDao = context.getBean(TestCreateTableDao.class);
        testMysqlTableDao = context.getBean(TestMysqlTableDao.class);
        testESTableDao = context.getBean(TestESTableDao.class);
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

    public List<TestESTable> getTestESTables(int size) {
        List<TestESTable> testMysqlTables = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            testMysqlTables.add(getTestESTable());
        }
        return testMysqlTables;
    }

    public static void main(String[] args) throws Exception {
        // 生成测试函数
//        for (int i = 1; i <= 30 ;i++) {
//
//            System.out.println(
//                    "@Benchmark\n" +
//                            "public void EsBatchInsertBy"+ (10  * i) + "() {\n" +
//                            "    testESTableDao.saveAll(getTestESTables(" + (10 *i) + "));\n" +
//                            "}"
//            );
//        }
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

    @TearDown
    public void tearDown() {
        context.close();
    }

    @Benchmark
    public void EsInsert() {
        testESTableDao.save(getTestESTable());
    }

    @Benchmark
    public void MysqlInsert() {
        testMysqlTableDao.save(getTestMysqlTable());
    }

    @Benchmark
    public void ClickHouseInsert() {
        testCreateTableDao.create(getTestCreateEntity());
    }

//    @Benchmark
//    public void ClickHouseBatchInsertBy10() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(10));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy20() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(20));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy30() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(30));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy40() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(40));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy50() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(50));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy60() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(60));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy70() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(70));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy80() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(80));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy90() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(90));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy100() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(100));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy110() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(110));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy120() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(120));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy130() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(130));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy140() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(140));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy150() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(150));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy160() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(160));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy170() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(170));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy180() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(180));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy190() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(190));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy200() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(200));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy210() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(210));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy220() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(220));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy230() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(230));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy240() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(240));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy250() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(250));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy260() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(260));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy270() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(270));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy280() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(280));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy290() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(290));
//    }
//    @Benchmark
//    public void ClickHouseBatchInsertBy300() {
//        testCreateTableDao.batchCreate(getTestCreateEntitys(300));
//    }
//
//    @Benchmark
//    public void MysqlBatchInsertBy10() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(10));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy20() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(20));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy30() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(30));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy40() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(40));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy50() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(50));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy60() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(60));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy70() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(70));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy80() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(80));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy90() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(90));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy100() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(100));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy110() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(110));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy120() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(120));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy130() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(130));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy140() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(140));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy150() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(150));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy160() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(160));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy170() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(170));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy180() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(180));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy190() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(190));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy200() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(200));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy210() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(210));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy220() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(220));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy230() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(230));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy240() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(240));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy250() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(250));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy260() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(260));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy270() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(270));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy280() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(280));
//    }
//    @Benchmark
//    public void MysqlBatchInsertBy290() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(290));
//    }
//
//    @Benchmark
//    public void MysqlBatchInsertBy300() {
//        testMysqlTableDao.saveAll(getInsertTestMysqlTables(300));
//    }
//
//    @Benchmark
//    public void EsBatchInsertBy10() {
//        testESTableDao.saveAll(getTestESTables(10));
//    }
//    @Benchmark
//    public void EsBatchInsertBy20() {
//        testESTableDao.saveAll(getTestESTables(20));
//    }
//    @Benchmark
//    public void EsBatchInsertBy30() {
//        testESTableDao.saveAll(getTestESTables(30));
//    }
//    @Benchmark
//    public void EsBatchInsertBy40() {
//        testESTableDao.saveAll(getTestESTables(40));
//    }
//    @Benchmark
//    public void EsBatchInsertBy50() {
//        testESTableDao.saveAll(getTestESTables(50));
//    }
//    @Benchmark
//    public void EsBatchInsertBy60() {
//        testESTableDao.saveAll(getTestESTables(60));
//    }
//    @Benchmark
//    public void EsBatchInsertBy70() {
//        testESTableDao.saveAll(getTestESTables(70));
//    }
//    @Benchmark
//    public void EsBatchInsertBy80() {
//        testESTableDao.saveAll(getTestESTables(80));
//    }
//    @Benchmark
//    public void EsBatchInsertBy90() {
//        testESTableDao.saveAll(getTestESTables(90));
//    }
//    @Benchmark
//    public void EsBatchInsertBy100() {
//        testESTableDao.saveAll(getTestESTables(100));
//    }
//    @Benchmark
//    public void EsBatchInsertBy110() {
//        testESTableDao.saveAll(getTestESTables(110));
//    }
//    @Benchmark
//    public void EsBatchInsertBy120() {
//        testESTableDao.saveAll(getTestESTables(120));
//    }
//    @Benchmark
//    public void EsBatchInsertBy130() {
//        testESTableDao.saveAll(getTestESTables(130));
//    }
//    @Benchmark
//    public void EsBatchInsertBy140() {
//        testESTableDao.saveAll(getTestESTables(140));
//    }
//    @Benchmark
//    public void EsBatchInsertBy150() {
//        testESTableDao.saveAll(getTestESTables(150));
//    }
//    @Benchmark
//    public void EsBatchInsertBy160() {
//        testESTableDao.saveAll(getTestESTables(160));
//    }
//    @Benchmark
//    public void EsBatchInsertBy170() {
//        testESTableDao.saveAll(getTestESTables(170));
//    }
//    @Benchmark
//    public void EsBatchInsertBy180() {
//        testESTableDao.saveAll(getTestESTables(180));
//    }
//    @Benchmark
//    public void EsBatchInsertBy190() {
//        testESTableDao.saveAll(getTestESTables(190));
//    }
//    @Benchmark
//    public void EsBatchInsertBy200() {
//        testESTableDao.saveAll(getTestESTables(200));
//    }
//    @Benchmark
//    public void EsBatchInsertBy210() {
//        testESTableDao.saveAll(getTestESTables(210));
//    }
//    @Benchmark
//    public void EsBatchInsertBy220() {
//        testESTableDao.saveAll(getTestESTables(220));
//    }
//    @Benchmark
//    public void EsBatchInsertBy230() {
//        testESTableDao.saveAll(getTestESTables(230));
//    }
//    @Benchmark
//    public void EsBatchInsertBy240() {
//        testESTableDao.saveAll(getTestESTables(240));
//    }
//    @Benchmark
//    public void EsBatchInsertBy250() {
//        testESTableDao.saveAll(getTestESTables(250));
//    }
//    @Benchmark
//    public void EsBatchInsertBy260() {
//        testESTableDao.saveAll(getTestESTables(260));
//    }
//    @Benchmark
//    public void EsBatchInsertBy270() {
//        testESTableDao.saveAll(getTestESTables(270));
//    }
//    @Benchmark
//    public void EsBatchInsertBy280() {
//        testESTableDao.saveAll(getTestESTables(280));
//    }
//    @Benchmark
//    public void EsBatchInsertBy290() {
//        testESTableDao.saveAll(getTestESTables(290));
//    }
//    @Benchmark
//    public void EsBatchInsertBy300() {
//        testESTableDao.saveAll(getTestESTables(300));
//    }
}

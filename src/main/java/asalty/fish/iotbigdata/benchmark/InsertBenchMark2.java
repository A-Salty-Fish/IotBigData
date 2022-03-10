package asalty.fish.iotbigdata.benchmark;

import asalty.fish.clickhousejpa.CRUDStatementHandler.handler.InsertStatementHandler;
import asalty.fish.clickhousejpa.jdbc.ClickHouseJdbcConfig;
import asalty.fish.iotbigdata.IotBigDataApplication;
import asalty.fish.iotbigdata.demo.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.demo.dao.TestMysqlTableDao;
import asalty.fish.iotbigdata.demo.entity.TestCreateTable;
import asalty.fish.iotbigdata.demo.entity.TestMysqlTable;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2021/10/2 16:26
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class InsertBenchMark2 {

    private ConfigurableApplicationContext context;

    TestCreateTableDao testCreateTableDao;

    TestCreateTable testCreateTable;

    InsertStatementHandler insertStatementHandler;

    ClickHouseJdbcConfig clickHouseJdbcConfig;

    TestMysqlTableDao testMysqlTableDao;

    TestMysqlTable testMysqlTable;

    String sql = "INSERT INTO test_create_table( id, WatchID, JavaEnable, Title, GoodEvent, UserAgentMajor, URLDomain, CreateTime, CreateDay) FORMAT Values ( 343748357 ,  394323340 ,  1 ,  'title74953' ,  'goodEvent77516' ,  84297 ,  'testUserDefinedColumn3238' ,  '2022-03-03 19:14:14' ,  '2022-03-03' )";
    /**
     * setup初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)
    public void init() throws Exception {
        context = SpringApplication.run(IotBigDataApplication.class);
        testCreateTableDao = context.getBean(TestCreateTableDao.class);
        testCreateTable = getTestTimeEntity();
        insertStatementHandler = context.getBean(InsertStatementHandler.class);
        testMysqlTableDao = context.getBean(TestMysqlTableDao.class);
        testMysqlTable = getTestMysqlTable();
//        sql = insertStatementHandler.getStatement(TestCreateTableDao.class.getMethod("create", TestCreateTable.class), new Object[] {testCreateTable}, testCreateTableDao.getClass());
        clickHouseJdbcConfig = context.getBean(ClickHouseJdbcConfig.class);
    }

    @TearDown
    public void tearDown() {
        context.close();
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

    // 随机生成一个测试实体
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

    //  克隆一个测试实体
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

    @Benchmark
    public void insertByMysqlJPA(Blackhole blackhole) throws Exception {
        testMysqlTableDao.save(getInsertTestMysqlTable());
    }

    @Benchmark
    public void insertByClickHouseJPA(Blackhole blackhole) {
        testCreateTableDao.create(testCreateTable);
    }

//    @Benchmark
//    public void insertByRowSql(Blackhole blackhole) throws Exception {
//        clickHouseJdbcConfig.threadLocalStatement().executeQuery(sql);
//    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(InsertBenchMark2.class.getSimpleName())
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
}

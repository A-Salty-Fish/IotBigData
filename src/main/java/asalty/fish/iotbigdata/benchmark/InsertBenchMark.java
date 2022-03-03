package asalty.fish.iotbigdata.benchmark;

import asalty.fish.iotbigdata.IotBigDataApplication;
import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.entity.TestCreateTable;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2021/10/2 16:26
 */

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Threads(24)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class InsertBenchMark {

    private ConfigurableApplicationContext context;

    TestCreateTableDao testCreateTableDao;

    TestCreateTable testCreateTable;
    /**
     * setup初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)
    public void init(){
        context = SpringApplication.run(IotBigDataApplication.class);
        testCreateTableDao = context.getBean(TestCreateTableDao.class);
        testCreateTable = getTestTimeEntity();
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

    @Benchmark
    public void insert(Blackhole blackhole) {
        testCreateTableDao.create(testCreateTable);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(InsertBenchMark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
    }
}

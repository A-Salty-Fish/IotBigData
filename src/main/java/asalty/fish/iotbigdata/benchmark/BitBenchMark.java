package asalty.fish.iotbigdata.benchmark;

import asalty.fish.iotbigdata.IotBigDataApplication;
import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.dao.TestMysqlTableDao;
import asalty.fish.iotbigdata.entity.TestCreateTable;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/6 14:43
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BitBenchMark {

    private ConfigurableApplicationContext context;

    TestCreateTableDao testCreateTableDao;

    TestMysqlTableDao testMysqlTableDao;

    @Setup(Level.Trial)
    public void init() throws Exception {
        context = SpringApplication.run(IotBigDataApplication.class);
        testCreateTableDao = context.getBean(TestCreateTableDao.class);
        testMysqlTableDao = context.getBean(TestMysqlTableDao.class);
    }

    @TearDown
    public void tearDown() {
        context.close();
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(BitBenchMark.class.getSimpleName())
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

    ThreadLocalRandom random = ThreadLocalRandom.current();

    @Benchmark
    public void mysqlBitOperation(Blackhole blackhole) {
        blackhole.consume(testMysqlTableDao.maxWatchIDByBit(random.nextLong(), 20L));
    }

    @Benchmark
    public void clickHouseBitOperation(Blackhole blackhole) {
        blackhole.consume(testCreateTableDao.maxWatchIDByBit(random.nextLong(), 20L));
    }
}

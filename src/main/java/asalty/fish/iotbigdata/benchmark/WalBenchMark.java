package asalty.fish.iotbigdata.benchmark;

import asalty.fish.iotbigdata.IotBigDataApplication;
import asalty.fish.iotbigdata.demo.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.job.Timer;
import asalty.fish.iotbigdata.wal.WalService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/12 15:33
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class WalBenchMark {

    private ConfigurableApplicationContext context;

    private TestCreateTableDao testCreateTableDao;

    private WalService walService;

    private static int batchSize = 200;

    @Setup(Level.Trial)
    public void init() throws Exception {
        context = SpringApplication.run(IotBigDataApplication.class);
        testCreateTableDao = context.getBean(TestCreateTableDao.class);
        walService = context.getBean(WalService.class);
    }

    @Benchmark
    public void SingleInsertByDao() throws Exception {
        testCreateTableDao.create(BatchInsertBenchMark.getTestCreateEntity());
    }

    @Benchmark
    public void BatchInsertByDao() throws Exception {
        testCreateTableDao.batchCreate(BatchInsertBenchMark.getTestCreateEntitys(batchSize));
    }

    @Benchmark
    public void SingleInsertByWal() throws Exception {
        walService.insert(BatchInsertBenchMark.getTestCreateEntity());
    }

    @Benchmark
    public void BatchInsertByWal() throws Exception {
        walService.insert(BatchInsertBenchMark.getTestCreateEntitys(batchSize));
    }

    @TearDown(Level.Trial)
    public void destroy() {
        context.close();
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(WalBenchMark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result("jmh-wal.json")
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

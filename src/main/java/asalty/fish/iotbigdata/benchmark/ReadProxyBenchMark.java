package asalty.fish.iotbigdata.benchmark;

import asalty.fish.iotbigdata.IotBigDataApplication;
import asalty.fish.iotbigdata.demo.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.demo.service.TestCreateTableService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/4/9 16:08
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class ReadProxyBenchMark {

    private ConfigurableApplicationContext context;

    TestCreateTableDao testCreateTableDao;

    TestCreateTableService testCreateTableService;

    @Setup(Level.Trial)
    public void init() throws Exception {
        context = SpringApplication.run(IotBigDataApplication.class);
        testCreateTableDao = context.getBean(TestCreateTableDao.class);
        testCreateTableService = context.getBean(TestCreateTableService.class);
    }

    @TearDown(Level.Trial)
    public void destroy() {
        context.close();
    }

    @Benchmark
    public void readAvgWithoutProxy(Blackhole blackhole) {
        blackhole.consume(testCreateTableDao.avgWatchID());
    }

    @Benchmark
    public void readAvgWithProxy(Blackhole blackhole) {
        blackhole.consume(testCreateTableService.avgValue1());
    }

    @Benchmark
    public void readMaxWithoutProxy(Blackhole blackhole) {
        blackhole.consume(testCreateTableDao.maxWatchID());
    }

    @Benchmark
    public void readMaxWithProxy(Blackhole blackhole) {
        blackhole.consume(testCreateTableService.maxValue1());
    }

    @Benchmark
    public void readAllWithoutProxy(Blackhole blackhole) {
        blackhole.consume(testCreateTableDao.maxWatchIDByBit(1L, 1L));
    }

    @Benchmark
    public void readAllWithProxy(Blackhole blackhole) {
        blackhole.consume(testCreateTableService.maxValueByBit(1L, 1L));
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(ReadProxyBenchMark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result("jmh-read-proxy-benchmark.json")
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

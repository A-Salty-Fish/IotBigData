package asalty.fish.iotbigdata.benchmark;

import asalty.fish.iotbigdata.IotBigDataApplication;
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

    Map<String, String> map;

    Timer timer;

    @Setup(Level.Trial)
    public void init() throws Exception {
        context = SpringApplication.run(IotBigDataApplication.class);
        timer = context.getBean(Timer.class);
        map = new ConcurrentHashMap<>();
    }

    @TearDown
    public void tearDown() throws Exception {
        context.stop();
        timer.destroy();
    }

    ThreadLocalRandom random = ThreadLocalRandom.current();

    @Benchmark
    public void testHashMap() {map.put("test" + random.nextInt(1000), "test");}

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

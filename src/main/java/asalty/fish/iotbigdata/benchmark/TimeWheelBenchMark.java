package asalty.fish.iotbigdata.benchmark;

import asalty.fish.iotbigdata.IotBigDataApplication;
import asalty.fish.iotbigdata.demo.entity.TestCreateTable;
import asalty.fish.iotbigdata.job.Timer;
import asalty.fish.iotbigdata.job.TimerTask;
import asalty.fish.iotbigdata.job.TimerTaskList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/11 10:06
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class TimeWheelBenchMark {

    private ConfigurableApplicationContext context;

    private Timer timer;

    ThreadLocalRandom random = ThreadLocalRandom.current();

    Thread bossThread;

    ThreadPoolTaskExecutor workerThreadPool;

    DelayQueue<DelayedJob> delayQueue;

    public class DelayedJob implements Delayed {

        public Runnable runnable;

        public DelayedJob(long delay, Runnable runnable) {
            this.expiration.set(delay);
            this.runnable = runnable;
        }

        private AtomicLong expiration = new AtomicLong(-1L);

        @Override
        public long getDelay(TimeUnit unit) {
            return expiration.get();
        }

        /**
         * 设置过期时间
         */
        public boolean setExpiration(long expire) {
            return expiration.getAndSet(expire) != expire;
        }

        /**
         * 获取过期时间
         */
        public long getExpiration() {
            return expiration.get();
        }

        @Override
        public int compareTo(Delayed o) {
            if (o instanceof DelayedJob) {
                return Long.compare(expiration.get(), ((DelayedJob) o).expiration.get());
            }
            return 0;
        }
    }

    @Setup(Level.Trial)
    public void init() throws Exception {
        context = SpringApplication.run(IotBigDataApplication.class);
        timer = context.getBean(Timer.class);
        delayQueue = new DelayQueue();
        workerThreadPool = context.getBean("workerThreadPool", ThreadPoolTaskExecutor.class);
        bossThread = new Thread(() -> {
            while (true) {
                try {
                    workerThreadPool.submit(delayQueue.take().runnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @TearDown
    public void tearDown() {
        bossThread.interrupt();
        workerThreadPool.shutdown();
        context.stop();
    }

    @Benchmark
    public void addJobTimeWheel() throws Exception {
        timer.addTask(new TimerTask(random.nextInt(), () -> {}));
    }

    @Benchmark
    public void addJobDelayQueue() throws Exception {
        delayQueue.put(new DelayedJob(random.nextInt(), () -> {}));
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(TimeWheelBenchMark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result("jmh-time-wheel.json")
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

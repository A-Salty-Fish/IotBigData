package asalty.fish.iotbigdata.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 定时器
 */
@Service
@Slf4j
public class Timer implements DisposableBean {

    /**
     * 一个Timer只有一个delayQueue
     */
    private DelayQueue<TimerTaskList> delayQueue;

    /**
     * 底层时间轮
     */
    private TimeWheel timeWheel;

    /**
     * 过期任务执行线程
     */
    @Resource
    private ThreadPoolTaskExecutor workerThreadPool;

    /**
     * 轮询delayQueue获取过期任务线程
     */
    @Resource
    private ThreadPoolTaskExecutor bossThreadPool;

    // 时间轮单位 ms
    @Value("${job.timeWheel.tick: 100}")
    long tick;

    // 时间轮大小
    @Value("${job.timeWheel.size: 60}")
    int wheelSize;
    /**
     * 初始化时间轮
     */
    @PostConstruct
    public void init() {
        this.delayQueue = new DelayQueue<>();
        this.timeWheel = new TimeWheel(tick, wheelSize, System.currentTimeMillis(), delayQueue);
        this.bossThreadPool.submit(() -> {
            while (true) {
                this.advanceClock(tick * wheelSize);
            }
        });
    }

    /**
     * 添加任务
     */
    public void addTask(TimerTask timerTask) {
        //添加失败任务直接执行
        if (! timeWheel.addTask(timerTask)) {
            workerThreadPool.submit(timerTask.getTask());
        }
    }

    /**
     * 获取过期任务
     */
    private void advanceClock(long timeout) {
        try {
            TimerTaskList timerTaskList = delayQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (timerTaskList != null) {
                //推进时间
                timeWheel.advanceClock(timerTaskList.getExpiration());
                //执行过期任务（包含降级操作）
                timerTaskList.flush(this::addTask);
            }
        } catch (InterruptedException e) {
            log.info("Job Timer ThreadPool Interrupted at time: " + new Date());
        }
    }

    @Override
    public void destroy() throws Exception {
        this.bossThreadPool.shutdown();
        this.workerThreadPool.shutdown();
        log.info("Job Timer ThreadPool shutdown at time: " + new Date());
    }
}

package asalty.fish.iotbigdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 13090
 * @version 1.0
 * @description: 用于配置任务调度线程池
 * @date 2022/3/10 18:16
 */
@Configuration
@EnableAsync
public class JobExecutorConfig {
    @Value("${job.executor.corePoolSize: 4}")
    private int corePoolSize;
    @Value("${job.executor.maxPoolSize: 6}")
    private int maxPoolSize;
    @Value("${job.executor.queueCapacity: 100}")
    private int queueCapacity;
    @Value("${job.executor.keepAliveSeconds: 60}")
    private int keepAliveSeconds;
    @Value("${job.executor.threadNamePrefix: ASaltyFish-WorkerExecutor-}")
    private String workerExecutorPrefix;
    @Value("${job.executor.bossExecutorPrefix: ASaltyFish-BossExecutor-}")
    private String bossExecutorPrefix;

    @Bean("workerThreadPool")
    public ThreadPoolTaskExecutor getWorkerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(workerExecutorPrefix);

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();

        return executor;
    }

    @Bean("bossThreadPool")
    public ThreadPoolTaskExecutor getBossExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(1);
        executor.setThreadNamePrefix(bossExecutorPrefix);

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        return executor;
    }
}

package asalty.fish.iotbigdata.wal;

import asalty.fish.clickhousejpa.annotation.ClickHouseRepository;
import asalty.fish.iotbigdata.job.Timer;
import asalty.fish.iotbigdata.job.TimerTask;
import asalty.fish.iotbigdata.util.ClassScanUtil;
import asalty.fish.iotbigdata.util.ThreadLocalGson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/11 14:35
 */
@Service
@Slf4j
public class WalService {

    @Resource
    Timer timer;

    @Resource
    ThreadPoolTaskExecutor workerThreadPool;

    @Value("${wal.enabled: true}")
    private boolean walEnabled;

    @Value("${wal.flushIntervalMs: 1000}")
    private int flushIntervalMs;

    @Value("${wal.flushSize: 200}")
    private int flushSize;

    HashMap<Class<?>, Method> batchCreateMethodMap;

    ConcurrentHashMap<Class<?>, ConcurrentLinkedQueue<Object>> batchCreateMap;

    HashMap<Class<?>, Object> daoMap;

    public void flush(Class<?> clazz, ConcurrentLinkedQueue<Object> queue) {
        Method batchCreateMethod = batchCreateMethodMap.get(clazz);
        if (batchCreateMethod != null) {
            Object dao = daoMap.get(clazz);
            if (queue.size() > flushSize) {
                int size = queue.size();
                for (int i = 0; i < size / flushSize; i++) {
                    List<Object> list = new ArrayList<>(flushSize);
                    for (int j = 0; j < flushSize; j++) {
                        list.add(queue.poll());
                    }
                    workerThreadPool.submit(() -> {
                        try {
                            batchCreateMethod.invoke(dao, list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    public void flushAll() {
        for (Class<?> clazz : batchCreateMap.keySet()) {
            workerThreadPool.submit(() -> flush(clazz, batchCreateMap.get(clazz)));
        }
        timer.addTask(new TimerTask(flushIntervalMs, this::flushAll));
    }

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        if (walEnabled) {
            // get springboot main class
            String[] springBootAppBeanName = context.getBeanNamesForAnnotation(org.springframework.boot.autoconfigure.SpringBootApplication.class);
            Object springbootApplication = context.getBean(springBootAppBeanName[0]);
            // get clickhouse jpa repository
            List<Class<?>> daos = ClassScanUtil.getClassByAnnotation(springbootApplication.getClass().getPackage().getName(), ClickHouseRepository.class);
            batchCreateMethodMap = new HashMap<>(daos.size());
            batchCreateMap = new ConcurrentHashMap<>(daos.size());
            daoMap = new HashMap<>(daos.size());
            // get the batch create method and init the map
            for (Class<?> dao : daos) {
                ClickHouseRepository clickHouseRepository = dao.getAnnotation(ClickHouseRepository.class);
                batchCreateMap.put(clickHouseRepository.entity(), new ConcurrentLinkedQueue<>());
                daoMap.put(clickHouseRepository.entity(), context.getBean(dao));
                try {
                    batchCreateMethodMap.put(clickHouseRepository.entity(), dao.getMethod("batchCreate", List.class));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

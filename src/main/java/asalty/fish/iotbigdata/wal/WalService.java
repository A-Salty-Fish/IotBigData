package asalty.fish.iotbigdata.wal;

import asalty.fish.clickhousejpa.annotation.ClickHouseRepository;
import asalty.fish.iotbigdata.job.Timer;
import asalty.fish.iotbigdata.job.TimerTask;
import asalty.fish.iotbigdata.util.ClassScanUtil;
import asalty.fish.iotbigdata.util.ThreadLocalGson;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

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

    @Value("${wal.enabled: true}")
    private boolean walEnabled;

    @Value("${wal.flushIntervalMs: 1000}")
    private int flushIntervalMs;

    @Value("${wal.flushSize: 200}")
    private int flushSize;

    ConcurrentMap<Long, String> dbMap;

    HashMap<Class<?>, Method> batchCreateMethodMap;

    ConcurrentHashMap<Class<?>, ConcurrentLinkedQueue<Object>> batchCreateMap;

    public <T> void flush(Class<T> clazz, List<T> list) {
        // todo
    }

    public void flushAll() {
        log.info("flushAll");
        timer.addTask(new TimerTask(flushIntervalMs, this::flushAll));
    }

    public <T> List<T> getAll(Class<T> clazz, String value) {
        if (value == null) {
            return null;
        } else {
            String[] values = value.split("\\?");
            List<T> result = new ArrayList<>(values.length);
            for (String v : values) {
                result.add(ThreadLocalGson.threadLocalGson.get().fromJson(v, clazz));
            }
            return result;
        }
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
            // get the batch create method and init the map
            for (Class<?> dao : daos) {
                ClickHouseRepository clickHouseRepository = dao.getAnnotation(ClickHouseRepository.class);
                batchCreateMap.put(clickHouseRepository.entity(), new ConcurrentLinkedQueue<>());
                try {
                    batchCreateMethodMap.put(clickHouseRepository.entity(), dao.getMethod("batchCreate", List.class));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

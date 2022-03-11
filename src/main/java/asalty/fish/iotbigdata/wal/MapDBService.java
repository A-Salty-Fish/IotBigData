package asalty.fish.iotbigdata.wal;

import asalty.fish.iotbigdata.job.Timer;
import asalty.fish.iotbigdata.job.TimerTask;
import asalty.fish.iotbigdata.util.ThreadLocalGson;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/11 14:35
 */
@Service
@Slf4j
public class MapDBService {

    @Resource
    Timer timer;

    @Value("${wal.enabled: true}")
    private boolean walEnabled;

    @Value("${wal.flushIntervalMs: 1000}")
    private int flushIntervalMs;

    @Value("${wal.flushSize: 200}")
    private int flushSize;

    DB db;

    ConcurrentNavigableMap<String, String> dbMap;

    ConcurrentHashMap<String, AtomicLong> dbMapCount;

    public void put(Object o) {
        String value = dbMap.get(o.getClass().getName());
        dbMap.computeIfPresent(o.getClass().getName(), (k, v) -> v.equals("") ? ThreadLocalGson.threadLocalGson.get().toJson(o) : v + "?" + ThreadLocalGson.threadLocalGson.get().toJson(o));
        dbMap.computeIfAbsent(o.getClass().getName(), k -> ThreadLocalGson.threadLocalGson.get().toJson(o));
        db.commit();
    }

    public String get(Class<?> clazz) {
        return dbMap.get(clazz.getName());
    }

    public <T> void flushAndRemove(Class<T> clazz) {
        String value = dbMap.get(clazz.getName());
        if (value != null){
            List<T> list = getAll(clazz, value);
            try {
                flush(clazz, list);
                dbMap.computeIfPresent(clazz.getName(), (k, v) -> v.substring(value.length()));
                db.commit();
            } catch (Exception e) {
                log.error("flushAndRemove error");
                e.printStackTrace();
            }
        }
    }

    public <T> void flush(Class<T> clazz, List<T> list) {
        // todo
    }

    public void flushAll() {
        log.info("flushAll");
        timer.addTask(new TimerTask(flushIntervalMs, this::flushAll));
    }

    public <T> List<T> getAll(Class<T> clazz, String value) {
        if (value == null){
            return null;
        } else {
            String[] values = value.split("\\?");
            List<T> result = new ArrayList<>(values.length);
            for (String v : values){
                result.add(ThreadLocalGson.threadLocalGson.get().fromJson(v, clazz));
            }
            return result;
        }
    }

    @PostConstruct
    public void init(){
        if (walEnabled){
            try {
                db = DBMaker
                        .fileDB("wal.db")
                        .transactionEnable()
                        .closeOnJvmShutdown()
                        .make();
                dbMap = db.treeMap("wal", Serializer.STRING, Serializer.STRING)
                        .createOrOpen();
                log.info("MapDBService init success");
                timer.addTask(new TimerTask(flushIntervalMs, this::flushAll));
                log.info("Flush Task Init");
            } catch (Exception e) {
                log.error("init wal error", e);
            }
        }
    }
}

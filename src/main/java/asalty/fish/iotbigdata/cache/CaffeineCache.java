package asalty.fish.iotbigdata.cache;

import asalty.fish.iotbigdata.util.ThreadLocalGson;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/10 15:07
 */
@Service
@Slf4j
public class CaffeineCache implements IotCache {

    private Cache<String, String> cache;

    // 过期时间，单位秒，默认是1小时
    @Value("${cache.caffeine.expires: 3600}")
    Long expires;

    // 最大缓存数，默认是10000
    @Value("${cache.caffeine.maximumSize: 10000}")
    Long maximumSize;

    @Override
    public <T> T getByString(String key, Class<T> clazz) throws IotCacheException {
        String value = cache.getIfPresent(key);
        if (value == null) {
            throw new IotCacheException("key:" + key + " not exists");
        }
        log.info("get value from caffeine, key:{}, value:{}", key, value);
        return ThreadLocalGson.threadLocalGson.get().fromJson(value, clazz);
    }

    @Override
    public <T> T putByString(String key, T value) throws IotCacheException {
        if (key == null) {
            throw new IotCacheException("key is null");
        }
        if (value == null) {
            throw new IotCacheException("value is null");
        }
        String valueJson = ThreadLocalGson.threadLocalGson.get().toJson(value);
        cache.put(key, valueJson);
        log.info("put value to caffeine, key:{}, value:{}", key, valueJson);
        return value;
    }

    @Override
    public void removeByString(String key) {
        cache.put(key, null);
    }

    @Override
    @PostConstruct
    public void init() {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(expires, TimeUnit.SECONDS)
                .maximumSize(maximumSize)
                .build();
    }

}

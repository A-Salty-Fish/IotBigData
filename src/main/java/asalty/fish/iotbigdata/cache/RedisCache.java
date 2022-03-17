package asalty.fish.iotbigdata.cache;

import asalty.fish.iotbigdata.util.ThreadLocalGson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/10 15:07
 */
@Service
@Slf4j
public class RedisCache implements IotCache{

    String prefix = "cache:";

    // 过期时间，单位秒，默认一小时
    @Value("${cache.redis.expires: 3600}")
    Long expireTime;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Override
    public <T> T getByString(String key, Class<T> clazz) throws IotCacheException {
        String value = redisTemplate.opsForValue().get(prefix + key);
        if (value == null) {
            return null;
        }
        log.info("get value from redis, key:{}, value:{}", key, value);
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
        redisTemplate.opsForValue().set(prefix + key, ThreadLocalGson.threadLocalGson.get().toJson(value), expireTime, TimeUnit.SECONDS);
        log.info("put value to redis, key:{}, value:{}", key, ThreadLocalGson.threadLocalGson.get().toJson(value));
        return value;
    }

    @Override
    public void removeByString(String key) {
        redisTemplate.delete(prefix + key);
    }

    @Override
    public void init() {
    }
}

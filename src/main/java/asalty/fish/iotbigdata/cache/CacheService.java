package asalty.fish.iotbigdata.cache;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 13090
 * @version 1.0
 * @description: cache service impl
 * @date 2022/3/17 14:51
 */
@Service
public class CacheService implements IotCache {

    @Resource
    CaffeineCache caffeineCache;

    @Resource
    RedisCache redisCache;

    @Override
    public <T> T getByString(String key, Class<T> clazz) throws IotCacheException {
        T caffeineResult = caffeineCache.getByString(key, clazz);
        if (caffeineResult != null) {
            return caffeineResult;
        }
        T redisResult = redisCache.getByString(key, clazz);
        if (redisResult != null) {
            caffeineCache.putByString(key, redisResult);
            return redisResult;
        }
        return null;
    }

    @Override
    public <T> T putByString(String key, T value) throws IotCacheException {
        redisCache.putByString(key, value);
        return caffeineCache.putByString(key, value);
    }

    @Override
    public void removeByString(String key) {
        caffeineCache.removeByString(key);
        redisCache.removeByString(key);
    }

    @Override
    public void init() {

    }
}

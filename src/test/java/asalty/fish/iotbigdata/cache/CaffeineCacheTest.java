package asalty.fish.iotbigdata.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/10 15:53
 */
@SpringBootTest
public class CaffeineCacheTest {

    @Resource
    CaffeineCache caffeineCache;


    @Test
    public void testPutCache() throws Exception {
        String key = "key";
        String value = "value";
        caffeineCache.putByString(key, value);
        Assertions.assertEquals(caffeineCache.getByString(key, String.class), value);
        Assertions.assertThrows(IotCacheException.class, () -> {
            caffeineCache.getByString(key + key, String.class);
        });
    }
}

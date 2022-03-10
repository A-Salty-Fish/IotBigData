package asalty.fish.iotbigdata.cache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/10 15:22
 */
@SpringBootTest
public class RedisCacheTest {

    @Resource
    RedisCache redisCache;

    @Test
    public void testPutCache() throws Exception {
        System.out.println(redisCache.putByString("1", "1"));
    }

    @Test
    public void testGetCache() throws Exception {
        System.out.println(redisCache.getByString("1", String.class));
    }

    @Test
    public void testRemoveCache() throws Exception {
        redisCache.removeByString("1");
        System.out.println(redisCache.getByString("1", String.class));
    }
}

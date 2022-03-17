package asalty.fish.iotbigdata.proxy.read;

import asalty.fish.iotbigdata.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 16:29
 */
@Service
@Slf4j
public class ReadEntityProxy {

    @Resource
    CacheService cacheService;

    public Object handleNormalRead(ProceedingJoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().getName() + ":" + Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(":"));
        Object result = cacheService.getByString(key, ((MethodSignature) joinPoint.getSignature()).getReturnType());
        if (result == null) {
            log.info("cache miss, key: {}", key);
            result = joinPoint.proceed();
            cacheService.putByString(key, result);
        }
        return result;
    }

}

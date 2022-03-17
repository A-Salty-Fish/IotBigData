package asalty.fish.iotbigdata.proxy.read;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/17 14:42
 */
@Component
@Aspect
@Slf4j
public class ReadProxyAop {

    @Pointcut("@annotation(asalty.fish.iotbigdata.proxy.read.ReadProxy)")
    private void pointCut() {
    }

    @Resource
    private ReadEntityProxy readEntityProxy;

    @Around("pointCut()")
    public Object readProxy(ProceedingJoinPoint joinPoint) throws Throwable {
        return readEntityProxy.handleNormalRead(joinPoint);
    }

}

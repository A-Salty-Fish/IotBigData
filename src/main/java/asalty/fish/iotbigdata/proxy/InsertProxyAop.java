package asalty.fish.iotbigdata.proxy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 16:43
 */
@Component
@Aspect
@Slf4j
public class InsertProxyAop {

    @Pointcut("@annotation(asalty.fish.iotbigdata.proxy.InsertProxy)")
    private void pointCut() {
    }


    @Around("pointCut()")
    public Object insertProxy(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("insertProxy");
        return joinPoint.proceed();
    }

}

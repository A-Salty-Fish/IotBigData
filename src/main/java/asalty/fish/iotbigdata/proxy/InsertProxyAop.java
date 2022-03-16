package asalty.fish.iotbigdata.proxy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

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
//        log.info(joinPoint.getArgs()[0].getClass().toString());
        if (joinPoint.getArgs()[0] instanceof List) {
            log.info("batch");
            // handle list insert
        } else {
            log.info("single");
            // handle single insert
        }
//        log.info("insertProxy");
        return null;
    }

}

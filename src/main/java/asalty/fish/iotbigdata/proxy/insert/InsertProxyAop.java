package asalty.fish.iotbigdata.proxy.insert;

import asalty.fish.iotbigdata.wal.WalService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Pointcut("@annotation(asalty.fish.iotbigdata.proxy.insert.InsertProxy)")
    private void pointCut() {
    }

    @Resource
    WalService walService;

    @Around("pointCut()")
    public Object insertProxy(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info(joinPoint.getArgs()[0].getClass().toString());
        if (joinPoint.getArgs()[0] instanceof List) {
            // handle list insert
            List<?> list = (List<?>) joinPoint.getArgs()[0];
            walService.insert(list);
        } else {
            // handle single insert
            Object object = joinPoint.getArgs()[0];
            walService.insert(object);
        }
//        log.info("insertProxy");
        return null;
    }

}

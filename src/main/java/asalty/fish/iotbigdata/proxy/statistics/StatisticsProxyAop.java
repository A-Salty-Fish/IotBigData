package asalty.fish.iotbigdata.proxy.statistics;

import asalty.fish.iotbigdata.proxy.statistics.annotation.BeginDate;
import asalty.fish.iotbigdata.proxy.statistics.annotation.DeviceId;
import asalty.fish.iotbigdata.proxy.statistics.annotation.EndDate;
import asalty.fish.iotbigdata.proxy.statistics.annotation.GeoPoint;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/17 15:58
 */
@Component
@Aspect
@Slf4j
public class StatisticsProxyAop {

//    ConcurrentHashMap<Method, Class<?>> esDocClassMap = new ConcurrentHashMap<>();

    ConcurrentHashMap<Class<?>, ConcurrentLinkedQueue<Long>> esDocIdsMap = new ConcurrentHashMap<>();

    @Pointcut("@annotation(asalty.fish.iotbigdata.proxy.statistics.StatisticsProxy)")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object statisticsProxy(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        StatisticsProxy statisticsProxy = method.getAnnotation(StatisticsProxy.class);
        StatisticsProxy.ResultType resultType = statisticsProxy.resultType();
        if (resultType == StatisticsProxy.ResultType.PLAIN) {
            return joinPoint.proceed();
        }
        Class<?> esDocClass = statisticsProxy.esDocClass();
        // check if the arg annotation is legal
        List<Object> deviceId = new ArrayList<>(joinPoint.getArgs().length);
        List<Object> beginDate = new ArrayList<>(joinPoint.getArgs().length);
        List<Object> endDate = new ArrayList<>(joinPoint.getArgs().length);
        List<Object> geoPoint = new ArrayList<>(joinPoint.getArgs().length);
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < method.getParameters().length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof DeviceId) {
                    deviceId.add(joinPoint.getArgs()[i]);
                } else if (annotation instanceof BeginDate) {
                    beginDate.add(joinPoint.getArgs()[i]);
                } else if (annotation instanceof EndDate) {
                    endDate.add(joinPoint.getArgs()[i]);
                } else if (annotation instanceof GeoPoint) {
                    geoPoint.add(joinPoint.getArgs()[i]);
                }
            }
        }
        if (deviceId.size() > 1 || beginDate.size() > 1 || endDate.size() > 1 || geoPoint.size() > 1) {
            throw new StatisticsAnnotationException("illegal statistics parameter annotation number");
        }
        if (deviceId.size() == 0 && beginDate.size() == 0 && endDate.size() == 0 && geoPoint.size() == 0) {
            throw new StatisticsAnnotationException("no statistics parameter annotation exists");
        }
        // try get prepared result from es
        Object esResult = tryGetResultFromEs(
                esDocClass,
                deviceId.size() == 1 ? deviceId.get(0) : null,
                beginDate.size() == 1 ? beginDate.get(0) : null,
                endDate.size() == 1 ? endDate.get(0) : null,
                geoPoint.size() == 1 ? geoPoint.get(0) : null,
                resultType
        );
        if (esResult == null) {
            esResult = joinPoint.proceed();
        }
        return esResult;
    }

    public Object tryGetResultFromEs(Class<?> esDocClass, Object deviceId, Object beginDate, Object endDate, Object geoPoint, StatisticsProxy.ResultType resultType) {
        if (deviceId == null && beginDate == null && endDate == null && geoPoint == null) {
            return doGetResultFromEsWithoutConstrain(esDocClass, resultType);
        }
        if (beginDate == null && endDate == null && geoPoint == null) {
            return doGetResultFromEsWithDeviceId(esDocClass, deviceId, resultType);
        }
        if (deviceId == null && beginDate == null && endDate == null) {
            return doGetResultFromEsWithGeo(esDocClass, geoPoint, resultType);
        }
        if (deviceId == null && geoPoint == null) {
            return doGetResultFromEsWithBeginAndEndDate(esDocClass, beginDate, endDate, resultType);
        }
        return null;
    }

    public Object doGetResultFromEsWithoutConstrain(Class<?> esDocClass, StatisticsProxy.ResultType resultType) {
        return null;
    }

    public Object doGetResultFromEsWithDeviceId(Class<?> esDocClass, Object deviceId, StatisticsProxy.ResultType resultType) {
        return null;
    }

    public Object doGetResultFromEsWithBeginAndEndDate(Class<?> esDocClass, Object beginDate, Object endDate, StatisticsProxy.ResultType resultType) {
        return null;
    }

    public Object doGetResultFromEsWithGeo(Class<?> esDocClass, Object geoPoint, StatisticsProxy.ResultType resultType) {
        return null;
    }

}

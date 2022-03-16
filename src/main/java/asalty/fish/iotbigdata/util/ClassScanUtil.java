package asalty.fish.iotbigdata.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 19:13
 */
@Slf4j
public class ClassScanUtil {
    public static List<Class<?>> getClassByAnnotation(String springBootPackage, Class<? extends Annotation> annotation) {
        List<Class<?>> clazzs = new ArrayList<>();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(springBootPackage) + "/**/*.class";
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);
                //判断是否有指定主解
                Annotation anno = clazz.getAnnotation(annotation);
                if (anno != null) {
                    clazzs.add(clazz);
                    log.info("scan {}: {}", annotation.getSimpleName(), classname);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("scan class fail", e);
        }
        return clazzs;
    }
}

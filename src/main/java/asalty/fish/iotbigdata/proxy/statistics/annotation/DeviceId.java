package asalty.fish.iotbigdata.proxy.statistics.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DeviceId {
}
